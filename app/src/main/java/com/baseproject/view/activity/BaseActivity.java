package com.baseproject.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baseproject.R;
import com.baseproject.common.IPermissionProducer;
import com.baseproject.library.CustomException;
import com.baseproject.library.ExceptionTracker;
import com.baseproject.library.Log;
import com.baseproject.presenter.ipresenter.IPresenter;
import com.baseproject.reciever.NetworkReceiver;
import com.baseproject.utils.CodeSnippet;
import com.baseproject.view.iview.IView;
import com.baseproject.view.widget.CustomProgressbar;

import static com.baseproject.common.Constants.Permission.KEY_PERMISSION_REQUEST_GPS;
import static com.baseproject.common.Constants.Permission.KEY_PERMISSION_REQUEST_READCONTACTS;
import static com.baseproject.common.Constants.Permission.KEY_PERMISSION_REQUEST_READEXTERNALSTORAGE;
import static com.baseproject.common.Constants.Permission.KEY_PERMISSION_REQUEST_WRITEEXTERNALSTORAGE;


public abstract class BaseActivity extends AppCompatActivity implements IView {

    private static final int WIFI_ENABLE_REQUEST = 100;
    private static final int ROAMING_REQUEST = 200;
    protected String TAG = getClass().getSimpleName();
    protected View mParentView;

    protected CodeSnippet mCodeSnippet;
    ProgressDialog pDialog;
    private IPresenter iPresenter;
    private CustomProgressbar mCustomProgressbar;
    private IPermissionProducer mIPermissionProducer;
    private boolean internetFlag = true;

    /***/
    public void callWriteExternalStoragePermission(IPermissionProducer mIPermissionProducer) {
        this.mIPermissionProducer = mIPermissionProducer;
        if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, KEY_PERMISSION_REQUEST_WRITEEXTERNALSTORAGE);
        } else {
            mIPermissionProducer.onReceivedPermissionStatus(KEY_PERMISSION_REQUEST_WRITEEXTERNALSTORAGE, true);
        }
    }


    /***/
    public void callReadExternalStoragePermission(IPermissionProducer mIPermissionProducer) {
        this.mIPermissionProducer = mIPermissionProducer;
        if (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, KEY_PERMISSION_REQUEST_READEXTERNALSTORAGE);
        } else {
            mIPermissionProducer.onReceivedPermissionStatus(KEY_PERMISSION_REQUEST_READEXTERNALSTORAGE, true);
        }
    }

    /***/
    public void callGPSPermission(IPermissionProducer mIPermissionProducer) {
        this.mIPermissionProducer = mIPermissionProducer;
        if (checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, KEY_PERMISSION_REQUEST_GPS);
        } else {
            mIPermissionProducer.onReceivedPermissionStatus(KEY_PERMISSION_REQUEST_GPS, true);
        }
    }

    /***/
    public void callContactPermission(IPermissionProducer mIPermissionProducer) {
        this.mIPermissionProducer = mIPermissionProducer;
        if (checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, KEY_PERMISSION_REQUEST_READCONTACTS);
        } else {
            mIPermissionProducer.onReceivedPermissionStatus(KEY_PERMISSION_REQUEST_READCONTACTS, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case KEY_PERMISSION_REQUEST_WRITEEXTERNALSTORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mIPermissionProducer != null)
                        mIPermissionProducer.onReceivedPermissionStatus(requestCode, true);
                } else {
                    callWriteExternalStoragePermission(mIPermissionProducer);
                }
                break;

            case KEY_PERMISSION_REQUEST_GPS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mIPermissionProducer != null)
                        mIPermissionProducer.onReceivedPermissionStatus(requestCode, true);
                } else {
                    callGPSPermission(mIPermissionProducer);
                }
                break;

            case KEY_PERMISSION_REQUEST_READEXTERNALSTORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mIPermissionProducer != null)
                        mIPermissionProducer.onReceivedPermissionStatus(requestCode, true);
                } else {
                    callReadExternalStoragePermission(mIPermissionProducer);
                }
                break;

            case KEY_PERMISSION_REQUEST_READCONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mIPermissionProducer != null)
                        mIPermissionProducer.onReceivedPermissionStatus(requestCode, true);
                } else {
                    callContactPermission(mIPermissionProducer);
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        checkInternet();
        startInternetThread();
    }

    private void startInternetThread() {
        NetworkReceiver.setListener(new NetworkReceiver.OnNetWorkChangeLister() {
            @Override
            public void networkChanged(boolean isOn) {

                if (isOn) {
                    System.out.println("ON working");
                    if (!internetFlag) {
                        System.out.println("Refresh working");
                        Intent a = new Intent(getActivity(), MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(a);
                        internetFlag = true;
                    }
                } else {
                    internetFlag = false;
                    checkInternet();
                    System.out.println("oFF working");
                }
            }
        });
    }

    private void checkInternet() {
        if (!getCodeSnippet().hasNetworkConnection()) {
            showSnackBar(getResources().getString(R.string.no_internet));
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        mParentView = getWindow().getDecorView().findViewById(android.R.id.content);
        return super.onCreateView(name, context, attrs);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.d("", "base pressed");
        if (menuItem.getItemId() == android.R.id.home) {
            Log.d("", "base pressed");
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (iPresenter != null) iPresenter.onStartPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearbroadCast();
        if (iPresenter != null) iPresenter.onStopPresenter();
    }

    private void clearbroadCast() {
        //todo clear broadcast
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (iPresenter != null) iPresenter.onPausePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (iPresenter != null) iPresenter.onResumePresenter();
        checkInternet();
        // if (!hasNetworkConnection()) showDialog(getString(R.string.no_network));
    }

    protected void showDialog(final String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        if (message != null) builder.setMessage(getText(R.string.no_internet));
        else builder.setMessage("");
        builder
                .setCancelable(false)
                .setPositiveButton("Turn on wifi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivityForResult(gpsOptionsIntent, WIFI_ENABLE_REQUEST);

                    }
                })
                .setNegativeButton("Turn on data", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        startActivityForResult(gpsOptionsIntent, ROAMING_REQUEST);
                    }
                });

        if (!isFinishing()) builder.show();
    }

    public boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return true;
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return true;
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroyCalledFromBaseActivity");
        clearbroadCast();
        if (iPresenter != null) iPresenter.onDestroyPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (iPresenter != null) iPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

    public void bindPresenter(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) {
            mCustomProgressbar = new CustomProgressbar(this);
        }
        return mCustomProgressbar;
    }

    @Override
    public void showMessage(String message) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.inflate_custom_toast,
                (ViewGroup) findViewById(R.id.ll_toast_layout_root));

        TextView text = layout.findViewById(R.id.tv_toastmsg);
        text.setText(message);

        text.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), PorterDuff.Mode.MULTIPLY);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL | Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void showMessage(int resId) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.inflate_custom_toast,
                (ViewGroup) findViewById(R.id.ll_toast_layout_root));

        TextView text = layout.findViewById(R.id.tv_toastmsg);
        text.setText(getString(resId));

        text.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), PorterDuff.Mode.MULTIPLY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.getBackground().applyTheme(getApplication().getTheme());
        }
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void showMessage(CustomException e) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.inflate_custom_toast,
                (ViewGroup) findViewById(R.id.ll_toast_layout_root));

        TextView text = layout.findViewById(R.id.tv_toastmsg);
        text.setText(e.getException());

        text.getBackground().setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), PorterDuff.Mode.MULTIPLY);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void showProgressbar() {
        // TODO have to menu_view_question_preference the custom progressbar
        getProgressBar().show();
        getProgressBar().setCancelable(false);
    }


    @Override
    public void dismissProgressbar() {
        // TODO dismiss the progressbar
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getProgressBar().dismissProgress();
                } catch (Exception e) {
                    ExceptionTracker.track(e);
                }
            }
        });
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void showSnackBar(String message) {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeSnippet.showNetworkSettings();
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showNetworkMessage() {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, "No Network found!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setAction("Settings", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeSnippet.showNetworkSettings();
                }
            });

            snackbar.show();
        }
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(getActivity());
            return mCodeSnippet;
        }
        return mCodeSnippet;
    }

    /**
     * Returns the layout id.
     *
     * @return
     */


    protected abstract int getLayoutId();


}

