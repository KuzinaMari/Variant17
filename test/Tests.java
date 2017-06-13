





import org.junit.jupiter.api.Tag;
        import org.junit.jupiter.api.Test;
import variant17.DecimalNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class tests {
    private DecimalNumber m1 = new DecimalNumber("12.119");
    private DecimalNumber m2 = new DecimalNumber("11.0");
    private DecimalNumber m3 = new DecimalNumber("23.119");
    private DecimalNumber m4 = new DecimalNumber("9.419");
    private DecimalNumber m5 = new DecimalNumber("6.001");
    private DecimalNumber m6 = new DecimalNumber("3.418");
    private DecimalNumber m7 = new DecimalNumber("133.309");


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
    @Tag("toDouble")
    void toDouble() {
        assertEquals(12.119, m1.toDouble());
    }

    @Test
    @Tag("round")
    void round() {
        assertEquals("12.12", m1.round(2).toString());
        assertEquals("12", new DecimalNumber( "12.03" ).round(1).toString());
        assertEquals("-12", new DecimalNumber( "-12.03" ).round(1).toString());
    }

    private static DecimalNumber numb( String str ){
        return new DecimalNumber( str );
    }

    @Test
    @Tag("Sum")
    void sum() {
        assertTrue( numb( "0.209" ).equals( numb( "0.099" ).sum( numb( "0.11" ) ) ) );
        assertTrue( m3.equals( m1.sum(m2) ) ); //assertEquals(m3, m1.sum(m2));
    }

    @Test
    @Tag("Minus")
    void minus() {
        assertTrue( m6.equals( m4.minus(m5) ) );
    }
    @Test
    @Tag("product")
    void product() {
        assertTrue(m7.equals( m2.product(m1) ) );
    }
}
