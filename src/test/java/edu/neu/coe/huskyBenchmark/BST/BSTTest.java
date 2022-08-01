package edu.neu.coe.huskyBenchmark.BST;

import edu.neu.coe.huskyBenchmark.ADT.Queue;
import edu.neu.coe.huskyBenchmark.ADT.QueueAPI;
import edu.neu.coe.huskyBenchmark.util.PrivateMethodInvoker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.*;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class BSTTest {

    @Test
    public void testSkewedStringTree() {
        BSTDetail<String, Integer> bst = new BinarySearchTree<>();
        bst.put("A",1);
        bst.put("B", 2);
        bst.put("C", 3);
        bst.put("D", 4);
        bst.put("E", 5);
        bst.put("F", 6);
        bst.put("G", 7);
        assertEquals(7, bst.depth());
        System.out.println(bst.toString());
    }

    @Test
    public void testSkewedIntegerTree() {
        BSTDetail<Integer, String> bst = new BinarySearchTree<>();
        bst.put(1, "A");
        bst.put(2, "B");
        bst.put(3, "C");
        bst.put(4, "D");
        bst.put(5, "E");
        bst.put(6, "F");
        bst.put(7, "G");
        assertEquals(7, bst.depth());
        System.out.println(bst.toString());
    }

    @Test
    public void testSkewed() {
        BSTDetail<Integer, String> bst = new BinarySearchTree<>();
        for (int i=1; i<=100; i++) {
            bst.put(i, Integer.toString(i));
        }
        System.out.println(bst.toString());
        assertEquals(100, bst.depth());
    }

    @Test
    public void testDepth() {
        BSTDetail<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("H",8);
        bst.put("M", 13);
        assertEquals(5,bst.depth());
        bst.delete("M");
        assertEquals(4,bst.depth());
        System.out.println(bst.toString());
    }


    ArrayList<Node> constructTrees(int start, int end)
    {
        ArrayList<Node> list=new ArrayList<>();
		/* if start > end then subtree will be empty so returning NULL
			in the list */
        if (start > end)
        {
            list.add(null);
            return list;
        }

		/* iterating through all values from start to end for constructing\
			left and right subtree recursively */
        for (int i = start; i <= end; i++)
        {
            /* constructing left subtree */
            ArrayList<Node> leftSubtree = constructTrees(start, i - 1);

            /* constructing right subtree */
            ArrayList<Node> rightSubtree = constructTrees(i + 1, end);

			/* now looping through all left and right subtrees and connecting
				them to ith root below */
            for (int j = 0; j < leftSubtree.size(); j++)
            {
                Node left = leftSubtree.get(j);
                for (int k = 0; k < rightSubtree.size(); k++)
                {
                    Node right = rightSubtree.get(k);
                    Node node = new Node(i);	 // making value i as root
                    node.left = left;			 // connect left subtree
                    node.right = right;		 // connect right subtree
                    list.add(node);			 // add this tree to list
                }
            }
        }
        return list;
    }
    @Test
    public void testDepthandSize() throws Exception {
        BSTDetail<String, Integer> bst = new BinarySearchTree<>();
        assertEquals(0,bst.size());
        bst.put("15",15);
        bst.put("10",10);
        bst.put("20",20);
        bst.put("08",8);
        bst.put("12",12);
        bst.put("18",18);
        bst.put("16",16);
        bst.put("30",30);

        assertEquals(4, bst.depth());
        assertEquals(8, bst.size());
        System.out.println(bst.toString());
    }

    @Test
    public void testSetRoot1() throws Exception {
        BST<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        System.out.println(bst);
    }

    @Test
    public void testSetRoot2() throws Exception {
        BST<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node nodeX = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BinarySearchTree.Node nodeY = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BinarySearchTree.Node nodeZ = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.left = nodeX;
        nodeY.right = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        System.out.println(bst);
    }

    @Test
    public void testPut0() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        assertEquals(0, bst.size());
        bst.put("X", 42);
        assertEquals(1, bst.size());
    }

    @Test
    public void testPut1() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 99);
        BinarySearchTree.Node root = (BinarySearchTree.Node) tester.invokePrivate("getRoot");
        assertEquals("X", root.key);
        assertEquals("Y", root.right.key);
        assertNull(root.left);
        assertEquals(2, bst.size());
    }

    @Test
    public void testPut2() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        BinarySearchTree.Node root = (BinarySearchTree.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertEquals("X", root.left.key);
        assertEquals("Z", root.right.key);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPut3() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        bst.put("Y", 42);
        BinarySearchTree.Node root = (BinarySearchTree.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertNull(root.left);
        assertNull(root.right);
        bst.put("X", 99);
        assertEquals("X", root.left.key);
        bst.put("Z", 37);
        assertEquals("Z", root.right.key);
        System.out.println(bst.toString());
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutN() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        System.out.println(bst);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutAll() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 3);
        map.put("Goodbye", 5);
        map.put("Ciao", 6);
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.putAll(map);
        System.out.println(bst);
        assertEquals(map.size(), bst.size());
    }

    @Test
    public void testTraverse() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("X", 99);
        bst.put("Z", 37);
        QueueAPI<String> queue = new Queue<>();
        bst.inOrderTraverse((w, x) -> {
            queue.enqueue(w);
            return null;
        });
        assertEquals("X", queue.dequeue());
        assertEquals("Y", queue.dequeue());
        assertEquals("Z", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDelete1() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.delete("X");
        assertNull(bst.root);
        assertEquals(0, bst.size());
    }

    @Test
    public void testDelete2() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("Y", 57);
        bst.delete("Y");
        assertNull(bst.root.left);
        assertNull(bst.root.right);
        assertEquals(1, bst.size());
    }

    @Test
    public void testDelete3() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("W");
        assertNull(bst.root.left);
        assertNull(bst.root.right);
        assertEquals(1, bst.size());
    }

    @Test
    public void testDelete4() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node node = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        tester.invokePrivate("setRoot", node);
        bst.put("W", 57);
        bst.delete("A");
        assertEquals(2, bst.size());
    }

    @Test
    public void testSize1() {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 100; i++) bst.put(Integer.toString(i), i);
        assertEquals(100, bst.size());
    }

    @Test
    public void testSize2() {
        Random random = new Random(0L);
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 100; i++) bst.put(Integer.toString(random.nextInt(200)), i);
        assertEquals(79, bst.size());
    }

    @Test
    public void testDepthKey1() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node nodeX = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BinarySearchTree.Node nodeY = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BinarySearchTree.Node nodeZ = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.left = nodeX;
        nodeY.right = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(1, bst.depth("X"));
        assertEquals(0, bst.depth("Y"));
        assertEquals(1, bst.depth("Z"));
        assertEquals(-1, bst.depth("A"));
    }

    @Test
    public void testDepthKey2() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(0, bst.depth("Hello"));
        assertEquals(1, bst.depth("Goodbye"));
        assertEquals(2, bst.depth("Ciao"));
    }

    @Test
    public void testDepth1() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        Class[] classes = {Comparable.class, Object.class, int.class};
        BinarySearchTree.Node nodeX = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "X", 42, 0);
        BinarySearchTree.Node nodeY = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Y", 52, 0);
        BinarySearchTree.Node nodeZ = (BinarySearchTree.Node) tester.invokePrivateExplicit("makeNode", classes, "Z", 99, 0);
        nodeY.left = nodeX;
        nodeY.right = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(2, bst.depth());
    }

    @Test
    public void testDepth2() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 22);
        bst.put("X", 13);
        bst.put("E", 3);
        bst.put("A", 3);
        bst.put("R", 34);
        bst.put("H", 3);
        bst.put("M", 356);
        bst.put("C", 3);
        System.out.println(bst.depth());
        assertEquals(5, bst.depth());
    }


    @Test
    public void testDepthForNvalues() throws Exception {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        ArrayList<Node> totalTreesFrom1toN = constructTrees(1, 3);
        for (int i = 0; i < totalTreesFrom1toN.size(); i++)
        {

            //bst.preorder(totalTreesFrom1toN.get(i));
           // System.out.println();


        }
    }


    @Test
    public void testDepthafterdelete() throws Exception {
        double dp = 0;
        for (int n = 3; n < 100; n++) {
            //int n=5000;
            int m = 2;
            int x = 1;


            while (x < n) {
                BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
                Random random = new Random(10L);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    String a = Integer.toString(random.nextInt(n * 2));
                    bst.put(a, i);
                    list.add(a);
                    //System.out.println(a);
                }
                //System.out.println(bst.depth());

                for (int i = 0; i < n - x; i++) {

                    bst.delete(list.get(i));


                }
                dp = dp + (bst.depth() / Math.sqrt(Double.valueOf(n - x)));
                System.out.println("DP::" + dp);
                x++;


            }
           // System.out.println("DP::" + dp / n);
            // System.out.println("After:"+bst.depth());
            //assertEquals(30, bst.depth());
        }
        System.out.println("DP::" + dp /100);
    }

}
