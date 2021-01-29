package Android_Intro.Lesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    EditText editTextName = findViewById(R.id.editTextName); // findViewById - метод, позволяющий
    // найти элемент по id. R позволяет вытащить его сюда и использовать.
    TextView textViewTop = findViewById(R.id.headText);
    Button buttonConfirm = findViewById(R.id.buttonConfirm);
    TextView textViewMiddle = findViewById(R.id.textMiddle);


}