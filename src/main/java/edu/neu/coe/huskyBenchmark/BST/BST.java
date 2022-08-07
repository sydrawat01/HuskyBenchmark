package edu.neu.coe.huskyBenchmark.BST;

import java.util.Map;

/**
 * Interface to define the behavior of a Binary Search Tree.
 *
 * @param <Key> the key type.
 * @param <Value> the value type.
 */
public interface BST<Key extends Comparable<Key>, Value> {

    /**
     * Get the value corresponding to key.
     *
     * @param key the key whose value is required.
     * @return the value.
     */
    Value get(Key key);

    /**
     * Insert or update the given key-value pair.
     *
     * @param key the key to be inserted/updated.
     * @param value the value to be retrieved by future calls of get(key).
     */
    void put(Key key, Value value);

    /**
     * Delete the given key.
     * CONSIDER returning the original value.
     *
     * @param key the key to be deleted.
     * @param hibbard boolean to delete by "leftist" or "rightist" strategy
     */
    void delete(Key key, boolean hibbard);

    /**
     * Method to return the size of the tree
     * @return the size of this BST.
     */
    int size();

    /**
     * Method to return the depth of the tree
     * @return the depth of this BST.
     */
    int height();

    /**
     * Method to put all map values into the BST
     * @param map a HashMap to store the key, value pairs of the BST
     */
    void putAll(Map<Key, Value> map);
}
