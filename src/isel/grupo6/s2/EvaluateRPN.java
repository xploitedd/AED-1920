package isel.grupo6.s2;

public class EvaluateRPN {

    public static int evaluateRPN(String expression) {
        if (expression == null || expression.length() == 0)
            return 0;

        StackArray<Integer> stack = new StackArray<>();
        int number = -1;
        boolean hasNumber = false;
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (c == ' ') {
                stack.push(number);
                number = -1;
            } else if (number != -1 || !isOperator(c)) {
                if (c < '0' || c > '9')
                    throw new IllegalArgumentException();

                hasNumber = true;
                number = number == -1 ? 0 : number;
                number *= 10;
                number += c - '0';
            } else {
                if (stack.getSize() < 2)
                    throw new IllegalArgumentException();

                int b = stack.pop();
                int a = stack.pop();
                stack.push(execute(a, b, c));
                ++i;
            }
        }

        if (number != -1)
            stack.push(number);
        else if (stack.getSize() == 0 || !hasNumber)
            return 0;

        if (stack.getSize() > 1)
            throw new IllegalArgumentException();

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
