package Android_Intro.Lesson_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected final static String KEY_BUTTONS = "Buttons";
    protected final static String KEY_LOGIC = "Logic";
    protected CalculatorLogic calculator;
    protected TextView display;
    protected String TAG = "  >>>>> [жизненный цикл активити] >>>>> ";
    protected ButtonInitiation buttonInitiation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeToast(TAG);

        buttonInitiation = new ButtonInitiation().invoke();
        int[] numberButtons = buttonInitiation.getNumberButtons();
        int[] actionButtons = buttonInitiation.getActionButtons();

        display = findViewById(R.id.display);
        calculator = new CalculatorLogic();

        setButtonsOnClickListener(numberButtons, actionButtons);

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
        makeToast("Повторный запуск!! - onRestoreInstanceState() :<<<<<");
        display.setText(calculator.getText());
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