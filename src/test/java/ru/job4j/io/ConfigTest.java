package ru.job4j.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("foo")).isEqualTo("bar");
    }

    @Test
    public void whenWithComment() {
        String path = "./data/pair_with_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("qwe")).isEqualTo("rty");
    }

    @Test
    public void whenWithBlankLines() {
        String path = "./data/pair_with_blank_lines.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("one")).isEqualTo("1");
    }

    @Test
    public void whenPairWithMultipleAssignmentSymbols() {
        String path = "./data/pair_with_multiple_assignment_symbols.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("foo")).isEqualTo("bar=2==x");
    }

    @Test
    public void whenMissingKeyThenThrowException() {
        String path = "./data/pair_with_missing_key.propertiez";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid property line: =bar");
    }

    @Test
    public void whenMissingValueThenThrowException() {
        String path = "./data/pair_with_missing_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid property line: foo=");
    }

    @Test
    public void whenMissingAssignmentSymbolThenThrowException()  {
        String path = "./data/missing_assignment_symbol.propertiez";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid property line: key_without_value");
    }
}