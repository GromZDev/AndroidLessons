package Android_Intro.Lesson_7_Notes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @SuppressLint("StaticFieldLeak")
    private static final NoteScreen noteScreen = new NoteScreen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, noteScreen);
        fragmentTransaction.commit();

//====================== Боковое меню ======================

        drawerLayout = findViewById(R.id.drawer_layout); // Из разметки мэйна
        navigationView = findViewById(R.id.navigation_view); // Из разметки мэйна
        toolbar = findViewById(R.id.toolbar); // Из разметки мэйна
//==========================================================
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public static NoteScreen getNoteScreen() {
        return noteScreen;
    }

}