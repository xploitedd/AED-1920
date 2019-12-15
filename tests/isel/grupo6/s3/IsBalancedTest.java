package isel.grupo6.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsBalancedTest {

    @Test
    public void balancedNull() { assertFalse(TreeUtils.isBalanced(null)); }

    @Test
    public void balancedOne() { assertTrue(TreeUtils.isBalanced(Nodes.getOneNode())); }

    @Test
    public void balancedMultipleLeft() { assertFalse(TreeUtils.isBalanced(Nodes.getTreeLeft())); }

    @Test
    public void balancedMultipleRight() { assertFalse(TreeUtils.isBalanced(Nodes.getTreeRight())); }

    @Test
    public void balancedMultipleBalanced() { assertTrue(TreeUtils.isBalanced(Nodes.getBalancedTree())); }

    @Test
    public void balancedMultipleUnbalanced() { assertFalse(TreeUtils.isBalanced(Nodes.getUnbalancedTree())); }

}
