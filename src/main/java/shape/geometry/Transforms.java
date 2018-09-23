package shape.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shape.model.FacePoint;
import shape.model.ShapeFace;

public final class Transforms {

  private static final double[][] translateMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] scaleMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rotateRxMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rotateRyMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rotateRzMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] resultMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] axonometryMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}
  };
  private static final double[][] scaleneMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] perspectiveMatrix = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rectangleMatrixXY = {
    {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rectangleMatrixYZ = {
    {0, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };
  private static final double[][] rectangleMatrixXZ = {
    {1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
  };

  private Transforms() {}

  public static double[][] getTranslateMatrix(double dx, double dy, double dz) {
    translateMatrix[3][0] = dx;
    translateMatrix[3][1] = dy;
    translateMatrix[3][2] = dz;
    return translateMatrix;
  }

  public static double[][] getScaleMatrix(double scaleX, double scaleY, double scaleZ) {
    scaleMatrix[0][0] = scaleX;
    scaleMatrix[1][1] = scaleY;
    scaleMatrix[2][2] = scaleZ;
    return scaleMatrix;
  }

  public static double[][] getRotateRxMatrix(double angle) {
    rotateRxMatrix[1][1] = Math.cos(Math.toRadians(angle));
    rotateRxMatrix[1][2] = -Math.sin(Math.toRadians(angle));
    rotateRxMatrix[2][1] = Math.sin(Math.toRadians(angle));
    rotateRxMatrix[2][2] = Math.cos(Math.toRadians(angle));
    return rotateRxMatrix;
  }

  public static double[][] getRotateRyMatrix(double angle) {
    rotateRyMatrix[0][0] = Math.cos(Math.toRadians(angle));
    rotateRyMatrix[0][2] = Math.sin(Math.toRadians(angle));
    rotateRyMatrix[2][0] = -Math.sin(Math.toRadians(angle));
    rotateRyMatrix[2][2] = Math.cos(Math.toRadians(angle));
    return rotateRyMatrix;
  }

  public static double[][] getRotateRzMatrix(double angle) {
    rotateRzMatrix[0][0] = Math.cos(Math.toRadians(angle));
    rotateRzMatrix[0][1] = -Math.sin(Math.toRadians(angle));
    rotateRzMatrix[1][0] = Math.sin(Math.toRadians(angle));
    rotateRzMatrix[1][1] = Math.cos(Math.toRadians(angle));
    return rotateRzMatrix;
  }

  public static double[][] getAxonometryMatrix(double psi, double fi) {
    axonometryMatrix[0][0] = Math.cos(Math.toRadians(psi));
    axonometryMatrix[0][1] = Math.sin(Math.toRadians(fi)) * Math.sin(Math.toRadians(psi));
    axonometryMatrix[1][1] = Math.cos(Math.toRadians(fi));
    axonometryMatrix[2][0] = Math.sin(Math.toRadians(psi));
    axonometryMatrix[2][1] = -Math.sin(Math.toRadians(fi)) * Math.cos(Math.toRadians(psi));
    return axonometryMatrix;
  }

  public static double[][] scaleneMatrix(double alfi, double l) {
    scaleneMatrix[2][0] = -l * Math.cos(Math.toRadians(alfi));
    scaleneMatrix[2][1] = -l * Math.sin(Math.toRadians(alfi));
    return scaleneMatrix;
  }

  public static double[][] getPerspectiveMatrix(double theta, double fi, double ro) {
    perspectiveMatrix[0][0] = Math.cos(Math.toRadians(theta));
    perspectiveMatrix[0][1] = -Math.cos(Math.toRadians(fi)) * Math.sin(Math.toRadians(theta));
    perspectiveMatrix[0][2] = -Math.sin(Math.toRadians(fi)) * Math.sin(Math.toRadians(theta));
    perspectiveMatrix[1][0] = Math.sin(Math.toRadians(theta));
    perspectiveMatrix[1][1] = Math.cos(Math.toRadians(fi)) * Math.cos(Math.toRadians(theta));
    perspectiveMatrix[1][2] = Math.sin(Math.toRadians(fi)) * Math.cos(Math.toRadians(theta));
    perspectiveMatrix[2][1] = Math.sin(Math.toRadians(fi));
    perspectiveMatrix[2][2] = -Math.cos(Math.toRadians(fi));
    perspectiveMatrix[3][2] = -ro;
    return perspectiveMatrix;
  }

  public static double[][] getResultMatrix() {
    return resultMatrix;
  }

  public static ShapeFace createRectangleXY(final ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(shapeFace.getFacePoints().size());
    for (FacePoint point : shapeFace.getFacePoints()) {
      double[] pointsAs3dArray = doTransformation(rectangleMatrixXY, point.get3DPointAsArray());
      points.add(new FacePoint(pointsAs3dArray));
    }
    ShapeFace face = new ShapeFace(shapeFace.getFaceId(), points);

    return doTransformationTo(resultMatrix, face);
  }

  public static ShapeFace createRectangleYZ(final ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(shapeFace.getFacePoints().size());
    for (FacePoint point : shapeFace.getFacePoints()) {
      double[] pointsAs3dArray = doTransformation(rectangleMatrixYZ, point.get3DPointAsArray());
      points.add(new FacePoint(pointsAs3dArray[2], pointsAs3dArray[1], pointsAs3dArray[0]));
    }
    ShapeFace face = new ShapeFace(shapeFace.getFaceId(), points);
    return doTransformationTo(resultMatrix, face);
  }

  public static ShapeFace createRectangleXZ(final ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(shapeFace.getFacePoints().size());
    for (FacePoint point : shapeFace.getFacePoints()) {
      double[] pointsAs3dArray = doTransformation(rectangleMatrixXZ, point.get3DPointAsArray());
      points.add(new FacePoint(pointsAs3dArray[0], pointsAs3dArray[2], pointsAs3dArray[1]));
    }
    ShapeFace face = new ShapeFace(shapeFace.getFaceId(), points);
    return doTransformationTo(resultMatrix, face);
  }

  public static void doTransformation(double[][] transformMatrix, ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(shapeFace.getFacePoints().size());
    for (FacePoint point : shapeFace.getFacePoints()) {
      points.add(new FacePoint(doTransformation(transformMatrix, point.get3DPointAsArray())));
    }
    shapeFace.getFacePoints().clear();
    shapeFace.setFacePoints(points);
  }

  public static ShapeFace doTransformationTo(
      double[][] transformMatrix, final ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(4);
    for (FacePoint point : shapeFace.getFacePoints()) {
      points.add(new FacePoint(doTransformation(transformMatrix, point.get3DPointAsArray())));
    }
    return new ShapeFace(shapeFace.getFaceId(), points);
  }

  public static ShapeFace doPerspectiveTransformationTo(
      double d, double theta, double fi, double ro, final ShapeFace shapeFace) {
    List<FacePoint> points = new ArrayList<>(4);
    for (FacePoint point : shapeFace.getFacePoints()) {
      double[] actualPoint =
          doTransformation(getPerspectiveMatrix(theta, fi, ro), point.get3DPointAsArray());
      if (actualPoint[2] == .0d) {
        actualPoint[2] = .1d;
      }
      double k = d / actualPoint[2];
      actualPoint[0] = actualPoint[0] * k;
      actualPoint[1] = actualPoint[1] * k;
      // TODO: Simple work around to avoid hidden lines visible
      //          actualPoint[2] = actualPoint[2] * k;
      points.add(new FacePoint(actualPoint));
    }
    return new ShapeFace(shapeFace.getFaceId(), points);
  }

  public static double[] doTransformation(double[][] transformMatrix, double[] facePoints) {
    double[] tFacePoints;
    if (facePoints.length <= 3) {
      int newArrayLength = facePoints.length + 1;
      tFacePoints = Arrays.copyOf(facePoints, newArrayLength);
      tFacePoints[3] = 1;
    } else {
      tFacePoints = facePoints;
    }
    return matrixMultiplication(tFacePoints, transformMatrix);
  }

  private static double[] matrixMultiplication(double[] m1, double[][] m2) {
    int rowCount = m2.length;
    int columnCount = m1.length;
    double[] result = new double[columnCount];
    for (int i = 0; i < columnCount; i++) {
      double sum = 0;
      for (int j = 0; j < rowCount; j++) {
        sum += m1[j] * m2[j][i];
      }
      result[i] = sum;
    }
    return result;
  }
}
