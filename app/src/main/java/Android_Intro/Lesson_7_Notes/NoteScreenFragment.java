package Android_Intro.Lesson_7_Notes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class NoteScreenFragment extends Fragment {

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    //==================== Создание верхнего меню =====================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // Активируем верхнее меню
        setHasOptionsMenu(true); // активация меню
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_header, menu); // Инфлейтим меню
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (clickOnNavigateFragment(id)) {
            return true;
        }
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getActivity(), "Go to Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(getActivity(), "Search MyNote", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_sort:
                Toast.makeText(getActivity(), "Do sort MyNotes", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //==============================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.drawer_fragment_note_screen, container, false);

        initiateSideNavMenu(v); // Наше боковое меню

        return v;
    }

    private void initiateSideNavMenu(View v) {
        toolbar = toolbarInitiation(v);

        drawerLayout = v.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
                toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = v.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (clickOnNavigateFragment(id)) {
                drawerLayout.closeDrawer(GravityCompat.START); // При клике возвращаем меню назад
                return true;

            }
            return false;
        });
    }

    @SuppressLint("NonConstantResourceId")
    private boolean clickOnNavigateFragment(int id) {
        switch (id) {
            case R.id.side_home:
                Toast.makeText(getActivity(), "Going to Home Page", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.side_menu_settings:
                Toast.makeText(getActivity(), "Going to Settings App", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.side_menu_about:
                if (getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new AboutApp())
                            //       .addToBackStack(null)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START); // При клике возвращаем меню назад
                    break;
                }
            case R.id.side_share_app:
                Toast.makeText(getActivity(), "Share this App with friends", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.side_feedback:
                Toast.makeText(getActivity(), "Feedback via mail", Toast.LENGTH_SHORT).show();

                return true;
        }
        return false;
    }

    private Toolbar toolbarInitiation(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar_main_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        List<MyNote> myNoteArrayList = new ArrayList<>();
        myNoteArrayList.add(new MyNote("Note Num1", "Note Description1", "Тема заметки 1"));
        myNoteArrayList.add(new MyNote("Note Num2", "Note Description2", "Тема заметки 2"));
        myNoteArrayList.add(new MyNote("Note Num2", "Note Description3", "Тема заметки 3"));
//        myNoteArrayList.add(new MyNote("Заметка4", "Описание4", "Тема заметки 4"));
//        myNoteArrayList.add(new MyNote("Заметка5", "Описание5", "Тема заметки 5"));
//        myNoteArrayList.add(new MyNote("Заметка6", "Описание6", "Тема заметки 6"));


        LinearLayout linearLayout = view.findViewById(R.id.linear_layout_note);

        for (int i = 0; i < myNoteArrayList.size(); i++) {
            String name = myNoteArrayList.get(i).getNoteName();
            //   private static List<MyNote> myNoteArrayList = new ArrayList<>(); //
            TextView textViewOfNotes = new TextView(linearLayout.getContext());
            textViewOfNotes.setText(name);
            textViewOfNotes.setTextSize(24f);
            textViewOfNotes.setPadding(60, 16, 0, 0);

            int index = i;

            textViewOfNotes.setOnClickListener(v -> {

                //   goToNoteDescriptionWithData(index); // Список заметок будет повторяться при возврате
                SettingsStorage ss = new SettingsStorage();
                Bundle bundle = new Bundle();
                bundle.putString(ss.getDataToFragmentDescription(), myNoteArrayList.get(index).getNoteDescription());
                MainActivity.getNoteDescriptionFragment().setArguments(bundle);
                if (getFragmentManager() != null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, MainActivity.getNoteDescriptionFragment())
                            //           .addToBackStack(null)
                            .commit();
                }
            });

            linearLayout.addView(textViewOfNotes);
        }
    }

//    private void goToNoteDescriptionWithData(int index) { // Список заметок будет повторяться при возврате
//        Bundle bundle = new Bundle();
//        bundle.putString("data", myNoteArrayList().get(index).getNoteDescription());
//        MainActivity.getNoteDescription().setArguments(bundle);
//        if (getFragmentManager() != null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, MainActivity.getNoteDescription())
//                    //           .addToBackStack(null)
//                    .commit();
//        }
//    }


}