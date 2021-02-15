package Android_Intro.Lesson_6_Notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteFragment extends Fragment { //TODO 1 создали класс-фрагмент, сделали лэйаут к нему


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

    private void initView(View view){ //TODO 2 Метод получения данных, далее идем в разметку мэйна
        LinearLayout linearLayout = (LinearLayout) view; // Получаем лайаут
        String[] array = getResources().getStringArray(R.array.MyNotes) ; // Получаем список
        int margin = getResources().getDimensionPixelSize(R.dimen.LEFT_notes_name_list_margin); // Отступы определяем

        for (int i = 0; i < array.length ; i++) {
            String name = array[i];
            TextView tw = new TextView(linearLayout.getContext()); // ВАЖНО! Если контекст вьюхи
            // передадим в какой-то класс, живущий дольше фрагмента, то будет утечка памяти!!!
            tw.setText(name);
            tw.setTextSize(30f);
            tw.setPadding(margin, 0, margin, 0); // Программно добавляем отступы

            int index = i; //TODO 3
            tw.setOnClickListener(new View.OnClickListener() { // Вешаем на текст тач
                @Override
                public void onClick(View view) {
                    startDescriptionActivity(index);
                }
            });
            linearLayout.addView(tw);
        }
    }



    private void startDescriptionActivity(int index){ // TODO 3 и 5
        Intent intent = new Intent(getActivity(), DescriptionActivity.class);
        intent.putExtra(DescriptionNote.ARGUMENT, index);
        startActivity(intent);
    }
}