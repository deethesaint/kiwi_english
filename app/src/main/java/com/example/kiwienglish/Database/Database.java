package com.example.kiwienglish.Database;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Database extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("kiwi_app_database.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static Realm getDBInstance() {
        return Realm.getDefaultInstance();
    }

}
