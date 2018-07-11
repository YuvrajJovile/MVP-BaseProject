package com.baseproject.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Buvaneswaran on 06-12-2016
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BaseResponse {
    private int status;
    private String message;

    public BaseResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
