package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Schema;
import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class SyntaxAnalyzer {

    public static Tree<Token> syntacticAnalysis(List<Token> tokens, Schema schema, Collection<Cmd> cmds, List<Arg> args) {
        Tree<Token> tree = new Tree<>();
        tree.setRoot(new Token(LexicalCategory.UNKNOWN, ""));

        switch (schema) {
            case OPTIONS:
                syntacticAnalysisOfOptions(tokens, tree);
                break;
            case ARGUMENTS:
                syntacticAnalysisOfArguments(tokens, tree);
                break;
            case OPTIONS_ARGUMENTS:
                syntacticAnalysisOfOptionsAndArguments(tokens, tree, args);
                break;
            case COMMANDS:
                syntacticAnalysisOfCommand(tokens, tree, cmds);
                break;
            default:
                throw new UnsupportedOperationException("not yet implemented");
        }

        return tree;
    }

    private static void syntacticAnalysisOfCommand(List<Token> tokens, Tree<Token> tree, Collection<Cmd> cmds) {
        if (tokens.size() < 1) {
            throw new ParsingException(Phase.SYNTAX_ANAlYSIS, "User input contains too few arguments!");
        }

        Token cmdToken = tokens.get(0);
        ensureLexicalCategory(cmdToken, LexicalCategory.LITERAL);

        Optional<Cmd> cmd = cmds.stream()
                .filter(x -> x.getName().equals(cmdToken.getValue()))
                .findAny();

        if (cmd.isEmpty()) {
            throw new ParsingException(Phase.SYNTAX_ANAlYSIS, "Command '" + cmdToken.getValue() + "' not found!");
        }

        tree.addChildTo(tree.getRoot(), cmdToken);
        Tree<Token> subTree = tree.getSubTree(tree.getChildrenFrom(tree.getRoot()).get(0));

        syntacticAnalysisOfOptionsAndArguments(tokens.subList(1, tokens.size()), subTree, cmd.get().getArgs());
    }

    private static void syntacticAnalysisOfOptionsAndArguments(
            List<Token> tokens,
            Tree<Token> tree,
            List<Arg> args) {
        if (tokens.size() < args.size()) {
            throw new ParsingException(Phase.SYNTAX_ANAlYSIS, "User input contains too few arguments!");
        }

        List<Token> optTokens = tokens.subList(0, tokens.size() - args.size());
        List<Token> argTokens = tokens.subList(tokens.size() - args.size(), tokens.size());
        syntacticAnalysisOfOptions(optTokens, tree);
        syntacticAnalysisOfArguments(argTokens, tree);
    }

    private static void syntacticAnalysisOfArguments(List<Token> tokens, Tree<Token> tree) {
        for (int i = 0; i < tokens.size(); i++) {
            ensureLexicalCategory(tokens.get(i), LexicalCategory.LITERAL);
            tree.addChildTo(tree.getRoot(), tokens.get(i));
        }
    }

    private static void syntacticAnalysisOfOptions(List<Token> tokens, Tree<Token> tree) {
        for (int i = 0; i < tokens.size();) {
            ensureLexicalCategory(tokens.get(i), LexicalCategory.OPERATOR);
            switch (tokens.get(i).getValue()) {
                case "-":
                case "--":
                    Tree.Node<Token> opt = tree.addChildTo(tree.getRoot(), tokens.get(i++));
                    ensureLexicalCategory(tokens.get(i), LexicalCategory.IDENTIFIER);
                    Tree.Node<Token> id = tree.addChildTo(opt, tokens.get(i++));

                    if (i < tokens.size()) {
                        if (tokens.get(i).getName() == LexicalCategory.SEPARATOR &&
                                tokens.get(i).getValue().contentEquals("=")) {
                            i++;
                        }

                        if (i >= tokens.size()) {
                            throw new IllegalArgumentException();
                        }

                        while (i < tokens.size() && tokens.get(i).getName() == LexicalCategory.LITERAL) {
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
}
