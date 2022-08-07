package edu.neu.coe.huskyBenchmark.BST;

import edu.neu.coe.huskyBenchmark.util.Utilities;

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

    private List<Double> calcHeight() {
        List<Double> result = new ArrayList<>();
        while (N < 1600) {
            double x = 0;
            N = 20*incr;
            count = N/2;
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
                    bst.delete(list.get(b), true);
                    list.remove(b);
                    height += bst.height();
                }
                x += height / (count + 1);
            result.add(Double.parseDouble(Utilities.formatDecimal3Places(x)));
            incr++;
        }
        return result;
    }
    public static void main(String[] args) throws IOException {
        HibbardDeletion ob = new HibbardDeletion(1,1,0);
        List<Double> actual = ob.calcHeight();
        // Actual values of BST height
        List<Double> expected = new ArrayList<>();
        // Expected values of BST height
        int x = 10;
        while (x <= 800) {
            expected.add(Utilities.sqRoot(x));
            x +=10;
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
