package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {

    @Test
    public void whenToArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] result = simpleConvert.toArray("one", "two", "three", "four", "five");
        assertThat(result)
                .isEqualTo(new String[]{"one", "two", "three", "four", "five"})
                .contains("one", "three", "five")
                .doesNotContain("seven")
                .startsWith("one")
                .endsWith("five")
                .containsAnyOf("three", "seven", "ten")
                .containsExactlyInAnyOrder("four", "two", "five", "one", "three")
                .hasSize(5);
    }

    @Test
    public void whenToList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> result = simpleConvert.toList("black", "white", "red", "green", "blue");
        assertThat(result)
                .contains("white", "green")
                .doesNotContain("magenta")
                .startsWith("black")
                .endsWith("blue")
                .hasSize(5)
                .containsExactly("black", "white", "red", "green", "blue")
                .containsExactlyInAnyOrder("red", "green", "blue", "black", "white")
                .containsAll(List.of("red", "blue"))
                .containsExactlyElementsOf(List.of("black", "white", "red", "green", "blue"))
                .containsSequence("white", "red");
    }

    @Test
    public void whenToSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> result = simpleConvert.toSet("black", "white", "red", "green", "blue");
        assertThat(result)
                .contains("white", "green")
                .doesNotContain("magenta")
                .hasSize(5)
                .containsExactlyInAnyOrder("red", "green", "blue", "black", "white")
                .containsAll(Set.of("red", "blue"))
                .containsExactlyInAnyOrderElementsOf(Set.of("black", "white", "red", "green", "blue"));
    }

    @Test
    public void whenToMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> result = simpleConvert.toMap("zero", "one", "two", "three", "four", "five");
        assertThat(result)
                .hasSize(6)
                .containsKeys("two", "four")
                .containsValues(1, 3, 5)
                .doesNotContainKey("hundred")
                .doesNotContainValue(33)
                .containsAnyOf(Map.entry("hundred", 100), Map.entry("three", 3))
                .containsOnlyKeys("one", "two", "three", "zero", "four", "five")
                .containsEntry("five", 5);
    }
}