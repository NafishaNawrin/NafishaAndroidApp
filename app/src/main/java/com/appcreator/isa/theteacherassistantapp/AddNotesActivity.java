package com.appcreator.isa.theteacherassistantapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity
{

    private boolean mIsViewingOrUpdating; //state of the activity
    private long mNoteCreationTime;
    private String mFileName;
    private Note mLoadedNote = null;

    private EditText mEtTitle;
    private EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        mEtTitle = (EditText) findViewById(R.id.edit_text_noteTitle);
        mEtContent = (EditText) findViewById(R.id.edit_text_noteContent);

        //check if view/edit note bundle is set, otherwise user wants to create new note
        mFileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(mFileName != null && !mFileName.isEmpty() && mFileName.endsWith(Utilities.FILE_EXTENSION))
        {
            mLoadedNote = Utilities.getNoteByFileName(getApplicationContext(), mFileName);
            if (mLoadedNote != null)
            {
                mEtTitle.setText(mLoadedNote.getTitle());
                mEtContent.setText(mLoadedNote.getContent());
                mNoteCreationTime = mLoadedNote.getDateTime();
                mIsViewingOrUpdating = true;
            }

        }
        else
        { //user wants to create a new note
            mNoteCreationTime = System.currentTimeMillis();
            mIsViewingOrUpdating = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if(mIsViewingOrUpdating)
        {
            getMenuInflater().inflate(R.menu.menu_note_view, menu);

        }
        else
        {
            getMenuInflater().inflate(R.menu.menu_note_add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_save_note:
            case R.id.action_update:
                validateAndSaveNote();
                break;

            case R.id.action_delete:
                actionDelete();
                break;

            case R.id.action_cancel:
                actionCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        actionCancel();
    }

    private void actionDelete()
    {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this).setTitle("Delete Note").setMessage("Do you really want to delte the note ?").setPositiveButton("YES", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(mLoadedNote != null && Utilities.deleteFile(getApplicationContext(), mFileName))
                        {
                            Toast.makeText(AddNotesActivity.this, mLoadedNote.getTitle() + " has been deleted !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddNotesActivity.this, "Problem occured cannot delete note !" + mLoadedNote.getTitle() + "'"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("NO", null);

        dialogDelete.show();
    }

    /**
     * Handle cancel action
     */
    private void actionCancel()
    {

        if(!checkNoteAltred())
        { //if note is not altered by user (user only viewed the note/or did not write anything)
            finish(); //just exit the activity and go back to NoteMainActivity
        }
        else
        {
            AlertDialog.Builder dialogCancel = new AlertDialog.Builder(this).setTitle("discard changes...").setMessage("are you sure you do not want to save changes to this note?").setPositiveButton("YES", new DialogInterface.OnClickListener()
            {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                        }
             }).setNegativeButton("NO", null);

            dialogCancel.show();
        }
    }

    /**
     * Check to see if a loaded note/new note has been changed by user or not
     * @return true if note is changed, otherwise false
     */
    private boolean checkNoteAltred()
    {
        if(mIsViewingOrUpdating)
        { //if in view/update mode
            return mLoadedNote != null && (!mEtTitle.getText().toString().equalsIgnoreCase(mLoadedNote.getTitle())
                    || !mEtContent.getText().toString().equalsIgnoreCase(mLoadedNote.getContent()));
        }

        else
        { //if in new note mode
            return !mEtTitle.getText().toString().isEmpty() || !mEtContent.getText().toString().isEmpty();
        }
    }

    /**
     * Validate the title and content and save the note and finally exit the activity and go back to NoteMainActivity
     */
    private void validateAndSaveNote()
    {
        //get the content of widgets to make a note object
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();

        //see if user has entered anything :D lol
        if(title.isEmpty())
        { //title
            Toast.makeText(AddNotesActivity.this, "Plese enter a title !", Toast.LENGTH_SHORT).show();
            return;
        }

        if(content.isEmpty())
        { //content
            Toast.makeText(AddNotesActivity.this, "Please fill in the details !", Toast.LENGTH_SHORT).show();
            return;
        }

        //set the creation time, if new note, now, otherwise the loaded note's creation time
        if(mLoadedNote != null)
        {
            mNoteCreationTime = mLoadedNote.getDateTime();
        }
        else
        {
            mNoteCreationTime = System.currentTimeMillis();
        }

        //finally save the note!
        if(Utilities.saveNote(this, new Note(mNoteCreationTime, title, content)))
        { //success!
            //tell user the note was saved!
            Toast.makeText(this, "Note has been saved successfully !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Something went terribly wrong D: Not enough storage space ?", Toast.LENGTH_SHORT).show();
        }

        finish(); //exit the activity, should return us to NoteMainActivity
    }
}
