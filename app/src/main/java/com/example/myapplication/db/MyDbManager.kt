package com.example.myapplication.db

import android.annotation.SuppressLint
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.myapplication.db.MyDbNameClass.TABLE_NAME


class MyDbManager(val cotext: Context) {
    val myDbHelper=MyDbHelper(cotext)
    var db: SQLiteDatabase? = null


    fun openDb(){
        db = myDbHelper.writableDatabase


    }

    fun closeDb(){
   //     db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.close()
        db=null
    }

    fun insertToDb(title: String, content: String, imageUri: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, imageUri)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }
    fun removeitemToDb(id: String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    @SuppressLint("Range")
    fun readDbData() : ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor){
            while(this?.moveToNext()!!){
                val dataTitle = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataContent = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataUri = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_IMAGE_URI))
                val dataID = cursor?.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val item = ListItem()
                item.title = dataTitle.toString()
                item.disc = dataContent.toString()
                item.uri = dataUri.toString()
                item.id = dataID!!
                dataList.add(item)
            }
        }
        cursor?.close()
        return dataList
    }



}