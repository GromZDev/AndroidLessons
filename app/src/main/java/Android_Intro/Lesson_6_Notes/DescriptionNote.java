package Android_Intro.Lesson_6_Notes;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import Android_Intro.Lesson_6_Notes.NoteFragment;

public class DescriptionNote extends Fragment { //TODO 3 Создаем фрагмент остального описания, лэйаут

    private TextView textView;
    private TextView dateView;
    private MaterialButton dateButton;
    String data;
    String d;
    private int day;
    private int month;
    private int year;


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
        dateView = view.findViewById(R.id.date_view);

//================================== Data ==========================
        dateButton = view.findViewById(R.id.button_date);
        Calendar calendar = Calendar.getInstance();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        data = SimpleDateFormat.getDateInstance().format(calendar.getTime());
                     //   String a = textView.getText().toString().concat(data);
                     //   textView.setText(a);
                        dateView.setText(data);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });

 // =============================================================
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // Вызывается после вью created
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            data = savedInstanceState.getString("kkk", data);
            dateView.setText(data);
        }

        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            int index = getArguments().getInt(ARGUMENT);
            TypedArray array = getResources().obtainTypedArray(R.array.MyNotesDescription); // 1:32 видео
            // Теперь нужно получить id описания заметки
   //         int descId = array.getResourceId(index, 0);
   //         textView.setText(descId);

  //============================ Вывод описания
            d = NoteFragment.getNoteList().get(index).getNoteDescription().concat(" | ")
                    .concat(NoteFragment.getNoteList().get(index).getTheme());
                textView.setText(d);

         //   textView.setText(NoteFragment.getNoteList().get(index).getTheme());

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dateView != null){
            outState.putString("kkk", dateView.getText().toString());
        }

    }

}
