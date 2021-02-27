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
    protected MyNote myNote;

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

        return viewEditNote;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTheme = view.findViewById(R.id.edit_Theme);
        editDescription = view.findViewById(R.id.edit_Description);

        if (getArguments() != null) {
            SettingsStorage ss = new SettingsStorage();
            myNote = (MyNote) getArguments().getParcelable(ss.getMyNoteDataToEdit());

            editDescription.setText(myNote.getNoteDescription());
            editTheme.setText(myNote.getTheme());

        }

        int image = myNote.getImg(); // Для передачи! Берем родную картинку и передаем ее обратно! (Менять пока не будем)

        buttonConfirm = view.findViewById(R.id.confirm_button);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDescriptionFragment(image);

            }
        });

    }

    private void sendDataToDescriptionFragment(int image) {

        MyNote myEditedNote = new MyNote(editDescription.getText().toString(),
                editDescription.getText().toString(), editTheme.getText().toString(), image);
        Fragment fragment = NoteDescriptionFragment.newInstance(myEditedNote); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }


}