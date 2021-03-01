package Android_Intro.Lesson_8_Notes;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class NoteDescriptionFragment extends Fragment {

    protected View viewFragment;
    private String descriptionFromNote;
    private int imageFromNote;
    private String themeFromNote;

    protected TextView descriptionView;
    protected ImageView imageNoteDescription;
    protected TextView themeView;

    //==================== Создание верхнего меню =====================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // Активируем верхнее меню
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
            case R.id.action_editNote:

                goToEditDataFragment();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //================================== Переходим во фрагмент редактора данных ====================
    private void goToEditDataFragment() {
        SettingsStorage ss = new SettingsStorage();
        Bundle bundle = new Bundle();

        bundle.putString(ss.getThemeToEdit(), themeFromNote);
        bundle.putString(ss.getDescriptionToEdit(), descriptionFromNote);

        Fragment fragment = new EditNoteFragment();
        fragment.setArguments(bundle);
//        MainActivity.getEditNoteFragment().setArguments(bundle);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    //==================================================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_note_description, container, false);

        receiveNoteDescription();

        toolbarInitiation();

        return viewFragment;
    }

    private void toolbarInitiation() {
        Toolbar toolbar = viewFragment.findViewById(R.id.toolbar_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    private void receiveNoteDescription() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            descriptionFromNote = bundle.getString(new SettingsStorage().getDataToFragmentDescription());
            imageFromNote = bundle.getInt(new SettingsStorage().getImageToFragmentDescription());
            themeFromNote = bundle.getString(new SettingsStorage().getThemeToFragmentDescription());
          // themeFromNote = bundle.getString(new SettingsStorage().getEditedThemeFromEditFragment());  // <<<<<<<<<<<<<<<<<<<<<<<<<<<
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        descriptionView = view.findViewById(R.id.textView);
        descriptionView.setText(descriptionFromNote);

        imageNoteDescription = view.findViewById(R.id.item_desc_image);
        imageNoteDescription.setImageResource(imageFromNote);

        themeView = view.findViewById(R.id.note_description_theme);
        themeView.setText(themeFromNote);

        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Fragment fragment = new NoteScreenFragment();
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