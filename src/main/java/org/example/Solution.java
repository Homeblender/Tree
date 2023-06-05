package org.example;

import org.example.tree.Tree;
import org.example.tree.TreeLeaf;
import org.example.tree.TreeNode;
import org.example.visitor.FancyVisitor;
import org.example.visitor.ProductOfRedNodesVisitor;
import org.example.visitor.SumInLeavesVisitor;

import java.util.*;

public class Solution {
    private static int[] values;
    private static int[] colors;
    private static Map<Integer, List<Integer>> relations;
    private static boolean[] used;

    public static Tree solve() {
        Scanner scan = new Scanner(System.in);

        int numOfTrees = scan.nextInt();

        values = new int[numOfTrees];
        colors = new int[numOfTrees];
        used = new boolean[numOfTrees];
        relations = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < numOfTrees; i++) {
            values[i] = scan.nextInt();
        }
        for (int i = 0; i < numOfTrees; i++) {
            colors[i] = scan.nextInt();
            relations.put(i + 1, new ArrayList<Integer>());
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
