package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;
import java.util.Objects;

public class SimpleSet<T> implements Set<T> {

    private final SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean canAdd = !contains(value);
        if (canAdd) {
            set.add(value);
        }
        return canAdd;
    }

    @Override
    public boolean contains(T value) {
        Iterator<T> iterator = iterator();
        boolean result = false;
        while (iterator.hasNext()) {
            if (Objects.equals(iterator.next(), value)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }
}
