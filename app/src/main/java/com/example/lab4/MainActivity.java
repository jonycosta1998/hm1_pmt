package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab4.tasks.TaskListContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener, TaskInfoFragment.OnFragmentInteractionListener, DeleteDialog.OnDeleteDialogInteractionListener {

    public static final String taskExtra = "taskExtra";

    private int currentItemPosition = -1;

    private MediaPlayer buttonPlayer;
    static public Uri[] sounds;

    public static int SELECTED_SONG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
            }
        });

        sounds = new Uri[4];

        sounds[0] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring01);
        sounds[1] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring02);
        sounds[2] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring03);
        buttonPlayer = new MediaPlayer();
        buttonPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        buttonPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
    }

    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayTaskInFragment(task);
        }else{
            startSecondActivity(task, position);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(TaskListContent.Task task, int position) {

        if (task.picPath != null && !task.picPath.isEmpty()) {
            if (task.picPath.contains("song")) {
                switch (task.picPath) {
                    case "song 1":
                        SELECTED_SONG = 0;
                        break;
                    case "song 2":
                        SELECTED_SONG = 1;
                        break;
                    case "song 3":
                        SELECTED_SONG = 2;
                        break;
                    default:
                        SELECTED_SONG = 0;
                        break;
                }
            }
        }

        buttonPlayer.reset();
        try {
            buttonPlayer.setDataSource(getApplicationContext(),sounds[SELECTED_SONG]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        buttonPlayer.prepareAsync();

    }


    @Override
    public void onListFragmentDeleteButtonInteraction(int position) {
        showDeleteDialog();
        currentItemPosition = position;
    }



    private void startSecondActivity(TaskListContent.Task task, int position) {
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(taskExtra, task);
        startActivity(intent);
    }

    private void displayTaskInFragment(TaskListContent.Task task){
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(taskInfoFragment != null){
            taskInfoFragment.displayTask(task);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size()){
            TaskListContent.removeItem(currentItemPosition);
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
