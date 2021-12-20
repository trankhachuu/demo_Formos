package formos.demo.executor.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseRespone<T> {

    private String message;
    private String errorDescription;
    boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
