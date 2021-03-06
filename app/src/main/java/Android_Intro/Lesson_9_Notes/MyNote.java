package Android_Intro.Lesson_9_Notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MyNote implements Parcelable {
    private String noteName;
    private String noteDescription;
    private String theme;
    private int img;
    private Date date;
    private String id;


    protected MyNote(Parcel in) {
        noteName = in.readString();
        noteDescription = in.readString();
        theme = in.readString();
        img = in.readInt();
        id = in.readString();
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @Override
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        @Override
        public MyNote[] newArray(int size) {
            return new MyNote[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteDescription);
        dest.writeString(theme);
        dest.writeInt(img);
        dest.writeString(id);
    }
}
