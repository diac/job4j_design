package ru.job4j.assertj;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {

    @Test
    public void whenWhatsThisThenSphere() {
        Box box = new Box(0, 10);
        String result = box.whatsThis();
        assertThat(result)
                .isNotEmpty()
                .isNotBlank()
                .isEqualTo("Sphere")
                .contains("here");
    }

    @Test
    public void whenWhatsThisThenUnknown() {
        Box box = new Box(25, 10);
        String result = box.whatsThis();
        assertThat(result)
                .isNotEmpty()
                .isNotBlank()
                .contains("object")
                .startsWith("Unknown")
                .endsWith("object")
                .doesNotContain("Cube")
                .isEqualTo("Unknown object");
    }

    @Test
    public void whenGetNumberOfVerticesIs4() {
        Box box = new Box(4, 10);
        int result = box.getNumberOfVertices();
        assertThat(result)
                .isLessThan(5)
                .isGreaterThan(3)
                .isEqualTo(4);
    }

    @Test
    public void whenGetNumberOfVerticesIs8() {
        Box box = new Box(8, 10);
        int result = box.getNumberOfVertices();
        assertThat(result)
                .isLessThan(20)
                .isGreaterThan(0)
                .isEqualTo(8);
    }

    @Test
    public void whenCubeThenIsExistTrue() {
        Box box = new Box(8, 10);
        boolean result = box.isExist();
        assertThat(result).isTrue();
    }

    @Test
    public void whenUnknownThenIsExistFalse() {
        Box box = new Box(33, 10);
        boolean result = box.isExist();
        assertThat(result).isFalse();
    }

    @Test
    public void whenGetAreaSphere() {
        Box box = new Box(0, 10);
        double result = box.getArea();
        assertThat(result)
                .isCloseTo(1256.637D, withPrecision(0.001D))
                .isCloseTo(1256.637D, Percentage.withPercentage(0.1D))
                .isGreaterThan(1000D)
                .isLessThan(1300);
    }

    @Test
    public void whenGetAreaTetrahedron() {
        Box box = new Box(4, 10);
        double result = box.getArea();
        assertThat(result)
                .isCloseTo(173.205D, withPrecision(0.001D))
                .isCloseTo(173.205D, Percentage.withPercentage(0.1D))
                .isGreaterThan(170D)
                .isLessThan(180D);
    }
}