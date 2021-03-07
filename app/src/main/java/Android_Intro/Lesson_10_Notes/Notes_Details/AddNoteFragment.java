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
import Android_Intro.Lesson_10_Notes.R;
import Android_Intro.Lesson_10_Notes.SettingsStorage;


public class AddNoteFragment extends Fragment implements MyNoteFireStoreDetailCallback {

    protected View addNoteView;
    protected EditText editNoteNameText;
    protected EditText editNoteThemeText;
    protected EditText editNoteDescText;
    protected MaterialButton buttonSaveNote;

    private final NoteDetailRepository repository = new NoteDetailRepositoryImpl(this); // Получаем репозиторий

    public static Fragment newInstance(@Nullable MyNote model) {
        Fragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putSerializable(ss.getDataToAddNote(), model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addNoteView = inflater.inflate(R.layout.fragment_add_note, container, false);
        return addNoteView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editNoteNameText = view.findViewById(R.id.et_note_name);
        editNoteThemeText = view.findViewById(R.id.et_note_theme);
        editNoteDescText = view.findViewById(R.id.et_note_description);
        buttonSaveNote = view.findViewById(R.id.save_new_note);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editNoteNameText.getText().toString();
                final String theme = editNoteThemeText.getText().toString();
                final String desc = editNoteDescText.getText().toString();
                saveDataToDB(name, theme, desc);
            }
        });
    }

    // =========================== Добавляем запись (заметку) в таблицу =====================
    private void saveDataToDB(@Nullable String name,
                              @Nullable String theme,
                              @Nullable String desc) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(theme) && !TextUtils.isEmpty(desc)) {
            repository.setNote(UUID.randomUUID().toString(), name, theme, desc);
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
    //========================================================================================
}