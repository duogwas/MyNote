package com.duogwas.mynote.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duogwas.mynote.Adapter.NoteAdapter;
import com.duogwas.mynote.Data.DBHelper;
import com.duogwas.mynote.Model.Note;
import com.duogwas.mynote.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Integer result;
    ArrayList<Note> noteArrayList = new ArrayList<>();
    ArrayList<Note> notePinnedArrayList = new ArrayList<>();
    ConstraintLayout clNoNote, clAction;
    ScrollView svNote, svNoteSearch;
    TextView tvPinned, tvCount;
    RecyclerView rcvPinnedNote, rcvAllNote, rcvSearch;
    ImageButton btnCreateNote;
    SearchView searchView;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        countNote();
        getPinnedNote();

        btnCreateNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteDetail.class);
            intent.putExtra("create", 1);
            startActivity(intent);
            finish();
        });


    }

    private void initView() {
        dbHelper = new DBHelper(this);
        clNoNote = findViewById(R.id.clNoNote);
        clAction = findViewById(R.id.clAction);
        svNote = findViewById(R.id.svNote);
        svNoteSearch = findViewById(R.id.svNoteSearch);
        tvPinned = findViewById(R.id.tvPinned);
        tvCount = findViewById(R.id.tvCount);
        rcvPinnedNote = findViewById(R.id.rcvPinnedNote);
        rcvAllNote = findViewById(R.id.rcvAllNote);
        rcvSearch = findViewById(R.id.rcvSearch);
        btnCreateNote = findViewById(R.id.btnCreateNote);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    private void countNote() {
        Cursor count = dbHelper.count("SELECT COUNT(id) FROM tbl_note");
        while (count.moveToNext()) {
            result = count.getInt(0);
        }
        String dem = Integer.toString(result);
        tvCount.setText(dem + " ghi chú");
        if (result > 0) {
            clNoNote.setVisibility(View.GONE);
            svNote.setVisibility(View.VISIBLE);
            getAllNote();
        } else {
            clNoNote.setVisibility(View.VISIBLE);
            svNote.setVisibility(View.GONE);
        }
    }

    private void getAllNote() {
        Cursor cursor = dbHelper.getAllNote();
        while (cursor.moveToNext()) {
            noteArrayList.add(new Note(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            ));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvAllNote.setLayoutManager(linearLayoutManager);
        NoteAdapter noteAdapter = new NoteAdapter(noteArrayList, this);
        rcvAllNote.setAdapter(noteAdapter);
    }

    private void getPinnedNote() {
        Cursor cursor = dbHelper.getPinnedNote();
        if (cursor.getCount() > 0) {
            tvPinned.setVisibility(View.VISIBLE);
            rcvPinnedNote.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {
                notePinnedArrayList.add(new Note(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4)
                ));
            }
        } else {
            tvPinned.setVisibility(View.GONE);
            rcvPinnedNote.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvPinnedNote.setLayoutManager(linearLayoutManager);
        NoteAdapter noteAdapter = new NoteAdapter(notePinnedArrayList, this);
        rcvPinnedNote.setAdapter(noteAdapter);
    }

    private void searchNote(String keyword) {
        ArrayList<Note> noteSearch = new ArrayList<>();
        for (Note note : noteArrayList) {
            if (note.title.toLowerCase().contains(keyword.toLowerCase())) {
                noteSearch.add(note);
            }
        }
        if(!noteSearch.isEmpty()){
            clNoNote.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rcvSearch.setLayoutManager(linearLayoutManager);
            NoteAdapter noteAdapter = new NoteAdapter(noteSearch, this);
            rcvSearch.setAdapter(noteAdapter);
        }
        else {
            clNoNote.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() == 0) {
            clNoNote.setVisibility(View.GONE);
            svNote.setVisibility(View.VISIBLE);
            clAction.setVisibility(View.VISIBLE);
            svNoteSearch.setVisibility(View.GONE);
        } else {
            svNote.setVisibility(View.GONE);
            clAction.setVisibility(View.GONE);
            svNoteSearch.setVisibility(View.VISIBLE);
            searchNote(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            clNoNote.setVisibility(View.GONE);
            svNote.setVisibility(View.VISIBLE);
            clAction.setVisibility(View.VISIBLE);
            svNoteSearch.setVisibility(View.GONE);
        } else {
            svNote.setVisibility(View.GONE);
            clAction.setVisibility(View.GONE);
            svNoteSearch.setVisibility(View.VISIBLE);
            searchNote(newText);
        }
        return false;
    }
}