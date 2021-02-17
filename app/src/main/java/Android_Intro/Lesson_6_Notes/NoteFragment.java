package Android_Intro.Lesson_6_Notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteFragment extends Fragment {

    private static List<MyNote> noteList = new ArrayList<>();
    private MyNote myNote;
    private boolean isLandscape;
    private TextView tw;
    private String dateFromDescription;
    private String receivedCode;
    protected View v;

    public static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_note, container, false);

        receiveSavedDateAndCode();

        return v;
    }

    private void receiveSavedDateAndCode() { // Получаем установленную дату и код для вставки конкретной заметке
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            dateFromDescription = bundle.getString("data");
            receivedCode = bundle.getString("hash");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            myNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            myNote = new MyNote("555", "666", "777");
        }

        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        if (isLandscape) {
            openDescription(currentPosition);
        }

    }


    private void initView(View view) {
        LinearLayout linearLayout = (LinearLayout) view; // Получаем лайаут
        int margin = getResources().getDimensionPixelSize(R.dimen.LEFT_notes_name_list_margin);

        setNotesList();

        viewNoteItem(linearLayout, margin);

        addNote(linearLayout, margin);
    }

    private void viewNoteItem(LinearLayout linearLayout, int margin) {

        for (int i = 0; i < noteList.size(); i++) {
            String name = noteList.get(i).getNoteName();
            tw = new TextView(linearLayout.getContext());
            tw.setText(name);
            tw.setTextSize(24f);
            tw.setPadding(margin, 0, margin, 0);

            setSavedDate(i);

            int index = i;

            tw.setOnClickListener(view ->
                    checkOrientation(index));

            linearLayout.addView(tw);
        }
    }

    private void addNote(LinearLayout linearLayout, int margin) {

        MaterialButton button = Objects.requireNonNull(getView()).findViewById(R.id.button_add);
        button.setOnClickListener(view -> {
            getNoteList().add(new MyNote("NewNote", "New Description of note", "New Theme"));
            setNameAndSaveAndViewLastAddedNote(linearLayout, margin);
        });
    }

    private void setNameAndSaveAndViewLastAddedNote(LinearLayout linearLayout, int margin) { // Ставим новую запись
        String name = noteList.get(noteList.size() - 1).getNoteName();
        tw = new TextView(linearLayout.getContext());
        tw.setText(name);
        tw.setTextSize(24f);
        tw.setPadding(margin, 0, margin, 0);

        setSavedDate(noteList.size() - 1);

        int index = (noteList.size() - 1);
        tw.setOnClickListener(view -> checkOrientation(index));

        linearLayout.addView(tw);
    }

    private void setNotesList() { // Установка списка заметок
        noteList = new ArrayList<>();
        noteList.add(new MyNote("Заметка1", "Описание1", "Тема заметки 1"));
        noteList.add(new MyNote("Заметка2", "Описание2", "Тема заметки 2"));
        noteList.add(new MyNote("Заметка3", "Описание3", "Тема заметки 3"));
        noteList.add(new MyNote("Заметка4", "Описание4", "Тема заметки 4"));
        noteList.add(new MyNote("Заметка5", "Описание5", "Тема заметки 5"));
        noteList.add(new MyNote("Заметка6", "Описание6", "Тема заметки 6"));

    }

    private void setSavedDate(int i) { // Устанавливаем полученную дату конкретной заметке, сравнивая с её описанием ! (Yeah!!!)
        if (dateFromDescription != null && receivedCode.equals(noteList.get(i).getNoteDescription())) {
            tw.setText(noteList.get(i).getNoteName().concat("  |  ").concat(dateFromDescription));
        }
    }

    private void checkOrientation(int index) {
        if (isLandscape) {
            openDescription(index);
        } else {
            startDescriptionActivity(index);
        }
    }

    private void openDescription(int index) {
        DescriptionNote fragment = DescriptionNote.newInstance(index);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_land_note, fragment)
                .commit();
    }

    private void startDescriptionActivity(int index) {
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra(DescriptionNote.ARGUMENT, index);
        startActivity(intent);
    }

    public static List<MyNote> getNoteList() {
        return noteList;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) { // Сохранили позицию
        outState.putParcelable(CURRENT_NOTE, myNote);
        super.onSaveInstanceState(outState);
    }
}