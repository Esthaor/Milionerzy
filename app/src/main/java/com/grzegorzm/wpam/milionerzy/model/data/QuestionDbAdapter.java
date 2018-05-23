package com.grzegorzm.wpam.milionerzy.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;
import com.grzegorzm.wpam.milionerzy.model.entity.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionDbAdapter {
    private static final String DEBUG_TAG = "SqLiteQuestionManager";

    private static final int DB_VERSION = 10;
    private static final String DB_NAME = "database.db";
    static final String DB_QUESTION_TABLE = "question";

    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String ALL_FIELD_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_THRESHOLD = "threshold";
    public static final int THRESHOLD_COLUMN = 1;
    public static final String THRESHOLD_OPTIONS = "INTEGER";
    public static final String KEY_QUESTION_TEXT = "text";
    public static final int QUESTION_TEXT_COLUMN = 2;
    public static final String KEY_CORRECT_ANSWER = "correct_answer";
    public static final int CORRECT_ANSWER_COLUMN = 3;
    public static final String KEY_POSSIBLE_ANSWER_0 = "possible_answer_0";
    public static final int POSSIBLE_ANSWER_0_COLUMN = 4;
    public static final String KEY_POSSIBLE_ANSWER_1 = "possible_answer_1";
    public static final int POSSIBLE_ANSWER_1_COLUMN = 5;
    public static final String KEY_POSSIBLE_ANSWER_2 = "possible_answer_2";
    public static final int POSSIBLE_ANSWER_2_COLUMN = 6;

    static final String DB_SCORE_TABLE = "score";
    public static final String KEY_DATE = "date";
    public static final int DATE_COLUMN = 1;
    public static final String DATE_OPTIONS = "INTEGER";
    public static final String KEY_SCORE = "score";
    public static final int SCORE_COLUMN = 2;
    public static final String SCORE_OPTIONS = "INTEGER";


    private static final String DB_CREATE_QUESTION_TABLE =
            "CREATE TABLE " + DB_QUESTION_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_THRESHOLD + " " + THRESHOLD_OPTIONS + ", " +
                    KEY_QUESTION_TEXT + " " + ALL_FIELD_OPTIONS + ", " +
                    KEY_CORRECT_ANSWER + " " + ALL_FIELD_OPTIONS + ", " +
                    KEY_POSSIBLE_ANSWER_0 + " " + ALL_FIELD_OPTIONS + ", " +
                    KEY_POSSIBLE_ANSWER_1 + " " + ALL_FIELD_OPTIONS + ", " +
                    KEY_POSSIBLE_ANSWER_2 + " " + ALL_FIELD_OPTIONS +
                    ");";
    private static final String DROP_QUESTION_TABLE =
            "DROP TABLE IF EXISTS " + DB_QUESTION_TABLE + ";";

    private static final String DB_CREATE_SCORE_TABLE =
            "CREATE TABLE " + DB_SCORE_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_DATE + " " + DATE_OPTIONS + ", " +
                    KEY_SCORE + " " + SCORE_OPTIONS +
                    ");";
    private static final String DROP_SCORE_TABLE =
            "DROP TABLE IF EXISTS " + DB_SCORE_TABLE + ";";

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public QuestionDbAdapter(Context context) {
        this.context = context;
    }

    public QuestionDbAdapter open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Question> getAllQuestionFromThreshold(int threshold) {
        String[] columns = {KEY_ID, KEY_THRESHOLD, KEY_QUESTION_TEXT, KEY_CORRECT_ANSWER,
                KEY_POSSIBLE_ANSWER_0, KEY_POSSIBLE_ANSWER_1, KEY_POSSIBLE_ANSWER_2};
        String where = KEY_THRESHOLD + "=" + threshold;
        Cursor c = db.query(DB_QUESTION_TABLE, columns, where, null, null,
                null, null);
        List<Question> res = new ArrayList();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(QuestionDbAdapter.ID_COLUMN);
            String text = c.getString(QuestionDbAdapter.QUESTION_TEXT_COLUMN);
            String correctAnswer = c.getString(QuestionDbAdapter.CORRECT_ANSWER_COLUMN);
            String possibleAnswer0 = c.getString(QuestionDbAdapter.POSSIBLE_ANSWER_0_COLUMN);
            String possibleAnswer1 = c.getString(QuestionDbAdapter.POSSIBLE_ANSWER_1_COLUMN);
            String possibleAnswer2 = c.getString(QuestionDbAdapter.POSSIBLE_ANSWER_2_COLUMN);
            Question q = new Question(id, threshold, text, correctAnswer, possibleAnswer0,
                    possibleAnswer1, possibleAnswer2);
            res.add(q);
        }
        return res;
    }

    public List<Score> getTop10Score() {
        String[] columns = {KEY_ID, KEY_DATE, KEY_SCORE};
        String orderBy = KEY_SCORE + " DESC";
        String limit = "10";
        Cursor c = db.query(DB_SCORE_TABLE, columns, null, null, null, null,
                orderBy, limit);
        List<Score> res = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int id = c.getInt(QuestionDbAdapter.ID_COLUMN);
            long date = c.getLong(QuestionDbAdapter.DATE_COLUMN);
            int score = c.getInt(QuestionDbAdapter.SCORE_COLUMN);
            Score s = new Score(id, new Date(date), score);
            res.add(s);
        }
        return res;
    }

    public void insertScore(Date date, int score) {
        ContentValues newQuestionValues = new ContentValues();
        newQuestionValues.put(QuestionDbAdapter.KEY_DATE, date.getTime());
        newQuestionValues.put(QuestionDbAdapter.KEY_SCORE, score);
        db.insert(DB_SCORE_TABLE, null, newQuestionValues);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqldb) {
            Log.d(DEBUG_TAG, "Database creating...");

            sqldb.execSQL(DB_CREATE_QUESTION_TABLE);
            sqldb.execSQL(DB_CREATE_SCORE_TABLE);
            QuestionPopulator.createDatabase(sqldb);

            Log.d(DEBUG_TAG, "Table " + DB_QUESTION_TABLE + " ver." + DB_VERSION + " created");
            Log.d(DEBUG_TAG, "Table " + DB_SCORE_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(DEBUG_TAG, "Database updating...");

            db.execSQL(DROP_QUESTION_TABLE);
            db.execSQL(DROP_SCORE_TABLE);

            Log.d(DEBUG_TAG, "Table " + DB_QUESTION_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "Table " + DB_SCORE_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }
}
