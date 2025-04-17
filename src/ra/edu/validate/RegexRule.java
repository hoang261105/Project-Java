package ra.edu.validate;

import java.util.regex.Pattern;

public class RegexRule {
    private String regex;
    private String errorMessage;

    public RegexRule(String regex, String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid(String input) {
        return Pattern.matches(regex, input);
    }
}
