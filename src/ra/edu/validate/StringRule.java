package ra.edu.validate;

public class StringRule {
    private int minLength;
    private int maxLength;
    private String message;

    public StringRule() {
        this.minLength = 0;
        this.maxLength = Integer.MAX_VALUE;
    }

    public StringRule(int minLength, int maxLength, String message) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValid(String input) {
        return input.length() > minLength && input.length() <= maxLength;
    }
}
