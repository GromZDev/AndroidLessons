package Android_Intro.Lesson_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements Serializable {

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);

            }
        });

    }

//    private void changeTheme() { // Смена темы по свичу!
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.DarkTheme_Lesson_4);
//        } else setTheme(R.style.Theme_Lesson_4);
//
//        setContentView(R.layout.activity_main); // Активити сдесь!
//
//        themeDisplay = findViewById(R.id.theme);
//        SwitchCompat mySwitch = findViewById(R.id.my_switch);
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            mySwitch.setChecked(true);
//        }
//        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                themeDisplay.setText(themeNameD);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                themeDisplay.setText(themeName);
//            }
//
//        });
//    }


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
            //  calculator.exit();
            //  moveTaskToBack(true);
            finish();

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
        calculator = (CalculatorLogic) saveInstanceState.getSerializable(KEY_LOGIC);
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
        saveInstanceState.putSerializable(KEY_LOGIC, calculator);

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

