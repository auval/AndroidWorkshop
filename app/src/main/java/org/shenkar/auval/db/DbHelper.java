package org.shenkar.auval.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.mta.sharedutils.Util;

import org.shenkar.auval.codesamples.BuildConfig;

import java.util.ArrayList;
import java.util.Random;

/**
 * Official training:
 * https://developer.android.com/training/basics/data-storage/databases.html
 * <p>
 * <p>
 * Created and documented by amir uval on 11/17/16.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TableFavColors.TABLE_NAME;
    private static final String TAG = DbHelper.class.getSimpleName();
    private static final String TEXT_TYPE = " TEXT";

    // create table favcol (_id int primary key, username string, fav_color string) ;
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TableFavColors.TABLE_NAME +
            " (" + TableFavColors._ID + " INTEGER PRIMARY KEY," +
            TableFavColors.COLUMN_UNAME + TEXT_TYPE + COMMA_SEP +
            TableFavColors.COLUMN_FAV_COLOR + TEXT_TYPE + " )";

    public static final String DATABASE_NAME = "my.db";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    private static DbHelper mInstance = null;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mInstance = this;
        // this call is needed for the onCreate to be called on the very first run.
        // and it doesn't do harm afterwards.
        getWritableDatabase();

        if (BuildConfig.DEBUG) {
            // tip:
            Log.i(TAG, "You can play with the database right on the emulator: \n adb shell '" +
                    "sqlite3 /data/data/" + BuildConfig.APPLICATION_ID + "/databases/" + DATABASE_NAME + "'\n" +
                    "or pull the sqlite database to your pc like that:\n" +
                    "adb pull /data/data/" + BuildConfig.APPLICATION_ID + "/databases/" + DATABASE_NAME);
        }
    }

    /**
     * creating a db helper singleton
     *
     * @param context
     * @return
     */
    @WorkerThread
    public static DbHelper getDb(Context context) {

        Util.assertTrue(Util.getThreadType() != Util.TH_UI, "Must not call this on UI thread!");

        if (mInstance == null) {
            synchronized (DATABASE_NAME) {
                // checking again inside the sync block, since it's a critical section.
                // I'm not doing this in the above if because it's really rare
                // and I don't want the overhead of locks all the time
                if (mInstance == null) {
                    // making sure you're not using activity context!!!
                    new DbHelper(context.getApplicationContext()); // mInstance is set in the CTOR
                }
            }
        }
        return mInstance;
    }

    /**
     * Notice:
     * called once per new installation (or factory delete data)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) {

            Log.i(TAG, "db onCreate() called - database created");

        }
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * called every time the DATABASE_VERSION increases
     *
     * @param db   the database
     * @param from the current version this app has
     * @param to   the most up to date version. I'm not using this param.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int from, int to) {
        switch (from) {
            case 1:
                // you can drop the table and re-create it here, or use sql UPDATE command to keep the data.
                // up to you..


            case 2:
                // notice I'm not breaking between the cases. so if the app wasn't updated for awhile
                // it will go through all the stages. 1 to 2, 2 to 3, etc...
                break;
            default:
                if (to == DATABASE_VERSION) {
                    throw new IllegalArgumentException("I forgot to provide a db update code " +
                            "for the new db version #" + DATABASE_VERSION + ", silly me..");
                    // comment: don't worry about runtime cost of concatenating the above strings.
                    // they will become one string during compilation!
                }
        }
        // code sample for drop,
        db.execSQL(SQL_DELETE_ENTRIES);
        // you'll need to create the table again if dropped..
        onCreate(db);

    }

    /**
     * demonstrating 3 ways to insert row into the database:
     * <p>
     * 1st - dangerous, never do something like this
     * 2nd - works great
     * 3rd - best
     *
     * @param nameString
     */
    public void addRow(String nameString, String color) {

        if (false) {
            // I need to insert a row, for example:
            // insert into favcol (username,fav_color) values ('eithan','green')

            // DANGEROUS WAY: CODE INJECTION
            // don't ever concatenate unchecked strings from users to qsl!

            getWritableDatabase().execSQL("insert into " + TableFavColors.TABLE_NAME +
                    " (" + TableFavColors.COLUMN_UNAME + COMMA_SEP +
                    TableFavColors.COLUMN_FAV_COLOR + ") values ('" + nameString + "','" + color + "')");

            // for example: write this name: Inject','hack'),('hey
            // example 2: write this name: a','b');update favcol set fav_color='boo';select 'busted
        } else if (false) {

            // this way prevents code injection:
            getWritableDatabase().execSQL("insert into " + TableFavColors.TABLE_NAME +
                            " (" + TableFavColors.COLUMN_UNAME + COMMA_SEP + TableFavColors.COLUMN_FAV_COLOR + ") values (?,?)",
                    new String[]{nameString, color});

        } else {

            // And this is the best way, using ContentValues:
            ContentValues cv = new ContentValues();
            cv.put(TableFavColors.COLUMN_UNAME, nameString);
            cv.put(TableFavColors.COLUMN_FAV_COLOR, color);
            // I don't need the newKey, but I just demonstrate that you can get it if you want
            long newKey = getWritableDatabase().insertWithOnConflict(TableFavColors.TABLE_NAME, null,
                    cv, SQLiteDatabase.CONFLICT_REPLACE);

        }
    }

    public ArrayList<String> whoLikesWhichColor(String color) {
        // this is the needed sql:
        // select * from favcol where fav_color='blue';
        // you can test it via adb or locally

        ArrayList<String> ret = new ArrayList<>();

        String q = "select * from " + TableFavColors.TABLE_NAME + " where " + TableFavColors.COLUMN_FAV_COLOR + "=?;";

        Cursor c = getWritableDatabase().rawQuery(q, new String[]{color});

        while (c.moveToNext()) {

            // Comments:
            // 1) I know that the name is the second parameter, but it could change in the future,
            //    so it's safest to check the index
            // 2) the query could be "select username from ...", and we could just use index 0
            String name = c.getString(c.getColumnIndex(TableFavColors.COLUMN_UNAME));
            ret.add(name);

        }
        c.close(); // << very important to close the Cursor!
        return ret;
    }

    // BaseColumns provides:
    // String _COUNT = "_count";
    // String _ID = "_id";
    public static class TableFavColors implements BaseColumns {

        public static final String COLUMN_FAV_COLOR = "fav_color";
        public static final String COLUMN_UNAME = "username";
        public static final String TABLE_NAME = "favcol";
    }
}
