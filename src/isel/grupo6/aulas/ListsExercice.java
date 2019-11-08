package isel.grupo6.aulas;

public class ListsExercice {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please insert k value!");
            return;
        }

        int k = 0;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for k!");
            return;
        }

        StackList<String> list = new StackList<>();
        list.push("si1");
        list.push("psc");
        list.push("egp");
        list.push("com");
        list.push("aed");
        System.out.println("K Highest Element is " + getKHighestElement(list.top, k));

    }

    public static String getKHighestElement(StackList.Node<String> node, int k) {
        StackList.Node<String> aux = node;
        for (int i = 0; i < k; i++, aux = aux.next);
        for (; node.next != null; node = node.next, aux = aux.next);
        return aux.element;
    }

}
