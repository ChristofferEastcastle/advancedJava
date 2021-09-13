package forelesning1;

import forelesning1.Roman;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestToRoman {

    @Test
    void convertTo1() {
        String expected = "I";
        Assertions.assertEquals(expected, Roman.convertToRoman(1));
    }

    @Test
    void convertTo4() {
        String expected = "IV";
        assertEquals(expected, Roman.convertToRoman(4));
    }

    @Test
    void convertTo50() {
        String expected = "L";
        assertEquals(expected, Roman.convertToRoman(50));
    }

    @Test
    void convertTo93() {
        String expected = "XCIII";
        assertEquals(expected, Roman.convertToRoman(93));
    }

    @Test
    void convertTo314() {
        String expected = "CCCXIV";
        assertEquals(expected, Roman.convertToRoman(314));
    }

    @Test
    void convertTo1792() {
        String expected = "MDCCXCII";
        assertEquals(expected, Roman.convertToRoman(1792));
    }

    @Test
    void convertTo4999() {
        String expected = "MMMMCMXCIX";
        assertEquals(expected, Roman.convertToRoman(4999));
    }

    @Test
    void convertXIXToDecimal() {
        int expected = 19;
        assertEquals(expected, Roman.convertToDecimal("XIX"));
    }

    @Test
    void convertMMMMCMXCIXToDecimal() {
        int expected = 4999;
        assertEquals(expected, Roman.convertToDecimal("MMMMCMXCIX"));
    }

    @Test
    void convertXIIIToDecimal() {
        int expected = 13;
        assertEquals(expected, Roman.convertToDecimal("XIII"));
    }

    @Test
    void convertMCMIIIToDecimal() {
        int expected = 1903;
        assertEquals(expected, Roman.convertToDecimal("MCMIII"));
    }

    @Test
    void convertLXXIVToDecimal() {
        int expected = 74;
        assertEquals(expected, Roman.convertToDecimal("LXXIV"));
    }
}