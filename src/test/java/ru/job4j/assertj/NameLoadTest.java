package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {

    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    public void whenParseOnEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is empty")
                .hasMessage("Names array is empty");
    }

    @Test
    public void whenParseDoesNotContainAssignmentSymbolThenIllegalArgument() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse("qwertyuiop"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("this name: qwertyuiop")
                .hasMessageContaining("does not contain the symbol \"=\"");
    }

    @Test
    public void whenParseStartsWithAssignmentSymbolThenIllegalArgument() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse("=qwertyuiop"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("this name: =")
                .hasMessageContaining("does not contain a key");
    }

    @Test
    public void whenParseDoesntContainValueThenIllegalArgument() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(() -> nameLoad.parse("foo=bar", "qwerty="))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("this name: qwerty")
                .hasMessageContaining("does not contain a value");
    }
}