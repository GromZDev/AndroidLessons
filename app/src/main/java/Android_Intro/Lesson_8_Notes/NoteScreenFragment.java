package Android_Intro.Lesson_8_Notes;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class NoteScreenFragment extends Fragment implements MyNoteAdapterCallback {

    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private List<MyNote> noteList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // Активируем верхнее меню
        setHasOptionsMenu(true); // активация меню
        super.onCreate(savedInstanceState);

        noteList = new ArrayList<>();
        noteList.add(new MyNote("Note Num1", "Note Description1", "Тема заметки 1"));
        noteList.add(new MyNote("Note Num2", "Note Description2", "Тема заметки 2"));
        noteList.add(new MyNote("Note Num3", "Note Description3", "Тема заметки 3"));
        noteList.add(new MyNote("Note Num4", "Note Description4", "Тема заметки 4"));
        noteList.add(new MyNote("Note Num5", "Note Description5", "Тема заметки 5"));
        noteList.add(new MyNote("Note Num6", "Note Description6", "Тема заметки 6"));
        noteList.add(new MyNote("Note Num7", "Note Description7", "Тема заметки 7"));
        noteList.add(new MyNote("Note Num8", "Note Description8", "Тема заметки 8"));
        noteList.add(new MyNote("Note Num9", "Note Description9", "Тема заметки 9"));
        noteList.add(new MyNote("Note Num10", "Note Description10", "Тема заметки 10"));
        noteList.add(new MyNote("Note Num11", "Note Description11", "Тема заметки 11"));
        noteList.add(new MyNote("Note Num12", "Note Description12", "Тема заметки 12"));
        noteList.add(new MyNote("Note Num13", "Note Description13", "Тема заметки 13"));
        noteList.add(new MyNote("Note Num14", "Note Description14", "Тема заметки 14"));
        noteList.add(new MyNote("Note Num15", "Note Description15", "Тема заметки 15"));
        noteList.add(new MyNote("Note Num16", "Note Description16", "Тема заметки 16"));
        noteList.add(new MyNote("Note Num17", "Note Description17", "Тема заметки 17"));
        noteList.add(new MyNote("Note Num18", "Note Description18", "Тема заметки 18"));
        noteList.add(new MyNote("Note Num19", "Note Description19", "Тема заметки 19"));
        noteList.add(new MyNote("Note Num20", "Note Description20", "Тема заметки 20"));


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewNoteFragment = inflater.inflate(R.layout.drawer_fragment_note_screen, container, false);

        initiateSideNavMenu(viewNoteFragment); // Наше боковое меню

        return viewNoteFragment;
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

//================================ Сетим RecyclerView =========================
        initNoteListByRecyclerView(view);
//=============================================================================

    }

    private void initNoteListByRecyclerView(@NonNull View view) {
        RecyclerView noteRecyclerView = view.findViewById(R.id.recyclerView_Notes);
        noteRecyclerView.setHasFixedSize(true); // Выше производительность если все элементы списка одинаковые по размеру!
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), noteList, this); //19.54
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Или new LinearLayoutManager(noteRecyclerView.getContext())
        noteRecyclerView.setAdapter(recyclerViewAdapter);
    }

// ========== Имплементим метод, берём позицию и переходим по клику конкретной заметки =============
    @Override
    public void onOnItemClicked(int position) {
        MyNote myNote = noteList.get(position);
        Toast.makeText(requireContext(), myNote.getNoteName(), Toast.LENGTH_SHORT).show();
    }
// =================================================================================================
}