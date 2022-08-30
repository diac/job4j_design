package ru.job4j.collection;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private transient Node<E> first;

    private transient Node<E> last;

    private transient int size;

    private int modCount;

    @Override
    public void add(E value) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Iterator<E> iterator = iterator();
        for (var i = 0; i < index; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }
        return iterator.next();
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<>() {
            private int index;

            private final int expectedModCount = modCount;

            private Node<E> current = first;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                index++;
                E result = current.item;
                current = current.next;
                return result;
            }
        };
    }
}
