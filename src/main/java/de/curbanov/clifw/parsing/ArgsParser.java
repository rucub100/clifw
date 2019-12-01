package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
import de.curbanov.clifw.option.Option;

import java.util.*;

public class ArgsParser {

    private final Args args;
    private final Schema schema;
    private final Collection<Option> options;
    private Phase currentPhase;

    public ArgsParser(Args args, Schema schema, Collection<Option> options) {
        this.args = args;
        this.schema = schema;
        this.options = options;
        this.currentPhase = Phase.PREPROCESSING;
    }

    public Result parse() {
        try {
            this.currentPhase = Phase.LEXICAL_ANALYSIS;
            List<Token> tokens = Tokenizer.lexicalAnalysis(this.args);

            this.currentPhase = Phase.SYNTAX_ANAlYSIS;
            ArgsTree tree = SyntaxAnalyzer.syntacticAnalysis(tokens, this.schema);

            this.currentPhase = Phase.SEMANTIC_ANALYSIS;
            return ContextAnalyzer.semanticAnalysis(tree, this.options);
        } catch(ParsingException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ParsingException(currentPhase, ex);
        }
    }
}
