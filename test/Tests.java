


import org.junit.jupiter.api.Tag;
        import org.junit.jupiter.api.Test;
        import variant17.Double_number;

        import static org.junit.jupiter.api.Assertions.assertEquals;


class tests {
    public Double_number m1 = new Double_number("12.419");
    public Double_number m2 = new Double_number("78767.0");
    public Double_number m3 = new Double_number("8753.0");


    @Test
    @Tag("toFloat")
    void toFloat()  {
        assertEquals((new Double_number("12")).toFloat(), 12);
    }


    @Test
    @Tag("toLong")
    void toLong() {
        assertEquals((new  Double_number("12")).toLong(), 12);
    }

    @Test
    @Tag("toIntE")
    void toIntE() {
        try {
            new Double_number("156.76876").toIntE();
            assertEquals("IllegalArgumentException", "---");
        } catch (IllegalArgumentException e) {
            assertEquals( 12, 12);
        }
    }

    @Test
    @Tag("toDouble")
    void toDouble() {
        assertEquals(12.42, (new Double_number("12.42")).toDouble());
    }
    @Test
    @Tag("curcle")
    void curcle() {
//System.out.print(m1+"\n"+ m2+"\n" + m3+"\n"+m4);
        assertEquals(12.42, m1.curcle(2).toDouble());
    }
}
