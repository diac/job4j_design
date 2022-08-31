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
        modCount++;
        expand();
        int hashCode = key != null ? key.hashCode() : 0;
        int index = indexFor(hash(hashCode));
        if (index < capacity && table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            result = true;
        }
        return result;
    }

    private int hash(int hashCode) {
        return (hashCode) ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        if (count >= table.length * LOAD_FACTOR) {
            table = Arrays.copyOf(table, table.length * 2);
            capacity = table.length;
            rehashNodes();
        }
    }

    @Override
    public V get(K key) {
        int hashCode = key != null ? key.hashCode() : 0;
        int index = indexFor(hash(hashCode));
        V result = null;
        if (key != null && table[index] != null && table[index].key != null) {
            if (key.hashCode() == table[index].key.hashCode() || key.equals(table[index].key)) {
                result = table[index].value;
            }
        } else if (key == null && Objects.equals(key, table[index].key)) {
            result = table[index].value;
        }
        return result;
    }

    /**
     * Метод возвращает n-ный не равный null индекс из table
     * @param n Порядковый номер не равного null индекса из table
     * @return Индекс, если найден по n. -1 -- если индекс не найден
     */
    public int nthIndex(int n) {
        int notNullCount = 0;
        int result = -1;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                notNullCount++;
            }
            if (notNullCount - 1 == n) {
                result = i;
                break;
            }
        }
        return result;
    }

    private void rehashNodes() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                int hashCode = table[i].key != null ? table[i].key.hashCode() : 0;
                int newIndex = indexFor(hash(hashCode));
                MapEntry<K, V> temp = table[i];
                table[i] = table[newIndex];
                table[newIndex] = temp;
            }
        }
    }

    @Override
    public boolean remove(K key) {
        int hashCode = key != null ? key.hashCode() : 0;
        int index = indexFor(hash(hashCode));
        boolean result = table[index] != null;
        modCount++;
        if (result) {
            table[index] = null;
            count--;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {

            private int iteratorIndex;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return nthIndex(iteratorIndex) >= 0;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[nthIndex(iteratorIndex++)].key;
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
