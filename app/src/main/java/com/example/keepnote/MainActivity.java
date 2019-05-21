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
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listNotes;
    private RecyclerView notesRecycler;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadNotes();
        initRecyclerView();

        notesAdapter.setListener(new NotesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
                intent.putExtra(NoteDetailsActivity.TEXT_NOTE, notesAdapter.getCurrentText(position));
                intent.putExtra(NoteDetailsActivity.ID_NOTE, position);
                startActivityForResult(intent, NoteDetailsActivity.EDDIT_NOTE);
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
        if(resultCode == RESULT_OK){
            String textNote = data.getStringExtra(NoteDetailsActivity.TEXT_NOTE);

            if(requestCode == NoteDetailsActivity.EDDIT_NOTE) {
                int idNote = data.getIntExtra(NoteDetailsActivity.ID_NOTE, -1);
                if(idNote != -1) {
                    notesAdapter.editItem(idNote, textNote);
                }else{
                    Toast.makeText(this, "Ошибка изменения заметки!", Toast.LENGTH_SHORT).show();
                }
            }else if(requestCode == NoteDetailsActivity.ADD_NOTE){
                notesAdapter.addItem(textNote);
            }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
