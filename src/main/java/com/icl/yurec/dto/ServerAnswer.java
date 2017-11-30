package com.icl.yurec.dto;

import java.io.Serializable;

public class ServerAnswer<T> implements Serializable {

    private boolean success;

    private T result;

    public ServerAnswer(final boolean success, final T result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
