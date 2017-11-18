package com.example.jiangtao.chargeup;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends RelativeLayout implements View.OnClickListener{

    private  int lastX;
    private  int lastY;
    TextView tb1;
    TextView tb2;
    TextView tb3;

    private void initView(Context context) {
        View inflate;
        inflate = View.inflate(context, R.layout.activity_main2, Main2Activity.this);
    }

    public Main2Activity(Context context) {
        super(context);
        initView(context);
    }

    public Main2Activity(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Main2Activity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public Main2Activity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }



    /*public boolean dispatchTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                if(offsetX<-120){
                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    super.dispatchTouchEvent(event);
                }
                break;
        }
        return true;
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
