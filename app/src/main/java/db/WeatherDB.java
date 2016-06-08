package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 哈哈 on 2016/6/8.
 */
public class WeatherDB {
    public static final String DB_NAME="weather";
    public static final int VERSION=1;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;
    private WeatherDB(Context context)
    {
        WeatherOpenHelper weatherOpenHelper=new WeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=weatherOpenHelper.getWritableDatabase();
    }
    public synchronized static WeatherDB getInstance(Context context)
    {
        if(weatherDB==null)
            weatherDB=new WeatherDB(context);
        return weatherDB;
    }
    public void saveProvince(Province province)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("province_name",province.getProvinceName());
        contentValues.put("province_code",province.getProvinceCode());
        db.insert("Province",null,contentValues);
    }
    public List<Province> loadProvince()
    {
        List<Province> list=new ArrayList<>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
            if(cursor!=null)
                cursor.close();
        }
        return list;

    }
    public void saveCity(City city)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("city_name",city.getCityName());
        contentValues.put("city_code",city.getCityCode());
        contentValues.put("province_id",city.getProvinceId());
        db.insert("City",null,contentValues);
    }
    public List<City> loadCity(int  provinceId)
    {
        ArrayList<City> list=new ArrayList<>();
        Cursor cursor=db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst())
        {
            City city=new City();
            do {
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while(cursor.moveToNext());
            if(cursor!=null)
                cursor.close();
        }
        return list;

    }
    public void saveCounty(County county)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("county_name",county.getCountyName());
        contentValues.put("county_code",county.getCountyCode());
        contentValues.put("city_id",county.getCityId());
        db.insert("County",null,contentValues);
    }
    public List<County> loadCounty(int cityId)
    {
        ArrayList<County> list=new ArrayList<>();
        Cursor cursor=db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while(cursor.moveToNext());
            if(cursor!=null)
                cursor.close();
        }
        return list;
    }
}
