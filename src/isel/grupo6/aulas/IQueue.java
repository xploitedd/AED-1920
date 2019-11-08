package isel.grupo6.aulas;

public interface IQueue<E> {

    boolean isEmpty();
    boolean isFull();
    boolean offer(E elem);
    E poll();
    E peek();

}
