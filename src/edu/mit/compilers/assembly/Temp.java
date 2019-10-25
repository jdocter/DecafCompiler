package edu.mit.compilers.assembly;

public class Temp {
    private static int idCounter = 0;

    private final int id;
    private final String name;

    Temp() {
        id = idCounter;
        idCounter++;
        name = "t"+id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Temp && this.id == ((Temp) obj).id;
    }

    @Override
    public String toString() {
        return name;
    }
}
