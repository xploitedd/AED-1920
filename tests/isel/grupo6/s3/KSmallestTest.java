package isel.grupo6.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class KSmallestTest {

    @Test
    public void smallestNull() {
        assertNull(TreeUtils.kSmallest(null, 10));
    }

    @Test
    public void smallestOne() {
        assertEquals(10, TreeUtils.kSmallest(Nodes.getOneNode(), 1));
        assertNull(TreeUtils.kSmallest(Nodes.getOneNode(), 2));
    }

    @Test
    public void smallestMultipleLeft() {
        assertEquals(2, TreeUtils.kSmallest(Nodes.getTreeLeft(), 1));
        assertEquals(5, TreeUtils.kSmallest(Nodes.getTreeLeft(), 2));
        assertEquals(10, TreeUtils.kSmallest(Nodes.getTreeLeft(), 3));
        assertEquals(20, TreeUtils.kSmallest(Nodes.getTreeLeft(), 4));
    }

    @Test
    public void smallestMultipleRight() {
        assertEquals(10, TreeUtils.kSmallest(Nodes.getTreeRight(), 1));
        assertEquals(20, TreeUtils.kSmallest(Nodes.getTreeRight(), 2));
        assertEquals(25, TreeUtils.kSmallest(Nodes.getTreeRight(), 3));
        assertEquals(30, TreeUtils.kSmallest(Nodes.getTreeRight(), 4));
    }

    @Test
    public void smallestMultiple() {
        assertEquals(5, TreeUtils.kSmallest(Nodes.getBalancedTree(), 1));
        assertEquals(10, TreeUtils.kSmallest(Nodes.getBalancedTree(), 2));
        assertEquals(11, TreeUtils.kSmallest(Nodes.getBalancedTree(), 3));
        assertEquals(25, TreeUtils.kSmallest(Nodes.getBalancedTree(), 4));
        assertEquals(27, TreeUtils.kSmallest(Nodes.getBalancedTree(), 5));
        assertEquals(30, TreeUtils.kSmallest(Nodes.getBalancedTree(), 6));
        assertEquals(35, TreeUtils.kSmallest(Nodes.getBalancedTree(), 7));
    }

}
