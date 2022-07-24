import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String expression = in.nextLine(); //Введите выражение
        System.out.println(calc(expression));
    }

    public static String calc(String input) throws Exception {
        input = input.replaceAll(" ", "");

        String sign = getExpressionSign(input);

        String[] numbers = input.split("[-+*/]",2);
        if (numbers.length != 2) {
            throw new Exception("The format of the mathematical operation does not satisfy the task");
        }
        String numberStr1 = numbers[0];
        String numberStr2 = numbers[1];

        int number1, number2;
        boolean bothArabic = isArabic(numberStr1) && isArabic(numberStr2);
        if (isRoman(numberStr1) && isRoman(numberStr2)) {
            number1 = romanToInt(numberStr1);
            number2 = romanToInt(numberStr2);
        } else if (bothArabic) {
            number1 = arabicToInt(numberStr1);
            number2 = arabicToInt(numberStr2);
        } else throw new Exception("Numbers not valid or different number systems are used simultaneously");

        if (number1 > 10 || number2 > 10) {
            throw new Exception("The entered numbers are greater than 10");
        }

        int resultOfExpression = switch (sign) {
            case "+" -> add(number1, number2);
            case "-" -> sub(number1, number2);
            case "*" -> mul(number1, number2);
            case "/" -> div(number1, number2);
            default -> throw new Exception("Unknown operation");
        };

        if (bothArabic) {
            return Integer.toString(resultOfExpression);
        } else
            return arabicToRoman(resultOfExpression);
    }

    public static String getExpressionSign(String exp) throws Exception {
        String[] signs = {"+", "-", "*", "/"};
        for (String currentSign : signs) {
            if (exp.contains(currentSign)) {
                return currentSign;
            }
        }
        throw new Exception("A string is not a mathematical operation");
    }

    public static boolean isRoman(String roman) {
        return roman.matches("[IXV]*");
    }

    public static boolean isArabic(String arabic) {
        return arabic.matches("\\d*");
    }

    public static int romanToInt(String x) {
        String[] roman = x.split("");
        int[] numbers = new int[roman.length];
        for (int i = 0; i < roman.length; i++) {
            numbers[i] = valuesOfArabicNumbers.valueOf(roman[i]).number;
        }
        int result = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (i == numbers.length - 1) {
                result += numbers[i];
            } else if (numbers[i + 1] > numbers[i]) {
                result -= numbers[i];
            } else {
                result += numbers[i];
            }
        }
        return result;
    }

    public static int arabicToInt(String x) {
        return parseInt(x);
    }

    public static String arabicToRoman(int x) throws Exception {
        if (x <= 0) {
            throw new Exception("The Roman number cannot be less than 1");
        }
        String result = "";
        while (x != 0) {
            for (valuesOfArabicNumbers val : valuesOfArabicNumbers.values()) {
                if (x == val.number - 1) {
                    result = result.concat(valuesOfArabicNumbers.I + val.toString());
                    x -= val.number - 1;
                    break;
                } else if (x >= val.number) {
                    result = result.concat(val.toString());
                    x -= val.number;
                    break;
                }
            }
        }
        return result;
    }

    public static int add(int x, int y) {
        return x + y;
    }

    public static int sub(int x, int y) {
        return x - y;
    }

    public static int mul(int x, int y) {
        return x * y;
    }

    public static int div(int x, int y) {
        return x / y;
    }

}