package com.appcreator.isa.theteacherassistantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NoteMainActivity extends AppCompatActivity
{

    private ListView mListNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);

        mListNotes = (ListView) findViewById(R.id.listview_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {

        switch (item.getItemId()) 
        {
            case R.id.action_main_new_note: //run AddNotesActivity in new note mode
                startActivity(new Intent(this, AddNotesActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //load saved notes into the listview
        //first, reset the listview
        mListNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(getApplicationContext());

        //sort notes from new to old
        Collections.sort(notes, new Comparator<Note>()
        {
            @Override
            public int compare(Note lhs, Note rhs)
            {
                if(lhs.getDateTime() > rhs.getDateTime())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
        });

        if(notes != null && notes.size() > 0)
        { //check if we have any notes!
            final NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListNotes.setAdapter(na);

            //set click listener for items in the list, by clicking each item the note should be loaded into AddNotesActivity
            mListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //run the AddNotesActivity in view/edit mode
                    String fileName = ((Note) mListNotes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), AddNotesActivity.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });
        }
        else
        { //remind user that we have no notes!
            Toast.makeText(getApplicationContext(), "No notes detected !", Toast.LENGTH_SHORT).show();
        }
    }

}
