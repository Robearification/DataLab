package data;

import android.support.v4.content.ContextCompat;

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