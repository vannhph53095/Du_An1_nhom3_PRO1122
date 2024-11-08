package fpoly.ph53095.nhom3_du_an_1_pro1122;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fpoly.ph53095.nhom3_du_an_1_pro1122.entity.PhimMoi;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phim.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PHIM = "phim";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PHIM_TABLE = "CREATE TABLE " + TABLE_PHIM + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_IMAGE + " INTEGER" + ")";
        db.execSQL(CREATE_PHIM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHIM);
        onCreate(db);
    }

    public void addPhim(PhimMoi phim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, phim.getTitle());
        values.put(COLUMN_IMAGE, phim.getImage());
        db.insert(TABLE_PHIM, null, values);
        db.close();
    }

    public List<PhimMoi> getAllPhim() {
        List<PhimMoi> phimList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PHIM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PhimMoi phim = new PhimMoi(cursor.getString(1), cursor.getInt(2));
                phimList.add(phim);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return phimList;
    }
}