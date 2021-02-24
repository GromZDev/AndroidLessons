package Android_Intro.Lesson_8_Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyNoteViewHolder> {

    protected Context mContext;
    protected List<MyNote> myNoteArrayList;
    protected static MyNoteAdapterCallback callback;

    public RecyclerViewAdapter(Context mContext, List<MyNote> myNoteArrayList, MyNoteAdapterCallback callback) {
        this.mContext = mContext;
        this.myNoteArrayList = myNoteArrayList;
        this.callback = callback;
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
        holder.imageView_Note.setImageResource(myNoteArrayList.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return myNoteArrayList.size();
    }

    public static class MyNoteViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView_Note;
        private final MaterialTextView textView_NoteName;
        private final MaterialTextView textView_NoteTheme;

        public MyNoteViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_Note = itemView.findViewById(R.id.item_note_image);
            textView_NoteName = itemView.findViewById(R.id.item_note_name);
            textView_NoteTheme = itemView.findViewById(R.id.item_note_theme);

            textView_NoteName.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    callback.onOnItemClicked(getAdapterPosition());
                }
            });
        }


    }
}
