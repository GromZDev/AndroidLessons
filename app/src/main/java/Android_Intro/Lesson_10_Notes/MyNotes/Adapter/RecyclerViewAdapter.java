package Android_Intro.Lesson_10_Notes.MyNotes.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import Android_Intro.Lesson_10_Notes.Model.MyNote;
import Android_Intro.Lesson_10_Notes.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyNoteViewHolder> {

    protected Context mContext;
    protected List<MyNote> myNoteArrayList;
    protected final MyNoteAdapterCallback callback;
    protected Fragment fragment; // Для контекстного меню + в конструктор
    private int contextMenuPosition; // Для контекстного меню + геттер

    public RecyclerViewAdapter(Context mContext, List<MyNote> myNoteArrayList, MyNoteAdapterCallback callback, Fragment fragment) {
        this.mContext = mContext;
        this.myNoteArrayList = myNoteArrayList;
        this.callback = callback;
        this.fragment = fragment;
    }

    public int getContextMenuPosition() {
        return contextMenuPosition;
    }

    @NonNull
    @Override
    public MyNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new MyNoteViewHolder(noteView, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNoteViewHolder holder, int position) {

//        holder.textView_NoteName.setText(myNoteArrayList.get(position).getNoteName());
//        holder.textView_NoteTheme.setText(myNoteArrayList.get(position).getTheme());
//        holder.imageView_Note.setImageResource(myNoteArrayList.get(position).getImg());
        // Либо делаем без метода onBind, тогда дополняем как выше
        holder.onBind(position, myNoteArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return myNoteArrayList.size();
    }

    public class MyNoteViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView_Note;
        private final MaterialTextView textView_NoteName;
        private final MaterialTextView textView_NoteTheme;
        private final MaterialTextView textView_NoteDate;
        private final MyNoteAdapterCallback callback;


        public MyNoteViewHolder(@NonNull final View itemView, MyNoteAdapterCallback callback) {
            super(itemView);

            imageView_Note = itemView.findViewById(R.id.item_note_image);
            textView_NoteName = itemView.findViewById(R.id.item_note_name);
            textView_NoteTheme = itemView.findViewById(R.id.item_note_theme);
            textView_NoteDate = itemView.findViewById(R.id.item_note_date);
            this.callback = callback;

//===================== Сетим контекстное меню ========================
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    contextMenuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }

            imageView_Note.setOnLongClickListener(v -> {
                contextMenuPosition = getLayoutPosition();
                itemView.showContextMenu(10, 10);
                return true;
            });
//===================== ======================= ========================
        }

        @SuppressLint("SimpleDateFormat")
        public void onBind(int position, MyNote model) {
         //   imageView_Note.setImageResource(model.getImg());
            textView_NoteName.setText(model.getNoteName());
            textView_NoteTheme.setText(model.getTheme());
        //    textView_NoteDate.setText(new SimpleDateFormat("dd-MM-yyyy =||= hh:mm:ss").format(model.getDate()));
            textView_NoteName.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    callback.onOnItemClicked(getAdapterPosition());
                }
            });

        }


    }
}
