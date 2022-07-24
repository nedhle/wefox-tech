package app.enums;

public enum PaymentErrorEnum {
    NETWORK("Network Error"),
    DATABASE("Database Error"),
    INVALID_PAYMENT("Invalid Payment"),
    INTERNAL("Internal Error"),
    ACCOUNT_DOES_NOT_EXIST("Account Does Not Exist");

    private String message;

    PaymentErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
