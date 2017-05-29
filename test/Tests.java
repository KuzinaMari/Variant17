


import org.junit.jupiter.api.Tag;
        import org.junit.jupiter.api.Test;
import variant17.DoubleNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;


class tests {
    private DoubleNumber m1 = new DoubleNumber("12.119");
    private DoubleNumber m2 = new DoubleNumber("11.0");
    private DoubleNumber m3 = new DoubleNumber("23.119");
    private DoubleNumber m4 = new DoubleNumber("9.419");
    private DoubleNumber m5 = new DoubleNumber("6.001");
    private DoubleNumber m6 = new DoubleNumber("3.418");
    private DoubleNumber m7 = new DoubleNumber("133.309");


    @Test
    @Tag("toFloat")
    void toFloat() {
        assertEquals(m1.toFloat(), 12, 419);
    }


    @Test
    @Tag("toLong")
    void toLong(){
        assertEquals(m2.toLong(), 11);
    }

    @Test
    @Tag("toIntE")
    void toIntE() {
        try {
            new DoubleNumber("156.76876").toIntE();
            assertEquals("IllegalArgumentException", "---");
        } catch (IllegalArgumentException e) {
            assertEquals(12, 12);
        }
    }

    @Test
    @Tag("toDouble")
    void toDouble() {
        assertEquals(12.419, m1.toDouble());
    }

    @Test
    @Tag("curcle")
    void curcle() {
        assertEquals(12.42, m1.round(2).toDouble());
    }

    @Test
    @Tag("Sum")
    void sum() {
        assertEquals(m3, m1.sum(m2));
    }

    @Test
    @Tag("Minus")
    void minus() {
        assertEquals(m6, m4.minus(m5));
    }
    @Test
    @Tag("Mult")
    void multi() {
        assertEquals(m7, m2.multi(m1));
    }
}
