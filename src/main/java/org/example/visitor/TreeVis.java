package org.example.visitor;

import org.example.tree.TreeLeaf;
import org.example.tree.TreeNode;

public abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}