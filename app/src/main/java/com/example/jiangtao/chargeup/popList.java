package com.example.jiangtao.chargeup;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jiangtao.chargeup.DBOperate.DBManager;
import com.example.jiangtao.chargeup.DBOperate.InDBManager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jiangtao on 2017/9/12.
 */

public class popList implements View.OnClickListener {

    private EditText editText;
    private DropdownAdapter adapter;
    private List<String> names;
    private PopupWindow pop;
    private Context context;
    private int times;
    private boolean bolo = false;
    static EditText[] text  = new EditText[7];
    final ListView listView;

   public  popList(EditText _editText,Context _context){//第三个字符串用来告诉该调用数据库的哪个表
        this.editText = _editText;
        this.context = _context;
       if(text!=null){
           text[text.length-1] = _editText;
       }else{
           text[0] = _editText;
       }

        this.times = 0;
        //构造方法写在onCreate方法体中会因为布局没有加载完毕而得不到宽高。
        adapter = new DropdownAdapter(context, getData(),editText);
        listView = new ListView(context);
        listView.setAdapter(adapter);
    }

    public List<String> getData() {
        names = new ArrayList<String>();
        InDBManager indb = new InDBManager(context);
        switch (editText.getId()){
            case R.id.text1:{
                return indb.getTenMax(1,1,null);
            }
            case R.id.text2:{
                return indb.getTenMax(1,2,new String[]{text[0].getText().toString()});
            }
            case R.id.text3:{
                if(isEmpty(3)){
                    if((text[2].getText()!=null)&&(!text[2].getText().toString().equals(""))){
                        GregorianCalendar calendar = new GregorianCalendar();
                        int year =calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.add(Calendar.DAY_OF_MONTH,-7);
                        int month1 = calendar.get(Calendar.MONTH);
                        bolo = true;
                        return new DBManager(context,year).getInforByDataAndName(1,year,text[2].getText().toString(),calendar.get(Calendar.DAY_OF_MONTH)+month1*100,day+month*100);
                    }
                    return indb.getTenMax(1,3,new String[]{text[0].getText().toString(),text[1].getText().toString()});
                }
                return null;
            }
            case R.id.text4:{
                    return indb.getTenMax(1,4,new String[]{text[0].getText().toString(),text[1].getText().toString(),text[2].getText().toString()});
            }
            case R.id.text5:{
                    return indb.getTenMax(1,5,new String[]{text[0].getText().toString(),text[1].getText().toString(),text[2].getText().toString(),text[3].getText().toString()});

            }
            case R.id.text6:{
                    return indb.getTenMax(1,1,new String[]{text[0].getText().toString(),text[1].getText().toString(),text[2].getText().toString(),text[3].getText().toString(),text[4].getText().toString()});
            }
            case R.id.text7:{
                    return indb.getTenMax(1,1,new String[]{text[0].getText().toString(),text[1].getText().toString(),text[2].getText().toString(),text[3].getText().toString(),text[4].getText().toString(),text[5].getText().toString()});
            }
        }

        return names;
    }

    private boolean isEmpty(int i){
        for(int j=0;j<i-1;j++){
            if(text[i].getText().toString().equals("")||(text[i].getText()==null)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if((this.times%2)==1){

            if (null == pop) {
                //创建一个在输入框下方的popup
                View layout = (View)editText.getParent();
                pop = new PopupWindow(listView, layout.getWidth(), names.size() * layout.getHeight());
                pop.showAsDropDown(layout);
            }
            else {
                if (pop.isShowing()) {
                    pop.dismiss();
                }
                else {
                    pop.showAsDropDown(editText);
                }
            }
        }else{
            pop.dismiss();
        }
        this.times++;

    }



    class DropdownAdapter extends BaseAdapter {

        public DropdownAdapter(Context context, List<String> list , EditText _editText) {
            this.context = context;
            this.list = list;
            this.editText1 = _editText;
        }

        public int getCount() {
            if(bolo){
                return list.size()/5;
            }
            return list.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            layoutInflater  = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            close = (ImageButton)convertView.findViewById(R.id.close_row);
            content = (TextView)convertView.findViewById(R.id.text_row);
            final String editContent;
            if(bolo){
                String str ="";
                for(int i=0;i<5;i++){
                    str += list.get(position * 5 + i).toString();
                }
                editContent = str;
            }else{
                editContent = list.get(position).toString();
            }
            content.setText(editContent);
            content.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if(bolo){
                        text[0].setText(list.get(position*5));
                        text[1].setText(list.get(position*5+1));
                        text[3].setText(list.get(position*5+2));
                        text[4].setText(list.get(position*5+3));
                        text[5].setText(list.get(position*5+4));
                    }
                    editText1.setText(editContent);
                    pop.dismiss();
                    return false;
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    names.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
        private EditText editText1;
        private Context context;
        private LayoutInflater layoutInflater;
        private List<String> list;
        private TextView content;
        private ImageButton close;
    }
}
