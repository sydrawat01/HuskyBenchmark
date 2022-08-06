package edu.neu.coe.huskyBenchmark.BST;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HibbardDeletion {

    public HibbardDeletion(int N, int incr, int count) {
        this.N = N;
        this.incr = incr;
        this.count = count;
    }

    private List<Double> calcDepth() {
        List<Double> result = new ArrayList<>();
        while (N < 2000) {
            double x = 0;
            N = 20*incr;
            count = N/2;
            for (int z = 0; z < 100; z++) {
                BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
                List<Integer> list = new ArrayList<>();
                Random r = new Random();
                double height = 0;
                for (int i = 0; i < N; i++) {
                    int a = r.nextInt(100000000);
                    bst.put(a, a);
                    list.add(a);
                }
                height = bst.height();
                for (int k = 0; k < count; k++) {
                    // random deletions from the BST
                    int b = r.nextInt(list.size());
                    bst.delete(list.get(b));
                    list.remove(b);
                    height += bst.height();
                }
                x += height / (count + 1);
            }
            result.add(x/100);
            incr++;
        }
        return result;
    }
    public static void main(String[] args) throws IOException {
        HibbardDeletion ob = new HibbardDeletion(1,1,0);
        List<Double> actual = ob.calcDepth();
        // Actual values of BST height
        List<Double> expected = new ArrayList<>();
        int x = 10;
        for (int i=1; i<=100; i++) {
            expected.add(Math.sqrt(x));
            x += 10;
        }
        try {
            FileWriter fw = new FileWriter("./data/result.csv");
            fw.append("Actual");
            fw.append(",");
            fw.append("Expected");
            fw.append("\n");
            for(int i=0; i<actual.size();i++) {
                fw.append(Double.toString(actual.get(i)));
                fw.append(",");
                fw.append(Double.toString(expected.get(i)));
                fw.append("\n");
            }
            fw.flush();
            fw.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private int N;
    private int incr;
    private int count;
}
