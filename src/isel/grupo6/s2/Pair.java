package isel.grupo6.s2;

public class Pair<E,F> {

    public E first;
    public F second;

    public Pair(E first, F second){
        this.first=first;
        this.second=second;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o){
        if(!(o instanceof Pair))
            return false;

        Pair<E, F> pair = (Pair<E,F>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    public String toString(){
        return "(" + first +"," + second +")";
    }

}
