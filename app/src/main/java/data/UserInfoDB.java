package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import model.UserInfo;

/**
 * Created by rober on 2015/11/09.
 */
public class UserInfoDB {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "UserInfo.db";

    private UserInfoDBHelper mUserInfoDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public UserInfoDB(Context context) {
        mUserInfoDBHelper = new UserInfoDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase =
                mUserInfoDBHelper.getWritableDatabase();
    }

    public List<UserInfo> selectUsers() {
        // Define a projection that specifies which columns from the database
       // you will actually use after this query.
        String[] columns = {
                "email", "pwd"
        };


        Cursor c = mSQLiteDatabase.query(
                "User",  // The table to query
                columns,                                // The columns to return
                null,                                  // The columns for the WHERE clause
                null,                                  // The values for the WHERE clause
                null,                                  // don't group the rows
                null,                                  // don't filter by row groups
                null                                   // The sort order
        );
        c.moveToFirst();
        List<UserInfo> list = new ArrayList<UserInfo>();
        for (int i=0; i<c.getCount(); i++) {
            String email = c.getString(0);
            String pwd = c.getString(1);
            UserInfo userInfo = new UserInfo(email, pwd);
            list.add(userInfo);
            c.moveToNext();
        }

        return list;
    }


    public boolean insertUser(String email, String pwd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("pwd", pwd);

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    public void deleteUserByEmail(String email) {
        mSQLiteDatabase.delete("User", "email=?",new String[] {email});
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }
}


class UserInfoDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER_SQL =
            "CREATE TABLE IF NOT EXISTS User (email TEXT PRIMARY KEY, pwd TEXT)";

    private static final String DROP_USER_SQL =
            "DROP TABLE IF EXISTS User";

    public UserInfoDBHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i,
                          int i1) {
        sqLiteDatabase.execSQL(DROP_USER_SQL);
        onCreate(sqLiteDatabase);
    }

}