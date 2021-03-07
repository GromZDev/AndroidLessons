package Android_Intro.Lesson_10_Notes.MyNotes;

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
import java.util.List;

import Android_Intro.Lesson_10_Notes.Notes_Details.AboutAppFragment;
import Android_Intro.Lesson_10_Notes.Notes_Details.AddNoteFragment;
import Android_Intro.Lesson_10_Notes.Model.MyNote;
import Android_Intro.Lesson_10_Notes.MyNotes.Adapter.MyNoteAdapterCallback;
import Android_Intro.Lesson_10_Notes.MyNotes.Adapter.MyNoteDecorator;
import Android_Intro.Lesson_10_Notes.Notes_Details.EditNoteFragment;
import Android_Intro.Lesson_10_Notes.R;
import Android_Intro.Lesson_10_Notes.MyNotes.Adapter.RecyclerViewAdapter;
import Android_Intro.Lesson_10_Notes.SettingsStorage;

public class NoteScreenFragment extends Fragment implements MyNoteAdapterCallback, MyNotesFireStoreCallback {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private final List<MyNote> noteList = new ArrayList<>();
    private RecyclerView noteRecyclerView;
    private final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), noteList, this, this);
    private FloatingActionButton addNoteButton;
    private final NotesRepository repository = new NotesRepositoryImpl(this);

    public static Fragment newInstance(@NonNull MyNote model) {
        Fragment fragment = new NoteScreenFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putSerializable(ss.getDataToMain(), model);
        fragment.setArguments(bundle);
        return fragment;
    }

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
// =============================== Сетим список заметок из БД =======================
        repository.requestNotes();
// ==================================================================================
    }

    private void initNoteListByRecyclerView(@NonNull View view) {
        noteRecyclerView = view.findViewById(R.id.recyclerView_Notes);

        noteRecyclerView.addItemDecoration(new MyNoteDecorator(getResources().getDimensionPixelSize(R.dimen.layout_marginTop),
                getResources().getDimensionPixelSize(R.dimen.layout_marginStartAndEnd),
                getResources().getDimensionPixelSize(R.dimen.layout_marginBottom)));

        noteRecyclerView.setHasFixedSize(true); // Выше производительность если все элементы списка одинаковые по размеру!
        //   recyclerViewAdapter = new RecyclerViewAdapter(getContext(), noteList, this, this); //19.54
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
// =========================== Удаляем заметку из БД ==========================
        int position = recyclerViewAdapter.getContextMenuPosition();
        MyNote myNote = noteList.get(position);

        if (item.getItemId() == R.id.action_delete) {
            repository.onDeleteClicked(myNote.getId());
            noteList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);
            return true;
        }
//=============================================================================
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

        MyNote myNote = noteList.get(position);
        replaceFragment(myNote);
        //TODO
    }

    @Override
    public void insertNewNote(int position) {
    }

    @Override
    public void sortMyNotes() {
    }

    @Override
    public void clearAllData() {
        noteList.clear();
    }

    private void replaceFragment(@NonNull MyNote model) {
        Fragment fragment = EditNoteFragment.newInstance(model); // Упаковали данные заодно!!!
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

    // =============================== Сетим список заметок из БД =======================
    @Override
    public void onSuccessNotes(@NonNull List<MyNote> items) {
        noteList.clear();
        noteList.addAll(items);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorNotes(@Nullable String message) {
        showToast(message);
    }

    private void showToast(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }

    }

// ==================================================================================
}