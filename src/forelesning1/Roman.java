package forelesning1;

public class Roman {

    private static final String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    private static final int[] romanValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

    public static String convertToRoman(int input) {

        String output = "";
        int decimal = input;

        while (decimal > 0) {
            for (int i = romanValues.length - 1; i >= 0; i--) {
                if (romanValues[i] <= decimal) {
                    output += romanNumerals[i];
                    decimal -= romanValues[i];
                    break;
                }
            }
        }
        return output;
    }

    public static int convertToDecimal(String input) {
        input = input.toUpperCase();
        String str = input;
        int decimal = 0;

        while (!str.equals("")) {
            if (str.length() == 1) {
                decimal += getValue(str.charAt(0));
                break;
            }
            int currentValue = getValue(str.charAt(0));
            int nextValue = getValue(str.charAt(1));

            if (currentValue >= nextValue) decimal += currentValue;

            else decimal -= currentValue;

            str = str.substring(1);
        }
        return decimal;
    }

    private static int getValue(char numeral) {
        for (int i = 0; i < romanNumerals.length; i++) {
            if (Character.toString(numeral).equals(romanNumerals[i])) {
                return romanValues[i];
            }
        }
        return -1;
    }
}