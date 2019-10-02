package com.app.medicalrestserver.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ExceptionInfo {
    private LocalDateTime dateTime;
    private String description;

    public ExceptionInfo(String description) {
        this.dateTime = LocalDateTime.now();
        this.description = description;
    }
}
