package Android_Intro.Lesson_7_Notes;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static final NoteScreen noteScreen = new NoteScreen();

    @SuppressLint("StaticFieldLeak")
    private static final NoteDescription noteDescription = new NoteDescription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, noteScreen);
        fragmentTransaction.commit();

    }

    public static NoteScreen getNoteScreen() {
        return noteScreen;
    }

    public static NoteDescription getNoteDescription() {
        return noteDescription;
    }

}