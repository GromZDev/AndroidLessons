package Android_Intro.Lesson_5;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorLogic implements Parcelable {
    private double firstValue;
    private double secondValue;

    StringBuilder inputStr = new StringBuilder(); // Строка ввода

    private State status;

    private int userActionSelected;

    protected CalculatorLogic(Parcel in) {
        firstValue = in.readDouble();
        secondValue = in.readDouble();
        userActionSelected = in.readInt();
    }

    public static final Creator<CalculatorLogic> CREATOR = new Creator<CalculatorLogic>() {
        @Override
        public CalculatorLogic createFromParcel(Parcel in) {
            return new CalculatorLogic(in);
        }

        @Override
        public CalculatorLogic[] newArray(int size) {
            return new CalculatorLogic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(firstValue);
        parcel.writeDouble(secondValue);
        parcel.writeInt(userActionSelected);
    }

    private enum State {
        firstValueInput, actionSelected, secondValueInput, resultShow,
    }

    public CalculatorLogic() {
        status = State.firstValueInput;
    }

    @SuppressLint("NonConstantResourceId")
    public void onNumberPressed(int buttonId) {

        if (status == State.resultShow) {
            status = State.firstValueInput;
            inputStr.setLength(0);
        }

        if (status == State.actionSelected) {
            status = State.secondValueInput;
            inputStr.setLength(0);
        }

        if (inputStr.length() < 10) {
            switch (buttonId) {
                case R.id.button_0:
                    if (inputStr.length() != 0) {
                        inputStr.append("0");
                    }
                    break;
                case R.id.button_1:
                    inputStr.append("1");
                    break;
                case R.id.button_2:
                    inputStr.append("2");
                    break;
                case R.id.button_3:
                    inputStr.append("3");
                    break;
                case R.id.button_4:
                    inputStr.append("4");
                    break;
                case R.id.button_5:
                    inputStr.append("5");
                    break;
                case R.id.button_6:
                    inputStr.append("6");
                    break;
                case R.id.button_7:
                    inputStr.append("7");
                    break;
                case R.id.button_8:
                    inputStr.append("8");
                    break;
                case R.id.button_9:
                    inputStr.append("9");
                    break;
                case R.id.button_point:
                    inputStr.append(".");
                    break;
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onActionPressed(int actionId) {
        if (actionId == R.id.button_equals && status == State.secondValueInput && inputStr.length() > 0) {
            secondValue = Double.parseDouble(inputStr.toString());
            status = State.resultShow;
            inputStr.setLength(0);
            switch (userActionSelected) {
                case R.id.button_plus:
                    inputStr.append(firstValue + secondValue);
                    break;
                case R.id.button_minus:
                    inputStr.append(firstValue - secondValue);
                    break;
                case R.id.button_multiply:
                    inputStr.append(firstValue * secondValue);
                    break;
                case R.id.button_divide:
                    inputStr.append(firstValue / secondValue);
                    break;
                case R.id.button_square:
                    inputStr.append(firstValue * firstValue);
                    break;
            }

        } else if (actionId == R.id.button_square) {
            status = State.actionSelected;
            firstValue = Double.parseDouble(inputStr.toString());
            secondValue = firstValue;
            inputStr.setLength(0);
            inputStr.append(firstValue * firstValue);
            userActionSelected = actionId;
            status = State.resultShow;

        } else if (inputStr.length() > 0 && status == State.firstValueInput) {
            firstValue = Double.parseDouble(inputStr.toString());
            status = State.actionSelected;
            userActionSelected = actionId;
        }

    }


    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        switch (status) {
            default:
                return inputStr.toString();
            case actionSelected:
                return stringBuilder.append(firstValue).append(' ')
                        .append(getOperationChar())
                        .toString();
            case secondValueInput:
                return stringBuilder.append(firstValue).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(inputStr)
                        .toString();
            case resultShow:
                return stringBuilder.append(firstValue).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(secondValue)
                        .append(" = ")
                        .append(inputStr.toString())
                        .toString();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private char getOperationChar() {
        switch (userActionSelected) {
            case R.id.button_plus:
                return '+';
            case R.id.button_minus:
                return '-';
            case R.id.button_multiply:
                return '*';
            case R.id.button_divide:
                return '/';
            case R.id.button_square:
                return '^';
            default:
                return '@';

        }
    }

    public void reset() {
        status = State.firstValueInput;
        inputStr.setLength(0);
    }

    public void backspace() {
        if (inputStr.length() > 0) {
            inputStr.replace(inputStr.length() - 1, inputStr.length(), "");
        }
    }

    public void exit() {
        System.exit(0);
    }


}
