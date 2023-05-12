package lt.tomas.quizzies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import lt.tomas.quizzies.QuizContract.*;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quizzies.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        insertCategory(c1);
        Category c2 = new Category("Geography");
        insertCategory(c2);
        Category c3 = new Category("Math");
        insertCategory(c3);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("A memory location that holds a single letter or number is called _ ?",
                "Char", "Double", "Int", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        insertQuestion(q1);
        Question q2 = new Question("What dose this equation mean ? a != t",
                "A is assinged t", "A is not equal to t", "T is add to a", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q2);
        Question q3 = new Question("Which data structure uses LIFO?",
                "Array", "Queues", "Stacks", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        insertQuestion(q3);
        Question q4 = new Question("One loop inside the body of another loop is called?",
                "Nested", "Loop in loop", "Double loops", 1,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q4);
        Question q5 = new Question("A Syntax Error is ?",
                "An error caused by language rules being broken", "An error you find at the end when the program gives out a wrong value due to logic error.", "An error you will never find", 1,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q5);
        Question q6 = new Question("A short sections of code written to complete a task",
                "Array", "Function", "Loop", 2,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        insertQuestion(q6);
        Question q7 = new Question("How many continents are there?",
                "7", "4", "13", 1,
                Question.DIFFICULTY_EASY, Category.GEOGRAPHY);
        insertQuestion(q7);
        Question q8 = new Question("What is the largest continent by land area?",
                "Europe", "Asia", "Africa", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOGRAPHY);
        insertQuestion(q8);
        Question q9 = new Question("How many oceans are there?",
                "4", "7", "5", 3,
                Question.DIFFICULTY_MEDIUM, Category.GEOGRAPHY);
        insertQuestion(q9);
        Question q10 = new Question("Which is the largest country in the world by land area?",
                "Russia", "Brazil", "United States", 1,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q10);
        Question q11 = new Question("Which is the largest country in the world by population?",
                "China", "India", "United States", 1,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q11);
        Question q12 = new Question("Which of these is NOT a country?",
                "Brunei", "Latveria", "Kiribati", 2,
                Question.DIFFICULTY_HARD, Category.GEOGRAPHY);
        insertQuestion(q12);
        Question q13 = new Question("2+2 = ?",
                "4", "22", "2", 1,
                Question.DIFFICULTY_EASY, Category.MATH);
        insertQuestion(q13);
        Question q14 = new Question("What is 11 x 11?",
                "111", "121", "112", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATH);
        insertQuestion(q14);
        Question q15 = new Question("What is 18 divided by 3?",
                "8", "9", "6", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATH);
        insertQuestion(q15);
        Question q16 = new Question("Jonukas turejo 6 tortukus ir 30 gabaliuku sonines, jis suvalge lygiai puse ju. Ka dabar turi Jonukas?",
                "Visi Atsakymai teisingi", "Diabeta", "Laime", 1,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q16);
        Question q17 = new Question("Whats 9 + 9 + 9 + 9 + 9 + 9 + 9 + 9 + 9 + 9 + 9?",
                "99", "90", "108", 1,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q17);
        Question q18 = new Question("How many minutes are in half of a day?",
                "1440", "720", "3600", 2,
                Question.DIFFICULTY_HARD, Category.MATH);
        insertQuestion(q18);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}