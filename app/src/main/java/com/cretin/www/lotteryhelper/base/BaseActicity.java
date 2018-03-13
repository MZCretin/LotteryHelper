package com.cretin.www.lotteryhelper.base;

import android.support.v7.app.AppCompatActivity;

import com.cretin.www.lotteryhelper.ui.view.CustomProgressDialog;

/**
 * Created by cretin on 2018/3/12.
 */

public class BaseActicity extends AppCompatActivity {
    private CustomProgressDialog dialog;

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg, boolean cancelable) {
        if ( dialog == null ) {
            dialog = CustomProgressDialog.createDialog(this);
        }
        if ( msg != null && !msg.equals("") ) {
            dialog.setMessage(msg);
        }
        dialog.setCancelable(cancelable);
        dialog.show();
    }

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        showDialog(msg, true);
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        showDialog("正在加载中...", true);
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
        }
    }
}
