package com.sap.jnc.marketing.service.exception;

import java.io.Serializable;

/**
 * Created by dy on 16/7/5.
 */
public class CommonBusinessException extends RuntimeException {

    private ErrorMessage errorMessage;

    public CommonBusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorCode());
        ErrorMessage errorMessage = new ErrorMessage(errorCode.getErrorCode(), null);
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(String message) {
        super(message);
        ErrorMessage errorMessage = new ErrorMessage(null, message);
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(String message, Throwable cause) {
        super(message, cause);
        ErrorMessage errorMessage = new ErrorMessage(null, message);
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }

    public CommonBusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage.getErrorMessage(), cause);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public static class ErrorMessage implements Serializable {

        private String errorCode;

        private String errorMessage;

        public ErrorMessage(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }

    public static class ErrorCode {

        private String errorCode;

        public ErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }

    public static class ErrorCodeBuilder {

        public static ErrorCode addErrorCode(String errorCode) {
            return new ErrorCode(errorCode);
        }
    }
}
