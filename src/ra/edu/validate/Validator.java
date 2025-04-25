package ra.edu.validate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Validator {
    public static String validateInputString(Scanner sc, String message, StringRule stringRule) {
        System.out.println(message);

        do {
            try {
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    if(stringRule.isAllowEmpty()){
                        return "";
                    }else{
                        System.err.println("Dữ liệu không được để trống!");
                        continue;
                    }
                }

                if(stringRule.isValid(input)) {
                    return input;
                }

                System.err.println(stringRule.getMessage() + " Vui lòng nhập lại!");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }

    public static int validateInputInt(Scanner sc, String message) {
        System.out.println(message);

        do {
            try {
                int input = Integer.parseInt(sc.nextLine());

                if(input >= 0){
                    return input;
                }

                System.err.println("Dữ liệu phải lớn hơn 0. Vui lòng nhập lại");
            } catch (NumberFormatException e) {
                System.err.println("Dữ liệu số thực không hợp lệ");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }

    public static boolean validateInputBoolean(Scanner sc, String message) {
        System.out.println(message);

        do {
            try {
                String input = sc.nextLine();

                if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(input);
                }

                System.err.println("Dữ liệu boolean không hợp lệ. Vui lòng nhập lại");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }

    public static LocalDate validateInputLocalDate(Scanner sc, String message) {
        System.out.println(message);

        do {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dob = LocalDate.parse(sc.nextLine(), formatter);

                if (dob.isAfter(LocalDate.now())) {
                    System.err.println("Ngày sinh không thể lớn hơn ngày hiện tại");
                    continue;
                }

                return dob;
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            System.err.println("Dữ liệu ngày không hợp lệ. Vui lòng nhâp lai");
        }while (true);
    }

    public static <T extends Enum<T>> T validateEnumInput(Scanner sc, String message, Class<T> enumClass) {
        System.out.println(message + " " + Arrays.toString(enumClass.getEnumConstants())); // Hiển thị danh sách hợp lệ

        do {
            try {
                String input = sc.nextLine().trim().toUpperCase(); // Chuyển về chữ hoa để so sánh

                return Enum.valueOf(enumClass, input); // Chuyển đổi thành Enum (nếu hợp lệ)

            } catch (IllegalArgumentException e) {
                System.err.println("Lỗi: Giá trị không hợp lệ. Vui lòng nhập một trong " + Arrays.toString(enumClass.getEnumConstants()));
            }
        } while (true);
    }

    public static String validateInputRegex(Scanner sc, String message, RegexRule regexRule) {
        System.out.println(message);

        do {
            try {
                String input = sc.nextLine();

                if (regexRule.isValid(input)) {
                    return input;
                }

                System.err.println(regexRule.getErrorMessage() + " Vui lòng nhập lại!");
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }while (true);
    }
}
