package isel.grupo6.s2;

import static isel.grupo6.s2.EvaluateRPN.evaluateRPN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class EvaluateRPNTest{

    @Test
    public void calculate_empty_String() {
        assertEquals(0, evaluateRPN(""));
    }

    @Test
    public void calculate_spaces_String() {
        assertEquals(0, evaluateRPN("       "));
    }

    @Test
    public void  calculate_only_a_single_operand() {
        assertEquals(0, evaluateRPN("0"));
        assertEquals(200, evaluateRPN("200"));
    }

    @Test
    public void  calculate_only_operands(){
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2 3"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("2 1"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("3 2 1"));
    }

    @Test
    public void  calculate_only_operations() {
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("*"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("* +"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("* + -"));
    }

    @Test
    public void  calculate_missing_operands() {
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 *"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2 * +"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2 3 * + -"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2 3 4 * + - /"));
    }

    @Test
    public void  calculate_divide_by_zero() {
        assertThrows(ArithmeticException.class, () -> evaluateRPN("1 0 /"));
        assertThrows(ArithmeticException.class, () -> evaluateRPN("4 2 2 - /"));
    }

    @Test
    public void  calculate_illegal_expressions() {
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 a"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1l 22 +"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 x 1 * + -"));
        assertThrows(IllegalArgumentException.class, () -> evaluateRPN("1 2 p 4 5 * + - /"));
    }

    @Test
    public void  calculate_legal_expressions() {
        assertEquals(3, evaluateRPN("1 2 +"));
        assertEquals(6, evaluateRPN("1 2 3 + +"));
        assertEquals(-1, evaluateRPN("1 2 -"));
        assertEquals(1, evaluateRPN("2 1 -"));
        assertEquals(2, evaluateRPN("3 2 1 - -"));
        assertEquals(2, evaluateRPN("1 2 *"));
        assertEquals(6, evaluateRPN("1 2 3 * *"));
        assertEquals(0, evaluateRPN("1 2 /"));
        assertEquals(2, evaluateRPN("2 1 /"));
        assertEquals(2, evaluateRPN("4 2 1 / /"));
        assertEquals(6, evaluateRPN("1 3 4 * 2 / + 1 -"));
        assertEquals(2075, evaluateRPN("5 9 8 + 4 6 * * 7 + *"));
    }

}
