package Android_Intro.Lesson_5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MenuActivity extends AppCompatActivity implements DataTransfer {

    protected TextView themeDisplay;
    protected String themeName = "Day";
    protected String themeNameD = "Dark";
    protected SaveThemeBySharedPref sharedPreference;
    protected EditText ed_inputName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreference = new SaveThemeBySharedPref(this); // Берём экземпляр настроек
        if (sharedPreference.loadNightModeState()) {
            setTheme(R.style.Theme_Lesson_5);
        } else {
            setTheme(R.style.DarkTheme_Lesson_5); // Темная тема
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        SwitchCompat mySwitch = findViewById(R.id.my_switch);
        if (sharedPreference.loadNightModeState()) {
            mySwitch.setChecked(true);
            restartApplication();
        }
        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreference.saveNightModeState(isChecked);
            restartApplication();
        });


        changeTheme(); // Логика смены темы

        ed_inputName = findViewById(R.id.inputText); // Edit Text для ввода имени

        goToMainActivity(); // Переход в калькулятор
    }


    public void restartApplication() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
        finish();
    }

    private void goToMainActivity() {
        Button backButton = findViewById(R.id.back_to_calculator);
        backButton.setOnClickListener(v -> {
            if (ed_inputName.getText().length() != 0) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra(userName, ed_inputName.getText().toString()); // Захватываем введенное имя
                intent.putExtra(currentTheme, themeDisplay.getText().toString()); // Захватываем название темы
                startActivity(intent);
                finish();
            } else if (ed_inputName.getText().length() == 0) {

                AlertDialog.Builder alert2 = new AlertDialog.Builder(MenuActivity.this);

                alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //       Toast.makeText(MenuActivity.this, "Now Enter your Name Please", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                ConstraintLayout alertLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
                alert2.setView(alertLayout);
                alert2.show();
            }

        });
    }

    private void changeTheme() { // Смена темы по свичу!
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_Lesson_5);
        } else {
            setTheme(R.style.Theme_Lesson_5);
        }

        setContentView(R.layout.activity_menu); // Активити сдесь!

        themeDisplay = findViewById(R.id.theme);
        SwitchCompat mySwitch = findViewById(R.id.my_switch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                themeDisplay.setText(themeNameD);

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                themeDisplay.setText(themeName);

            }

        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);

        // для сохранения состояния показа темы
        if (themeDisplay != null) themeDisplay.setText(saveInstanceState.getString(themeNameD));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        // для сохранения состояния показа темы
        if (themeDisplay != null)
            saveInstanceState.putString(themeNameD, themeDisplay.getText().toString());

    }

}
