package utils;

import static utils.FieldValidator.REG_EXP_DECIMAL_NEGATIVE;
import static utils.FieldValidator.REG_EXP_DIGITS;
import static utils.FieldValidator.REG_EXP_GRAD;
import static utils.FieldValidator.REG_EXP_SCALE;
import static utils.FieldValidator.validateField;

import java.util.List;

import javafx.event.Event;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public final class UiHelper {

  private UiHelper() {}

  public static double getAngelFormUi(Slider uiAngle, TextField uiCurrentAngle, Event event) {
    if (event.getTarget().toString().startsWith("TextField")) {
      if (!validateField(uiCurrentAngle, REG_EXP_GRAD)) {
        uiCurrentAngle.setText(String.format("%.0f", uiAngle.getValue()));
      }
      uiAngle.setValue(Double.valueOf(uiCurrentAngle.getText()));
    } else {
      uiCurrentAngle.setText(String.format("%.0f", uiAngle.getValue()));
    }
    return Double.valueOf(uiCurrentAngle.getText());
  }

  public static double getValueAsDouble(TextField textField, double ifNull) {
    if (validateField(textField, REG_EXP_DECIMAL_NEGATIVE)) {
      return Double.valueOf(textField.getText());
    } else {
      textField.setText(String.format("%.0f", ifNull));
      return ifNull;
    }
  }

  public static int getValueAsInt(TextField textField, int ifNull) {
    if (validateField(textField, REG_EXP_DIGITS)) {
      return Integer.valueOf(textField.getText());
    } else {
      textField.setText(String.valueOf(ifNull));
      return ifNull;
    }
  }

  public static double getValueAsScale(TextField textField) {
    if (validateField(textField, REG_EXP_SCALE)) {
      return Double.valueOf(textField.getText());
    } else {
      textField.setText(String.valueOf((double) 1));
      return (double) 1;
    }
  }

  public static void setDisable(boolean isVisible, List<Control> controls) {
    for (Control control : controls) {
      control.setDisable(isVisible);
    }
  }
}
