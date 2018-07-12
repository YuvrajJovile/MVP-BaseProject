package com.baseproject.presenter;

import android.content.Intent;

import com.baseproject.presenter.ipresenter.IPresenter;
import com.baseproject.utils.CodeSnippet;
import com.baseproject.view.iview.IView;

public abstract class BasePresenter implements IPresenter {
    protected String TAG = getClass().getSimpleName();
    CodeSnippet mCodeSnippet;

    private IView iView;

    public BasePresenter(IView iView) {
        this.iView = iView;
        iView.bindPresenter(this);
    }

    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onStopPresenter() {

    }

    @Override
    public void onPausePresenter() {

    }

    @Override
    public void onResumePresenter() {

    }

    @Override
    public void onDestroyPresenter() {

    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {

    }
}
