package isel.grupo6.s2;

public class EvaluateRPN {

    private static final int NO_NUMBER = -1;

    public static int evaluateRPN(String expression) {
        if (expression == null || expression.length() == 0)
            return 0;

        StackArray<Integer> stack = new StackArray<>();
        int number = NO_NUMBER;
        boolean hasAnyNumber = false;

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c == ' ') {
                // if the char is a space then add a number, if it exists
                if (number != NO_NUMBER) {
                    stack.push(number);
                    number = NO_NUMBER;
                }
            } else if (number != NO_NUMBER || !isOperator(c)) {
                // if it's a number then check boundaries and add the digit
                if (c < '0' || c > '9')
                    throw new IllegalArgumentException();

                hasAnyNumber = true;
                number = number == -1 ? 0 : number;
                number *= 10;
                number += c - '0';
            } else {
                // if it's an operator then get the result from the last
                // two numbers on the stack and save it to the stack
                if (stack.getSize() < 2)
                    throw new IllegalArgumentException();

                int b = stack.pop();
                int a = stack.pop();
                stack.push(execute(a, b, c));
                // increment i to jump the next space
                ++i;
            }
        }

        // check if any number is left, or if there are no numbers
        if (number != NO_NUMBER)
            stack.push(number);
        else if (stack.getSize() == 0 || !hasAnyNumber)
            return 0;

        // check if the expression is missing any operators
        if (stack.getSize() > 1)
            throw new IllegalArgumentException();

        // return the result of the expression
        return stack.pop();
    }

    private static boolean isOperator(char c) { return c == '+' || c == '-' || c == '*' || c == '/'; }

    private static int execute(int a, int b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
        }

        throw new IllegalArgumentException();
    }

}
