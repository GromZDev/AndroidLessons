package Android_Intro.Lesson_8_Notes;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static final NoteScreenFragment NOTE_SCREEN_FRAGMENT = new NoteScreenFragment();

    @SuppressLint("StaticFieldLeak")
    private static final NoteDescriptionFragment NOTE_DESCRIPTION_FRAGMENT = new NoteDescriptionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, NOTE_SCREEN_FRAGMENT);
        fragmentTransaction.commit();

    }

    public static NoteScreenFragment getNoteScreenFragment() {
        return NOTE_SCREEN_FRAGMENT;
    }

    public static NoteDescriptionFragment getNoteDescriptionFragment() {
        return NOTE_DESCRIPTION_FRAGMENT;
    }

}