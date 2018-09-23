package shape.model;

import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import shape.geometry.GeometryUtils;
import shape.geometry.Transforms;

public class Shape extends Group {
  private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
  private static final Color DEFAULT_FILL_COLOR = Color.TRANSPARENT;
  private static final double DEFAULT_BORDER_WIDTH = 1.0;
  private Color shapeBorderColor;
  private Color shapeFilColor;
  private double shapeBorderWidth;
  private List<ShapeFace> shapeFaces;
  private double currentAngleX;
  private double currentAngleY;
  private double currentAngleZ;
  private double currentScaleX;
  private double currentScaleY;
  private double currentScaleZ;
  private double currentTranslateX;
  private double currentTranslateY;
  private double currentTranslateZ;
  private boolean isHiddenLinesVisible;
  private boolean isLight;
  private boolean isShadow;
  private FacePoint lightPoint;
  private ShapeLight shapeLight = new ShapeLight();

  public Shape(List<ShapeFace> shapeFaces) {
    this.shapeFaces = shapeFaces;
    shapeBorderColor = DEFAULT_BORDER_COLOR;
    shapeBorderWidth = DEFAULT_BORDER_WIDTH;
    shapeFilColor = DEFAULT_FILL_COLOR;
    currentAngleX = 0;
    currentAngleY = 0;
    currentAngleZ = 0;
    currentScaleX = 1;
    currentScaleY = 1;
    currentScaleZ = 1;
    currentTranslateX = 0;
    currentTranslateY = 0;
    currentTranslateZ = 0;
    isHiddenLinesVisible = true;
    lightPoint = new FacePoint(0, 0, -300);
    rebuildShape();
  }

  public Shape(
      Color shapeBorderColor,
      Color shapeFilColor,
      double shapeBorderWidth,
      List<ShapeFace> shapeFaces) {
    this.shapeFilColor = shapeFilColor;
    this.shapeBorderColor = shapeBorderColor;
    this.shapeBorderWidth = shapeBorderWidth;
    this.shapeFaces = shapeFaces;
    currentAngleX = 0;
    currentAngleY = 0;
    currentAngleZ = 0;
    currentScaleX = 1;
    currentScaleY = 1;
    currentScaleZ = 1;
    currentTranslateX = 0;
    currentTranslateY = 0;
    currentTranslateZ = 0;
    isHiddenLinesVisible = true;
    rebuildShape();
  }

  public List<ShapeFace> getShapeFaces() {
    Collections.sort(shapeFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Z);
    return shapeFaces;
  }

  public void setShapeFaces(List<ShapeFace> shapeFaces) {
    this.shapeFaces = shapeFaces;
    rebuildShape();
  }

  public Color getShapeBorderColor() {
    return shapeBorderColor;
  }

  public void setShapeBorderColor(Color shapeBorderColor) {
    this.shapeBorderColor = shapeBorderColor;
    rebuildShape();
  }

  public Color getShapeFilColor() {
    return shapeFilColor;
  }

  public void setShapeFilColor(Color shapeFilColor) {
    this.shapeFilColor = shapeFilColor;
    rebuildShape();
  }

  public double getShapeBorderWidth() {
    return shapeBorderWidth;
  }

  public void setShapeBorderWidth(double shapeBorderWidth) {
    this.shapeBorderWidth = shapeBorderWidth;
    rebuildShape();
  }

  public void rotateRx(double angle) {
    double tAngle = angle - currentAngleX;
    for (ShapeFace face : shapeFaces) {
      Transforms.doTransformation(Transforms.getRotateRxMatrix(tAngle), face);
      Transforms.doTransformation(Transforms.getResultMatrix(), face);
    }
    rebuildShape();
    currentAngleX = angle;
  }

  public void rotateRy(double angle) {
    double tAngle = angle - currentAngleY;
    for (ShapeFace face : shapeFaces) {
      Transforms.doTransformation(Transforms.getRotateRyMatrix(tAngle), face);
      Transforms.doTransformation(Transforms.getResultMatrix(), face);
    }
    rebuildShape();
    currentAngleY = angle;
  }

  public void rotateRz(double angle) {
    double tAngle = angle - currentAngleZ;
    for (ShapeFace face : shapeFaces) {
      Transforms.doTransformation(Transforms.getRotateRzMatrix(tAngle), face);
      Transforms.doTransformation(Transforms.getResultMatrix(), face);
    }
    rebuildShape();
    currentAngleZ = angle;
  }

  public void scale(double scaleX, double scaleY, double scaleZ) {
    for (ShapeFace face : shapeFaces) {
      Transforms.doTransformation(Transforms.getScaleMatrix(scaleX, scaleY, scaleZ), face);
      Transforms.doTransformation(Transforms.getResultMatrix(), face);
    }
    currentScaleX += scaleX;
    currentScaleY += scaleY;
    currentScaleZ += scaleZ;
    rebuildShape();
  }

  public void translate(double dx, double dy, double dz) {
    for (ShapeFace face : shapeFaces) {
      Transforms.doTransformation(Transforms.getTranslateMatrix(dx, 0 - dy, dz), face);
      Transforms.doTransformation(Transforms.getResultMatrix(), face);
    }
    currentTranslateX = +dx;
    currentTranslateY = +dy;
    currentTranslateZ = +dz;
    rebuildShape();
  }

  public void addShape(Shape shape, String axisName, double indent) {
    switch (axisName) {
      case GeometryUtils.X_AXIS:
        shape.rotateRz(indent < 0 ? 90 : -90);
        shape.translate(indent, 0, 0);
        break;
      case GeometryUtils.Y_AXIS:
        shape.rotateRx(indent < 0 ? 180 : 0);
        shape.translate(0, indent, 0);
        break;
      case GeometryUtils.Z_AXIS:
        shape.rotateRx(indent < 0 ? 90 : -90);
        shape.translate(0, 0, -indent);
        break;
    }
    this.shapeFaces.addAll(shape.getShapeFaces());
  }

  public WritableImage getShapeSnapshot() {
    return snapshot(new SnapshotParameters(), null);
  }

  public void resetToCurrentState() {
    double tAngleX = currentAngleX;
    currentAngleX = 0;
    for (int i = 0; i <= tAngleX; i++) {
      rotateRx(i);
    }

    double tAngleY = currentAngleY;
    currentAngleY = 0;
    for (int i = 0; i <= tAngleY; i++) {
      rotateRy(i);
    }

    double tAngleZ = currentAngleZ;
    currentAngleZ = 0;
    for (int i = 0; i <= tAngleZ; i++) {
      rotateRz(i);
    }

    double tScaleX = currentScaleX;
    currentScaleX = 0;

    double tScaleY = currentScaleY;
    currentScaleY = 0;

    double tScaleZ = currentScaleZ;
    currentScaleZ = 0;

    scale(tScaleX, tScaleY, tScaleZ);

    double tTranslateX = currentTranslateX;
    currentTranslateX = 0;

    double tTranslateY = currentTranslateY;
    currentTranslateY = 0;

    double tTranslateZ = currentTranslateZ;
    currentTranslateZ = 0;

    translate(tTranslateX, tTranslateY, tTranslateZ);
  }

  public boolean isHiddenLinesVisible() {
    return isHiddenLinesVisible;
  }

  public void setHiddenLinesVisible(boolean hiddenLinesVisible) {
    isHiddenLinesVisible = hiddenLinesVisible;
    if (isHiddenLinesVisible && !shapeFilColor.equals(DEFAULT_FILL_COLOR)) {
      shapeFilColor = DEFAULT_FILL_COLOR;
    }
    rebuildShape();
  }

  public boolean isLight() {
    return isLight;
  }

  public void setLight(boolean light) {
    if (!isHiddenLinesVisible) {
      isLight = light;
      rebuildShape();
    }
  }

  public boolean isShadow() {
    return isShadow;
  }

  public void setShadow(boolean shadow) {
    isShadow = shadow;
  }

  public FacePoint getLightPoint() {
    return lightPoint;
  }

  public void setLightPoint(FacePoint lightPoint) {
    this.lightPoint = lightPoint;
    rebuildShape();
  }

  private void rebuildShape() {
    getChildren().removeAll(getChildren());
    Collections.sort(shapeFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Z);
    for (ShapeFace face : shapeFaces) {
      double lightIntensity = shapeLight.getLightIntensity(lightPoint, face);
      Color newShapeFilColor = shapeFilColor;
      if (isLight) {
        double Ia = 10;
        double Il = 5;
        double I = Ia * 0.7 + Il * 0.8 * lightIntensity;
        newShapeFilColor =
            Color.rgb(
                (int) (max(0.0, (shapeFilColor.getRed() * 255) * I / (Ia + Il))),
                (int) (max(0.0, (shapeFilColor.getGreen() * 255) * I / (Ia + Il))),
                (int) (max(0.0, (shapeFilColor.getBlue() * 255) * I / (Ia + Il))));
      }
      face.rebuildFace(shapeBorderColor, shapeBorderWidth, newShapeFilColor);
      getChildren().addAll(face);
    }
  }

  double max(double a, double b) {
    return a > b ? a : b;
  }
}
