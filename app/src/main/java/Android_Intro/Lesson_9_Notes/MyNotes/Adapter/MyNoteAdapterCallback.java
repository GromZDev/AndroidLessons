package Android_Intro.Lesson_9_Notes.MyNotes.Adapter;

public interface MyNoteAdapterCallback {

    void onOnItemClicked(int position);

    void insertNewNote(int position);

    void sortMyNotes();

    void clearAllData();
}
