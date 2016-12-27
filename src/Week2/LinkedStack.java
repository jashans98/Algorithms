package Week2;

/**
 * Created by Jashan Shewakramani
 * Description: Stack to store strings, using a linked list implementation
 */
public class LinkedStack {
    // this is the only reference we need
    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public String pop() {
        String item = first.item;
        first = first.next;
        return item;
    }
}
