package ra.edu.validate;

public class ExistRule {
    private String type;
    private String errorMessage;

    public ExistRule(String type, String errorMessage) {
        this.type = type;
        this.errorMessage = errorMessage;
    }

    public String getType() {
        return type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
