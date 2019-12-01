package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Schema;

import java.util.List;

class SyntaxAnalyzer {

    public static ArgsTree syntacticAnalysis(List<Token> tokens, Schema schema) {
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
