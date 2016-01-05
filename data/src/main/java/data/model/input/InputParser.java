package data.model.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser {

    private static final String LP = "LP";
    private static final String MP = "MP";
    private static final String HP = "HP";
    private static final String LK = "LK";
    private static final String MK = "MK";
    private static final String HK = "HK";
    private static final String PUNCH = "PUNCH";
    private static final String KICK = "KICK";
    private static final String ALL_PUNCHES = "ALL_PUNCHES";
    private static final String ALL_KICKS = "ALL_KICKS";
    private static final String U = "UP";
    private static final String UR = "UP_FORWARD";
    private static final String R = "FORWARD";
    private static final String DR = "DOWN_FORWARD";
    private static final String D = "DOWN";
    private static final String DL = "DOWN_BACK";
    private static final String L = "BACK";
    private static final String UL = "UP_BACK";
    private static final String QCF = "QCF";
    private static final String QCB = "QCB";
    private static final String SRK = "SRK";
    private static final String SRK_BACK = "SRK_BACK";
    private static final String HCF = "HCF";
    private static final String HCB = "HCB";
    private static final String CHARGE_BACK = "CHARGE_BACK";
    private static final String CHARGE_DOWN = "CHARGE_DOWN";
    private static final String RELEASE_FORWARD = "RELEASE_FORWARD";
    private static final String RELEASE_UP = "RELEASE_UP";
    private static final String SPD = "SPD";
    private static final String PLUS = "PLUS";
    private static final String ARROW = "ARROW";
    private static final String OR = "OR";
    private static final String NO_INPUT = "NO_INPUT";

    public List<InputElement> parseInputString(String inputString) {
        List<InputElement> inputSequence = new ArrayList<InputElement>();
        List<String> inputStringList = Arrays.asList(inputString.split("\\|"));
        for(String input : inputStringList) {
            inputSequence.add(stringToInputElement(input));
        }
        return inputSequence;
    }

    public boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        String trimmedString = s.trim();
        return trimmedString.equals("");
    }

    private InputElement stringToInputElement(String inputString) {

        if(isEmpty(inputString)) {
            throw new RuntimeException("Empty input string.");
        }

        switch (inputString) {
            case LP:
                return InputElement.LP;
            case MP:
                return InputElement.MP;
            case HP:
                return InputElement.HP;
            case LK:
                return InputElement.LK;
            case MK:
                return InputElement.MK;
            case HK:
                return InputElement.HK;
            case PUNCH:
                return InputElement.PUNCH;
            case KICK:
                return InputElement.KICK;
            case ALL_PUNCHES:
                return InputElement.ALL_PUNCHES;
            case ALL_KICKS:
                return InputElement.ALL_KICKS;
            case U:
                return InputElement.UP;
            case UR:
                return InputElement.UP_FORWARD;
            case R:
                return InputElement.FORWARD;
            case DR:
                return InputElement.DOWN_FORWARD;
            case D:
                return InputElement.DOWN;
            case DL:
                return InputElement.DOWN_BACK;
            case L:
                return InputElement.BACK;
            case UL:
                return InputElement.UP_BACK;
            case QCF:
                return InputElement.QCF;
            case QCB:
                return InputElement.QCB;
            case SRK:
                return InputElement.SRK;
            case SRK_BACK:
                return InputElement.SRK_BACK;
            case HCF:
                return InputElement.HCF;
            case HCB:
                return InputElement.HCB;
            case CHARGE_BACK:
                return InputElement.CHARGE_BACK;
            case CHARGE_DOWN:
                return InputElement.CHARGE_DOWN;
            case RELEASE_FORWARD:
                return InputElement.RELEASE_FORWARD;
            case RELEASE_UP:
                return InputElement.RELEASE_UP;
            case SPD:
                return InputElement.SPD;
            case PLUS:
                return InputElement.PLUS;
            case ARROW:
                return InputElement.ARROW;
            case OR:
                return InputElement.OR;
            case NO_INPUT:
                return InputElement.NO_INPUT;
            default:
                throw new RuntimeException("Unknown input string: '" + inputString + "'");
        }
    }
}
