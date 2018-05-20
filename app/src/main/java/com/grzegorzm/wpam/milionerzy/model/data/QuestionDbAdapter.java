package com.grzegorzm.wpam.milionerzy.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.grzegorzm.wpam.milionerzy.model.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDbAdapter {
    private static final String DEBUG_TAG = "SqLiteQuestionManager";

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "database.db";
    private static final String DB_QUESTION_TABLE = "question";

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
            "DROP TABLE IF EXISTS " + DB_QUESTION_TABLE;

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

    public long insertQuestion(String text, Integer threshold, String correctAnswer, String possibleAnswer0,
                               String possibleAnswer1, String possibleAnswer2) {
        ContentValues newQuestionValues = new ContentValues();
        newQuestionValues.put(KEY_THRESHOLD, threshold);
        newQuestionValues.put(KEY_QUESTION_TEXT, text);
        newQuestionValues.put(KEY_CORRECT_ANSWER, correctAnswer);
        newQuestionValues.put(KEY_POSSIBLE_ANSWER_0, possibleAnswer0);
        newQuestionValues.put(KEY_POSSIBLE_ANSWER_1, possibleAnswer1);
        newQuestionValues.put(KEY_POSSIBLE_ANSWER_2, possibleAnswer2);
        return db.insert(DB_QUESTION_TABLE, null, newQuestionValues);
    }

    public boolean deleteQuestion(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(DB_QUESTION_TABLE, where, null) > 0;
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

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(DEBUG_TAG, "Database creating...");

            db.execSQL(DB_CREATE_QUESTION_TABLE);

            Log.d(DEBUG_TAG, "Table " + DB_QUESTION_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(DEBUG_TAG, "Database updating...");

            db.execSQL(DROP_QUESTION_TABLE);

            Log.d(DEBUG_TAG, "Table " + DB_QUESTION_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }
}
