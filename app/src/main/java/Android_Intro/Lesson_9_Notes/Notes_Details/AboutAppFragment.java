package Android_Intro.Lesson_9_Notes.Notes_Details;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import Android_Intro.Lesson_9_Notes.MyNotes.NoteScreenFragment;
import Android_Intro.Lesson_9_Notes.R;


public class AboutAppFragment extends Fragment {

    View viewAboutApp;

    // ================== Меню тулбар ================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); // активация меню
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_description, menu); // Инфлейтим меню
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Toast.makeText(getActivity(), "Share this Note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_send:
                Toast.makeText(getActivity(), "Send this Note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_addImage:
                Toast.makeText(getActivity(), "Add Image", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
// =========================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewAboutApp = inflater.inflate(R.layout.fragment_about_app, container, false);

        doButtonBack();

        toolbarInitiation();

        return viewAboutApp;

    }

    private void toolbarInitiation() {
        Toolbar toolbar = viewAboutApp.findViewById(R.id.toolbar_about_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    private void doButtonBack() {
        MaterialButton buttonBack = viewAboutApp.findViewById(R.id.button_back_from_aboutApp);
        Fragment fragment = new NoteScreenFragment();
        buttonBack.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }
}