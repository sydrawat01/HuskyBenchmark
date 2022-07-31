package edu.neu.coe.huskyBenchmark.BST;

import edu.neu.coe.huskyBenchmark.ADT.Queue;
import edu.neu.coe.huskyBenchmark.ADT.QueueAPI;
import edu.neu.coe.huskyBenchmark.util.PrivateMethodInvoker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class BSTTest {
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
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
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
        assertEquals("Y", root.larger.key);
        assertNull(root.smaller);
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
        assertEquals("X", root.smaller.key);
        assertEquals("Z", root.larger.key);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPut3() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        PrivateMethodInvoker tester = new PrivateMethodInvoker(bst);
        bst.put("Y", 42);
        BinarySearchTree.Node root = (BinarySearchTree.Node) tester.invokePrivate("getRoot");
        assertEquals("Y", root.key);
        assertNull(root.smaller);
        assertNull(root.larger);
        bst.put("X", 99);
        assertEquals("X", root.smaller.key);
        bst.put("Z", 37);
        assertEquals("Z", root.larger.key);
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
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
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
        assertNull(bst.root.smaller);
        assertNull(bst.root.larger);
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
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
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
        nodeY.smaller = nodeX;
        nodeY.larger = nodeZ;
        tester.invokePrivate("setRoot", nodeY);
        assertEquals(2, bst.depth());
    }

    @Test
    public void testDepth2() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(3, bst.depth());
    }
}
