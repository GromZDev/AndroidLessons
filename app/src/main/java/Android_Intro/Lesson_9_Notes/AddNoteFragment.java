package Android_Intro.Lesson_9_Notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AddNoteFragment extends Fragment {

    protected View addNoteView;

    public static Fragment newInstance(MyNote model) {
        Fragment fragment = new AddNoteFragment();
        Bundle bundle = new Bundle();
        SettingsStorage ss = new SettingsStorage();
        bundle.putParcelable(ss.getDataToAddNote(), model);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addNoteView = inflater.inflate(R.layout.fragment_add_note, container, false);
        return addNoteView;
    }
}