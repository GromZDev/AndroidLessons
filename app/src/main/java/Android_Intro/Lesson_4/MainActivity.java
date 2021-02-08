package Android_Intro.Lesson_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements Serializable {

    protected final static String KEY_BUTTONS = "Buttons";
    protected final static String KEY_LOGIC = "Logic";
    protected final static String SWITCH_THEME = "Theme";
    protected CalculatorLogic calculator;
    protected TextView display;
    protected TextView themeDisplay;
    protected String TAG = "  >>>>> [жизненный цикл активити] >>>>> ";
    protected ButtonInitiation buttonInitiation;
    protected MainActivity mySwitch;
    protected String themeName = "Light";
    protected String themeNameD = "Dark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //================= Для смены темы ================================================
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme_Lesson_4);
        } else setTheme(R.style.Theme_Lesson_4);


        setContentView(R.layout.activity_main);


        themeDisplay = findViewById(R.id.theme);
        SwitchCompat mySwitch = findViewById(R.id.my_switch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    themeDisplay.setText(themeNameD);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                   themeDisplay.setText(themeName);
                }

                // restartApp();

            }
        });

        //============================================================
        makeToast(TAG);

        buttonInitiation = new ButtonInitiation().invoke();
        int[] numberButtons = buttonInitiation.getNumberButtons();
        int[] actionButtons = buttonInitiation.getActionButtons();

        display = findViewById(R.id.display);
        calculator = new CalculatorLogic();

        setButtonsOnClickListener(numberButtons, actionButtons);


    }
////==============     Для смены темы ======================
//    private void restartApp() {
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
//        finish();
//    }
////=======================================================

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
            calculator.exit();
        });
    }

    private void makeToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
        mySwitch = (MainActivity) saveInstanceState.getSerializable(SWITCH_THEME);
        makeToast("Повторный запуск!! - onRestoreInstanceState() :<<<<<");
        display.setText(calculator.getText());
        themeDisplay.setText(themeDisplay.getText());
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
        saveInstanceState.putSerializable(SWITCH_THEME, (Serializable) mySwitch);
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

    //=========================================================


}

