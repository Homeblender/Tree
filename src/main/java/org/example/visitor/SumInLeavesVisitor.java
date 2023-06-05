package org.example.visitor;

import org.example.tree.TreeLeaf;
import org.example.tree.TreeNode;

public class SumInLeavesVisitor extends TreeVis {

    private int Sum = 0;

    public int getResult() {
        int result = Sum;
        Sum = 0;
        return result;
    }

    public void visitNode(TreeNode node) {
    }

    public void visitLeaf(TreeLeaf leaf) {
        Sum += leaf.getValue();
    }
}
