package com.misc_proj.lammi.secretnuclearbaseapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.IfNotExists;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Laemmi on 27.7.2016.
 */
@Database(version = SecretDatabase.VERSION,
        packageName = "com.misc_proj.lammi.secretnuclearbaseapplication.provider")
public final class SecretDatabase {
    public static final int VERSION = 1;

    private SecretDatabase(){}

    public static class Tables {

        @Table(ListColumns.class) @IfNotExists
        public static final String LISTS = "lists";
    }

    @Table(SecretColumns.class) public static final String SECRETS = "secrets";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {
    }

    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + SECRETS;
}
