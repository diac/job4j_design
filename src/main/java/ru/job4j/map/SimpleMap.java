package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        expand();
        int hashCode = Objects.hashCode(key);
        int index = indexFor(hash(hashCode));
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            result = true;
        }
        return result;
    }

    private int hash(int hashCode) {
        return (hashCode) ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        if (count >= table.length * LOAD_FACTOR) {
            capacity = table.length * 2;
            MapEntry<K, V>[] newTable = new MapEntry[capacity];
            for (MapEntry<K, V> oldTableEntry : table) {
                if (oldTableEntry != null) {
                    int hashCode = Objects.hashCode(oldTableEntry.key);
                    int newIndex = indexFor(hash(hashCode));
                    newTable[newIndex] = oldTableEntry;
                }
            }
            table = newTable;
        }
    }

    @Override
    public V get(K key) {
        int keyHashCode = Objects.hashCode(key);
        int index = indexFor(hash(keyHashCode));
        V result = null;
        if (table[index] != null) {
            if (keyHashCode == Objects.hashCode(table[index].key)
                            && Objects.equals(key, table[index].key)) {
                result = table[index].value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        int hashCode = Objects.hashCode(key);
        int index = indexFor(hash(hashCode));
        boolean result = false;
        if (table[index] != null) {
            if (hashCode == Objects.hashCode(table[index].key)
                    && Objects.equals(key, table[index].key)) {
                table[index] = null;
                result = true;
                count--;
                modCount++;
            }
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {

            private int index;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }

        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
