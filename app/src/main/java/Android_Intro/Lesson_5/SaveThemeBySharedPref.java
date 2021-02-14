package Android_Intro.Lesson_5;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveThemeBySharedPref {
    SharedPreferences sharedPreferences;
    public SaveThemeBySharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("Name",Context.MODE_PRIVATE);
    }

    public void saveNightModeState(Boolean state) { // Сохранение выбора темы
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DarkTheme",state);
        editor.apply();
    }

    public Boolean loadNightModeState (){ // Загрузка выбранной темы
        return sharedPreferences.getBoolean("DarkTheme",false);
    }
}
