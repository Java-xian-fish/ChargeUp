package com.example.jiangtao.chargeup.DBOperate;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jiangtao on 2017/11/8.
 * 这个类主要用来存各类数据用户以前存过哪些可能并统计每项存的次数，方便用户快捷选择
 * 当然提供的方法就以检索和更新为主了。而且随着时间的推移次数将是最有价值的数据
 * 一丶储存类方法
 * 1.首先得判断是不是该数据是不是和里面的数据是不是有相同的,hasTheSame()
 */

public class InDBManager {
    private Context context;
    private SQLiteDatabase db;
    private String id;

    public InDBManager(Context _context) {
        this.context = _context;
        SharedPreferences e = context.getSharedPreferences("data",0);
        this.id = e.getString("login","");
        createDatabase create = new createDatabase(context, id+ _context, null, 1);
        db = create.getWritableDatabase();
    }

    public boolean hasTheSame(int _career,int _whichTable,String[] _text){//这个函数的作用就是检查现在进来的数据是否已经存在，如果存在就把次数加一要是不存在就添加进去
        if(_whichTable>=1){
            Cursor cursor = db.rawQuery("select * from "+DBManager.getCareer(_career)+"infor1 where TYPE  = " + _text[0], null);
            if(cursor.moveToFirst()){
                updateTimes(_career,1,_text[0]);
                return true;
            }else{
                ContentValues values = new ContentValues();
                values.put("TYPE",_text[0]);
                values.put( "TYPE_TIMES",1);
                db.insert(DBManager.getCareer(_career)+"infor1", null, values);
                return false;
            }
        }
        if(_whichTable>=2){
            Cursor cursor = db.rawQuery("select * from "+DBManager.getCareer(_career)+"infor2 where TIPS = " + _text[1]+" AND TYPE  = " + _text[0], null);
            if(cursor.moveToFirst()){
                updateTimes(_career,2,_text[0]);
                return true;
            }else{
                ContentValues values = new ContentValues();
                values.put("TIPS",_text[1]);
                values.put("TIPS_TIMES",1);
                values.put("TYPE",_text[0]);
                db.insert(DBManager.getCareer(_career)+"infor2", null, values);
                return false;
            }
        }
        if(_whichTable>=3){
            Cursor cursor = db.rawQuery("select * from "+DBManager.getCareer(_career)+"infor3 where NAME  = "
                    + _text[2]+" AND TIPS  = " + _text[1]+" AND TYPE  = " + _text[0], null);
            if(cursor.moveToFirst()){
                updateTimes(_career,3,_text[0]);
                return true;
            }else{
                ContentValues values = new ContentValues();
                values.put("NAME",_text[2]);
                values.put("NAME_TIMES",1);
                values.put("TYPE",_text[0]);
                values.put("TIPS",_text[1]);
                db.insert(DBManager.getCareer(_career)+"infor3", null, values);
                return false;
            }
        }
        if(_whichTable>=4){
            Cursor cursor = db.rawQuery("select * from "+DBManager.getCareer(_career)+" infor4 where " +
                    " TYPE = " + _text[0]+" AND "+
                    " TIPS  = " + _text[1]+" AND "+
                    " NAME = " + _text[2]+" AND " +
                    " LOCATION  = " + _text[3], null);
            if(cursor.moveToFirst()){
                updateTimes(_career,4,_text[0]);
                return true;
            }else{
                ContentValues values = new ContentValues();
                values.put("LOCATION",_text[3]);
                values.put( "LOCATION_TIMES",1);
                values.put("TYPE",_text[0]);
                values.put("TIPS",_text[1]);
                values.put("NAME",_text[2]);
                db.insert(DBManager.getCareer(_career)+"infor4" , null, values);
                return false;
            }
        }
        if(_whichTable>=5){
            Cursor cursor = db.rawQuery("select * from "+DBManager.getCareer(_career)+"infor5 where" +
                    " PAYEE = " + _text[4]+" AND "+
                    " TYPE = " + _text[0]+" AND "+
                    " TIPS  = " + _text[1]+" AND "+
                    " NAME = " + _text[2], null);
            if(cursor.moveToFirst()){
                updateTimes(_career,_whichTable,_text[0]);
                return true;
            }else{
                ContentValues values = new ContentValues();
                values.put("PAYEE",_text[4]);
                values.put("PAYEE_TIMES",1);
                values.put("TYPE",_text[0]);
                values.put("TIPS",_text[1]);
                values.put("NAME",_text[2]);
                db.insert(DBManager.getCareer(_career)+"infor5" , null, values);
                return false;
            }
        }
        return false;
    }


    public void updateTimes(int _career,int _whichTable,String _text){
        Cursor cursor = db.rawQuery("select "+ getDataType(_whichTable)+"_TIMES from "+DBManager.getCareer(_career)+"infor" + _whichTable  + "where" +
                getDataType(_whichTable)+"  = " + _text, null);
        if(cursor.moveToFirst()) {
            int times = cursor.getInt(0)+1;
            ContentValues values = new ContentValues();
            values.put(getDataType(_whichTable)+ "_TIMES",times);
            db.update(DBManager.getCareer(_career)+"infor" + _whichTable ,values,getDataType(_whichTable)+"=?",new String[]{_text});
        }
    }

    public ArrayList<String> getTenMax(int _career,int _whichTable,String[] infor){//这儿主要根据用户填的信息快速检索最有可能接下来要填的
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select "+ getDataType(_whichTable) +" from "+DBManager.getCareer(_career)+"infor" + _whichTable + " ORDER BY "+ getDataType(_whichTable)+"_TIMES DESC", null);
        if(_whichTable==1){
            int i=0;
            if(cursor.moveToFirst()){
                do{
                    arrayList.add(cursor.getString(0));
                }while (cursor.moveToNext()&&((i++)<10));
            }else{
                return arrayList;
            }

        }
        if(_whichTable==2){
            Cursor cursor1 = db.rawQuery("select "+ getDataType(_whichTable) +"  from "+DBManager.getCareer(_career)+
                    "infor" + _whichTable +" WHERE "+ getDataType(_whichTable-1) +" = " + infor[0]+ " ORDER BY "+
                    getDataType(_whichTable)+"_TIMES DESC", null);
            int i=0;
            if(cursor1.moveToFirst()){
                do{
                    arrayList.add(cursor1.getString(0));
                    i++;
                }while (cursor1.moveToNext());
                if(cursor.moveToFirst()) {
                    for (; i < 10; i++) {
                        arrayList.add(cursor.getString(0));
                        if(cursor.moveToFirst()){}else{
                            return arrayList;
                        }
                    }
                    return arrayList;
                }
            }
        }
        if(_whichTable==3){
            Cursor cursor1 = db.rawQuery("select "+ getDataType(_whichTable) +"  from "+DBManager.getCareer(_career)+
                    "infor" + _whichTable +" WHERE "+ getDataType(_whichTable-2) +" = " +
                    infor[0]+" AND "+ getDataType(_whichTable-1) +" = " + infor[1]+" ORDER BY "+
                    getDataType(_whichTable)+"_TIMES DESC", null);
            int i=0;
            if(cursor1.moveToFirst()){
                do{
                    arrayList.add(cursor1.getString(0));
                    i++;
                }while (cursor1.moveToNext());
                if(cursor.moveToFirst()) {
                    for (; i < 10; i++) {
                        arrayList.add(cursor.getString(0));
                        if(cursor.moveToFirst()){}else{
                            return arrayList;
                        }
                    }
                    return arrayList;
                }
            }
        }
        if(_whichTable==4){
            Cursor cursor1 = db.rawQuery("select "+ getDataType(_whichTable) +"  from "+DBManager.getCareer(_career)+
                    "infor" + _whichTable +" WHERE "+ getDataType(_whichTable-3) +" = " +
                    infor[0]+" AND "+ getDataType(_whichTable-2) +" = " + infor[1]+" AND "+ getDataType(_whichTable-1) +" = " + infor[2]+" ORDER BY "+
                    getDataType(_whichTable)+"_TIMES DESC", null);
            arrayList.add(infor[3]);
            int i=0;
            if(cursor1.moveToFirst()){
                do{
                    arrayList.add(cursor1.getString(0));
                    i++;
                }while (cursor1.moveToNext());
                if(cursor.moveToFirst()) {
                    for (; i < 10; i++) {
                        arrayList.add(cursor.getString(0));
                        if(cursor.moveToFirst()){}else{
                            return arrayList;
                        }
                    }
                    return arrayList;
                }
            }
        }
        if(_whichTable==5){
            Cursor cursor1 = db.rawQuery("select "+ getDataType(_whichTable) +"  from "+DBManager.getCareer(_career)+
                    "infor" + _whichTable +" WHERE "+ getDataType(_whichTable-4) +" = " +
                    infor[0]+" AND "+ getDataType(_whichTable-3) +" = " + infor[1]+" AND "+ getDataType(_whichTable-2) +" = " + infor[2]+" ORDER BY "+
                    getDataType(_whichTable)+"_TIMES DESC", null);
            arrayList.add(infor[4]);
            int i=1;
            if(cursor1.moveToFirst()){
                do{
                    arrayList.add(cursor1.getString(0));
                    i++;
                }while (cursor1.moveToNext());
                if(cursor.moveToFirst()) {
                    for (; i < 10; i++) {
                        arrayList.add(cursor.getString(0));
                        if(cursor.moveToFirst()){}else{
                            return arrayList;
                        }
                    }
                    return arrayList;
                }
            }
        }
        return arrayList;
    }

    private static String getDataType(int _whileTable){
        switch (_whileTable){
            case 1:return "TYPE";
            case 2:return "TIPS";
            case 4:return "PAYEE";
            case 5:return "LOCATION";
            case 3:return "NAME";
        }
        return "";
    }
}
