package com.duogwas.mynote.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.duogwas.mynote.Data.DBHelper;
import com.duogwas.mynote.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.thanguit.toastperfect.ToastPerfect;

public class NoteDetail extends AppCompatActivity {
    DBHelper dbHelper;
    ImageView ivBack;
    EditText edtNoteTitle, edtNoteContent;
    CheckBox cbPinned;
    AppCompatButton btnCreate, btnUpdate, btnDelete;
    LinearLayout lnEditUpdate;
    int createNote;
    Long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        getDataIntent();

        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnCreate.setOnClickListener(v -> {
            createNote();
        });

        btnUpdate.setOnClickListener(v -> {
            updateNote();
        });

    }

    private void initView() {
        dbHelper = new DBHelper(this);
        ivBack = findViewById(R.id.ivBack);
        edtNoteTitle = findViewById(R.id.edtNoteTitle);
        edtNoteContent = findViewById(R.id.edtNoteContent);
        cbPinned = findViewById(R.id.cbPinned);
        btnCreate = findViewById(R.id.btnCreate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        lnEditUpdate = findViewById(R.id.lnEditUpdate);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        createNote = intent.getIntExtra("create", -1);
        if (createNote == 1) {
            lnEditUpdate.setVisibility(View.GONE);
            btnCreate.setVisibility(View.VISIBLE);
        } else {
            lnEditUpdate.setVisibility(View.VISIBLE);
            btnCreate.setVisibility(View.GONE);
        }

        noteId = intent.getLongExtra("noteID", -1);
        if (noteId != -1) {
            getNoteDetail();
        }
    }

    private void getNoteDetail() {
        Cursor cursor = dbHelper.getNoteDetail(noteId);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DBHelper.row_title));
            @SuppressLint("Range") String detail = cursor.getString(cursor.getColumnIndex(DBHelper.row_content));
            @SuppressLint("Range") int checked = cursor.getInt(cursor.getColumnIndex(DBHelper.row_pinned));

            edtNoteTitle.setText(title);
            edtNoteContent.setText(detail);
            if (checked == 0) {
                cbPinned.setChecked(false);
            } else {
                cbPinned.setChecked(true);
            }
        }
    }

    private void createNote() {
        String title = edtNoteTitle.getText().toString().trim();
        String content = edtNoteContent.getText().toString().trim();
        int pinned;
        Boolean pinnedChecked = cbPinned.isChecked();
        if (pinnedChecked) {
            pinned = 1;
        } else {
            pinned = 0;
        }

        //Get Date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        String created = simpleDate.format(calendar.getTime());

        ContentValues values = new ContentValues();
        values.put(DBHelper.row_title, title);
        values.put(DBHelper.row_content, content);
        values.put(DBHelper.row_pinned, pinned);
        values.put(DBHelper.row_created, created);

        if (title.equals("") && content.equals("")) {
            ToastPerfect.makeText(this, ToastPerfect.ERROR, "Vui lòng nhập đầy đủ thông tin", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
        } else {
            dbHelper.insertNote(values);
            ToastPerfect.makeText(this, ToastPerfect.SUCCESS, "Tạo thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateNote() {
        String title = edtNoteTitle.getText().toString().trim();
        String content = edtNoteContent.getText().toString().trim();
        int pinned;
        Boolean pinnedChecked = cbPinned.isChecked();
        if (pinnedChecked) {
            pinned = 1;
        } else {
            pinned = 0;
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.row_title, title);
        values.put(DBHelper.row_content, content);
        values.put(DBHelper.row_pinned, pinned);

        if (title.equals("") && content.equals("")) {
            ToastPerfect.makeText(this, ToastPerfect.ERROR, "Vui lòng nhập đầy đủ thông tin", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
        } else {
            dbHelper.updateNote(values, noteId);
            ToastPerfect.makeText(this, ToastPerfect.SUCCESS, "Cập nhật thành công", ToastPerfect.TOP, ToastPerfect.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void deleteNote() {

    }
}