package com.example.finalproyect_allengram.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalproyect_allengram.ModeloDatos.Schema_Database;

public class SQL_Helper extends SQLiteOpenHelper {

    public SQL_Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
       //Comando para borrar bbdd
       //  context.deleteDatabase(Schema_Database.NOM_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + Schema_Database.NOM_TAB + " (" +
                Schema_Database.NOM_COL_ID + " INTEGER PRIMARY KEY," +
                Schema_Database.NOM_COL_NAME + " TEXT," +
                Schema_Database.NOM_COL_THEME + " INTEGER," +
                Schema_Database.NOM_COL_PASS + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
