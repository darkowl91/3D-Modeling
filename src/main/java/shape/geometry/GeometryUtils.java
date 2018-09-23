package shape.geometry;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class GeometryUtils {

  public static final String X_AXIS = "X";
  public static final String Y_AXIS = "Y";
  public static final String Z_AXIS = "Z";
  private static final Color DEFAULT_AXIS_COLOR = Color.BLACK;
  private static final double DEFAULT_AXIS_WIDTH = 1.0;
  private static final Font DEFAULT_FONT = new Font("Arial", 12);
  private static final Color DEFAULT_GRID_COLOR = Color.GRAY;
  private static final double DEFAULT_GRID_WIDTH = 0.5;
  private static final double DEFAULT_INDENT = 5.0;

  private GeometryUtils() {}

  public static Group creteAxis() {
    Point2D xArrowBegin = new Point2D(0, Size.HEIGHT / 2);
    Point2D xArrowEnd = new Point2D(Size.WIDTH, Size.HEIGHT / 2);
    Point2D yArrowBegin = new Point2D(Size.WIDTH / 2, Size.HEIGHT);
    Point2D yArrowEnd = new Point2D(Size.WIDTH / 2, 0);
    return createAxis(xArrowBegin, xArrowEnd, yArrowBegin, yArrowEnd);
  }

  private static Group createAxis(
      Point2D xArrowBegin, Point2D xArrowEnd, Point2D yArrowBegin, Point2D yArrowEnd) {
    Group axisGroup = new Group();
    // OX Arrow
    Text xArrowText = new Text(xArrowEnd.getX() - 15, xArrowEnd.getY() + 20, X_AXIS);
    xArrowText.setStroke(DEFAULT_AXIS_COLOR);
    xArrowText.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    xArrowText.setFont(DEFAULT_FONT);
    Line xLine =
        new Line(xArrowBegin.getX(), xArrowBegin.getY(), xArrowEnd.getX(), xArrowEnd.getY());
    xLine.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    xLine.setFill(DEFAULT_AXIS_COLOR);
    double xArrowAngle =
        Math.atan2(xArrowBegin.getX() - xArrowEnd.getX(), xArrowBegin.getY() - xArrowEnd.getY());
    Line xWingFirst =
        new Line(
            xArrowEnd.getX(),
            xArrowEnd.getY(),
            xArrowEnd.getX() + 15 * Math.sin(0.3 + xArrowAngle),
            xArrowEnd.getY() + 15 * Math.cos(0.3 + xArrowAngle));
    xWingFirst.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    xWingFirst.setFill(DEFAULT_AXIS_COLOR);
    Line xWingSecond =
        new Line(
            xArrowEnd.getX(),
            xArrowEnd.getY(),
            xArrowEnd.getX() + 15 * Math.sin(xArrowAngle - 0.3),
            xArrowEnd.getY() + 15 * Math.cos(xArrowAngle - 0.3));
    xWingSecond.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    xWingSecond.setFill(DEFAULT_AXIS_COLOR);
    // OY Arrow
    Text yArrowText = new Text(yArrowEnd.getX() - 20, yArrowEnd.getY() + 15, Y_AXIS);
    yArrowText.setStroke(DEFAULT_AXIS_COLOR);
    yArrowText.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    yArrowText.setFont(DEFAULT_FONT);
    Line yLine =
        new Line(yArrowBegin.getX(), yArrowBegin.getY(), yArrowEnd.getX(), yArrowEnd.getY());
    yLine.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    yLine.setFill(DEFAULT_AXIS_COLOR);
    double yArrowAngle =
        Math.atan2(yArrowBegin.getX() - yArrowEnd.getX(), yArrowBegin.getY() - yArrowEnd.getY());
    Line yWingFirst =
        new Line(
            yArrowEnd.getX(),
            yArrowEnd.getY(),
            yArrowEnd.getX() + 15 * Math.sin(0.3 + yArrowAngle),
            yArrowEnd.getY() + 15 * Math.cos(0.3 + yArrowAngle));
    yWingFirst.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    yWingFirst.setFill(DEFAULT_AXIS_COLOR);
    Line yWingSecond =
        new Line(
            yArrowEnd.getX(),
            yArrowEnd.getY(),
            yArrowEnd.getX() + 15 * Math.sin(yArrowAngle - 0.3),
            yArrowEnd.getY() + 15 * Math.cos(yArrowAngle - 0.3));
    yWingSecond.setStrokeWidth(DEFAULT_AXIS_WIDTH);
    yWingSecond.setFill(DEFAULT_AXIS_COLOR);

    axisGroup
        .getChildren()
        .addAll(
            xLine, xWingFirst, xWingSecond, xArrowText, yLine, yWingFirst, yWingSecond, yArrowText);
    return axisGroup;
  }

  public static Group createGrid(double step) {
    Group gridGroup = new Group();
    double hCount = Size.WIDTH / step;
    double vCount = Size.HEIGHT / step;
    for (int i = 0; i <= hCount; i++) {
      double nextX = i * step;
      Line line = new Line(nextX, 0, nextX, Size.HEIGHT);
      line.setStroke(DEFAULT_GRID_COLOR);
      line.setStrokeWidth(DEFAULT_GRID_WIDTH);
      gridGroup.getChildren().addAll(line);
    }
    for (int i = 0; i <= vCount; i++) {
      double nextY = i * step;
      Line line = new Line(0, nextY, Size.WIDTH, nextY);
      line.setStroke(DEFAULT_GRID_COLOR);
      line.setStrokeWidth(DEFAULT_GRID_WIDTH);
      gridGroup.getChildren().addAll(line);
    }
    return gridGroup;
  }

  public static Group createAxisText(double step, int tick) {
    Group axisTextGroup = new Group();
    double hCount = Size.WIDTH / step;
    double vCount = Size.HEIGHT / step;
    double hQuoter = (hCount / 2) / tick;
    for (int i = 1; i < hQuoter; i++) {
      double nextX = i * step * tick;
      // draw text on OX from 0 to -n
      Text textNegative =
          new Text(
              Size.WIDTH / 2 - nextX,
              (Size.HEIGHT / 2) - DEFAULT_INDENT,
              String.format("%.0f", (0 - nextX)));
      // draw text on OX form 0 to n
      Text textPositive =
          new Text(
              Size.WIDTH / 2 + nextX,
              (Size.HEIGHT / 2) - DEFAULT_INDENT,
              String.format("%.0f", (nextX)));
      axisTextGroup.getChildren().addAll(textNegative, textPositive);
    }
    double vQuoter = (vCount / 2) / tick;
    for (int i = 1; i < vQuoter; i++) {
      double nextY = i * step * tick;
      // draw text on OY from 0 to n
      Text textNegative =
          new Text(
              (Size.WIDTH / 2) + DEFAULT_INDENT,
              Size.HEIGHT / 2 - nextY,
              String.format("%.0f", (nextY)));
      // draw text on OY form 0 to -n
      Text textPositive =
          new Text(
              (Size.WIDTH / 2) + DEFAULT_INDENT,
              Size.HEIGHT / 2 + nextY,
              String.format("%.0f", (0 - nextY)));
      axisTextGroup.getChildren().addAll(textNegative, textPositive);
    }
    Text textZero =
        new Text((Size.WIDTH / 2) + DEFAULT_INDENT, (Size.HEIGHT / 2) - DEFAULT_INDENT, "0");
    axisTextGroup.getChildren().add(textZero);
    return axisTextGroup;
  }

  public static class Size {
    public static final double HEIGHT = 660;
    public static final double WIDTH = 1030;
    public static final double CENTER_WIDTH = WIDTH / 2;
    public static final double CENTER_HEIGHT = HEIGHT / 2;
  }
}
