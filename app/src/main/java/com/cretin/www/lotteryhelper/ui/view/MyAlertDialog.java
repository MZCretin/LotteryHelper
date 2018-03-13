package com.cretin.www.lotteryhelper.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cretin.www.lotteryhelper.R;


public class MyAlertDialog extends Dialog implements
        View.OnClickListener {
    public static final int TYPE_INVITE_AWARD_DETAILS = 1;
    public static final int TYPE_SINGLE = 2;
    private View mView;
    private TextView mTitle;
    public TextView mContent;
    public TextView mBtnCancel;
    private TextView mBtnConfirm;
    private String title;
    private String message;
    private String rightMsg;
    private int layoutId;
    private View contentView;
    private Context mContext;
    private boolean positiveDismiss = true;
    private boolean negativeDismiss = true;
    private int type;
    private View inviteFriendAwardView;

    public MyAlertDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyAlertDialog(Context context, int layoutId) {
        super(context);
        this.layoutId = layoutId;
        this.mContext = context;
    }

    public MyAlertDialog(Context context, int layoutId, int type) {
        super(context);
        this.layoutId = layoutId;
        this.mContext = context;
        this.type = type;
    }

    public MyAlertDialog(Context context, View contentView) {
        super(context);
        this.contentView = contentView;
        this.mContext = context;
    }

    public MyAlertDialog(Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;
        this.mContext = context;
    }
    public MyAlertDialog(Context context, String title, String message, int type) {
        super(context);
        this.title = title;
        this.message = message;
        this.mContext = context;
        this.type = type;
    }

    public MyAlertDialog(Context context, String title, String message, String rightBtnMsg) {
        super(context);
        this.title = title;
        this.message = message;
        this.mContext = context;
        this.rightMsg = rightBtnMsg;
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if ( layoutId != 0 ) {
            setContentView(layoutId);
        } else if ( contentView != null ) {
            setContentView(contentView);
        } else if ( type == TYPE_SINGLE ) {
            setContentView(R.layout.common_dialog_single);
            mTitle = ( TextView ) findViewById(R.id.dialog_title);
            mContent = ( TextView ) findViewById(R.id.dialog_content);
            mBtnConfirm = ( TextView ) findViewById(R.id.button_confirm);
            mTitle.setText(title);
            mContent.setText(message);
            if ( !TextUtils.isEmpty(rightMsg) ) {
                mBtnConfirm.setText(rightMsg);
            }
            mBtnConfirm.setOnClickListener(this);
        } else {
            setContentView(R.layout.common_dialog);
            mTitle = ( TextView ) findViewById(R.id.dialog_title);
            mContent = ( TextView ) findViewById(R.id.dialog_content);
            mBtnConfirm = ( TextView ) findViewById(R.id.button_confirm);
            mBtnCancel = ( TextView ) findViewById(R.id.button_cancel);
            mTitle.setText(title);
            mContent.setText(message);
            if ( !TextUtils.isEmpty(rightMsg) ) {
                mBtnConfirm.setText(rightMsg);
            }
            mBtnConfirm.setOnClickListener(this);
            mBtnCancel.setOnClickListener(this);
        }
    }

    public void setTitle(String title) {
        if ( mTitle != null ) {
            mTitle.setText(title);
        }
    }

    public void hideCancel() {
        if ( mBtnCancel != null )
            mBtnCancel.setVisibility(View.GONE);
    }

    public void setRightButton(String button) {
        if ( mBtnConfirm != null ) {
            mBtnConfirm.setText(button);
        }
    }

    public void setMessage(String message) {
        if ( mContent != null ) {
            mContent.setText(message);
        }
    }

    OnPositiveClickListener mClickListener;

    public interface OnPositiveClickListener {
        void onPositiveClickListener(View v);

    }

    public interface OnNegativeClickListener {
        void onNegativeClickListener(View v);
    }

    private OnNegativeClickListener mNegativeListener;

    public MyAlertDialog setOnNegativeListener(
            OnNegativeClickListener mNegativeListener) {
        this.mNegativeListener = mNegativeListener;
        return this;
    }

    public MyAlertDialog setOnClickListener(OnPositiveClickListener mClickListener) {
        this.mClickListener = mClickListener;
        return this;
    }

    public void setPositiveDismiss(boolean flag) {
        positiveDismiss = flag;
    }

    public void setNegativeDismiss(boolean flag) {
        negativeDismiss = flag;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_cancel:
                if ( mNegativeListener != null ) {
                    mNegativeListener.onNegativeClickListener(v);
                }
                if ( negativeDismiss ) {
                    this.dismiss();
                }
                break;
            case R.id.button_confirm:
                if ( mClickListener != null ) {
                    if ( positiveDismiss ) {
                        this.dismiss();
                    }
                    mClickListener.onPositiveClickListener(v);
                }
                break;
            default:
                break;
        }
    }
}
