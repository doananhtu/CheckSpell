package myapp.anhtu.com.checkspell.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by anhtu on 4/22/2017.
 */

public class ToHopAmGiua extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AmGiua";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "amgiua";
    private static final String KEY_ID = "id";
    private static final String KEY_CONTENT = "content";

    public ToHopAmGiua(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE IF NOT EXISTS " +
                "%s" +
                "(%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "%s INTEGER)",TABLE_NAME,KEY_ID,KEY_CONTENT);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void addAmGiua(String amGiua){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT,amGiua);
        db.insertWithOnConflict(TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<String> getAllAmGiua(){
        ArrayList<String> arrAmGiua = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            String str = new String(cursor.getString(1));
            arrAmGiua.add(str);
            cursor.moveToNext();
        }
        return arrAmGiua;
    }

    public void delAllAmGiua(){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE * FROM " + TABLE_NAME;
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
