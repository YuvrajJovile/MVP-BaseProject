package com.baseproject.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by yuvaraj_android on 7/6/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BaseRequest {
    private String authToken;
    private String userId;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
