package Android_Intro.Lesson_10_Notes.Notes_Details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Date;
import java.util.UUID;

import Android_Intro.Lesson_10_Notes.Model.MyNote;
import Android_Intro.Lesson_10_Notes.MyNotes.PictureIndexConverter;
import Android_Intro.Lesson_10_Notes.R;
import Android_Intro.Lesson_10_Notes.SettingsStorage;


public class EditNoteFragment extends Fragment implements MyNoteFireStoreDetailCallback {

    protected View viewEditNote;
    protected EditText editTheme;
    protected EditText editDescription;
    protected MaterialButton buttonConfirm;
    protected MyNote myNote;
    protected int image;
    protected Date date;
    protected MyNote newNote;

    private final NoteDetailRepository repository = new NoteDetailRepositoryImpl(this);

    public static Fragment newInstance(@NonNull MyNote model) {
        Fragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putSerializable(ss.getMyNoteDataToEdit(), model);
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
            myNote = (MyNote) getArguments().getSerializable(ss.getMyNoteDataToEdit());

            editTheme.setText(myNote.getTheme());
            editDescription.setText(myNote.getNoteDescription());

        }

        image = PictureIndexConverter.getIndexByPicture(myNote.getImg());

        date = myNote.getDate();

        buttonConfirm = view.findViewById(R.id.confirm_button);
        buttonConfirm.setOnClickListener(v -> {
            saveNoteData(image, date);

            newNote = new MyNote(myNote.getNoteName(), editTheme.getText().toString(), editDescription.getText().toString(),
                    image, date);
            goBackToNoteDescriptionFragment();
        });

    }

    private void goBackToNoteDescriptionFragment() {
        SettingsStorage ss = new SettingsStorage();
        MyNote thisNote = (MyNote) getArguments().getSerializable(ss.getMyNoteDataToEdit());
        MyNote noteToDescription = new MyNote(thisNote.getNoteName(), editTheme.getText().toString(), editDescription.getText().toString(),
                image, thisNote.getDate());
        Fragment fragment = NoteDescriptionFragment.newInstance(noteToDescription, PictureIndexConverter.getIndexByPicture(myNote.getImg())); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void saveNoteData(int image, Date date) {
        final String name = myNote.getNoteName();
        final String theme = editTheme.getText().toString();
        final String desc = editDescription.getText().toString();
        saveDataToDB(name, theme, desc, image, date);
    }

    private void saveDataToDB(@Nullable String name,
                              @Nullable String theme,
                              @Nullable String desc,
                              int img,
                              Date date) {
        if (!TextUtils.isEmpty(theme) && !TextUtils.isEmpty(desc)) {
            repository.setNote(UUID.randomUUID().toString(), name, theme, desc, img, date);
            //  getActivity().onBackPressed();
        } else {
            showToast("Please input all fields!");
        }
    }

    private void showToast(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(@Nullable String message) {
        showToast(message);
    }

    @Override
    public void onError(@Nullable String message) {
        showToast(message);
    }
}