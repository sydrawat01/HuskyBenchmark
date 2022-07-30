package edu.neu.coe.huskyBenchmark.BST;
import edu.neu.coe.huskyBenchmark.sort.ComparableSortHelper;
import java.util.*;

public class BinarySearchTree <Key extends Comparable<Key>, Value> implements BST<Key, Value> {


    /*================================================
                         SEARCH
     ================================================
     */
    public Value get(Key key) {
        Node x = getNode(key);
        if (x!=null)
            return x.value;
        return null;
    }

    private Node getNode(Key key) {
        Node x = root;
        while (x!=null) {
            if (helper.compare(key, x.key)<0)
                x = x.left;
            else if (helper.compare(key, x.key)>0)
                x= x.right;
            else
                return x;
        }
        return null;
    }

    /*================================================
                        INSERTION
     ================================================
     */

    public void put(Key key,Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x==null) return new Node(key, value, 1);
        if(helper.compare(key, x.key)<0)
            x.left = put(x.left, key, value);
        else if (helper.compare(key, x.key)>0)
            x.right = put(x.right, key, value);
        else
            x.value = value;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*================================================
                         DELETION
     ================================================
     */

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x==null) return null;
        if (helper.compare(key, x.key)<0)
            x.left = delete(x.left, key);
        else if (helper.compare(key,x.key)>0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*================================================
                         SIZE
     ================================================
     */
    public int size() {
        return root != null ? root.size : 0;
    }

    public int size(Node x) {
        if (x==null)
            return 0;
        return x.size;
    }
    public int depth() {
        return root!=null ? root.depth() : 0;
    }
    public int depth(Key key) {
        try {
            return depth(root, key);
        } catch (DepthException e) {
            return -1;
        }
    }
    private int depth(Node node, Key key) throws DepthException {
        if (node == null) throw new DepthException();
        int cf = key.compareTo(node.key);
        if (cf < 0) return 1 + depth(node.left, key);
        else if (cf > 0) return 1 + depth(node.right, key);
        else return 0;
    }

    /**
     * Check if the BST is empty
     * @return true if BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
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

    /**
     * Function to find the max of {@code x} node
     * @param x the node whose max node is to be found
     * @return the max node
     */
    private Node max(Node x) {
        if(x==null) throw new RuntimeException("max not implemented for null");
        else if (x.right==null) return x;
        else  return max(x.right);
    }

    /*================================================
                       DELETE MIN
     ================================================
     */
    public void deleteMin() {
        root = deleteMin(root);
    }
    private Node makeNode(Key key, Value value, int depth) {
        return new Node(key, value, depth);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Function to get the root of the tree
     * @return root of the BST
     */
    private Node getRoot() {
        return root;
    }

    /*================================================
                     INORDER TRAVERSAL
     ================================================
     */
    public void traverse(Node x) {
        if (x==null) return;
        System.out.print(x.key + " ");
        traverse(x.left);
        traverse(x.right);
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

        printTree(root.right, trunk, true);

        if (prev == null) {
            trunk.str = " ";
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

        printTree(root.left, trunk, false);
    }

    public class Node {
        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
        int depth() {
            int depthS = left != null ? left.depth() : 0;
            int depthL = right != null ? right.depth() : 0;
            return 1 + Math.max(depthL, depthS);
        }

        final Key key;
        private Value value;
        Node left=null;
        Node right=null;
         int depth;
         int size;

    }

    public BinarySearchTree() {}
    private void show(Node node, StringBuffer sb, int indent) {
        if (node == null)
            return;
        for (int i = 0; i < indent; i++) sb.append("  ");
        sb.append(node.key);
        sb.append(": ");
        sb.append(node.value);
        sb.append("\n");
        if (node.left != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("smaller: ");
            show(node.left, sb, indent + 1);
        }
        if (node.right != null) {
            for (int i = 0; i <= indent; i++) sb.append("  ");
            sb.append("larger: ");
            show(node.right, sb, indent + 1);
        }
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        show(root, sb, 0);
        return sb.toString();
    }

    public BinarySearchTree(Map<Key, Value> map) {
        this();
        putAll(map);
    }
    private void setRoot(Node node) {
        if (root == null) {
            root = node;
            root.size++;
        } else
            root = node;
    }

    public void putAll(Map<Key, Value> map) {
        List<Key> ks = new ArrayList<>(map.keySet());
        Collections.shuffle(ks);
        for (Key k : ks) put(k, map.get(k));
    }
    private static class DepthException extends Exception {
        public DepthException() {
        }
    }
    Node root= null;
    private final ComparableSortHelper<Key> helper = new ComparableSortHelper<>("Binary Search Tree");

    public static void main(String []args){
        BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        st.put(Integer.toString(50), 1);
        st.put(Integer.toString(30), 1);
        st.put(Integer.toString(70), 1);
//        System.out.println(st.size());
//        System.out.println(st.depth());
       // st.printTree();
//        System.out.println(st);
        st.traverse(st.getRoot());
    }
}
