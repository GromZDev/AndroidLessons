package Android_Intro.Lesson_9_Notes;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class NoteScreenFragment extends Fragment implements MyNoteAdapterCallback {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private final List<MyNote> noteList = new ArrayList<>();
    private RecyclerView noteRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton addNoteButton;

    public static Fragment newInstance(@NonNull MyNote model) {
        Fragment fragment = new NoteScreenFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putParcelable(ss.getDataToMain(), model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // Активируем верхнее меню
        setHasOptionsMenu(true); // активация меню
        super.onCreate(savedInstanceState);

        initNoteList();

    }

    private void initNoteList() {
        noteList.add(new MyNote("Note Num01", "Note Description1", "Тема заметки 1", R.drawable.fallout_1, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num02", "Note Description2", "Тема заметки 2", R.drawable.fallout_6, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num03", "Note Description3", "Тема заметки 3", R.drawable.fallout_8, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num04", "Note Description4", "Тема заметки 4", R.drawable.fallout_5, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num05", "Note Description5", "Тема заметки 5", R.drawable.fallout_3, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num06", "Note Description6", "Тема заметки 6", R.drawable.fallout_4, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num07", "Note Description7", "Тема заметки 7", R.drawable.fallout_7, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num08", "Note Description8", "Тема заметки 8", R.drawable.fallout_2, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num09", "Note Description9", "Тема заметки 9", R.drawable.fallout_8, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num10", "Note Description10", "Тема заметки 10", R.drawable.fallout_4, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num11", "Note Description11", "Тема заметки 11", R.drawable.fallout_3, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num12", "Note Description12", "Тема заметки 12", R.drawable.fallout_1, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num13", "Note Description13", "Тема заметки 13", R.drawable.fallout_9, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num14", "Note Description14", "Тема заметки 14", R.drawable.fallout_2, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num15", "Note Description15", "Тема заметки 15", R.drawable.fallout_4, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num16", "Note Description16", "Тема заметки 16", R.drawable.fallout_5, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num17", "Note Description17", "Тема заметки 17", R.drawable.fallout_8, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num18", "Note Description18", "Тема заметки 18", R.drawable.fallout_6, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num19", "Note Description19", "Тема заметки 19", R.drawable.fallout_9, Calendar.getInstance().getTime()));
        noteList.add(new MyNote("Note Num20", "Note Description20", "Тема заметки 20", R.drawable.fallout_7, Calendar.getInstance().getTime()));
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
                sortMyNotes();
                recyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.action_add_new_note:
                insertNewNote(0);
                recyclerViewAdapter.notifyItemInserted(0); // Уведомляем о добавлении для отображения
                noteRecyclerView.scrollToPosition(0); // Фокусируемся на этой позиции
                break;
            case R.id.action_clear_all:
                clearAllData();
                recyclerViewAdapter.notifyDataSetChanged();
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
                            .replace(R.id.fragment_container, new AboutAppFragment())
                            .addToBackStack(null)
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
        addNoteButton = view.findViewById(R.id.fb_notes_add);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddNoteFragment();
            }
        });
    }

    private void initNoteListByRecyclerView(@NonNull View view) {
        noteRecyclerView = view.findViewById(R.id.recyclerView_Notes);

        noteRecyclerView.addItemDecoration(new MyNoteDecorator(getResources().getDimensionPixelSize(R.dimen.layout_marginTop),
                getResources().getDimensionPixelSize(R.dimen.layout_marginStartAndEnd),
                getResources().getDimensionPixelSize(R.dimen.layout_marginBottom)));

        noteRecyclerView.setHasFixedSize(true); // Выше производительность если все элементы списка одинаковые по размеру!
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), noteList, this, this); //19.54
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Или new LinearLayoutManager(noteRecyclerView.getContext())
        noteRecyclerView.setAdapter(recyclerViewAdapter);

        animatorInitiate(); // Устанавливаем аниматор

    }

    //====================== Сетим контекстное меню ===========================
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.item_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = recyclerViewAdapter.getContextMenuPosition();
        if (item.getItemId() == R.id.action_delete) {
            noteList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    //=========================================================================
    private void animatorInitiate() { // Устанавливаем аниматор
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        SettingsStorage ss = new SettingsStorage();
        itemAnimator.setAddDuration(ss.getAnimationDuration());
        itemAnimator.setChangeDuration(ss.getAnimationDuration());
        itemAnimator.setRemoveDuration(ss.getAnimationDuration());
        noteRecyclerView.setItemAnimator(itemAnimator);
    }

    // ========== Имплементим метод, берём позицию и переходим по клику конкретной заметки =============
    @Override
    public void onOnItemClicked(int position) {
        //   MyNote myNote = noteList.get(position);
        //   Toast.makeText(requireContext(), myNote.getNoteName(), Toast.LENGTH_SHORT).show();

        MyNote myNote = noteList.get(position);
        replaceFragment(myNote);
    }

    @Override
    public void insertNewNote(int position) {
        int size = noteList.size();
        noteList.add(position, new MyNote("Note Num" + (size + 1),
                "Note Description" + (size + 1),
                "Тема заметки " + (size + 1), R.drawable.fallout_1, Calendar.getInstance().getTime()));

    }

    @Override
    public void sortMyNotes() {
        noteList.sort(Comparator.comparing(MyNote::getNoteName));
    }

    @Override
    public void clearAllData() {
        noteList.clear();
    }

    private void replaceFragment(@NonNull MyNote model) {
        Fragment fragment = NoteDescriptionFragment.newInstance(model); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToAddNoteFragment() {
        Fragment fragment = AddNoteFragment.newInstance(null); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}