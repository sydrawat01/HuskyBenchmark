package edu.neu.coe.huskyBenchmark.BST;

import java.util.*;

@SuppressWarnings("ALL")
public class BinarySearchTree<Key extends Comparable<Key>, Value> implements BST<Key, Value> {

    /*================================================
                            SEARCH
      ================================================
    */
    public Value get(Key key) {
        Node x = getNode(key);
        if (x != null)
            return x.value;
        return null;
    }

    private Node getNode(Key key) {
        Node x = root;
        while (x != null) {
            if (key.compareTo(x.key) < 0)
                x = x.left;
            else if (key.compareTo(x.key) > 0)
                x = x.right;
            else
                return x;
        }
        return null;
    }

    /*================================================
                        INSERTION
     ================================================
     */

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value, null, null, 1);
        if (key.compareTo(x.key) < 0)
            x.left = put(x.left, key, value);
        else if (key.compareTo(x.key) > 0)
            x.right = put(x.right, key, value);
        else
            x.value = value;
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /*================================================
                         DELETION
     ================================================
     */

    public void delete(Key key, String delType) {
        root = delete(root, key, delType);
    }

    private Node successor(Node t, Node x) {
        //Hibbard deletion: "Rightist" strategy
        x = min(t.right);
        x.right = deleteMin(t.right);
        x.left = t.left;
        return x;
    }

    private Node predecessor(Node t, Node x) {
        // "Leftist" strategy
        x = max(t.left);
        x.left = deleteMax(t.left);
        x.right = t.right;
        return x;
    }

    private Node delete(Node x, Key key, String delType) {
        if (x == null) return null;
        if (key.compareTo(x.key) < 0)
            x.left = delete(x.left, key, delType);
        else if (key.compareTo(x.key) > 0)
            x.right = delete(x.right, key, delType);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x;
            switch (delType) {
                case "0":
                case "Hibbard":
                    x = successor(t, x);
                    break;
                case "1":
                case "Leftist":
                    x = predecessor(t, x);
                    break;
                case "SizedDeletion":
                    if (size(x.left) < size(x.right)) {
                        x = successor(t, x);
                    } else {
                        x = predecessor(t, x);
                    }
                    break;
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*================================================
                          SELECT
     ================================================
     */

    /**
     * Return the key of a given rank
     *
     * @param k the number of keys that are smaller than the key to be returned
     * @return the key
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) return null;
        Node x = select(root, k);
        return x.key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k)
            return select(x.left, k);
        else if (t < k)
            return select(x.right, k - t - 1);
        else // if (t==k)
            return x;
    }

    /*================================================
                         HEIGHT
     ================================================
     */

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /*================================================
                         SIZE
     ================================================
     */
    public int size() {
        if (root == null)
            return 0;
        return root.size;
    }

    public int size(Node x) {
        if (x == null)
            return 0;
        return x.size;
    }

    /**
     * Check if the BST is empty
     *
     * @return true if BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Function to find the min of {@code x} node
     *
     * @param x the node whose min node is to be found
     * @return the min node
     */
    private Node min(Node x) {
        if (x == null) throw new RuntimeException("min not implemented for null");
        else if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Function to find the max of {@code x} node
     *
     * @param x the node whose max node is to be found
     * @return the max node
     */
    private Node max(Node x) {
        if (x == null) throw new RuntimeException("max not implemented for null");
        else if (x.right == null) return x;
        else return max(x.right);
    }

    /*================================================
                       DELETE MIN
     ================================================
     */
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*================================================
                       DELETE MAX
     ================================================
     */
    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Function to get the root of the tree
     *
     * @return root of the BST
     */
    public Node getRoot() {
        return root;
    }

    /*================================================
                     INORDER TRAVERSAL
     ================================================
     */
    public String traverse(Node x) {
        if (x == null) return "";
        traverse(x.left);
        inorder += x.key + " ";
        traverse(x.right);
        return inorder;
    }

    public class Node {
        public Node(Key key, Value value, Node left, Node right, int size) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.size = size;
        }

        private final Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;
    }

    public BinarySearchTree() {
    }

    public BinarySearchTree(Map<Key, Value> map) {
        this();
        putAll(map);
    }

    public void putAll(Map<Key, Value> map) {
        List<Key> ks = new ArrayList<>(map.keySet());
        Collections.shuffle(ks);
        for (Key k : ks) put(k, map.get(k));
    }

    private Node root;
    String inorder = "";
}
