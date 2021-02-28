package Android_Intro.Lesson_9_Notes;

public interface MyNoteAdapterCallback {

    void onOnItemClicked(int position);

    void insertNewNote(int position);

    void sortMyNotes();
}
