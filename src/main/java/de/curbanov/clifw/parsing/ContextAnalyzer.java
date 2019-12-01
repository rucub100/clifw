package de.curbanov.clifw.parsing;

import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.option.UserOption;

import java.util.*;

class ContextAnalyzer {

    public static Result semanticAnalysis(ArgsTree tree, Collection<Option> options) {
        List<UserOption> userOptions = new ArrayList<>(tree.getChildrenFrom(tree.getRoot()).size());
        for (Tree.Node<Token> node : tree.getChildrenFrom(tree.getRoot())) {

            Token t = tree.getDataFrom(node);
            boolean shortOpt = t.getValue().length() < 2;
            Tree.Node<Token> id = tree.getChildrenFrom(node).get(0);

            Optional<Option> option = options
                    .stream()
                    .filter(o -> shortOpt ? o.hasShortName() : o.hasLongName())
                    .filter(o -> tree.getDataFrom(id).getValue().contentEquals(
                            shortOpt ? o.getShortName() : o.getLongName()))
                    .findAny();

            if (!option.isPresent()) {
                throw new IllegalArgumentException();
            }

            List<Tree.Node<Token>> args = tree.getChildrenFrom(id);
            List<String> strArgs = null;

            // check args
            if (option.get().hasArgs() == args.isEmpty()) {
                throw new IllegalArgumentException();
            }

            if (option.get().hasArgs() && option.get().getArgsCount() != args.size()) {
                throw new IllegalArgumentException();
            }


            if (!args.isEmpty()) {
                strArgs = new ArrayList<>(args.size());
                for (int i = 0; i < args.size(); i++) {
                    String strArg = tree.getDataFrom(args.get(i)).getValue();

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

            UserOption userOption = new UserOption(
                    tree.getDataFrom(id).getValue(),
                    shortOpt,
                    strArgs);

            userOptions.add(userOption);
        }

        boolean requiredOptIsPresent = options.stream().filter(o -> o.isRequired()).allMatch(o -> {
            boolean _requiredOptIsPresent = false;

            if (o.hasShortName()) {
                _requiredOptIsPresent = userOptions
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getShortName()));
            } else if (o.hasLongName()) {
                _requiredOptIsPresent = userOptions
                        .stream()
                        .anyMatch(u -> u.getId().contentEquals(o.getLongName()));
            }

            return _requiredOptIsPresent;
        });

        if (!requiredOptIsPresent) {
            throw new IllegalStateException();
        }

        return new Result(userOptions);
    }
}
