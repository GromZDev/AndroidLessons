package Android_Intro.Lesson_9_Notes;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddNoteFragment extends Fragment {

    protected View addNoteView;
    protected EditText editNoteNameText;
    protected EditText editNoteThemeText;
    protected EditText editNoteDescText;
    protected MaterialButton buttonSaveNote;
    protected FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(); // Получаем БД

    public static Fragment newInstance(@Nullable MyNote model) {
        Fragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putParcelable(ss.getDataToAddNote(), model);
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
            final String id = UUID.randomUUID().toString(); // Генерим рандомный id
            final Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("theme", theme);
            map.put("desc", desc);

            firebaseFirestore.collection(Constants.TABLE_NAME) // Имя таблицы
                    .document(id) // Нужен для того, чтобы вытащить заметку по id из БД.
                    // док если мы делаем set. Если add то не нужен
                    .set(map) // set либо добавляет, либо обновляет!
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show();
                        }
                    });


            //  getActivity().onBackPressed();
        } else {
            Toast.makeText(requireContext(), "Please input all fields!", Toast.LENGTH_SHORT).show();
        }
    }
    //========================================================================================
}