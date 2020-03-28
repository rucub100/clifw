package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Schema;
import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Opt;
import de.curbanov.clifw.option.Option;

import java.util.*;

class ContextAnalyzer {

    public static Result semanticAnalysis(
            Tree<Token> tree,
            Schema schema,
            Collection<Opt> opts,
            List<Arg> args,
            Collection<Cmd> cmds) {
        switch (schema) {
            case OPTIONS:
            case ARGUMENTS:
            case OPTIONS_ARGUMENTS:
                List<Option> options = new ArrayList<>(tree.getChildrenFrom(tree.getRoot()).size());
                List<Argument<?>> arguments = new ArrayList<>(tree.getChildrenFrom(tree.getRoot()).size());
                int argsCnt = 0;

                for (Tree.Node<Token> node : tree.getChildrenFrom(tree.getRoot())) {
                    if (!opts.isEmpty() && tree.getDataFrom(node).getName() == LexicalCategory.OPERATOR) {
                        Option o = analyzeOption(tree, opts, node);
                        options.add(o);
                    } else if (!args.isEmpty() && tree.getDataFrom(node).getName() == LexicalCategory.LITERAL) {
                        Arg arg = args.get(argsCnt++);
                        String strValue = tree.getDataFrom(node).getValue();
                        arguments.add(parseArg(arg, strValue));
                    }
                }

                if (!checkRequiredOpts(opts, options)) {
                    throw new ParsingException(Phase.SEMANTIC_ANALYSIS, "Required options are missing!");
                }

                if (args.size() > argsCnt) {
                    throw new ParsingException(Phase.SEMANTIC_ANALYSIS, "Required arguments are missing!");
                }

                return new Result(options, arguments);
            case COMMANDS:
                Cmd cmd = cmds.stream()
                        .filter(x -> x.getName().equals(
                                tree.getDataFrom(
                                        tree.getChildrenFrom(tree.getRoot()).get(0)).getValue()))
                        .findAny()
                        .get();
                Tree<Token> subTree = tree.getSubTree(tree.getChildrenFrom(tree.getRoot()).get(0));
                Result nestedResult = semanticAnalysis(
                        subTree,
                        Schema.OPTIONS_ARGUMENTS,
                        cmd.getOpts(),
                        cmd.getArgs(),
                        null);
                Command command = new Command(cmd, nestedResult.getOptions(), nestedResult.getArguments());
                return new Result(command);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static Option analyzeOption(Tree<Token> tree, Collection<Opt> opts, Tree.Node<Token> node) {
        Token t = tree.getDataFrom(node);
        boolean shortOpt = t.getValue().length() < 2;
        Tree.Node<Token> id = tree.getChildrenFrom(node).get(0);

        Optional<Opt> option = opts
                .stream()
                .filter(o -> shortOpt ? o.hasShortName() : o.hasLongName())
                .filter(o -> tree.getDataFrom(id).getValue().contentEquals(
                        shortOpt ? o.getShortName() : o.getLongName()))
                .findAny();

        if (!option.isPresent()) {
            throw new IllegalArgumentException();
        }

        List<Tree.Node<Token>> optArgs = tree.getChildrenFrom(id);
        List<String> strArgs = null;

        // check args
        if (option.get().hasArgs() == optArgs.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (option.get().hasArgs() && option.get().getArgsCount() != optArgs.size()) {
            throw new IllegalArgumentException();
        }


        if (!optArgs.isEmpty()) {
            strArgs = new ArrayList<>(optArgs.size());
            for (int i = 0; i < optArgs.size(); i++) {
                String strArg = tree.getDataFrom(optArgs.get(i)).getValue();

                // check type
                Class clazz = option.get().getArgType(i);
                if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                    if (!strArg.equalsIgnoreCase("true") &&
                            !strArg.equalsIgnoreCase("false")) {
                        throw new IllegalArgumentException();
                    }
                } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
                    Byte.parseByte(strArg);
                } else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
                    if (strArg.length() != 1) {
                        throw new IllegalArgumentException();
                    }
                } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                    Double.parseDouble(strArg);
                } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                    Float.parseFloat(strArg);
                } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                    Integer.parseInt(strArg);
                } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
                    Long.parseLong(strArg);
                } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
                    Short.parseShort(strArg);
                }

                strArgs.add(strArg);
            }
        }

        return new Option(option.get(), strArgs);
    }

    private static Argument parseArg(Arg arg, String strArg) {
        Argument a;
        Class clazz = arg.getClazz();

        if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            a = new Argument<>(arg, Boolean.parseBoolean(strArg));
        } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            a = new Argument<>(arg, Byte.parseByte(strArg));
        } else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
            if (strArg == null || strArg.isEmpty() || strArg.length() != 1) {
                throw new ParsingException(Phase.SEMANTIC_ANALYSIS);
            }

            a = new Argument<>(arg, strArg.charAt(0));
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            a = new Argument<>(arg, Double.parseDouble(strArg));
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            a = new Argument<>(arg, Float.parseFloat(strArg));
        } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            a = new Argument<>(arg, Integer.parseInt(strArg));
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            a = new Argument<>(arg, Long.parseLong(strArg));
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            a = new Argument<>(arg, Short.parseShort(strArg));
        } else {
            a = new Argument<>(arg, strArg);
        }

        return a;
    }

    private static boolean checkRequiredOpts(Collection<Opt> opts, List<Option> options) {
        return opts.stream().filter(o -> o.isRequired()).allMatch(o -> {
            boolean requiredOptIsPresent = false;

            if (o.hasShortName()) {
                requiredOptIsPresent = options
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getShortName()));
            } else if (o.hasLongName()) {
                requiredOptIsPresent = options
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getLongName()));
            }

            return requiredOptIsPresent;
        });
    }
}
