package forelesning1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestToRoman {

    @Test
    void convertTo1() {
        String expected = "I";
        assertEquals(expected, Roman.convert(1));
    }

    @Test
    void convertTo4() {
        String expected = "IV";
        assertEquals(expected, Roman.convert(4));
    }

    @Test
    void convertTo50() {
        String expected = "L";
        assertEquals(expected, Roman.convert(50));
    }

    @Test
    void convertTo93() {
        String expected = "XCIII";
        assertEquals(expected, Roman.convert(93));
    }

    @Test
    void convertTo314() {
        String expected = "CCCXIV";
        assertEquals(expected, Roman.convert(314));
    }

    @Test
    void convertTo1792() {
        String expected = "MDCCXCII";
        assertEquals(expected, Roman.convert(1792));
    }
}