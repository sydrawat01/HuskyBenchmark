package edu.neu.coe.huskyBenchmark.util;

import java.util.*;
import java.util.function.BiFunction;

public class BST_Simple<Key extends Comparable<Key>, Value> implements BstDetail<Key, Value> {

    public Boolean contains(Key key) {
        return get(key) != null;
    }

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

    private Node deleteMin(Node x){
        if(x.smaller==null) return x.larger;
        x.smaller=deleteMin(x.smaller);
        x.count=size(x.smaller)+size(x.larger)+1;
        return x;
    }
    @Override
    public Set<Key> keySet() {
        return null;
    }

    public int depth(Key key) {
        try {
            return depth(root, key);
        } catch (DepthException e) {
            return -1;
        }
    }

    public BST_Simple() {
    }
    public boolean isEmpty() {
        return size() == 0;
    }


    public BST_Simple(Map<Key, Value> map) {
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
        if (cf < 0) return getNode(node.smaller, key);
        else if (cf > 0) return getNode(node.larger, key);
        else return node;
    }
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
            // if key is less than node's key, we recursively invoke put in the smaller subtree
            NodeValue result = put(node.smaller, key, value);
            if (node.smaller == null)
                node.smaller = result.node;
            if (result.value == null)
                result.node.count++;
            return result;
        } else {
            // if key is greater than node's key, we recursively invoke put in the larger subtree
            NodeValue result = put(node.larger, key, value);
            if (node.larger == null)
                node.larger = result.node;
            if (result.value == null)
                result.node.count++;
            return result;
        }
    }
    private Node delete(Node x, Key key) {
        // FIXME by replacing the following code
        if(x==null) return null;
        int cmp=key.compareTo(x.key);
        if(cmp<0) x.smaller=delete(x.smaller,key);
        if(cmp>0) x.larger=delete(x.larger,key);
        else{
            if(x.larger==null) return x.smaller;
            if(x.smaller==null) return x.larger;
            Node t = x;
            x=min(t.larger);
            x.larger=deleteMin(t.larger);
            x.smaller=t.smaller;
        }
        x.count=size(x.larger)+size(x.smaller)+1;
        return x;

        // END
    }
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.smaller == null) return x;
        else return min(x.smaller);
    }

    private int size(Node x) {
        return x == null ? 0 : x.count;
    }
    private void doTraverse(int q, Node node, BiFunction<Key, Value, Void> f) {
        if (node == null) return;
        if (q < 0) f.apply(node.key, node.value);
        doTraverse(q, node.smaller, f);
        if (q == 0) f.apply(node.key, node.value);
        doTraverse(q, node.larger, f);
        if (q > 0) f.apply(node.key, node.value);
    }

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


        int depth() {
            int depthS = smaller != null ? smaller.depth() : 0;
            int depthL = larger != null ? larger.depth() : 0;
            return 1 + Math.max(depthL, depthS);
        }

        final Key key;
        Value value;
        final int depth;
        Node smaller = null;
        Node larger = null;
        int count = 0;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Node: " + key + ":" + value);
            if (smaller != null) sb.append(", smaller: ").append(smaller.key);
            if (larger != null) sb.append(", larger: ").append(larger.key);
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
        if (node.smaller != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("smaller: ");
            show(node.smaller, sb, indent + 1);
        }
        if (node.larger != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("larger: ");
            show(node.larger, sb, indent + 1);
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
        if (cf < 0) return 1 + depth(node.smaller, key);
        else if (cf > 0) return 1 + depth(node.larger, key);
        else return 0;
    }

    private static class DepthException extends Exception {
        public DepthException() {
        }
    }
    public void showTrunks(Trunk p) {
        if (p == null) {
            return;
        }

        showTrunks(p.prev);
        System.out.print(p.str);
    }
    public void printTree(){
        printTree(root,null,false);
    }
    class Trunk {
        Trunk prev;
        String str;

        Trunk(Trunk prev, String str) {
            this.prev = prev;
            this.str = str;
        }
    }
    private void printTree (Node root, Trunk prev, boolean isLeft) {
        if (root == null) {
            return;
        }

        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);

        printTree(root.larger, trunk, true);

        if (prev == null) {
            trunk.str = "———";
        } else if (isLeft) {
            trunk.str = ".———";
            prev_str = "   |";
        } else {
            trunk.str = "`———";
            prev.str = prev_str;
        }

        showTrunks(trunk);
        System.out.println(" " + root.key);

        if (prev != null) {
            prev.str = prev_str;
        }
        trunk.str = "   |";

        printTree(root.smaller, trunk, false);
    }

/*
    public Node convert(int[] keys, int low, int high, Node root)
    {
        // base case
        if (low > high) {
            return root;
        }

        // find the middle element of the current range
        int mid = (low + high) / 2;

        // construct a new node from the middle element and assign it to the root
        root=new Node(, null, 0);

        // left subtree of the root will be formed by keys less than middle element
        root.smaller = convert(keys, low, mid - 1, root.larger);

        // right subtree of the root will be formed by keys more than the
        // middle element
        root.larger = convert(keys, mid + 1, high, root.smaller);

        return root;
    }

    // Function to construct balanced BST from the given unsorted array
    public Node convert(int[] keys)
    {
        // sort the keys first
        Arrays.sort(keys);

        // construct a balanced BST and return the root node of the tree
        return convert(keys, 0, keys.length - 1, null);
    }
    */


    public static void main(String[]args){
        BST_Simple<Integer, Integer> st = new BST_Simple<>();
        st.put(50, 1);
        st.put(30, 1);
        st.put(20, 1);
        st.put(10, 1);
        st.put(40, 1);
        st.put(35, 1);
        st.put(34, 1);
        st.put(45, 1);
        st.put(80, 1);
        st.put(70, 1);
        st.put(60, 1);
        st.put(90, 1);
        st.put(100, 1);
        st.printTree();
        System.out.println(st.size());
        st.delete(10);
        st.printTree();
        System.out.println(st.size());

    }
}


