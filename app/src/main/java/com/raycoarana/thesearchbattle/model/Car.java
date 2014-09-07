package com.raycoarana.thesearchbattle.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Car {

    private long mId;
    private String mCountry;
    private String mBrand;
    private String mName;
    private int mYear;

    public static List<Car> fromCursor(Cursor cursor) {
        ArrayList<Car> cars = new ArrayList<Car>();

        int idColumnIndex = cursor.getColumnIndex("_id");
        int countryColumnIndex = cursor.getColumnIndex("country");
        int brandColumnIndex = cursor.getColumnIndex("brand");
        int nameColumnIndex = cursor.getColumnIndex("name");
        int yearColumnIndex = cursor.getColumnIndex("year");

        while(cursor.moveToNext()) {
            Car car = new Car();
            car.setId(cursor.getLong(idColumnIndex));
            car.setCountry(cursor.getString(countryColumnIndex));
            car.setBrand(cursor.getString(brandColumnIndex));
            car.setName(cursor.getString(nameColumnIndex));
            car.setYear(cursor.getInt(yearColumnIndex));
            cars.add(car);
        }

        return cars;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        this.mBrand = brand;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }
}
