package com.aleprimo.nova_store.handler;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    int status;
    String message;
    String path;
    LocalDateTime timestamp;

    public ApiError(int status, String message, String path, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

}
