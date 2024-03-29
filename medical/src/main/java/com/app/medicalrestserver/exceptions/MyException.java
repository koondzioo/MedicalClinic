package com.app.medicalrestserver.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException{

    private ExceptionInfo exceptionInfo;

    public MyException(String description) {
        this.exceptionInfo = new ExceptionInfo(description);
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }
}
