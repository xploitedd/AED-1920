package isel.grupo6.aulas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestQueueArray {

    @Test
    public void testQueueArray() {
        QueueArray<Integer> queueArray = new QueueArray<>();
        boolean ins = true;
        for (int i = 0; i < 10; i++)
            ins = queueArray.offer(i);

        assertFalse(ins);
        assertTrue(queueArray.isFull());
        for (int i = 0; i < 9; i++)
            assertEquals(i, queueArray.poll());

        assertEquals(9, queueArray.peek());
        assertEquals(9, queueArray.poll());
        assertTrue(queueArray.isEmpty());
    }

}
