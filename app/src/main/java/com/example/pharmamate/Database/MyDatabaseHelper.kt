package com.example.pharmamate.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MedicineStock.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Medicines"

        const val COLUMN_ID = "unique_code" // Text, not a primary key

        const val COLUMN_NAME = "medicine_name"
        const val COLUMN_PRICE = "medicine_price" // Integer
        const val COLUMN_QTY = "medicine_qty"
        const val COLUMN_MFG = "medicine_mfg" // Text
        const val COLUMN_EXP = "medicine_exp" // Text
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_QTY + " INTEGER, " +
                COLUMN_MFG + " TEXT, " +
                COLUMN_EXP + " TEXT);";

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addMedicine(unique_code:String,medicine_name:String,medicine_price:String,medicine_qty:String,medicine_mfg:String,medicine_exp:String) : Boolean{
        val db = writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_ID,unique_code)
        cv.put(COLUMN_NAME,medicine_name)
        cv.put(COLUMN_PRICE,medicine_price)
        cv.put(COLUMN_QTY,medicine_qty)
        cv.put(COLUMN_MFG,medicine_mfg)
        cv.put(COLUMN_EXP,medicine_exp)
        val result = db.insert(TABLE_NAME,null,cv)

        return result != -1L
    }

    fun getAllMedicines() : Cursor {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor : Cursor = db.rawQuery(query, null)
        return cursor
    }

    fun getOneMedicine(unqCode: String): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME WHERE unique_code = ?"
        val db = readableDatabase
        try {
            val cursor = db.rawQuery(query, arrayOf(unqCode))
            return cursor
        } catch (e: Exception) {
            Log.e("Database error", "Error fetching medicine: ${e.message}")
            return null
        }
    }


    fun updateMedicines(unique_code:String,medicine_name:String,medicine_price:String,medicine_qty:String,medicine_mfg:String,medicine_exp:String) : Boolean {
        val db = writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_ID,unique_code)
        cv.put(COLUMN_NAME,medicine_name)
        cv.put(COLUMN_PRICE,medicine_price)
        cv.put(COLUMN_QTY,medicine_qty)
        cv.put(COLUMN_MFG,medicine_mfg)
        cv.put(COLUMN_EXP,medicine_exp)

        val result = db.update(TABLE_NAME,cv,"$COLUMN_ID=?", arrayOf(unique_code))
        return result > 0
    }

    fun deleteOneMedicine(unique_code: String) : Boolean{
        val db = writableDatabase
        val cv = ContentValues()

        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(unique_code))

        return result > 0
    }
}
