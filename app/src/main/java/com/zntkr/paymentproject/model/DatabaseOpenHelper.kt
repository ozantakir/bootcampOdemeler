package com.zntkr.paymentproject.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context : Context, name: String, factory : SQLiteDatabase.CursorFactory?, version : Int): SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val sorgu1 = "CREATE TABLE PaymentType(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, period TEXT, date TEXT)"
        val sorgu2 = "CREATE TABLE Payment(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, typeId INTEGER, value TEXT, date TEXT)"

        p0!!.execSQL(sorgu1)
        p0!!.execSQL(sorgu2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       //
    }
}