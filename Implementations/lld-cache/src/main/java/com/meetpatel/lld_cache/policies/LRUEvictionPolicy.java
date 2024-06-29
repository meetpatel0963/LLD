package com.meetpatel.lld_cache.policies;

import com.meetpatel.lld_cache.algorithms.DoublyLinkedList;
import com.meetpatel.lld_cache.algorithms.DoublyLinkedListNode;

import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {
    private final DoublyLinkedList<Key> dll;
    private final Map<Key, DoublyLinkedListNode<Key>> mapper;

    public LRUEvictionPolicy() {
        this.dll = new DoublyLinkedList<>();
        this.mapper = new HashMap<>();
    }

    @Override
    public void keyAccessed(Key key) {
        if (mapper.containsKey(key)) {
            DoublyLinkedListNode<Key> node = mapper.get(key);
            dll.detachNode(node);
            dll.addNodeAtLast(node);
        } else {
            DoublyLinkedListNode<Key> newNode = dll.addElementAtLast(key);
            mapper.put(key, newNode);
        }
    }

    @Override
    public Key evictKey() {
        DoublyLinkedListNode<Key> first = dll.getFirstNode();
        if (first == null) {
            return null;
        }
        dll.detachNode(first);
        return first.getElement();
    }
}
