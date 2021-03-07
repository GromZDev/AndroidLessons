package Android_Intro.Lesson_10_Notes.MyNotes.Adapter;

public interface MyNoteAdapterCallback {

    void onOnItemClicked(int position);

    void insertNewNote(int position);

    void sortMyNotes();

    void clearAllData();
}
