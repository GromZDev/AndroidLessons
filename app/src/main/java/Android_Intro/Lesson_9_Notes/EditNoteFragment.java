package Android_Intro.Lesson_9_Notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;


public class EditNoteFragment extends Fragment {

    protected View viewEditNote;
    protected EditText editTheme;
    protected EditText editDescription;
    protected MaterialButton buttonConfirm;


    private String themeToEdit;
    private String descriptionToEdit;

    public static Fragment newInstance(@NonNull MyNote model) {
        Fragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putParcelable(ss.getMyNoteDataToEdit(), model);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewEditNote = inflater.inflate(R.layout.fragment_edit_note, container, false);

     //   receiveNoteThemeAndDescriptionToEdit(viewEditNote);


        return viewEditNote;
    }

    private void receiveNoteThemeAndDescriptionToEdit(View view) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            themeToEdit = bundle.getString(new SettingsStorage().getThemeToEdit());
            descriptionToEdit = bundle.getString(new SettingsStorage().getDescriptionToEdit());

            editTheme = view.findViewById(R.id.edit_Theme);
            editTheme.setText(themeToEdit);

            editDescription = view.findViewById(R.id.edit_Description);
            editDescription.setText(descriptionToEdit);

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTheme = view.findViewById(R.id.edit_Theme);
        editDescription = view.findViewById(R.id.edit_Description);

        if (getArguments() != null) {
            SettingsStorage ss = new SettingsStorage();
            MyNote myNote = (MyNote) getArguments().getParcelable(ss.getMyNoteDataToEdit());

            editDescription.setText(myNote.getNoteDescription());

            editTheme.setText(myNote.getTheme());

        }

        buttonConfirm = view.findViewById(R.id.confirm_button);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDescriptionFragment();

            }
        });

    }

    private void sendDataToDescriptionFragment() {

        MyNote myEditedNote = new MyNote(editDescription.getText().toString(),
                editDescription.getText().toString(), editTheme.getText().toString(), R.drawable.fallout_1);
        Fragment fragment = NoteDescriptionFragment.newInstance(myEditedNote); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();



//        String editedTheme = editTheme.getText().toString();
//
//
//        SettingsStorage ss = new SettingsStorage();
//        Bundle bundle = new Bundle();
//
//        bundle.putString(ss.getEditedThemeFromEditFragment(), editedTheme);
//
//        Fragment fragment = new NoteDescriptionFragment();
////        MainActivity.getNoteDescriptionFragment().setArguments(bundle);
//        if (getFragmentManager() != null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
    }


}