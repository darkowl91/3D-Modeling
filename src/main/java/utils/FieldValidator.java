package utils;

import javafx.scene.control.TextField;
import sun.misc.Regexp;

public final class FieldValidator {

  public static final Regexp REG_EXP_DIGITS_NEGATIVE = new Regexp("^[-]?[\\d]");
  public static final Regexp REG_EXP_DIGITS = new Regexp("\\d+");
  public static final Regexp REG_EXP_DECIMAL_DIGITS = new Regexp("[\\d]+([.]?[\\d]+)?");
  public static final Regexp REG_EXP_DECIMAL_NEGATIVE = new Regexp("^[-]?[\\d]+([.]?[\\d]+)?");
  public static final Regexp REG_EXP_SCALE =
      new Regexp("((0.)\\d*[1-9]{1})|^([1-9]{1}\\d*([.]?[\\d]+)?)");
  public static final Regexp REG_EXP_GRAD =
      new Regexp("^([0-9]|[1-9]\\d|[12]\\d\\d|3[0-5]\\d|360)(\\.\\d+)?$");
  private static FieldValidator ourInstance;

  private FieldValidator() {}

  public static boolean validateField(TextField field, Regexp regexp) {
    return field.getText().matches(regexp.exp);
  }
}
