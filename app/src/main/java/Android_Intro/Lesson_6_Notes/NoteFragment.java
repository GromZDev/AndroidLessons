package Android_Intro.Lesson_6_Notes;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {//TODO 1 создали класс-фрагмент, сделали лэйаут к нему

    private static List<MyNote> noteList;

    private boolean isLandscape;

    public static List<MyNote> getNoteList() {
        return noteList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Вызывается после того, когда вьюха создана. Тут получаем наш вью - fragment_note
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // TODO 6 делаем лэндскейп
        super.onActivityCreated(savedInstanceState);
        isLandscape =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }






    private void initView(View view){ //TODO 2 Метод получения данных, далее идем в разметку мэйна
        LinearLayout linearLayout = (LinearLayout) view; // Получаем лайаут
        String[] array = getResources().getStringArray(R.array.MyNotes) ; // Получаем список
        int margin = getResources().getDimensionPixelSize(R.dimen.LEFT_notes_name_list_margin); // Отступы определяем


        noteList = new ArrayList<>();
        noteList.add(new MyNote("Заметка1", "Описание1", "Тема заметки 1"));
        noteList.add(new MyNote("Заметка2", "Описание2", "Тема заметки 2"));
        noteList.add(new MyNote("Заметка3", "Описание3", "Тема заметки 3"));
        noteList.add(new MyNote("Заметка4", "Описание4", "Тема заметки 4"));
        noteList.add(new MyNote("Заметка5", "Описание5", "Тема заметки 5"));



//===============================
        for (int i = 0; i < noteList.size() ; i++) {
            String name = array[i];
            TextView tw = new TextView(linearLayout.getContext()); // ВАЖНО! Если контекст вьюхи
            // передадим в какой-то класс, живущий дольше фрагмента, то будет утечка памяти!!!
            tw.setText(name);
            tw.setTextSize(30f);
            tw.setPadding(margin, 0, margin, 0); // Программно добавляем отступы
            tw.setText(noteList.get(i).getNoteName());


   // ============================

            int index = i; //TODO 3
            tw.setOnClickListener(new View.OnClickListener() { // Вешаем на текст тач
                @Override
                public void onClick(View view) {
                    checkOrientation(index);
                }
            });
            linearLayout.addView(tw);
        }
    }

    private void checkOrientation (int index){ //TODO 6
        if (isLandscape){
            openDescription(index);
        } else {
            startDescriptionActivity(index);
        }
    }

    private void openDescription(int index){ //TODO 6 2:06
        DescriptionNote fragment = DescriptionNote.newInstance(index);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_land_note, fragment)
                .commit();
    }

    private void startDescriptionActivity(int index){ // TODO 3 и 5
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra(DescriptionNote.ARGUMENT, index);
        startActivity(intent);
    }
}