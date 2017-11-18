package com.example.jiangtao.chargeup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @author gao_chun
 * 自定义标题栏
 */
public class TitleActivity extends AppCompatActivity {

    private  int lastX;
    private  int lastY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                click(lastX,lastY,x,y);
                break;

        }
        return true;
    }*/

    private void click(int fx,int fy,int sx,int sy){
        int sizeX = Title.sizeX;
        int sizeY = Title.sizeY;
        if(((sizeX/16f-sizeX/76f)<fx)&&((sizeX/16f-sizeX/76f)<sx)
                &&((sizeY/4f-sizeX/76f)<fy)&&((sizeY/4f-sizeX/76f)<sy)
                &&((sizeX/16f+sizeX/76f*3+sizeX/11f)>fx)&&((sizeX/16f+sizeX/76f*3+sizeX/11f)>sx)
                &&((sizeY/4f*3+sizeX/76f)>fy)&&((sizeY/4f*3+sizeX/76f)>sy)){
                return;
        }
        float textSize = sizeY*2f/6f;
        float cheek = 10f;
        float bottom = sizeY*4/6f+2*cheek;
        float shifting = 20f;
        float allShiftX = textSize*2+2*cheek+shifting;
        float allShiftY = textSize+2*cheek;
        if(((sizeX/2-allShiftX)<fx)&&((sizeX/2-allShiftX)<sx)
                &&((bottom-allShiftY)<fy)&&((bottom-allShiftY)<sy)
                &&((sizeX/2-shifting)>fx)&&((sizeX/2-shifting)>sx)
                &&((bottom)>fy)&&((bottom)>sy)){
            new MainActivity().setChange(1);
            return;
        }
        if(((sizeX/2+allShiftX)>fx)&&((sizeX/2+allShiftX)>sx)
                &&((bottom-allShiftY)<fy)&&((bottom-allShiftY)<sy)
                &&((sizeX/2+shifting)<fx)&&((sizeX/2+shifting)<sx)
                &&((bottom)>fy)&&((bottom)>sy)){
            new MainActivity().setChange(2);
            return;
        }
    }
}