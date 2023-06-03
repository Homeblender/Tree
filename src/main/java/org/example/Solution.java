package org.example;

import org.example.visitors.FancyVisitor;
import org.example.visitors.ProductOfRedNodesVisitor;
import org.example.visitors.SumInLeavesVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {
    private static int[] values;
    private static int[] colors;
    private static Map<Integer, List<Integer>> relations;
    private static boolean[] used;

    public static Tree solve() {
        File file = new File("src/main/java/org/example/input09.txt");
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int numOfTrees = scan.nextInt();

        values = new int[numOfTrees];
        colors = new int[numOfTrees];
        used = new boolean[numOfTrees];
        relations = new HashMap<>();

        for (int i = 0; i < numOfTrees; i++) {
            values[i] = scan.nextInt();
        }
        for (int i = 0; i < numOfTrees; i++) {
            colors[i] = scan.nextInt();
            relations.put(i + 1, new ArrayList<>());
        }


        for (int i = 1; i < numOfTrees; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();

            relations.get(x).add(y);
            relations.get(y).add(x);
        }


        return treeBuilder(1, -1);
    }

    private static Tree treeBuilder(int node, int depth) {
        used[node - 1] = true;

        TreeNode treeNode = null;
        for (Integer integer : relations.get(node)) {
            if (!used[integer - 1]) {
                if (treeNode == null)
                    treeNode = new TreeNode(values[node - 1], colors[node - 1] == 1 ? Color.GREEN : Color.RED, depth + 1);

                treeNode.addChild(treeBuilder(integer, depth + 1));
            }
        }
        if (treeNode != null) return treeNode;
        return new TreeLeaf(values[node - 1], colors[node - 1] == 1 ? Color.GREEN : Color.RED, depth + 1);
    }


    public static void main(String[] args) {
        var start = new Date().getTime();

        Tree root = solve();

        var end = new Date().getTime();
        System.out.println(end - start);

        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
