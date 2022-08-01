package edu.neu.coe.huskyBenchmark.BST;

import java.util.*;
import java.util.function.BiFunction;

public class BinarySearchTree <Key extends Comparable<Key>, Value> implements BSTDetail<Key, Value> {
    @Override
    public Boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * This implementation of putAll ensures that the keys are inserted into this BST in random order.
     *
     * @param map a map of key value pairs
     */
    @Override
    public void putAll(Map<Key, Value> map) {
        List<Key> ks = new ArrayList<>(map.keySet());
        Collections.shuffle(ks);
        for (Key k : ks) put(k, map.get(k));
    }

    @Override
    public int size() {
        return root != null ? root.count : 0;
    }

    @Override
    public void inOrderTraverse(BiFunction<Key, Value, Void> f) {
        doTraverse(0, root, f);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public Value put(Key key, Value value) {
        NodeValue nodeValue = put(root, key, value);
        if (root == null) root = nodeValue.node;
        if (nodeValue.value == null) root.count++;
        return nodeValue.value;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    @Override
    public Set<Key> keySet() {
        return null;
    }

    /**
     * Method to yield the depth of a key, relative to the root.
     *
     * @param key the key whose depth we are interested in.
     * @return the depth of the key (root: 0) otherwise -1 if key is not found.
     */
    public int depth(Key key) {
        try {
            return depth(root, key);
        } catch (DepthException e) {
            return -1;
        }
    }

    public BinarySearchTree() {
    }

    public BinarySearchTree(Map<Key, Value> map) {
        this();
        putAll(map);
    }

    Node root = null;

    private Value get(Node node, Key key) {
        Node result = getNode(node, key);
        return result != null ? result.value : null;
    }

    private Node getNode(Node node, Key key) {
        if (node == null) return null;
        int cf = key.compareTo(node.key);
        if (cf < 0) return getNode(node.left, key);
        else if (cf > 0) return getNode(node.right, key);
        else return node;
    }

    /**
     * Method to put the key/value pair into the subtree whose root is node.
     *
     * @param node  the root of a subtree
     * @param key   the key to insert
     * @param value the value to associate with the key
     * @return a tuple of Node and Value: Node is the
     */
    private NodeValue put(Node node, Key key, Value value) {
        // If node is null, then we return the newly constructed Node, and value=null
        if (node == null) return new NodeValue(new Node(key, value, 0), null);
        int cf = key.compareTo(node.key);
        if (cf == 0) {
            // If keys match, then we return the node and its value
            NodeValue result = new NodeValue(node, node.value);
            node.value = value;
            return result;
        } else if (cf < 0) {
            // if key is less than node's key, we recursively invoke put in the left subtree
            NodeValue result = put(node.left, key, value);
            if (node.left == null)
                node.left = result.node;
            if (result.value == null)
                result.node.count++;
            return result;
        } else {
            // if key is greater than node's key, we recursively invoke put in the right subtree
            NodeValue result = put(node.right, key, value);
            if (node.right == null)
                node.right = result.node;
            if (result.value == null)
                result.node.count++;
            return result;
        }
    }

    // CONSIDER this should be an instance method of Node.
    private Node delete(Node x, Key key) {
        // FIXME by replacing the following code
        if (x==null) return null;
        if (key.compareTo(x.key) < 0)
            x.left = delete(x.left, key);
        else if (key.compareTo(x.key) > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
        // END
    }

    /**
     * Function to find the min of {@code x} node
     * @param x the node whose min node is to be found
     * @return the min node
     */
    private Node min(Node x) {
        if (x==null) throw new RuntimeException("min not implemented for null");
        else if (x.left==null) return x;
        else return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    private int size(Node x) {
        return x == null ? 0 : x.count;
    }

    /**
     * Do a generic traverse of the binary tree starting with node
     *
     * @param q    determines when the function f is invoked ( lt 0: pre, ==0: in, gt 0: post)
     * @param node the node
     * @param f    the function to be invoked
     */
    private void doTraverse(int q, Node node, BiFunction<Key, Value, Void> f) {
        if (node == null) return;
        if (q < 0) f.apply(node.key, node.value);
        doTraverse(q, node.left, f);
        if (q == 0) f.apply(node.key, node.value);
        doTraverse(q, node.right, f);
        if (q > 0) f.apply(node.key, node.value);
    }

    /**
     * Yield the total depth of this BST. If root is null, then depth will be 0.
     *
     * @return the total number of levels in this BST.
     */
    public int depth() {
        return root!=null ? root.depth() : 0;
    }

    private class NodeValue {
        private final Node node;
        private final Value value;

        NodeValue(Node node, Value value) {
            this.node = node;
            this.value = value;
        }

        @Override
        public String toString() {
            return node + "<->" + value;
        }
    }

    class Node {
        Node(Key key, Value value, int depth) {
            this.key = key;
            this.value = value;
            this.depth = depth;
        }

        Node min() {
            return left != null ? left.min() : this;
        }

        int depth() {
            int depthS = left != null ? left.depth() : 0;
            int depthL = right != null ? right.depth() : 0;
            return 1 + Math.max(depthL, depthS);
        }

        final Key key;
        Value value;
        final int depth;
        Node left = null;
        Node right = null;
        int count = 0;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Node: " + key + ":" + value);
            if (left != null) sb.append(", left: ").append(left.key);
            if (right != null) sb.append(", right: ").append(right.key);
            return sb.toString();
        }

    }

    private Node makeNode(Key key, Value value, int depth) {
        return new Node(key, value, depth);
    }

    private Node getRoot() {
        return root;
    }

    private void setRoot(Node node) {
        if (root == null) {
            root = node;
            root.count++;
        } else
            root = node;
    }

    private void show(Node node, StringBuffer sb, int indent) {
        if (node == null) return;
        for (int i = 0; i < indent; i++) sb.append("  ");
        sb.append(node.key);
        sb.append(": ");
        sb.append(node.value);
        sb.append("\n");
        if (node.left != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("left: ");
            show(node.left, sb, indent + 1);
        }
        if (node.right != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("right: ");
            show(node.right, sb, indent + 1);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        show(root, sb, 0);
        return sb.toString();
    }

    private int depth(Node node, Key key) throws DepthException {
        if (node == null) throw new DepthException();
        int cf = key.compareTo(node.key);
        if (cf < 0) return 1 + depth(node.left, key);
        else if (cf > 0) return 1 + depth(node.right, key);
        else return 0;
    }

    private static class DepthException extends Exception {
        public DepthException() {
        }
    }
}
