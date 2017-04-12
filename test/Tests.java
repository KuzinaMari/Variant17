


import org.junit.jupiter.api.Tag;
        import org.junit.jupiter.api.Test;
        import variant17.Double_number;

        import static org.junit.jupiter.api.Assertions.assertEquals;


class tests {
    private Double_number m1 = new Double_number("12.419");
    private Double_number m2 = new Double_number("12.0");
    private Double_number m3 = new Double_number("24.419");
    private Double_number m4 = new Double_number("-0.419");
    private Double_number m5 = new Double_number("149.028");


    @Test
    @Tag("toFloat")
    void toFloat() {
        assertEquals(m1.toFloat(), 12, 419);
    }


    @Test
    @Tag("toLong")
    void toLong() {
        assertEquals(m2.toLong(), 78767);
    }

    @Test
    @Tag("toIntE")
    void toIntE() {
        try {
            new Double_number("156.76876").toIntE();
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
        assertEquals(12.42, m1.curcle(2).toDouble());
    }

    @Test
    @Tag("Sum")
    void sum() {
        assertEquals(m3, m1.sum(m2));
    }

    @Test
    @Tag("Minus")
    void minus() {
        assertEquals(m4, m2.minus(m1));
    }
    @Test
    @Tag("Mult")
    void multi() {
        assertEquals(m5, m2.multi(m1));
    }
}
