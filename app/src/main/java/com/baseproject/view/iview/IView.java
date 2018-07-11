package com.baseproject.view.iview;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.baseproject.library.CustomException;
import com.baseproject.presenter.ipresenter.IPresenter;
import com.baseproject.utils.CodeSnippet;


/**
 * Created by Yuvi on 22/06/17.
 */

public interface IView {

    void showMessage(String message);

    void showMessage(int resId);

    void showMessage(CustomException e);

    void showProgressbar();

    void dismissProgressbar();

    FragmentActivity getActivity();

    void showSnackBar(String message);

    void showSnackBar(@NonNull View view, String message);

    void showNetworkMessage();

    void bindPresenter(IPresenter iPresenter);

    CodeSnippet getCodeSnippet();

}
