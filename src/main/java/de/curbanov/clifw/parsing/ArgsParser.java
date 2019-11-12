package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.option.UserOption;

import java.util.*;

public class ArgsParser {

    public Result parse(Args args, Schema schema, Collection<Option> options) {
        List<Token> tokens = lexicalAnalysis(args);
        ArgsTree tree = syntacticAnalysis(tokens, schema);
        return semanticAnalysis(tree, options);
    }

    private static List<Token> lexicalAnalysis(Args args) {
        List<Token> tokens = new ArrayList<>();

        for (String arg : args) {
            tokens.addAll(nextToken(arg, 0));
        }

        return tokens;
    }

    private static List<Token> nextToken(String term, final int d) {
        if (term.startsWith("-")) {
            if (term.startsWith("--")) {
                if (term.contentEquals("--")) {
                    // args separator
                    return new ArrayList<>(List.of(new Token(LexicalCategory.SEPARATOR, term)));
                } else {
                    // long option
                    List<Token> t = new ArrayList(
                            List.of(new Token(LexicalCategory.OPERATOR, term.substring(0, 2))));
                    t.addAll(nextToken(term.substring(2), d + 1));
                    return t;
                }
            } else {
                // short option
                List<Token> t = new ArrayList<>(
                        List.of(new Token(LexicalCategory.OPERATOR, term.substring(0, 1))));
                t.addAll(nextToken(term.substring(1), d + 1));
                return t;
            }
        } else {
            if (term.contains("=")) {
                if (d <= 0) {
                    throw new IllegalArgumentException();
                }

                String[] terms = term.split("=");

                if (terms.length != 2) {
                    throw new IllegalArgumentException();
                }

                return new ArrayList<>(
                        List.of(
                                new Token(LexicalCategory.IDENTIFIER, terms[0]),
                                new Token(LexicalCategory.SEPARATOR, "="),
                                new Token(LexicalCategory.LITERAL, terms[1])));
            } else {
                LexicalCategory cat = (d > 0) ? LexicalCategory.IDENTIFIER : LexicalCategory.LITERAL;
                return new ArrayList<>(List.of(new Token(cat, term)));
            }
        }
    }

    private static ArgsTree syntacticAnalysis(List<Token> tokens, Schema schema) {
        ArgsTree tree = new ArgsTree();
        tree.setRoot(new Token(LexicalCategory.UNKNOWN, ""));

        switch (schema) {
            case OPTIONS:
                syntacticAnalysisOfOptions(tokens, tree);
                break;
            default:
                throw new UnsupportedOperationException("not yet implemented");
        }

        return tree;
    }

    private static void syntacticAnalysisOfOptions(List<Token> tokens, ArgsTree tree) {
        for (int i = 0; i < tokens.size();) {
            ensureLexicalCategory(tokens.get(i), LexicalCategory.OPERATOR);
            switch (tokens.get(i).getValue()) {
                case "-":
                case "--":
                    Tree.Node<Token> opt = tree.addChildTo(tree.getRoot(), tokens.get(i++));
                    ensureLexicalCategory(tokens.get(i), LexicalCategory.IDENTIFIER);
                    Tree.Node<Token> id = tree.addChildTo(opt, tokens.get(i++));

                    if (i < tokens.size()) {
                        if (tokens.get(i).getName() == LexicalCategory.OPERATOR &&
                                tokens.get(i).getValue().contentEquals("=")) {
                            i++;
                        }

                        if (i >= tokens.size()) {
                            throw new IllegalArgumentException();
                        }

                        while (tokens.get(i).getName() == LexicalCategory.LITERAL) {
                            tree.addChildTo(id, tokens.get(i++));
                        }
                    }

                    break;
            }
        }
    }

    private static void ensureLexicalCategory(Token token, LexicalCategory category) {
        if (token.getName() != category) {
            throw new AssertionError();
        }
    }

    private static Result semanticAnalysis(ArgsTree tree, Collection<Option> options) {
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

            if (!args.isEmpty()) {
                strArgs = new ArrayList<>(args.size());
                for (Tree.Node<Token> arg : args) {
                    strArgs.add(tree.getDataFrom(arg).getValue());
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
