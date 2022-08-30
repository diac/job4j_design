package ru.job4j.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class ForwardLinkedTest {

    private ForwardLinked<Integer> linked;

    @BeforeEach
    public void init() {
        linked = new ForwardLinked<>();
        linked.add(1);
        linked.add(2);
        linked.add(3);
        linked.add(4);
    }

    @Test
    void whenDeleteFirst() {
        assertThat(linked.deleteFirst()).isEqualTo(1);
        assertThat(linked.deleteFirst()).isEqualTo(2);
        assertThat(linked.deleteFirst()).isEqualTo(3);
        assertThat(linked.deleteFirst()).isEqualTo(4);
        assertThatThrownBy(linked.iterator()::next)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenDeleteEmptyLinked() {
        ForwardLinked<Integer> linked = new ForwardLinked<>();
        assertThatThrownBy(linked::deleteFirst)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void whenMultiDelete() {
        linked.deleteFirst();
        Iterator<Integer> it = linked.iterator();
        assertThat(it.next()).isEqualTo(2);
    }

    @Test
    void whenSize0ThenReturnFalse() {
        ForwardLinked<Integer> forwardLinked = new ForwardLinked<>();
        assertThat(forwardLinked.revert()).isFalse();
    }

    @Test
    void whenSize1ThenReturnFalse() {
        ForwardLinked<Integer> forwardLinked = new ForwardLinked<>();
        forwardLinked.add(1);
        assertThat(forwardLinked.revert()).isFalse();
    }

    @Test
    void whenAddAndRevertTrue() {
        ForwardLinked<Integer> forwardLinked = new ForwardLinked<>();
        forwardLinked.add(1);
        forwardLinked.add(2);
        forwardLinked.add(3);
        forwardLinked.add(4);
        assertThat(forwardLinked).containsSequence(1, 2, 3, 4);
        assertThat(forwardLinked.revert()).isTrue();
        assertThat(forwardLinked).containsSequence(4, 3, 2, 1);
    }
}