package Android_Intro.Lesson_3;

import java.io.Serializable;

public class ButtonInitiation implements Serializable {

    private int[] numberButtons;
    private int[] actionButtons;

    public int[] getNumberButtons() {
        return numberButtons;
    }

    public int[] getActionButtons() {
        return actionButtons;
    }

    public ButtonInitiation invoke() {
        numberButtons = new int[]{
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9, R.id.button_point
        };

        actionButtons = new int[]{
                R.id.button_equals, R.id.button_plus, R.id.button_minus,
                R.id.button_multiply, R.id.button_divide, R.id.button_clear,
                R.id.button_square
        };
        return this;
    }


}
