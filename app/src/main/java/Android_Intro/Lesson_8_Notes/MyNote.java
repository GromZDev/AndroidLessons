package Android_Intro.Lesson_8_Notes;

public class MyNote {
    private final String noteName;
    private final String noteDescription;
    private final String theme;
    private final int img;

    public MyNote(String noteName, String noteDescription, String theme, int img) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.theme = theme;
        this.img = img;
    }



    public int getImg() {
        return img;
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
