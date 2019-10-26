package edu.mit.compilers.util;

public class Triad <K, V, Y> {

    private final K first;
    private final V second;
    private final Y third;

    public Triad(K first, V second, Y third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public Y getThird() {
        return third;
    }
}
