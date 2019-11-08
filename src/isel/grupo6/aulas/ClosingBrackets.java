package isel.grupo6.aulas;

import java.util.Stack;

public class ClosingBrackets {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please insert the string");
            return;
        }

        String str = args[0];
        int len = str.length();
        if (len % 2 != 0) {
            System.out.println("Invalid!");
            return;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (isClosingChar(c)) {
                int size = stack.size();
                for (int j = 0; j < size && j < len; j++) {
                    if (getCorrespondingChar(stack.pop()) != c) {
                        System.out.println("Invalid!");
                        return;
                    }

                    c = str.charAt(++i);
                }
            } else {
                stack.push(c);
            }
        }

        System.out.println("Valid!");
    }

    private static char getCorrespondingChar(char c) {
        switch (c) {
            case '(': return ')';
            case '{': return '}';
            case '[': return ']';
        }

        return 0;
    }

    private static boolean isClosingChar(char c) { return c == ')' || c == ']' || c == '}'; }

}
