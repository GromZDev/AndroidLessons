package Android_Intro.Lesson_10_Notes.Model;

import java.io.Serializable;
import java.util.Date;

public class MyNote implements Serializable {
    private String noteName;
    private String noteDescription;
    private String theme;
    private int img;
    private Date date;
    private String id;

    public MyNote(String noteName, String theme, String noteDescription) {
        this.noteName = noteName;
        this.theme = theme;
        this.noteDescription = noteDescription;
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

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

}
