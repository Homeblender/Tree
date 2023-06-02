package org.example.visitors;

import org.example.TreeLeaf;
import org.example.TreeNode;

public class SumInLeavesVisitor extends TreeVis {

    private int Sum = 0;

    public int getResult() {
        int result = Sum;
        Sum = 0;
        return result;
    }

    public void visitNode(TreeNode node) {
        return;
    }

    public void visitLeaf(TreeLeaf leaf) {
        Sum += leaf.getValue();
    }
}
