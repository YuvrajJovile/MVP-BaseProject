package com.baseproject.common;


public interface Constants {


    interface RequestCodes {
        int KEY_REQUEST_GALLERY_IMAGE = 101;
        int PERMISSION_CONTACTS = 102;
        int PERMISSION_STORAGE = 104;
        int PERMISSION_GPS = 103;
    }

    interface ApiRequestKey {
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1001;
    }


    interface InternalHttpCode {
        int SUCCESS_CODE = 200;
        int UNAUTH_CODE = 401;
        int ERROR_CODE = 400;
        int FAILURE_CODE = 0;
        int NOT_FOUND = 404;

    }

    interface HttpErrorMessage {
        String INTERNAL_SERVER_ERROR = "server maintance error";
    }


    interface Permission {
        int SMS_READ_REQUEST_CODE = 10001;
        int KEY_PERMISSION_REQUEST_WRITEEXTERNALSTORAGE = 2000;
        int KEY_PERMISSION_REQUEST_READEXTERNALSTORAGE = 3000;
        int KEY_PERMISSION_REQUEST_READCONTACTS = 4000;
        int KEY_PERMISSION_REQUEST_GPS = 5000;
    }

    interface Header {

        String HEADER_CONTENT_TYPE = "Content-Type: application/json";
    }


}
