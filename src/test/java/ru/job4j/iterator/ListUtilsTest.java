package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected = List.of(1, 3, 5, 7, 9);
        Predicate<Integer> predicate = (number) -> number % 2 == 0;
        ListUtils.removeIf(list, predicate);
        assertThat(list).isEqualTo(expected);
    }

    @Test
    public void whenReplaceIf() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected = List.of(1, 100, 3, 100, 5, 100, 7, 100, 9);
        Predicate<Integer> predicate = (number) -> number % 2 == 0;
        ListUtils.replaceIf(list, predicate, 100);
        assertThat(list).isEqualTo(expected);
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 0, 0, 2, 3, 0, 4, 5, 0, 1000, 6, 7, 1000, 8, 9));
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        ListUtils.removeAll(list, List.of(0, 1000));
        assertThat(list).isEqualTo(expected);
    }
}