package Android_Intro.Lesson_7_Notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NoteScreen extends Fragment {

    private static List<MyNote> myNoteArrayList = new ArrayList<>();
    private TextView tw;
    private NoteDescription fragment = new NoteDescription();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_note_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myNoteArrayList.add(new MyNote("Заметка1", "Описание1", "Тема заметки 1"));
        myNoteArrayList.add(new MyNote("Заметка2", "Описание2", "Тема заметки 2"));
        myNoteArrayList.add(new MyNote("Заметка3", "Описание3", "Тема заметки 3"));
//        myNoteArrayList.add(new MyNote("Заметка4", "Описание4", "Тема заметки 4"));
//        myNoteArrayList.add(new MyNote("Заметка5", "Описание5", "Тема заметки 5"));
//        myNoteArrayList.add(new MyNote("Заметка6", "Описание6", "Тема заметки 6"));


        LinearLayout linearLayout = (LinearLayout) view;

        for (int i = 0; i < myNoteArrayList.size(); i++) {
            String name = myNoteArrayList.get(i).getNoteName();
            tw = new TextView(linearLayout.getContext());
            tw.setText(name);
            tw.setTextSize(24f);
            tw.setPadding(20, 0, 20, 0);

            int index = i;

           tw.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   goToNoteDescriptionWithData(index);
               }
           });

            linearLayout.addView(tw);
        }
    }



    private void goToNoteDescriptionWithData(int index) {
        fragment = new NoteDescription();

        Bundle bundle = new Bundle();
        bundle.putString("data", myNoteArrayList.get(index).getNoteDescription());
        fragment.setArguments(bundle);
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
         //           .addToBackStack(null)
                    .commit();
        }
    }

    public static List<MyNote> getMyNoteArrayList() {
        return myNoteArrayList;
    }

}