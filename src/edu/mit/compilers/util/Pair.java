package edu.mit.compilers.util;


public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K _key, V _value) {
        this.key = _key;
        this.value = _value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

}
