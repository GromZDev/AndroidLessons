package Android_Intro.Lesson_9_Notes.MyNotes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import Android_Intro.Lesson_9_Notes.Constants;
import Android_Intro.Lesson_9_Notes.Model.MyNote;

// Класс для запросов к БД
public class NotesRepositoryImpl implements NotesRepository {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final MyNotesFireStoreCallback noteCallback;

    public NotesRepositoryImpl(MyNotesFireStoreCallback noteCallback) {
        this.noteCallback = noteCallback;
    }

    @Override
    public void requestNotes() {
        firebaseFirestore.collection(Constants.TABLE_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult() != null) {
                            List<MyNote> items = task.getResult().toObjects(MyNote.class); // Вернет нам готовый список
                            noteCallback.onSuccessNotes(items);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        noteCallback.onErrorNotes(e.getMessage());
                    }
                });
    }

    @Override
    public void onDeleteClicked(@NonNull String id) {
        firebaseFirestore.collection(Constants.TABLE_NAME)
                .document(id)
                .delete();
    }
}