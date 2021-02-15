package Android_Intro.Lesson_6_Notes;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DescriptionNote extends Fragment { //TODO 3 Создаем фрагмент остального описания, лэйаут

    private TextView textView;

    public static final String ARGUMENT = "arg_index"; // ключ для передачи описания заметки

    public static DescriptionNote newInstance(int index){
        DescriptionNote descriptionNote = new DescriptionNote(); // создаем фрагмент
        Bundle bundle = new Bundle(); // будет хранить наш индекс
        bundle.putInt(ARGUMENT, index);
        descriptionNote.setArguments(bundle); // Сохраненные данные передаем во фрагмент
        return descriptionNote; // возвращаем наш фрагмент
    }

    @Nullable
    @Override
    //Вытаскиваем лэйаут
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // Получаем лэйаут
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.note_fragment_description);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // Вызывается после вью created
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            int index = getArguments().getInt(ARGUMENT);
            TypedArray array = getResources().obtainTypedArray(R.array.MyNotesDescription); // 1:32 видео
            // Теперь нужно получить id описания заметки
            int descId = array.getResourceId(index, 0);
            textView.setText(descId);
        }

    }
}
