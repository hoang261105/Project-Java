package ra.edu.business.model;

import ra.edu.validate.ExistRule;

public class ExcludeId extends ExistRule {
    private String excludeId;

    public ExcludeId(String type, String errorMessage, String excludeId) {
        super(type, errorMessage);
        this.excludeId = excludeId;
    }

    public String getExcludeId() {
        return excludeId;
    }
}
