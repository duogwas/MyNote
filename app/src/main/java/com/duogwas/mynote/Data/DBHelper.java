package com.duogwas.mynote.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String database_name = "db_note";
    public static final String table_name = "tbl_note";
    public static final String row_id = "id";
    public static final String row_title = "title";
    public static final String row_content = "content";
    public static final String row_pinned = "pinned";
    public static final String row_created = "created_at";
    private SQLiteDatabase myDB;

    public DBHelper(Context context) {
        super(context, database_name, null, 1);
        myDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name +
                "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_title + " TEXT,"
                + row_content + " TEXT,"
                + row_pinned + " INTEGER CHECK (" + row_pinned + " IN (0, 1)),"
                + row_created + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor count(String sql) {
        return myDB.rawQuery(sql, null);
    }

    public Cursor getAllNote() {
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_pinned + "= 0" + " ORDER BY " + row_id + " DESC ", null);
        return cursor;
    }

    public Cursor getNoteDetail(Long id) {
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cursor;
    }

    public Cursor getPinnedNote() {
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_pinned + "= 1" + " ORDER BY " + row_id + " DESC ", null);
        return cursor;
    }

    public void insertNote(ContentValues values) {
        myDB.insert(table_name, null, values);
    }

    public void updateNote(ContentValues values, long id) {
        myDB.update(table_name, values, row_id + "=" + id, null);
    }

    public void deleteNote(long id) {
        myDB.delete(table_name, row_id + "=" + id, null);
    }
}
