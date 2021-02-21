package Android_Intro.Lesson_7_Notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

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

//        drawerLayout = findViewById(R.id.drawer_layout); // Из разметки мэйна
//        navigationView = findViewById(R.id.navigation_view); // Из разметки мэйна
//        toolbar = findViewById(R.id.toolbar); // Из разметки мэйна
//==========================================================
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//====================== Ставим тулбар =====================
     //   initToolbar();
//====================== ============= =====================



    }
//    private void initToolbar() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }

//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_settings:
//                Toast.makeText(MainActivity.this, "Go to Settings", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_search:
//                Toast.makeText(MainActivity.this, "Search MyNote", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_sort:
//                Toast.makeText(MainActivity.this, "Do sort MyNotes", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main_header, menu); // Инфлейтим меню
//        return true;
//    }



    public static NoteScreen getNoteScreen() {
        return noteScreen;
    }

}