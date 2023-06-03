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
    private static boolean[][] relations;

    private static Tree[] treeArray;
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

        treeArray = new Tree[numOfTrees];


        relations = new boolean[numOfTrees][numOfTrees];
        for (int i = 1; i < numOfTrees; i++) {
            int xy = scan.nextInt() - 1;
            int yx = scan.nextInt() - 1;

            relations[xy][yx] = true;
            relations[yx][xy] = true;
        }


        var start = new Date().getTime();
        mapFilling(1, -1);
        var end = new Date().getTime();
        System.out.println(end - start);
        return treeArray[0];
    }

    private static void mapFilling(int node, int depth) {
        boolean isnode = false;
        for (int i = 0; i < numOfTrees; i++) {
            if (!used[i] && relations[node - 1][i]) {
                if (!isnode) {
                    treeArray[node - 1] = new TreeNode(values[node - 1], colors[node - 1] == 1 ? Color.GREEN : Color.RED, depth + 1);
                    isnode = true;
                }
                relations[node - 1][i] = false;
                relations[i][node - 1] = false;
                used[i] = true;
                mapFilling(i + 1, depth + 1);
                ((TreeNode) treeArray[node - 1]).addChild(treeArray[i]);
            }
        }
        if (isnode) return;

        treeArray[node - 1] = new TreeLeaf(values[node - 1], colors[node - 1] == 1 ? Color.GREEN : Color.RED, depth + 1);
    }


    public static void main(String[] args) {
        Tree root = solve();
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
