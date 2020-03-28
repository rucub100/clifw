package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;

import java.util.*;

public class ArgsParser {

    private final Args input;
    private final Schema schema;
    private final Collection<Opt> opts;
    private final List<Arg> args;
    private final Collection<Cmd> cmds;
    private Phase currentPhase;

    public ArgsParser(Args input, Schema schema, Collection<Opt> opts, List<Arg> args, Collection<Cmd> cmds) {
        this.input = input;
        this.schema = schema;
        this.opts = opts == null ? new ArrayList<>() : opts;
        this.args = args == null ? new ArrayList<>() : args;
        this.cmds = cmds == null ? new ArrayList<>() : cmds;
        this.currentPhase = Phase.PREPROCESSING;
    }

    public Result parse() {
        try {
            this.currentPhase = Phase.LEXICAL_ANALYSIS;
            List<Token> tokens = Tokenizer.lexicalAnalysis(this.input);

            this.currentPhase = Phase.SYNTAX_ANAlYSIS;
            Tree<Token> tree = SyntaxAnalyzer.syntacticAnalysis(tokens, this.schema, this.cmds, this.args);

            this.currentPhase = Phase.SEMANTIC_ANALYSIS;
            return ContextAnalyzer.semanticAnalysis(tree, this.schema, this.opts, this.args, this.cmds);
        } catch(ParsingException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ParsingException(currentPhase, ex);
        }
    }
}
