package com.meetpatel.lld_cache.algorithms;

import com.meetpatel.lld_cache.algorithms.exceptions.InvalidElementException;

public class DoublyLinkedList<E> {

    private final DoublyLinkedListNode<E> head;
    private final DoublyLinkedListNode<E> tail;

    public DoublyLinkedList() {
        head = new DoublyLinkedListNode<>(null);
        tail = new DoublyLinkedListNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    public void detachNode(DoublyLinkedListNode<E> node) {
        if (node != null) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    public void addNodeAtLast(DoublyLinkedListNode<E> node) {
        DoublyLinkedListNode tailPrev = tail.prev;
        tailPrev.next = node;
        node.prev = tailPrev;
        node.next = tail;
        tail.prev = node;
    }

    public DoublyLinkedListNode<E> addElementAtLast(E element) {
        if (element == null) {
            throw new InvalidElementException();
        }
        DoublyLinkedListNode<E> newNode = new DoublyLinkedListNode<>(element);
        addNodeAtLast(newNode);
        return newNode;
    }

    private boolean isItemPresent() {
        return head.next != tail;
    }

    public DoublyLinkedListNode<E> getFirstNode() {
        if (!isItemPresent()) {
            return null;
        }
        return head.next;
    }

    public DoublyLinkedListNode<E> getLastNode() {
        if (!isItemPresent()) {
            return null;
        }
        return tail.prev;
    }

}
