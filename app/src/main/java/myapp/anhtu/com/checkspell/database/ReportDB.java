package myapp.anhtu.com.checkspell.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import myapp.anhtu.com.checkspell.entity.Report;

/**
 * Created by anhtu on 4/23/2017.
 */

public class ReportDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Report";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "report";
    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DESCRIP = "description";

    public ReportDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS " +
                "%s" +
                "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR, " +
                "%s VARCHAR)", TABLE_NAME, KEY_ID, KEY_CONTENT, KEY_DESCRIP);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void addReport(String content, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT,content);
        values.put(KEY_DESCRIP,description);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<Report> getAllReport(){
        ArrayList<Report> arrReport = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            String content = new String(cursor.getString(1));
            String description = new String(cursor.getString(2));
            arrReport.add(new Report(content,description));
            cursor.moveToNext();
        }
        return arrReport;
    }

    public void delAllReport(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE * FROM " + TABLE_NAME;
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
