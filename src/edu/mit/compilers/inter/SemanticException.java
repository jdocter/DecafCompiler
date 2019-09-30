package edu.mit.compilers.inter;


public class SemanticException extends Exception {
    private int line;
    private String message;

    public SemanticException(int line, String message) {
        this.line = line;
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Uncaught semantic exception in Line " + line + ":");
        new Exception(this.message).printStackTrace();
    }

    @Override
    public String toString() {
        return "SemanticException<" + line + ", " + new Exception(this.message).toString() + ">";
    }

    @Override
    public String getMessage() {
        return "Semantic exception in Line " + line + ": " + new Exception(this.message).getMessage();
    }
}
