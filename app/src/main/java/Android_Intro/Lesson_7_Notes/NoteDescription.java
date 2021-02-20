package Android_Intro.Lesson_7_Notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;


public class NoteDescription extends Fragment {

    protected View v;
    private String descriptionFromNote;

    private NoteScreen fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_note_description, container, false);

        receiveNoteDescription();
        return v;
    }

    private void receiveNoteDescription() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            descriptionFromNote = bundle.getString("data");

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tw = view.findViewById(R.id.textView);
        tw.setText(descriptionFromNote);


        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             //   NoteScreen fragment = new NoteScreen();
             //   NoteScreen fragment = new NoteScreen();
                if (getFragmentManager() != null) {
                   getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, MainActivity.getNoteScreen())
                     //       .addToBackStack(null)
                            .commit();
                }
            }
        });

    }

}