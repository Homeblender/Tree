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
    private static byte[][] relations;

    private static boolean[] used;
    private static int numOfTrees;

    public static Tree solve() {
        File file = new File("C:\\Users\\kolch\\Desktop\\Projects\\Tree\\src\\main\\java\\org\\example\\input08.txt");
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        numOfTrees = scan.nextInt();

        values = new int[numOfTrees];
        colors = new int[numOfTrees];

        used = new boolean[numOfTrees];

        for (int i = 0; i < numOfTrees; i++) {
            values[i] = scan.nextInt();
        }
        for (int i = 0; i < numOfTrees; i++) {
            colors[i] = scan.nextInt();
            used[i] = false;
        }



        relations = new byte[numOfTrees][numOfTrees];
        for (int i = 1; i < numOfTrees; i++) {
            int xy = scan.nextInt() - 1;
            int yx = scan.nextInt() - 1;

            relations[xy][yx] = 1;
            relations[yx][xy] = 1;
        }


        return mapFilling(1, -1);
    }

    private static Tree mapFilling(int node, int depth) {
        boolean isnode = false;
        TreeNode treeNode = null;
        for (int i = 0; i < numOfTrees; i++) {
            if (!used[i] && relations[node - 1][i]==1) {
                if (!isnode) {
                    treeNode = new TreeNode(values[node - 1], colors[node - 1] == 1 ? Color.GREEN : Color.RED, depth + 1);
                    isnode = true;
                }
                relations[node - 1][i] = 0;
                relations[i][node - 1] = 0;
                used[i] = true;
                treeNode.addChild(mapFilling(i + 1, depth + 1));
            }
        }
        if (isnode) return treeNode;

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
