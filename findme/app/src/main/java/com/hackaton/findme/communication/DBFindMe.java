package com.hackaton.findme.communication;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.DocumentChange;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gregrtw on 15-02-22
 */
public class DBFindMe {

    private static final long UNI_ID = 0;
    public static final String SyncURL = "http://54.211.39.201:4984/hack19/";
    private Map<String, Object> documentContent = new HashMap<String, Object>();
    private String dbname = "FindMeDB";
    Database database;
    Manager manager;
    private List<QueryRow> list;
    Query query;
    private View myView;

    public DBFindMe() {
        if (!Manager.isValidDatabaseName(dbname)) {
            Log.e("DB", "Bad database name");
            return;
        }

        try {
            database = manager.getDatabase(dbname);
            myView = database.getView("");
            Log.d("DB", "Database created");
        } catch (CouchbaseLiteException e) {
            Log.e("DB","Cannot get database");
            return;
        }
    }

    public void CreateNewUserDoc(long u_id, String name, String LongLat) {
        documentContent.put("unique_id", u_id);
        documentContent.put("user_name", name);
        documentContent.put("LongLat", LongLat);
        Log.d("Doc", "docContent= " + String.valueOf(documentContent));

        Document doc = database.createDocument();
        try {
            doc.putProperties(documentContent);
            Log.d("Doc", "Document written to db "+ dbname + " with ID= " + doc.getId());
        } catch (CouchbaseLiteException e) {
            Log.e("Doc", "Cannot write document to DB");
        }
    }

    public void RetrieveUserDocs(long u_id) {
        Document retrieveDoc;
        query = new Query();
        query.setReduce(true);

    }



}

