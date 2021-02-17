package Android_Intro.Lesson_6_Notes;

public class MyNote {
    private final String noteName;
    private final String noteDescription;
    private final String theme;

    public MyNote(String noteName, String noteDescription, String theme) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.theme = theme;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getTheme() {
        return theme;
    }

}
