package com.example.jiangtao.chargeup.DBOperate;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jiangtao on 2017/9/12.
 */

public class createTable {

    private Context context;
    private SQLiteDatabase db;
    String id;
    public static final String table = "(" +
            "ID integer primary key," +
            "TYPE text," +
            "TIPS text," +
            "NAME text," +
            "PAYEE text," +
            "LOCATION  text," +
            "DESCRIPTION text," +
            "SUM float," +
            "DATE integer," +
            "PATICULARTIME integer)";
    public static final String tableIncome = "(" +
            "ID integer primary key," +
            "TYPE text," +
            "PAYEE text," +
            "LOCATION  text," +
            "DESCRIPTION text," +
            "SUM float," +
            "DATE integer," +
            "PATICULARTIME integer)";

    public static final String table1 = "(" +
            "TYPE text primary key," +
            "TYPE_TIMES integer)";

    public static final String table2 = "(" +
            "TIPS text primary key," +
            "TIPS_TIMES integer," +
            "TYPE text," +
            "FOREIGN KEY (TYPE) REFERENCES stuinfor1(TYPE))" ;

    public static final String table5 = "(" +
            "PAYEE text primary key," +
            "PAYEE_TIMES integer," +
            "TYPE text," +
            "TIPS text," +
            "NAME text," +
            "FOREIGN KEY (TYPE) REFERENCES stuinfor1(TYPE)," +
            "FOREIGN KEY (TIPS) REFERENCES stuinfor2(TIPS)," +
            "FOREIGN KEY (NAME) REFERENCES stuinfor3(NAME))" ;

    public static final String table4 = "(" +
            "LOCATION  text primary key," +
            "LOCATION_TIMES integer," +
            "TYPE text," +
            "TIPS text," +
            "NAME text," +
            "FOREIGN KEY (TYPE) REFERENCES stuinfor1(TYPE)," +
            "FOREIGN KEY (TIPS) REFERENCES stuinfor2(TIPS)," +
            "FOREIGN KEY (NAME) REFERENCES stuinfor3(NAME))" ;

    public static final String table3 = "(" +
            "NAME text primary key," +
            "TYPE text," +
            "TIPS text," +
            "NAME_TIMES integer,"+
            "FOREIGN KEY (TYPE) REFERENCES stuinfor1(TYPE)," +
            "FOREIGN KEY (TIPS) REFERENCES stuinfor2(TIPS))" ;


    public  createTable(Context _context,int _year){
        this.context = _context;
        SharedPreferences e = context.getSharedPreferences("data",0);
        this.id = e.getString("login","");
        createDatabase create = new createDatabase(context,id+_year,null,1);
        db = create.getWritableDatabase();
    }

    public void createStudentTable(){
        GregorianCalendar calendar = new GregorianCalendar();
        String studentTable = "create table stu" + calendar.get(Calendar.YEAR) + table;
        db.execSQL(studentTable);
        String studentInformationTable1 = "create table stuinfor1" + table1;
        db.execSQL(studentInformationTable1);
        String studentInformationTable2 = "create table stuinfor2" + table2;
        db.execSQL(studentInformationTable2);
        String studentInformationTable3 = "create table stuinfor3" + table3;
        db.execSQL(studentInformationTable3);
        String studentInformationTable4 = "create table stuinfor4" + table4;
        db.execSQL(studentInformationTable4);
        String studentInformationTable5 = "create table stuinfor5"  + table5;
        db.execSQL(studentInformationTable5);
    }
}
