package id.poncoe.crudsqlite.kotlin

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import java.util.ArrayList
import java.util.HashMap

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val allData: ArrayList<HashMap<String, String>>
        get() {
            val wordList: ArrayList<HashMap<String, String>>
            wordList = ArrayList()
            val selectQuery = "SELECT * FROM $TABLE_SQLite"
            val database = this.writableDatabase
            val cursor = database.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val map = HashMap<String, String>()
                    map[COLUMN_ID] = cursor.getString(0)
                    map[COLUMN_NAME] = cursor.getString(1)
                    map[COLUMN_ADDRESS] = cursor.getString(2)
                    wordList.add(map)
                } while (cursor.moveToNext())
            }

            Log.e("select sqlite ", "" + wordList)

            database.close()
            return wordList
        }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL" +
                " )"

        db.execSQL(SQL_CREATE_MOVIE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SQLite")
        onCreate(db)
    }

    fun insert(name: String, address: String) {
        val database = this.writableDatabase
        val queryValues = "INSERT INTO " + TABLE_SQLite + " (name, address) " +
                "VALUES ('" + name + "', '" + address + "')"

        Log.e("insert sqlite ", "" + queryValues)
        database.execSQL(queryValues)
        database.close()
    }

    fun update(id: Int, name: String, address: String) {
        val database = this.writableDatabase

        val updateQuery = ("UPDATE " + TABLE_SQLite + " SET "
                + COLUMN_NAME + "='" + name + "', "
                + COLUMN_ADDRESS + "='" + address + "'"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'")
        Log.e("update sqlite ", updateQuery)
        database.execSQL(updateQuery)
        database.close()
    }

    fun delete(id: Int) {
        val database = this.writableDatabase

        val updateQuery = "DELETE FROM $TABLE_SQLite WHERE $COLUMN_ID='$id'"
        Log.e("update sqlite ", updateQuery)
        database.execSQL(updateQuery)
        database.close()
    }

    companion object {

        private val DATABASE_VERSION = 1

        internal val DATABASE_NAME = "crud.db"

        val TABLE_SQLite = "sqlite"

        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_ADDRESS = "address"
    }

}