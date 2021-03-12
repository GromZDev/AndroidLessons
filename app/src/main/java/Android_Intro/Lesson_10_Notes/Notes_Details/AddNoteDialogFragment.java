package Android_Intro.Lesson_10_Notes.Notes_Details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import Android_Intro.Lesson_10_Notes.MyNotes.PictureIndexConverter;
import Android_Intro.Lesson_10_Notes.R;

public class AddNoteDialogFragment extends DialogFragment implements MyNoteFireStoreDetailCallback {

    protected View addNoteView;
    protected EditText editNoteNameText;
    protected EditText editNoteThemeText;
    protected EditText editNoteDescText;
    protected MaterialButton buttonSaveNote;
    protected Context mContext; // Для передачи контекста при закрытии диалога

    private final NoteDetailRepository repository = new NoteDetailRepositoryImpl(this); // Получаем репозиторий

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addNoteView = inflater.inflate(R.layout.dialog_add_fragment, container, false);
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
        buttonSaveNote.setOnClickListener(v -> {

            // Берем рандомную картинку для сохранения в БД:
            final int img = PictureIndexConverter.randomPictureIndex();
            final String name = editNoteNameText.getText().toString();
            final String theme = editNoteThemeText.getText().toString();
            final String desc = editNoteDescText.getText().toString();

            @SuppressLint("SimpleDateFormat")
            Date date = Calendar.getInstance().getTime();
            saveDataToDB(name, theme, desc, img, date);
            Objects.requireNonNull(getDialog()).dismiss(); // Отключаем диалог
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

// При присоединении (открытии диалога) присваиваем контекст для передачи в onDetach чтобы не крашился
        mContext = context;
    }

    @Override
    public void onDetach() {
        // При присоединении (открытии диалога) присваиваем контекст фрагмента диалогу чтобы не крашился
        super.onDetach();
        try {
            Toast.makeText(mContext, "NewNote successfully Saved", Toast.LENGTH_SHORT).show();
        } catch (ClassCastException e) {
            Log.e("AddNoteDialogFragment", "onAttach: " + e.getMessage());
        }
        mContext = null;
    }

    // =========================== Добавляем запись (заметку) в таблицу =====================
    private void saveDataToDB(@NonNull String name,
                              @NonNull String theme,
                              @NonNull String desc,
                              int img,
                              Date date) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(theme) && !TextUtils.isEmpty(desc)) {
            repository.setNote(UUID.randomUUID().toString(), name, theme, desc, img, date);
        } else {
            showToast();
        }
    }

    private void showToast() {
        Toast.makeText(mContext, "Please input all fields!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(@Nullable String message) {
    }

    @Override
    public void onError(@Nullable String message) {
    }
    //========================================================================================
}
