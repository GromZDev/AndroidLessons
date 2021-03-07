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

import java.util.UUID;

import Android_Intro.Lesson_10_Notes.Model.MyNote;
import Android_Intro.Lesson_10_Notes.MyNotes.PictureIndexConverter;
import Android_Intro.Lesson_10_Notes.R;
import Android_Intro.Lesson_10_Notes.SettingsStorage;


public class EditNoteFragment extends Fragment implements MyNoteFireStoreDetailCallback {

    protected View viewEditNote;
    protected EditText editName;
    protected EditText editTheme;
    protected EditText editDescription;
    protected MaterialButton buttonConfirm;
    protected MyNote myNote;
    protected int image;

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

        editName = view.findViewById(R.id.edit_Name);
        editTheme = view.findViewById(R.id.edit_Theme);
        editDescription = view.findViewById(R.id.edit_Description);

        if (getArguments() != null) {
            SettingsStorage ss = new SettingsStorage();
            myNote = (MyNote) getArguments().getSerializable(ss.getMyNoteDataToEdit());

            editName.setText(myNote.getNoteName());
            editTheme.setText(myNote.getTheme());
            editDescription.setText(myNote.getNoteDescription());

        }

     //   image = myNote.getImg(); // Для передачи! Берем родную картинку и передаем ее обратно! (Менять пока не будем)
            image = PictureIndexConverter.getIndexByPicture(myNote.getImg());

        buttonConfirm = view.findViewById(R.id.confirm_button);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteData(image);

            }
        });

    }

    private void saveNoteData(int image) {
        final String name = editName.getText().toString();
        final String theme = editTheme.getText().toString();
        final String desc = editDescription.getText().toString();
        saveDataToDB(name, theme, desc, image);

    }

    private void saveDataToDB(@Nullable String name,
                              @Nullable String theme,
                              @Nullable String desc,
                              int img) {
        if (!TextUtils.isEmpty(theme) && !TextUtils.isEmpty(desc)) {
            repository.setNote(UUID.randomUUID().toString(), name, theme, desc, img);
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