package com.cretin.www.lotteryhelper.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cretin.www.lotteryhelper.R;


public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context) {
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.custom_progressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if ( customProgressDialog == null ) {
            return;
        }
        ImageView imageView = ( ImageView ) customProgressDialog
                .findViewById(R.id.loading_image);
        AnimationDrawable animationDrawable = ( AnimationDrawable ) imageView
                .getBackground();
        animationDrawable.start();
    }

    /**
     * [Summary] setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public CustomProgressDialog setTitile(String strTitle) {
        return customProgressDialog;
    }

    /**
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage) {
        TextView tvMsg = ( TextView ) customProgressDialog
                .findViewById(R.id.loading_msg);

        if ( tvMsg != null ) {
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }
}
