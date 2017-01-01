package Week2.A2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Jashan Shewakramani
 * Description: Doubly-Linked list to store a deck
 */
public class Deque<Item> implements Iterable<Item>{
    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item s) {
        if (s == null)
            throw new NullPointerException("Do not add null items");

        if (first == null) {
            Node t = new Node();
            t.item = s;
            first = t;
            last = t;
        } else {
            Node oldFirst = first;
            Node newFirst = new Node();
            newFirst.item = s;
            newFirst.next = oldFirst;
            oldFirst.previous = newFirst;
            first = newFirst;
        }

        size++;
    }

    public void addLast(Item s) {
        if (s== null)
            throw new NullPointerException("Do not add null items");

        if (last == null) {
            Node t = new Node();
            t.item = s;
            last = t;
            first = t;
        } else {
            Node oldLast = last;
            Node newLast = new Node();
            newLast.item = s;
            newLast.previous = oldLast;
            oldLast.next = newLast;
            last = newLast;
        }

        size++;
    }

    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException("Dequeue is empty");

        Item item = first.item;
        Node second = first.next;

        if (second == null) {
            first = null;
            last = null;
        } else {
            second.previous = null;
            first = second;
        }

        size--;
        return item;
    }

    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException("Dequeue is empty");

        Item item = last.item;
        Node secondLast = last.previous;

        if (secondLast == null) {
            first = null;
            last = null;
        } else {
            secondLast.next = null;
            last = secondLast;
        }

        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("Iterator does not have any more elements");
            Item t = current.item;
            current = current.next;
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method unsupported in iterator");
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("hello world");
        deque.addFirst("chocolate");
        deque.addLast("my");
        deque.addLast("name");


        System.out.println("Dequeue size: " + deque.size() + "\n");

        for (String s: deque)
            System.out.println(s);

        System.out.println("=================\n");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.isEmpty());

        System.out.println("Dequeue size: " + deque.size() + "\n");

        System.out.println("=================\n");
        for (String s: deque)
            System.out.println(s);

        for (int i = 0; i < 5; i++)
            deque.addLast(String.valueOf(i));

        System.out.println(deque.isEmpty());

        for (String s: deque)
            System.out.println(s);
    }
}
