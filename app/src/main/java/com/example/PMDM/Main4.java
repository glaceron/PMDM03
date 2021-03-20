package com.example.PMDM;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.PMDM.model.Web;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Main4 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    AdaptadorWebs adapter;
    RecyclerView rv_list;
    FloatingActionButton fab;

    private static final String AUTHORITY = "com.example.PMDM";
    private static final String BASE_PATH = "webs";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int WEBS = 1;
    private static final int WEBS_ID = 2;

    private boolean firstTimeLoaded = false;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, WEBS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", WEBS_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        rv_list = (RecyclerView) findViewById(R.id.web_list);

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        fab = (FloatingActionButton) findViewById(R.id.fabAgregarWeb);
        fab.setOnClickListener(this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id == 0) {
            return new CursorLoader(this, CONTENT_URI, null, null, null, null);
        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        List<Web> list = new ArrayList<>();

        while (cursor.moveToNext()) {

            String nombreObtenidoDeBD = cursor.getString(0);
            String linkObtenidoDeBD = cursor.getString(1);
            String emailObtenidoDeBD = cursor.getString(2);
            String categoriaObtenidoDeBD = cursor.getString(3);
            String fotoObtenidoDeBD = cursor.getString(4);

            Web web = new Web(nombreObtenidoDeBD, linkObtenidoDeBD, emailObtenidoDeBD, categoriaObtenidoDeBD, fotoObtenidoDeBD);
            list.add(web);
        }

        adapter = new AdaptadorWebs(this, list);
        // rv_list.setAdapter(adapter);

        rv_list.setAdapter(adapter);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAgregarWeb:
                if (firstTimeLoaded == false) {
                    getLoaderManager().initLoader(0, null, this);
                    firstTimeLoaded = true;
                } else {
                    getLoaderManager().restartLoader(0, null, this);
                }

                break;
            default:
                break;
        }

    }
}
