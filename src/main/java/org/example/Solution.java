package org.example;

import org.example.visitors.FancyVisitor;
import org.example.visitors.ProductOfRedNodesVisitor;
import org.example.visitors.SumInLeavesVisitor;

import java.util.*;

public class Solution {
    private static String[] values;
    private static String[] colors;
    private static Tree[] treeArray;

    public static Tree solve() {
        Scanner scan = new Scanner(System.in);

        int numOfTrees = Integer.parseInt(scan.nextLine()); // колличество узлов и листьев

        treeArray = new Tree[numOfTrees]; // все узлы и листья дерева
        values = scan.nextLine().split(" "); // значения узлов и листьев
        colors = scan.nextLine().split(" "); // цвета узлов и листьев


        List<Integer> leftSide = new ArrayList<>();
        List<Integer> rightSide = new ArrayList<>();

        for (int i = 1; i < numOfTrees; i++) {
            String[] tmp = scan.nextLine().split(" ");
            leftSide.add(Integer.parseInt(tmp[0]));
            rightSide.add(Integer.parseInt(tmp[1]));
        }

        treeArray[0] = new TreeNode(Integer.parseInt(values[0]), Objects.equals(colors[0], "0") ? Color.RED : Color.GREEN, 0);
        Map<Integer, ArrayList<Integer>> children = new HashMap<>();

        for (int i = 1; i <= numOfTrees; i++) {
            children.put(i, new ArrayList<Integer>());
        }

        mapFilling(children, 1, -1, leftSide, rightSide);


        for (Map.Entry<Integer, ArrayList<Integer>> entry : children.entrySet()) {
            for (Integer integer :
                    entry.getValue()) {
                ((TreeNode) treeArray[entry.getKey() - 1]).addChild(treeArray[integer - 1]);
            }
        }
        return treeArray[0];
    }

    private static void mapFilling(Map<Integer, ArrayList<Integer>> children, int node, int depth, List<Integer> leftSide, List<Integer> rightSide) {
        for (int i = 0; i < leftSide.size(); i++) {
            if (leftSide.get(i) == node) {
                children.get(node).add(rightSide.get(i));
                leftSide.remove(i);
                rightSide.remove(i);
                i--;
            } else if (rightSide.get(i) == node) {
                children.get(node).add(leftSide.get(i));
                leftSide.remove(i);
                rightSide.remove(i);
                i--;
            }
        }

        if (children.get(node).isEmpty()) {
            treeArray[node - 1] = new TreeLeaf(Integer.parseInt(values[node - 1]), Objects.equals(colors[node - 1], "1") ? Color.GREEN : Color.RED, depth + 1);
        } else {
            treeArray[node - 1] = new TreeNode(Integer.parseInt(values[node - 1]), Objects.equals(colors[node - 1], "1") ? Color.GREEN : Color.RED, depth + 1);
        }
        for (int iter : children.get(node)) {
            mapFilling(children, iter, depth + 1, leftSide, rightSide);
        }
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
