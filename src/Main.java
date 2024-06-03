import java.io.IOException;
import java.util.HashMap;
import  java.util.Scanner;


public class Main {

    static boolean romanMode = false;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nВведите данные для расчета(q-выход): ");
            String inputData = in.nextLine();
            if (inputData.equals("q")) {
                System.out.println("Работа калькулятора завершена");
                return;
            }
            String validData = validatePrepareData(inputData);
            System.out.println(calc(validData));
        }
    }

    public static String validatePrepareData(String str) throws IOException {
        String arabicPattern = "^([1-9]|10)\\s[-+*/]\\s([1-9]|10)$";
        String romanPattern = "^(X|IX|IV|V?I{0,3})\\s[-+*/]\\s(X|IX|IV|V?I{0,3})$";
        if (str.matches(arabicPattern)) {
            romanMode = false;
            return str;
        } else if (str.matches(romanPattern)) {
            romanMode = true;
            String[] chars = str.split(" ");
            String num1 = RomanNumbers.convertToArabic(chars[0]);
            String num2 = RomanNumbers.convertToArabic(chars[2]);
            String arabicStr = num1 +" "+ chars[1] +" "+num2;
            if (arabicStr.matches(arabicPattern)) {
                return arabicStr;
            } else {
                throw new IOException();
            }
        } else {
            throw new IOException();
        }
    }

    public static String calc(String input) throws IOException {
        String[] chars = input.split(" ");
        int number1 = Integer.parseInt(chars[0]);
        int number2 = Integer.parseInt(chars[2]);
        String output = switch (chars[1]) {
            case "+" -> Integer.toString(number1 + number2);
            case "-" -> Integer.toString(number1 - number2);
            case "*" -> Integer.toString(number1 * number2);
            case "/" -> Integer.toString(number1 / number2);
            default -> null;
        };
        if (romanMode) {
            if (Integer.parseInt(output) >= 1) {
                return RomanNumbers.convertFromArabic(output);
            } else {
                throw new IOException();
            }

        }
        return output;
    }

}

class RomanNumbers {

    public static String convertToArabic(String romanNumber) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);

        int end = romanNumber.length()-1;
        char[] arr = romanNumber.toCharArray();
        int arabian;
        int result = map.get(arr[end]);
        for (int i = end-1; i >=0; i--) {
            arabian = map.get(arr[i]);

            if (arabian < map.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }

        }
        return Integer.toString(result);

    }

    public static String convertFromArabic(String arabicNumber) {
        int x = Integer.parseInt(arabicNumber);
        int units = x%10;
        int tens = (x%100)/10;
        int hundreds = (x%1000)/100;
        return Hundreds(hundreds) + Tens(tens) + Units(units);
    }
    public static String Units(int units) {
        String str_units = "";
        switch (units) {
            case 1: str_units = "I";
            break;
            case 2: str_units = "II";
            break;
            case 3: str_units = "III";
            break;
            case 4: str_units = "IV";
            break;
            case 5: str_units = "V";
            break;
            case 6: str_units = "VI";
            break;
            case 7: str_units = "VII";
            break;
            case 8: str_units = "VIII";
            break;
            case 9: str_units = "IX";
            break;
        }
        return str_units;

    }
    public static String Tens(int tens) {
        String str_tens = "";
        switch (tens) {
            case 1: str_tens = "X";
            break;
            case 2: str_tens = "XX";
            break;
            case 3: str_tens = "XXX";
            break;
            case 4: str_tens = "XL";
            break;
            case 5: str_tens = "L";
            break;
            case 6: str_tens = "LX";
            break;
            case 7: str_tens = "LXX";
            break;
            case 8: str_tens = "LXXX";
            break;
            case 9: str_tens = "XC";
            break;
        }
        return str_tens;
    }
    public static String Hundreds(int hundreds) {
        String str_hundreds = "";
        switch (hundreds) {
            case 1: str_hundreds = "C";
            break;
        }
        return str_hundreds;
    }

}