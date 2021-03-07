package Android_Intro.Lesson_9_Notes.MyNotes;

import androidx.annotation.NonNull;

public interface NotesRepository {

    void requestNotes();

    void onDeleteClicked(@NonNull String id);

}
