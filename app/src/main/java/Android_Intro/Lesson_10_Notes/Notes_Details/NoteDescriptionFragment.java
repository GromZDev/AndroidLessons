package Android_Intro.Lesson_10_Notes.Notes_Details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Android_Intro.Lesson_10_Notes.Model.MyNote;
import Android_Intro.Lesson_10_Notes.R;
import Android_Intro.Lesson_10_Notes.SettingsStorage;


public class NoteDescriptionFragment extends Fragment implements MyNoteFireStoreDetailCallback {

    protected View viewFragment;

    protected TextView descriptionView;
    protected ImageView imageNoteDescription;
    protected TextView themeView;
    protected TextView dateView;
    protected DatePickerDialog.OnDateSetListener dateSetListener;
    protected MyNote newData;
    private final NoteDetailRepository repository = new NoteDetailRepositoryImpl(this); // Получаем репозиторий

    public static Fragment newInstance(@NonNull MyNote model) {
        Fragment fragment = new NoteDescriptionFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putSerializable(ss.getMyNoteData(), model);
        fragment.setArguments(bundle);
        return fragment;
    }

    //==================== Создание верхнего меню =====================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { // Активируем верхнее меню
        setHasOptionsMenu(true); // активация меню
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note_description, menu); // Инфлейтим меню
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Toast.makeText(getActivity(), "Share this Note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_send:
                Toast.makeText(getActivity(), "Send this Note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_addImage:
                Toast.makeText(getActivity(), "Add Image", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_editNote:

                goToEditDataFragment();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //================================== Переходим во фрагмент редактора данных ====================
    private void goToEditDataFragment() {
        SettingsStorage ss = new SettingsStorage();
        MyNote myNote = (MyNote) getArguments().getParcelable(ss.getMyNoteData());
        Fragment fragment = EditNoteFragment.newInstance(myNote); // Упаковали данные заодно!!!
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    //==================================================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_note_description, container, false);

        toolbarInitiation();

        return viewFragment;
    }

    private void toolbarInitiation() {
        Toolbar toolbar = viewFragment.findViewById(R.id.toolbar_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        descriptionView = view.findViewById(R.id.textView);
        imageNoteDescription = view.findViewById(R.id.item_desc_image);
        themeView = view.findViewById(R.id.note_description_theme);
        dateView = view.findViewById(R.id.note_description_date);

        if (getArguments() != null) {
            SettingsStorage ss = new SettingsStorage();
            MyNote myNote = (MyNote) getArguments().getParcelable(ss.getMyNoteData());
                if (myNote !=null) {
                    repository.setNote(myNote.getId(), myNote.getNoteName(), myNote.getTheme(), myNote.getNoteDescription(), myNote.getImg(), myNote.getDate());
                }
         //   descriptionView.setText(myNote.getNoteDescription());

         //   imageNoteDescription.setImageResource(myNote.getImg());

         //   themeView.setText(myNote.getTheme());

//            @SuppressLint("SimpleDateFormat")
//            String date = new SimpleDateFormat("dd-MM-yyyy =||= hh:mm:ss").format(myNote.getDate());
//            // Берём дату, установленную при инициации заметок!!!
//            dateView.setText(date);

        }

        Button back = view.findViewById(R.id.back);


        // ========================= Устанавливаем DatePicker Dialog ========================
        setDatePickerDialog();
        //===================================================================================
    }

    private void setDatePickerDialog() {
        dateView.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    android.R.style.Theme_Material_Dialog, dateSetListener,
                    year, month, day);
            // Меняя темы получаем разный дизайн!!!
            datePickerDialog.show();
        });

        dateSetListener = (view1, year, month, dayOfMonth) -> {
            month = month + 1;
            Calendar calendar = Calendar.getInstance();

            String newDate = dayOfMonth + "-" + month + "-" + year;

            @SuppressLint("SimpleDateFormat")
            String date = new SimpleDateFormat(" =||= hh:mm:ss").format(calendar.getTime());

            dateView.setText(newDate.concat(date));
        };
    }

    @Override
    public void onSuccess(@Nullable String message) {

    }

    @Override
    public void onError(@Nullable String message) {

    }
}