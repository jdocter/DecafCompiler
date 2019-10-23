package edu.mit.compilers.util;


public class UIDObject {

    public static int currentID = 0;
    public final int UID;

    public UIDObject() {
        this.UID = currentID;
        currentID++;
    }

    public int getUID() {
        return this.UID;
    }

    @Override public String toString() {
        return "UIDObject [UID=" + UID + "]";
    }
}
