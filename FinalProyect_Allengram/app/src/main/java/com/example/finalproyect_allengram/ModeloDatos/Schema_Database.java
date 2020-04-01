package com.example.finalproyect_allengram.ModeloDatos;

import android.provider.BaseColumns;

public interface Schema_Database extends BaseColumns {
    String NOM_DB = "Gestion_User";
    String NOM_TAB = "Usuarios";
    String NOM_COL_NAME = "user_name";
    String NOM_COL_THEME = "theme";
    String NOM_COL_PASS = "pass";
    String NOM_COL_ID = "id";
}