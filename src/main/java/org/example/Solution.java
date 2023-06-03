package org.example;

import org.example.visitors.FancyVisitor;
import org.example.visitors.ProductOfRedNodesVisitor;
import org.example.visitors.SumInLeavesVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {
    private static String[] values;
    private static String[] colors;
    private static Tree[] treeArray;
    private static Boolean[] used;
    private static int numOfTrees;
    private static List<Integer> leftSide;
    private static List<Integer> rightSide;

    public static Tree solve() {
        File file = new File("C:\\Users\\kolch\\Desktop\\Projects\\Tree\\src\\main\\java\\org\\example\\input09.txt");
        Scanner scan;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        numOfTrees = Integer.parseInt(scan.nextLine());
        values = scan.nextLine().split(" ");
        colors = scan.nextLine().split(" ");

        leftSide = new ArrayList<>();
        rightSide = new ArrayList<>();

        used = new Boolean[numOfTrees];
        for (int i = 0; i < numOfTrees; i++) {
            used[i] = false;
        }
        treeArray = new Tree[numOfTrees];

        for (int i = 1; i < numOfTrees; i++) {
            leftSide.add(scan.nextInt());
            rightSide.add(scan.nextInt());
        }
        var start = new Date().getTime();
        mapFilling(1, -1);
        var end = new Date().getTime();
        System.out.println(end-start);
        return treeArray[0];
    }

    private static void mapFilling(int node, int depth) {
        for (int i = 0; i < numOfTrees-1; i++) {
            if (!used[i] && leftSide.get(i) == node) {
                used[i] = true;
                treeArray[node - 1] = treeArray[node - 1] == null ? new TreeNode(Integer.parseInt(values[node - 1]), Objects.equals(colors[node - 1], "1") ? Color.GREEN : Color.RED, depth + 1) : treeArray[node - 1];

                int newNode = rightSide.get(i);

                mapFilling(newNode, depth + 1);
                ((TreeNode) treeArray[node - 1]).addChild(treeArray[newNode - 1]);

            } else if (!used[i] && rightSide.get(i) == node) {
                used[i] = true;
                treeArray[node - 1] = treeArray[node - 1] == null ? new TreeNode(Integer.parseInt(values[node - 1]), Objects.equals(colors[node - 1], "1") ? Color.GREEN : Color.RED, depth + 1) : treeArray[node - 1];
                int newNode = leftSide.get(i);

                mapFilling(newNode, depth + 1);
                ((TreeNode) treeArray[node - 1]).addChild(treeArray[newNode - 1]);
            }
        }

        if (treeArray[node - 1] != null) {
            return;
        }

        treeArray[node - 1] = new TreeLeaf(Integer.parseInt(values[node - 1]), Objects.equals(colors[node - 1], "1") ? Color.GREEN : Color.RED, depth + 1);
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
