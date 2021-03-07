package Android_Intro.Lesson_10_Notes.MyNotes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Android_Intro.Lesson_10_Notes.Constants;
import Android_Intro.Lesson_10_Notes.Model.MyNote;

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
                        List<MyNote> list = new ArrayList<>();
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot doc: task.getResult()) {
                             //   int indexPic = PictureIndexConverter.randomPictureIndex(); // Это, если все время рандомную картинку показывать
                                long indexPic = (long) doc.get("img"); // Тут берем уже сохраненную в БД картинку
                                Timestamp timeStamp = (Timestamp)doc.get("date");
                                MyNote model = new MyNote(doc.getString("name"),
                                        doc.getString("theme"),
                                        doc.getString("desc"),
                                        PictureIndexConverter.getPictureByIndex((int) indexPic),
                                        timeStamp.toDate()

                                );
                                list.add(model);

                            }
                            noteCallback.onSuccessNotes(list);
//                            List<MyNote> items = task.getResult().toObjects(MyNote.class); // Вернет нам готовый список
//                            noteCallback.onSuccessNotes(items);
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
    public void onDeleteClicked(@NonNull String name) {
        firebaseFirestore.collection(Constants.TABLE_NAME)
                .document(name)
                .delete();
    }
}
