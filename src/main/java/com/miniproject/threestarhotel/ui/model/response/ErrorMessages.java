package com.miniproject.threestarhotel.ui.model.response;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field"),
    EMAIL_ALREADY_EXISTS("Email already exists. Use another one"),
    FAILED_AUTHENTICATION("Wrong username or password"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    USER_NOT_FOUND("User not found in the database. Please register first"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
