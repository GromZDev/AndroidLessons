package Android_Intro.Lesson_10_Notes.Notes_Details;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Android_Intro.Lesson_10_Notes.Constants;

public class NoteDetailRepositoryImpl implements NoteDetailRepository {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance(); // Получаем БД
    private final MyNoteFireStoreDetailCallback callback;

    public NoteDetailRepositoryImpl(MyNoteFireStoreDetailCallback callback) {
        this.callback = callback;
    }

    @Override
    public void setNote(
            @NonNull String id,
            @NonNull String name,
            @NonNull String theme,
            @NonNull String description,
            int img,
            Date date) {

        //   final String id = UUID.randomUUID().toString(); // Генерим рандомный id
        final Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("theme", theme);
        map.put("desc", description);
        map.put("img", img);
        map.put("date", date);
        firebaseFirestore.collection(Constants.TABLE_NAME) // Имя таблицы
                .document(name) // Нужен для того, чтобы вытащить заметку по Имени из БД.
                // док если мы делаем set. Если add то не нужен
                .set(map) // set либо добавляет, либо обновляет!
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess("Update Success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
                });

    }
}
