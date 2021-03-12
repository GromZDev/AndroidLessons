package Android_Intro.Lesson_10_Notes.Notes_Details;

import androidx.annotation.NonNull;

import java.util.Date;

public interface NoteDetailRepository {

    void setNote(@NonNull String id,
                 @NonNull String name,
                 @NonNull String theme,
                 @NonNull String description,
                 int img,
                 Date date
    );
}
