package Android_Intro.Lesson_8_Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyNoteViewHolder> {

    protected Context mContext;
    protected List<MyNote> myNoteArrayList;

    public RecyclerViewAdapter(Context mContext, List<MyNote> myNoteArrayList) {
        this.mContext = mContext;
        this.myNoteArrayList = myNoteArrayList;
    }

    @NonNull
    @Override
    public MyNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false);
        return new MyNoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNoteViewHolder holder, int position) {

        holder.textView_NoteName.setText(myNoteArrayList.get(position).getNoteName());
        holder.textView_NoteTheme.setText(myNoteArrayList.get(position).getTheme());

    }

    @Override
    public int getItemCount() {
        return myNoteArrayList.size();
    }

    public static class MyNoteViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_Note;
        private TextView textView_NoteName;
        private TextView textView_NoteTheme;

        public MyNoteViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_Note = itemView.findViewById(R.id.item_note_image);
            textView_NoteName = itemView.findViewById(R.id.item_note_name);
            textView_NoteTheme = itemView.findViewById(R.id.item_note_theme);



        }
    }
}
