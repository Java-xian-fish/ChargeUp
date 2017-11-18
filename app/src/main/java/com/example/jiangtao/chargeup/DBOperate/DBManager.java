package com.example.jiangtao.chargeup.DBOperate;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;

import com.example.jiangtao.chargeup.tools.timeManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jiangtao on 2017/9/13.
 * 所有的检索方法：
 * 一丶这些都是用户查账单时候用到的方法
 * 1.getAllInformation（int _career,int _year,int _start ,int _end）
 * 2.getAllInformationByType(int _career,int _year,String _type,int _start ,int _end)
 * 3.getAllInformationByTips(int _career,int _year,String _tips,int _start ,int _end)
 * 4.getAllInformationByPayee(int _career,int _year,String _payee,int _start ,int _end)
 * 5.getAllInformationByLocation(int _career,int _year,String _location,int _start ,int _end)
 * 6.getAllInformationByDescription(int _career,int _year,String _description,int _start ,int _end)
 * 7.getAllInformationBySum(int _career,int _year,float down,float up)
 *
 * 二丶这些是可视化数据要用到的方法
 * 1.getSumByDay(int _career,int _year,int _month,int _day)
 * 2.getSumByMonth(int _career,int _year,int _month)
 * 3.getSumByYear(int _career,int _year)
 * 4.getSumByDate(int _career,int _year,_start,int _end)
 * 5.getSumByTypeAndDate(int _career,int _year,String _type,int _start,int _end)//检索下线0000表示某个时间之前所有的记录9999表示某个时间之后所有的
 * 6.getSumByTipsAndDate(int _career,int _year,String _tips,int _start ,int _end)
 * 7.getSumByPayeeAndDate(int _career,int _year,String _payee,int _start ,int _end)
 * 8.getSumByLocationAndDate(int _career,int _year,String _location,int _start ,int _end)
 * 9.getSumByDescriptionAndDate(int _career,int _year,String _description,int _start ,int _end)
 * 10.getSumByNameAndDate(int _career,int _year,String _name,int _start ,int _end)
 */

public class DBManager {

    public static String getCareer(int career) {
        switch (career) {
            case 1: {
                return "stu";
            }

        }
        return "";
    }

    public ArrayList<String> getSumByDate(int _career, int _year, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getSumByYear(int _career, int _year) {
        Cursor cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getSumByMonth(int _career, int _year, int _month) {
        Cursor cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where DATE <= " + (_month * 100 + 31) + " AND DATE >" + (_month * 100), null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getSumByDay(int _career, int _year, int _month, int _day) {
        Cursor cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where DATE = " + (_month * 100 + _day), null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));

        }
        return array;
    }

    public ArrayList<String> getAllInformationBySum(int _career, int _year, float down, float up) {
        Cursor cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where SUM <= " + up + " AND SUM>=" + down, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getSumByNameAndDate(int _career, int _year, String _name, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND NAME=" + _name, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getAllInformationByName(int _career, int _year, String _name, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND NAME=" + _name, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    public ArrayList<String> getSumByDescriptionAndDate(int _career, int _year, String _description, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND DESCRIPTION=" + _description, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getAllInformationByDescription(int _career, int _year, String _description, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND DESCRIPTION=" + _description, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    public ArrayList<String> getSumByLocationAndDate(int _career, int _year, String _location, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND LOCATION=" + _location, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }


    public ArrayList<String> getAllInformationByLocation(int _career, int _year, String _location, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND LOCATION=" + _location, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    public ArrayList<String> getSumByPayeeAndDate(int _career, int _year, String _payee, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND PAYEE=" + _payee, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getAllInformationByPayee(int _career, int _year, String _payee, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND PAYEE=" + _payee, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    public ArrayList<String> getSumByTipsAndDate(int _career, int _year, String _tips, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select SUM from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND TIPS=" + _tips, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getAllInformationByTips(int _career, int _year, String _tips, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND TIPS=" + _tips, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    public ArrayList<String> getSumByTypeAndDate(int _career, int _year, String _type, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select Sum from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND TYPE=" + _type, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
        }
        return array;
    }

    public ArrayList<String> getAllInformationByType(int _career, int _year, String _type, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = 0000;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_career) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_career) + _year + " where ID <= " + endID + " AND ID>=" + startID + " AND TYPE=" + _type, null);
        ArrayList<String> array = new ArrayList<String>();
        while (cursor.moveToFirst()) {
            array.add(cursor.getString(0));
            array.add(cursor.getString(1));
            array.add(cursor.getString(2));
            array.add(cursor.getString(3));
            array.add(cursor.getString(4));
            array.add(cursor.getString(5));
            array.add(cursor.getString(6));
            array.add(cursor.getString(7));
            array.add(cursor.getString(9));
        }
        return array;
    }

    private Context context;
    private SQLiteDatabase db;
    String id;
    int ID;

    public DBManager(Context _context,int _year) {
        this.context = _context;
        SharedPreferences e = context.getSharedPreferences("data",0);
        this.id = e.getString("login","");
        this.ID = e.getInt(this.id + "ID",00);
        createDatabase create = new createDatabase(context, id+_year, null, 1);
        db = create.getWritableDatabase();
    }


    public boolean Write(int career, String ID, String _type, String _tips,String _name, String _payee, String _location, String _description, float _sum,
                         GregorianCalendar _calendar) {//第一个参数代表职业类型
        ContentValues values = new ContentValues();
        values.put("ID", ID);
        values.put("TYPE", _type);
        values.put("TIPS", _tips);
        values.put("NAME",_name);
        values.put("PAYEE", _payee);
        values.put("LOCATION", _location);
        values.put("DESCRIPTION", _description);
        values.put("SUM", _sum);
        values.put("DATE", new timeManager(_calendar).storageID());
        values.put("PATICULARTIME", new timeManager(_calendar).storageAll());
        long bool = db.insert(getCareer(career) + _calendar.get(Calendar.YEAR), null, values);
        values.clear();
        if (bool == 9) {
            int i=0;
            Log.e("" + (i++) + "*******************", "数据添加成功");
            new InDBManager(this.context).hasTheSame(career,5,new String[]{_type,_tips,_name,_location,_payee});
            return true;
        }
        return false;
    }


    /*因为所有的条目都有唯一的ID，而且ID 是升序排列的，所以在检索的时候采用的方式是找到start时间的一条账目（这天没有一直往后推）
     *然后记下它的ID，同理找出end往前的ID（end当天没有就一直往前推），然后再遍历之间的所有ID
     *
     */
    public ArrayList<String> getInforByDataAndName(int _type, int _year,String _name, int _start, int _end){
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_type) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = ID;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_type) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select DISTINCT TYPE,TIPS ,PAYEE,LOCATION,SUM from " + getCareer(_type) + _year + " where ID <= " + endID + " AND ID>=" + startID+ " AND NAME = "+ _name, null);
        ArrayList<String> array = new ArrayList<String>();
        int i =0;
        if(cursor.moveToFirst()) {
            do {
                Log.e("" + (i++) + "*******************", "" + getMemory());
                array.add(cursor.getString(0));
                array.add(cursor.getString(1));
                array.add(cursor.getString(2));
                array.add(cursor.getString(3));
                array.add(""+cursor.getFloat(4));
            } while (cursor.moveToNext());
        }
        return array;
    }

    public ArrayList<String> getAllInformation(int _type, int _year, int _start, int _end) {
        Cursor cursor;
        int startID;
        int endID;
        if (_start == 0000) {
            startID = _year * 1_000_000;
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_type) + _year + "where DATE = " + _start, null);
                _start++;//因为这是个四位数要是中间隔得那几个日子恰好跨月份怎么办呢?让他一直循环？
            } while (cursor.moveToFirst() || _start == _end);
            startID = cursor.getInt(0);
        }
        if (_end == 9999) {
            endID = ID;//访问最后一个ID
        } else {
            do {
                cursor = db.rawQuery("select ID from " + getCareer(_type) + _year + "where DATE = " + _end, null);
                _end--;
            } while (cursor.moveToFirst() || _end == _start);
            endID = cursor.getInt(0);
            while (true) {
                if (!cursor.moveToFirst()) {
                    break;
                }
                endID = cursor.getInt(0);
            }
        }
        cursor = db.rawQuery("select * from " + getCareer(_type) + _year + " where ID <= " + endID + " AND ID>=" + startID, null);
        ArrayList<String> array = new ArrayList<String>();
        int i =0;
        if(cursor.moveToFirst()) {
            do {
                Log.e("" + (i++) + "*******************", "" + getMemory());
                array.add(cursor.getString(0));
                array.add(cursor.getString(1));
                array.add(cursor.getString(2));
                array.add(cursor.getString(3));
                array.add(cursor.getString(4));
                array.add(cursor.getString(5));
                array.add(cursor.getString(6));
                array.add(""+cursor.getFloat(7));
                array.add(cursor.getString(9));
            } while (cursor.moveToNext());
        }
        return array;
    }
    public static int getMemory() {
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        // dalvikPrivateClean + nativePrivateClean + otherPrivateClean;
        int totalPrivateClean = memoryInfo.getTotalPrivateClean();
        // dalvikPrivateDirty + nativePrivateDirty + otherPrivateDirty;
        int totalPrivateDirty = memoryInfo.getTotalPrivateDirty();
        // dalvikPss + nativePss + otherPss;
        int totalPss = memoryInfo.getTotalPss();
        // dalvikSharedClean + nativeSharedClean + otherSharedClean;
        int totalSharedClean = memoryInfo.getTotalSharedClean();
        // dalvikSharedDirty + nativeSharedDirty + otherSharedDirty;
        int totalSharedDirty = memoryInfo.getTotalSharedDirty();
        // dalvikSwappablePss + nativeSwappablePss + otherSwappablePss;
        int totalSwappablePss = memoryInfo.getTotalSwappablePss();

        int total = totalPrivateClean + totalPrivateDirty + totalPss + totalSharedClean + totalSharedDirty + totalSwappablePss;
        return total ;
    }
}

