package Android_Intro.Lesson_9_Notes;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNote implements Parcelable {
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


    protected MyNote(Parcel in) {
        noteName = in.readString();
        noteDescription = in.readString();
        theme = in.readString();
        img = in.readInt();
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
    }
}
