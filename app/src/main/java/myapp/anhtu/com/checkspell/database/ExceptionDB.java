package myapp.anhtu.com.checkspell.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by anhtu on 4/23/2017.
 */

public class ExceptionDB extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "Exception";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "exception";
    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";

    public ExceptionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS " +
                "%s" +
                "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR)", TABLE_NAME, KEY_ID, KEY_CONTENT);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void addEx(String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT,content);
        db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<String> getAllEx(){
        ArrayList<String> arrEx = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            arrEx.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return arrEx;
    }

    public void delAllReport(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE * FROM " + TABLE_NAME;
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
