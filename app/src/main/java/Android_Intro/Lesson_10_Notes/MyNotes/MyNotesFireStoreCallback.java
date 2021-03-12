package Android_Intro.Lesson_10_Notes.MyNotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import Android_Intro.Lesson_10_Notes.Model.MyNote;

// Пробросили коллбэки в главный фрагмент для того, чтобы знать о результате: успешно или нет
// Если успешно - возвращаем список, если нет - ошибку
public interface MyNotesFireStoreCallback {

    void onSuccessNotes (@NonNull List<MyNote> items);
    void onErrorNotes (@Nullable String message);
}
