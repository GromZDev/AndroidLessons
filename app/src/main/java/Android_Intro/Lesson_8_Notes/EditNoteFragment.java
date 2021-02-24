package Android_Intro.Lesson_8_Notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;


public class EditNoteFragment extends Fragment {

    protected View viewEditNote;
    protected EditText editTheme;
    protected EditText editDescription;
    protected MaterialButton buttonConfirm;


    private String themeToEdit;
    private String descriptionToEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewEditNote = inflater.inflate(R.layout.fragment_edit_note, container, false);

        receiveNoteThemeAndDescriptionToEdit(viewEditNote);


        return viewEditNote;
    }

    private void receiveNoteThemeAndDescriptionToEdit(View view) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            themeToEdit = bundle.getString(new SettingsStorage().getThemeToEdit());
            descriptionToEdit = bundle.getString(new SettingsStorage().getDescriptionToEdit());

            editTheme = view.findViewById(R.id.edit_Theme);
            editTheme.setText(themeToEdit);

            editDescription = view.findViewById(R.id.edit_Description);
            editDescription.setText(descriptionToEdit);

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}