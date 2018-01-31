package tanat.androidtesttask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Cache";
    public static final String TABLE_NAME = "table_cache";
    private static final int DB_VERSION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";

    public static final String ID = "_id";

    public static final String NAME_FROM_CITY = "name_from_city";
    public static final String HIGHLIGHT_FROM_CITY = "highlight_from_city";
    public static final String ID_FROM_CITY = "id_from_city";

    public static final String NAME_TO_CITY = "name_to_city";
    public static final String HIGHLIGHT_TO_CITY = "highlight_to_city";
    public static final String ID_TO_CITY = "id_to_city";

    public static final String INFO = "info";

    public static final String FROM_DATE = "from_date";
    public static final String FROM_TIME = "from_time";
    public static final String FROM_INFO = "from_info";

    public static final String TO_DATE = "to_date";
    public static final String TO_TIME = "to_time";
    public static final String TO_INFO = "to_info";

    public static final String PRICE = "price";
    public static final String BUS_ID = "bus_id";
    public static final String RESERVATION_COUNT = "reservation_count";

    // create database and table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP
                + NAME_FROM_CITY + TEXT_TYPE + COMMA_SEP
                + HIGHLIGHT_FROM_CITY + TEXT_TYPE + COMMA_SEP
                + ID_FROM_CITY + TEXT_TYPE + COMMA_SEP
                + NAME_TO_CITY + TEXT_TYPE + COMMA_SEP
                + HIGHLIGHT_TO_CITY + TEXT_TYPE + COMMA_SEP
                + ID_TO_CITY + TEXT_TYPE + COMMA_SEP
                + INFO + TEXT_TYPE + COMMA_SEP
                + FROM_DATE + TEXT_TYPE + COMMA_SEP
                + FROM_TIME + TEXT_TYPE + COMMA_SEP
                + FROM_INFO + TEXT_TYPE + COMMA_SEP
                + TO_DATE + TEXT_TYPE + COMMA_SEP
                + TO_TIME + TEXT_TYPE + COMMA_SEP
                + TO_INFO + TEXT_TYPE + COMMA_SEP
                + PRICE + TEXT_TYPE + COMMA_SEP
                + BUS_ID + TEXT_TYPE + COMMA_SEP
                + RESERVATION_COUNT + TEXT_TYPE + " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
