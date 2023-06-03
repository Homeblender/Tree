package org.example.visitors;

import org.example.Color;
import org.example.TreeLeaf;
import org.example.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ProductOfRedNodesVisitor extends TreeVis {
    private List<Integer> listValues = new ArrayList<>();
    private static final int module = 1000000007;

    public int getResult() {
        long f = 1;
        for (Integer integer: listValues)
            f = (f*integer) % module;
        listValues = new ArrayList<>();
        return (int) Math.abs(f);
    }

    public void visitNode(TreeNode node) {
        if (node.getColor() == Color.RED) {
            if (node.getValue() != 0)
                listValues.add(node.getValue());
        }
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.RED) {
            if (leaf.getValue() != 0)
                listValues.add(leaf.getValue());
        }
    }
}
