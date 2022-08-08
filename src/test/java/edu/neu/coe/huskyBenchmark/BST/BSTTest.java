package edu.neu.coe.huskyBenchmark.BST;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

@SuppressWarnings("ALL")
public class BSTTest {

    @Test
    public void testSkewedStringTree() {
        BST<String, Integer> bst = new BinarySearchTree<>();
        bst.put("A", 1);
        bst.put("B", 2);
        bst.put("C", 3);
        bst.put("D", 4);
        bst.put("E", 5);
        bst.put("F", 6);
        bst.put("G", 7);
        assertEquals(6, bst.height());
        assertEquals(7, bst.size());
    }

    @Test
    public void testSkewedIntegerTree() {
        BST<Integer, String> bst = new BinarySearchTree<>();
        bst.put(1, "A");
        bst.put(2, "B");
        bst.put(3, "C");
        bst.put(4, "D");
        bst.put(5, "E");
        bst.put(6, "F");
        bst.put(7, "G");
        assertEquals(6, bst.height());
    }

    @Test
    public void testDeleteRightist() {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("H", 8);
        bst.put("M", 13);
        bst.delete("E", "Hibbard");
        assertEquals(3, bst.height());
        assertEquals(7, bst.size());
        assertTrue(bst.traverse(bst.getRoot()).equals("A C H M R S X "));
    }

    @Test
    public void testDeleteLeftist() {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("H", 8);
        bst.put("M", 13);
        bst.delete("E", "Leftist");
        assertEquals(4, bst.height());
        assertEquals(7, bst.size());
        assertTrue(bst.traverse(bst.getRoot()).equals("A C H M R S X "));
    }

    @Test
    public void testDeleteRandom() {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("H", 8);
        bst.put("M", 13);
        String random = Integer.toString(new Random().nextInt(2));
        bst.delete("E", random);
        if (random.equals("0")) {
            assertEquals(3, bst.height());
        } else {
            assertEquals(4, bst.height());
        }
    }

    @Test
    public void testSkewed() {
        BST<Integer, String> bst = new BinarySearchTree<>();
        for (int i = 1; i <= 100; i++) {
            bst.put(i, Integer.toString(i));
        }
        assertEquals(99, bst.height());
    }

    @Test
    public void testHeightandSize1() {
        BST<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("H", 8);
        bst.put("M", 13);
        assertEquals(8, bst.size());
        assertEquals(4, bst.height());
        bst.delete("M", "Hibbard");
        bst.delete("E", "Hibbard");
        bst.delete("C", "Hibbard");
        assertEquals(5, bst.size());
        assertEquals(2, bst.height());
    }

    @Test
    public void testHeightandSize2() throws Exception {
        BST<String, Integer> bst = new BinarySearchTree<>();
        assertEquals(0, bst.size());
        bst.put("15", 15);
        bst.put("10", 10);
        bst.put("20", 20);
        bst.put("08", 8);
        bst.put("12", 12);
        bst.put("18", 18);
        bst.put("16", 16);
        bst.put("30", 30);

        assertEquals(3, bst.height());
        assertEquals(8, bst.size());
    }

    @Test
    public void testPut0() throws Exception {
        BST<String, Integer> bst = new BinarySearchTree<>();
        assertEquals(0, bst.size());
        bst.put("X", 42);
        assertEquals(1, bst.size());
    }

    @Test
    public void testPutN() throws Exception {
        BST<String, Integer> bst = new BinarySearchTree<>();
        bst.put("Hello", 3);
        bst.put("Goodbye", 5);
        bst.put("Ciao", 8);
        assertEquals(3, bst.size());
    }

    @Test
    public void testPutAll() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("Hello", 3);
        map.put("Goodbye", 5);
        map.put("Ciao", 6);
        BST<String, Integer> bst = new BinarySearchTree<>();
        bst.putAll(map);
        assertEquals(map.size(), bst.size());
    }

    @Test
    public void testTraverse() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("S", 19);
        map.put("E", 5);
        map.put("X", 24);
        map.put("A", 1);
        map.put("R", 18);
        map.put("C", 3);
        map.put("H", 8);
        map.put("M", 13);
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.putAll(map);
        assertTrue(bst.traverse(bst.getRoot()).equals("A C E H M R S X "));
    }

    @Test
    public void testSize1() {
        BST<Integer, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 100; i++) bst.put(i, i);
        assertEquals(100, bst.size());
        for (int i=10;i<20; i++) {
            bst.delete(i, "Hibbard");
        }
        assertEquals(90, bst.size());
        System.out.println(bst.height());
    }

    @Test
    public void testSize2() {
        Random random = new Random(0L);
        BST<String, Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < 100; i++) bst.put(Integer.toString(random.nextInt(200)), i);
        assertEquals(79, bst.size());
    }
    @Test
    public void testdeletebySize(){
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<>();
        bst.put("S", 19);
        bst.put("E", 5);
        bst.put("X", 24);
        bst.put("A", 1);
        bst.put("R", 18);
        bst.put("C", 3);
        bst.put("B",10);
        bst.put("H", 8);
        bst.delete("E", "SizedDeletion");
        assertEquals(3, bst.height());
    }
}
