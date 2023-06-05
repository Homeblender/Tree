package org.example.visitor;

import org.example.Color;
import org.example.tree.TreeLeaf;
import org.example.tree.TreeNode;

public class FancyVisitor extends TreeVis {
    private int SumEven = 0;
    private int SumGreen = 0;

    public int getResult() {
        int result = Math.abs(SumEven - SumGreen);
        SumEven = 0;
        SumGreen = 0;
        return result;
    }
    public void visitNode(TreeNode node) {
        if (node.getDepth()%2 == 0) SumEven += node.getValue();
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.GREEN) SumGreen += leaf.getValue();
    }
}
