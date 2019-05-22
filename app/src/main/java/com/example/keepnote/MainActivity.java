package com.example.keepnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> listNotes;
    private RecyclerView notesRecycler;
    private NotesAdapter notesAdapter;
    private EditText txtSearch;
    private ImageView imgSearch;
    private ImageView imgClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadNotes();
        initRecyclerView();
        searchAction();

        notesAdapter.setListener(new NotesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
                intent.putExtra(NoteDetailsActivity.TEXT_NOTE, notesAdapter.getCurrentText(position));
                intent.putExtra(NoteDetailsActivity.ID_NOTE, position);
                startActivityForResult(intent, NoteDetailsActivity.EDIT_NOTE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
                startActivityForResult(intent, NoteDetailsActivity.ADD_NOTE);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int idNote = data.getIntExtra(NoteDetailsActivity.ID_NOTE, -1);
        String textNote = data.getStringExtra(NoteDetailsActivity.TEXT_NOTE);

        if(resultCode == RESULT_OK) {
            if (requestCode == NoteDetailsActivity.EDIT_NOTE) {
                 if (idNote != -1) {
                    notesAdapter.editItem(idNote, textNote);
                } else {
                    Toast.makeText(this, "Ошибка изменения заметки!", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == NoteDetailsActivity.ADD_NOTE) {
                notesAdapter.addItem(textNote);
            }
        }else if(resultCode == NoteDetailsActivity.RESULT_DELETE){
            notesAdapter.deleteItem(idNote);
        }else {
            Toast.makeText(this, "Отмена Добавления/Редактирования заметки", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotes() {
        listNotes = new ArrayList<>();

        listNotes.add("Заметка 1");
        listNotes.add("Заметка 2");
        listNotes.add("Заметка 3");
        listNotes.add("Заметка 4");
        listNotes.add("Заметка 5");
        listNotes.add("Заметка 6");
    }

    private void initRecyclerView(){
        notesRecycler = findViewById(R.id.recycler_view_notes);
        notesRecycler.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(listNotes);
        notesRecycler.setAdapter(notesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
            case R.id.action_sort:
                return true;
            case R.id.action_sortUp:
                sort(false);
                return true;
            case R.id.action_sortDown:
                sort(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sort(boolean isSortDown){
        if(isSortDown) {
            Toast.makeText(this, "Сортирую по убыванию", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Сортирую по возрастанию", Toast.LENGTH_SHORT).show();

        }
    }

    private void searchAction(){
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                txtSearch.setFocusable(true);
                txtSearch.setFocusableInTouchMode(true);
                txtSearch.requestFocus();
                UpdateList();
            }
        });
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                UpdateList();
            }
        });
        imgClear = (ImageView) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
                closeKeyboard();
                UpdateList();
            }
        });
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    private void UpdateList() {
        //Выполняем запрос для обновления списка
    }

}
