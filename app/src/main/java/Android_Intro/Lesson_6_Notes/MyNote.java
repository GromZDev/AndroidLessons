package Android_Intro.Lesson_6_Notes;

public class MyNote {
    private String noteName;
    private String noteDescription;
    private String theme;

    public MyNote(String noteName, String noteDescription, String theme) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.theme = theme;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
