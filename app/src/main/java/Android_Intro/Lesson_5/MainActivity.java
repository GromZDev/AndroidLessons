package Android_Intro.Lesson_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements Serializable, DataTransfer {

    protected final static String KEY_BUTTONS = "Buttons";
    protected final static String KEY_LOGIC = "Logic";
    protected CalculatorLogic calculator;
    protected TextView display;
    protected TextView themeDisplay;
    protected String TAG = "  >>>>> [жизненный цикл активити] >>>>> ";
    protected ButtonInitiation buttonInitiation;
    protected String themeName = "Day";
    protected String themeNameD = "Dark";
    protected Button button;
    protected SaveThemeBySharedPref sharedPreference;
    protected TextView userNameGreetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        sharedPreference = new SaveThemeBySharedPref(this); // Берём экземпляр настроек
//        if (sharedPreference.loadNightModeState()) {
//            setTheme(R.style.Theme_Lesson_5); // Светлая тема
//        } else setTheme(R.style.DarkTheme_Lesson_5); // Темная тема
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeToast(TAG);

        buttonInitiation = new ButtonInitiation().invoke();
        int[] numberButtons = buttonInitiation.getNumberButtons();
        int[] actionButtons = buttonInitiation.getActionButtons();

        display = findViewById(R.id.display);
        calculator = new CalculatorLogic();

        setButtonsOnClickListener(numberButtons, actionButtons);

        button = findViewById(R.id.button_menu);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            stopMainActivity();

        });

        userNameGreetings = findViewById(R.id.greetings);

//==================================== Инфа из менюшки ========
        // bundle - чтобы MainActivity не зависала при отсутствии данных из меню и если в
        // манифесте "открыта" MainActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null){
            return;
        }

        String text = bundle.getString(userName);

        if (text == null){
            Intent intent2 = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent2);

        } else {
            text = getIntent().getExtras().getString(userName); // userName тут из интерфейса!
            userNameGreetings.append(" " + text + "!");
            String themeText = getIntent().getExtras().getString(currentTheme);
            userNameGreetings.append("\nYour Theme is " + themeText);
        }
 // ================================
    }

    public void stopMainActivity() {
        finish();
    }


    private void setButtonsOnClickListener(int[] numberButtons, int[] actionButtons) {
        View.OnClickListener buttonListener = view -> {
            calculator.onNumberPressed(view.getId());
            display.setText(calculator.getText());
        };

        for (int numberButton : numberButtons) {
            findViewById(numberButton).setOnClickListener(buttonListener);
        }

        View.OnClickListener actionListener = view -> {
            calculator.onActionPressed(view.getId());
            display.setText(calculator.getText());
        };

        for (int actionButton : actionButtons) {
            findViewById(actionButton).setOnClickListener(actionListener);
        }

        findViewById(R.id.button_clear).setOnClickListener(v -> {
            calculator.reset();
            display.setText(calculator.getText());
        });

        findViewById(R.id.button_back).setOnClickListener(v -> {
            calculator.backspace();
            display.setText(calculator.getText());
        });

        findViewById(R.id.button_exit).setOnClickListener(v -> {
             // calculator.exit();
            stopMainActivity();
        

        });

    }


    private void makeToast(String message) { // Всплывающее окно снизу
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show(); // Всплывающее окно снизу
        Log.d(TAG, message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeToast("onStart! :<<<<<");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        buttonInitiation = (ButtonInitiation) saveInstanceState.getSerializable(KEY_BUTTONS);
        calculator = (CalculatorLogic) saveInstanceState.getParcelable(KEY_LOGIC);
        makeToast("Повторный запуск!! - onRestoreInstanceState() :<<<<<");
        display.setText(calculator.getText());

        // для сохранения состояния показа темы
        if (themeDisplay != null) themeDisplay.setText(saveInstanceState.getString(themeNameD));
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeToast("onResume() :<<<<<");
    }

    @Override
    protected void onPause() {
        super.onPause();
        makeToast("onPause() :<<<<<");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putSerializable(KEY_BUTTONS, buttonInitiation);
        saveInstanceState.putParcelable(KEY_LOGIC, calculator);

        // для сохранения состояния показа темы
        if (themeDisplay != null)
            saveInstanceState.putString(themeNameD, themeDisplay.getText().toString());
        makeToast("onSaveInstanceState() :<<<<<");
    }

    @Override
    protected void onStop() {
        super.onStop();
        makeToast("onStop() ]");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        makeToast("onRestart() ]");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        makeToast("onDestroy() ]");
    }

}

