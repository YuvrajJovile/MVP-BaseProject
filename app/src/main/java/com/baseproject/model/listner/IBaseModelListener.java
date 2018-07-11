package com.baseproject.model.listner;


import com.baseproject.library.CustomException;

/**
 * Created by guru on 1/6/2017
 */

public interface IBaseModelListener<T> {

    void onSuccessfulApi(long taskId, T response);

    void onFailureApi(long taskId, CustomException e);


}
