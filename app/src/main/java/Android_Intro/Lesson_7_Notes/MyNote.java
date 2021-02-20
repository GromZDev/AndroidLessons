package Android_Intro.Lesson_7_Notes;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNote implements Parcelable {
    private final String noteName;
    private final String noteDescription;
    private final String theme;

    public MyNote(String noteName, String noteDescription, String theme) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.theme = theme;
    }

    protected MyNote(Parcel in) {
        noteName = in.readString();
        noteDescription = in.readString();
        theme = in.readString();
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @Override
        public Android_Intro.Lesson_7_Notes.MyNote createFromParcel(Parcel in) {
            return new Android_Intro.Lesson_7_Notes.MyNote(in);
        }

        @Override
        public Android_Intro.Lesson_7_Notes.MyNote[] newArray(int size) {
            return new Android_Intro.Lesson_7_Notes.MyNote[size];
        }
    };

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getTheme() {
        return theme;
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
    }
}