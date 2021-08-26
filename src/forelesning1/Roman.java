package forelesning1;

public class Roman {

    private static final String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };

    private static final int[] romanValues = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };

    public static String convert(int input){

        String output = "";
        int decimal = input;

        while (decimal > 0) {
            forloop:
            for (int i = romanValues.length - 1; i >= 0; i--) {
                if (romanValues[i] <= decimal) {
                    output += romanNumerals[i];
                    decimal -= romanValues[i];
                    break forloop;
                }
            }
        }
        return output;
    }
}
