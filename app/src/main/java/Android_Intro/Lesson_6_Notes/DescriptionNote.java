package Android_Intro.Lesson_6_Notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DescriptionNote extends Fragment {

    private TextView textView;
    private TextView dateView;
    String data;
    String d;
    private int day;
    private int month;
    private int year;
    public static String code;
    public static final String ARGUMENT = "arg_index"; // ключ для передачи описания заметки

    public static DescriptionNote newInstance(int index) {
        DescriptionNote descriptionNote = new DescriptionNote(); // создаем фрагмент
        Bundle bundle = new Bundle(); // будет хранить наш индекс
        bundle.putInt(ARGUMENT, index);
        descriptionNote.setArguments(bundle); // Сохраненные данные передаем во фрагмент
        return descriptionNote; // возвращаем наш фрагмент
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_description, container, false);

        sendDateToMainFragment(view);

        return view;
    }

    private void sendDateToMainFragment(View view) {
        MaterialButton buttonSave = view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v -> saveToTransferDateAndCode());
    }

    private void saveToTransferDateAndCode() { // Сохраняем для передачи в главный фрагмент дату и код для присваивания конкретной заметке
        Bundle bundle = new Bundle();
        bundle.putString("data", dateView.getText().toString());
        bundle.putString("hash", code);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(bundle);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().replace(R.id.layout_container, fragment).commit();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { // Получаем лэйаут
        super.onViewCreated(view, savedInstanceState);

        setDateWithDatePicker(view);
    }

    private void setDateWithDatePicker(@NonNull View view) { // Ставим дату к записи
        textView = view.findViewById(R.id.note_fragment_description);
        dateView = view.findViewById(R.id.date_view);

        MaterialButton dateButton = view.findViewById(R.id.button_date);
        Calendar calendar = Calendar.getInstance();
        dateButton.setOnClickListener(v -> {
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                data = SimpleDateFormat.getDateInstance().format(calendar.getTime());
                dateView.setText(data);
            }, day, month, year);
            datePickerDialog.show();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // Вызывается после вью created
        if (savedInstanceState != null) {
            data = savedInstanceState.getString("kkk", data);
            dateView.setText(data);
        }

        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            int index = getArguments().getInt(ARGUMENT);
            d = NoteFragment.getNoteList().get(index).getNoteDescription().concat(" | ")
                    .concat(NoteFragment.getNoteList().get(index).getTheme());
            textView.setText(d);
            code = NoteFragment.getNoteList().get(index).getNoteDescription(); // передаю строку по которой буду опознавать
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dateView != null) {
            outState.putString("kkk", dateView.getText().toString());
        }
    }
}
