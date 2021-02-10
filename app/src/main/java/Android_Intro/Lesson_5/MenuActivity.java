package Android_Intro.Lesson_5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MenuActivity extends AppCompatActivity {

    protected TextView themeDisplay;
    protected String themeName = "Day";
    protected String themeNameD = "Dark";
    protected SaveThemeBySharedPref sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreference = new SaveThemeBySharedPref(this); // Берём экземпляр настроек
        if (sharedPreference.loadNightModeState()) {
            setTheme(R.style.Theme_Lesson_5); // Светлая тема
        } else setTheme(R.style.DarkTheme_Lesson_5); // Темная тема
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
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void changeTheme() { // Смена темы по свичу!
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme_Lesson_5);
        } else setTheme(R.style.Theme_Lesson_5);

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
