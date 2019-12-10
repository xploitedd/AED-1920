package isel.grupo6.s3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CountMultiplesTest {

    @Test
    public void countNull() {
        assertEquals(0, TreeUtils.countMultiples(null, 10));
    }

    @Test
    public void countOne() {
        assertEquals(1, TreeUtils.countMultiples(Nodes.getOneNode(), 10));
        assertEquals(0, TreeUtils.countMultiples(Nodes.getOneNode(), 15));
    }

    @Test
    public void countMultipleLeft() {
        assertEquals(2, TreeUtils.countMultiples(Nodes.getTreeLeft(), 10));
    }

    @Test
    public void countMultipleRight() {
        assertEquals(4, TreeUtils.countMultiples(Nodes.getTreeRight(), 5));
    }

    @Test
    public void countMultiple() {
        assertEquals(2, TreeUtils.countMultiples(Nodes.getBalancedTree(), 10));
    }

}
