package Android_Intro.Lesson_10_Notes.Notes_Details;

import androidx.annotation.Nullable;

public interface MyNoteFireStoreDetailCallback {

    void onSuccess(@Nullable String message);

    void onError(@Nullable String message);
}
