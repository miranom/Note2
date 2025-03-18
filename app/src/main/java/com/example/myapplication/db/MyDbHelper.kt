package com.example.myapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.myapplication.db.MyDbNameClass.COLUMN_NAME_CONTENT
import com.example.myapplication.db.MyDbNameClass.COLUMN_NAME_IMAGE_URI
import com.example.myapplication.db.MyDbNameClass.COLUMN_NAME_TITLE
import com.example.myapplication.db.MyDbNameClass.TABLE_NAME

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, MyDbNameClass.DATABASE_NAME,
    null, MyDbNameClass.DATABASE_VERSION) {






    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${MyDbNameClass.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TITLE TEXT," +
                "$COLUMN_NAME_CONTENT TEXT," +
                "$COLUMN_NAME_IMAGE_URI TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

}