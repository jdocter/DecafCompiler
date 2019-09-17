package edu.mit.compilers.parser;

public class DecafParseException extends Exception {
    public DecafParseException(String s,Exception e) {
        super(s,e);
    }
    public DecafParseException(String s) {
        super(s);
    }
}
