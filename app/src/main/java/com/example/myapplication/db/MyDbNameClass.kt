package com.example.myapplication.db

import android.provider.BaseColumns

object MyDbNameClass:BaseColumns {
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_IMAGE_URI = "imageUri"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MyDb.db"




}
