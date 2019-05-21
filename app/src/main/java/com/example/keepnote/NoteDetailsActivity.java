package com.example.keepnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class NoteDetailsActivity extends AppCompatActivity {
    public static final int ADD_NOTE = 0;
    public static final int EDDIT_NOTE = 1;
    public static final String ID_NOTE = "id_note";
    public static final String TEXT_NOTE = "edit_note";
    public static final String CURRENT_NOTE ="current_note";
    private int idNote = -1;
    private TextInputEditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail_edit);

        inputEditText = (TextInputEditText)findViewById(R.id.input_edit_text);

        if(savedInstanceState != null){
            inputEditText.setText(savedInstanceState.getString(CURRENT_NOTE));
            idNote = savedInstanceState.getInt(ID_NOTE);
        }

        Intent intent = getIntent();
        String noteText = intent.getStringExtra(TEXT_NOTE);
        idNote = intent.getIntExtra(ID_NOTE,-1);
        inputEditText.setText(noteText);

        inputEditText.requestFocus();
        inputEditText.setSelection(inputEditText.length());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @Override
    public void onBackPressed() {
        saveNote();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_NOTE, inputEditText.getText().toString());
        outState.putInt(ID_NOTE, idNote);
    }

    private void saveNote(){
        String textNote = inputEditText.getText().toString();
        if(!textNote.isEmpty()){
            Intent answerIntent = new Intent(NoteDetailsActivity.this, MainActivity.class);
            answerIntent.putExtra(TEXT_NOTE, inputEditText.getText().toString());
            if(idNote >= 0){
                answerIntent.putExtra(ID_NOTE, idNote);
                setResult(RESULT_OK, answerIntent);
            }else {
                setResult(RESULT_OK, answerIntent);
            }
            finish();
        }else {
            Toast.makeText(this, "Заметка не может быть пустой! Введите текст заметки!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickSaveNote(View view) {
        saveNote();
    }
}
