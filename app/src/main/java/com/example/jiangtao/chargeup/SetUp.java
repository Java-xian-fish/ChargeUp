package com.example.jiangtao.chargeup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SetUp extends RelativeLayout {
    private void initView(Context context) {
        View inflate;
        inflate = View.inflate(context, R.layout.activity_set_up, SetUp.this);
    }

    public SetUp(Context context) {
        super(context);
        initView(context);
    }

    public SetUp(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SetUp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SetUp(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
}
