package Android_Intro.Lesson_6_Notes;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DescriptionActivity extends AppCompatActivity { // TODO 4 сама активити с описанием, лэйаут

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int index = intent.getIntExtra(DescriptionNote.ARGUMENT, 0);
            DescriptionNote fragment = DescriptionNote.newInstance(index);

            // TODO 5 теперь создадим фрагмент динамически (а ранее создали просто объявлением в лэйауте) 1:43
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment); // Замена
            fragmentTransaction.commit();
        }
    }
}
