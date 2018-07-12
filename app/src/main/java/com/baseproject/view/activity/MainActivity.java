package com.baseproject.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baseproject.R;
import com.baseproject.model.dto.response.GetApiResponse;
import com.baseproject.presenter.MainActivityPresenter;
import com.baseproject.presenter.ipresenter.IMainActivityPresenter;
import com.baseproject.view.iview.IMainActivityView;

public class MainActivity extends BaseActivity implements IMainActivityView, View.OnClickListener {


    private IMainActivityPresenter mIMainActivityPresenter;

    private TextView mTvResult;
    private Button mbtGet, mBtPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivityPresenter = new MainActivityPresenter(this);
        mIMainActivityPresenter.onCreatePresenter(getIntent().getExtras());
        init();
    }

    private void init() {
        mTvResult = findViewById(R.id.tv_result);
        mbtGet = findViewById(R.id.bt_get);
        mBtPost = findViewById(R.id.bt_post);
        mbtGet.setOnClickListener(this);
        mBtPost.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get:
                showProgressbar();
                mIMainActivityPresenter.onClickGet();
                break;
            case R.id.bt_post:
                showProgressbar();
                mIMainActivityPresenter.onClickPost();
                break;
        }
    }

    @Override
    public void setResult(String response) {
        dismissProgressbar();
        mTvResult.setText(response);
    }
}
