package Android_Intro.Lesson_9_Notes.MyNotes.Adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyNoteDecorator extends RecyclerView.ItemDecoration {
    // Декоратор нужен для того, чтобы переиспользовать наши карточки

    private final int topSpace;
    private final int mainStartAndEndSpace;
    private final int bottomSpace;

    public MyNoteDecorator(int space, int mainStartAndEndSpace, int bottomSpace) {
        this.topSpace = space;
        this.mainStartAndEndSpace = mainStartAndEndSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view); // получаем позицию

        if (position >= 0) {
            outRect.set(mainStartAndEndSpace, topSpace, mainStartAndEndSpace, bottomSpace);
        }
    }


}
