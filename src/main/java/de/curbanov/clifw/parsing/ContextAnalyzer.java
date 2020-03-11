package de.curbanov.clifw.parsing;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.option.Opt;
import de.curbanov.clifw.option.Option;

import java.util.*;

class ContextAnalyzer {

    public static Result semanticAnalysis(ArgsTree tree, Collection<Opt> opts, List<Arg> args) {
        List<Option> options = new ArrayList<>(tree.getChildrenFrom(tree.getRoot()).size());
        List<Argument<?>> arguments = new ArrayList<>(tree.getChildrenFrom(tree.getRoot()).size());
        int argsCnt = 0;

        for (Tree.Node<Token> node : tree.getChildrenFrom(tree.getRoot())) {
            if (!opts.isEmpty() && tree.getDataFrom(node).getName() == LexicalCategory.OPERATOR) {
                Option o = analyzeOption(tree, opts, node);
                options.add(o);
            } else if (!args.isEmpty() && tree.getDataFrom(node).getName() == LexicalCategory.LITERAL) {
                Arg arg = args.get(argsCnt++);
                Argument a;
                Class clazz = arg.getClazz();
                String strValue = tree.getDataFrom(node).getValue();

                if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                    a = new Argument<>(arg, Boolean.parseBoolean(strValue));
                } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
                    a = new Argument<>(arg, Byte.parseByte(strValue));
                } else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
                    if (strValue == null || strValue.isEmpty() || strValue.length() != 1) {
                        throw new ParsingException(Phase.SEMANTIC_ANALYSIS);
                    }

                    a = new Argument<>(arg, strValue.charAt(0));
                } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                    a = new Argument<>(arg, Double.parseDouble(strValue));
                } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
                    a = new Argument<>(arg, Float.parseFloat(strValue));
                } else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                    a = new Argument<>(arg, Integer.parseInt(strValue));
                } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
                    a = new Argument<>(arg, Long.parseLong(strValue));
                } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
                    a = new Argument<>(arg, Short.parseShort(strValue));
                } else {
                    a = new Argument<>(arg, strValue);
                }

                arguments.add(a);
            }
        }

        boolean requiredOptIsPresent = opts.stream().filter(o -> o.isRequired()).allMatch(o -> {
            boolean _requiredOptIsPresent = false;

            if (o.hasShortName()) {
                _requiredOptIsPresent = options
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getShortName()));
            } else if (o.hasLongName()) {
                _requiredOptIsPresent = options
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getLongName()));
            }

            return _requiredOptIsPresent;
        });

        if (!requiredOptIsPresent) {
            throw new IllegalStateException();
        }

        return new Result(options, arguments);
    }

    private static Option analyzeOption(ArgsTree tree, Collection<Opt> opts, Tree.Node<Token> node) {
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
}
