package edu.neu.coe.huskyBenchmark.BST;

public interface BST <Key extends Comparable<Key>, Value> {
    Value get(Key key);
    void put(Key key, Value value);
    void delete(Key key);
}