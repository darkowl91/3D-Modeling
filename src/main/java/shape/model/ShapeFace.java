package shape.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class ShapeFace extends Polygon {

  public static final Comparator<ShapeFace> SHAPE_FACE_COMPARATOR_Z =
      new Comparator<ShapeFace>() {
        @Override
        public int compare(ShapeFace sf1, ShapeFace sf2) {
          if (sf1.getBaryCenter().getZ() < sf2.getBaryCenter().getZ()) {
            return 1;
          } else if (sf1.getBaryCenter().getZ() == sf2.getBaryCenter().getZ()) {
            return 0;
          } else {
            return -1;
          }
        }
      };
  public static final Comparator<ShapeFace> SHAPE_FACE_COMPARATOR_X =
      new Comparator<ShapeFace>() {
        @Override
        public int compare(ShapeFace sf1, ShapeFace sf2) {
          if (sf1.getBaryCenter().getX() < sf2.getBaryCenter().getX()) {
            return 1;
          } else if (sf1.getBaryCenter().getX() == sf2.getBaryCenter().getX()) {
            return 0;
          } else {
            return -1;
          }
        }
      };
  public static final Comparator<ShapeFace> SHAPE_FACE_COMPARATOR_Y =
      new Comparator<ShapeFace>() {
        @Override
        public int compare(ShapeFace sf1, ShapeFace sf2) {
          if (sf1.getBaryCenter().getY() < sf2.getBaryCenter().getY()) {
            return 1;
          } else if (sf1.getBaryCenter().getY() == sf2.getBaryCenter().getY()) {
            return 0;
          } else {
            return -1;
          }
        }
      };
  // private static final FacePoint Z_CAMERA_POINT = new FacePoint(330, 515, 100000);
  private static final FacePoint Z_CAMERA_POINT = new FacePoint(0, 0, 100000);
  private List<FacePoint> facePoints;
  private String faceId;

  public ShapeFace(String faceId, FacePoint... facePoints) {
    this.facePoints = new ArrayList<>();
    Collections.addAll(this.facePoints, facePoints);
    this.faceId = faceId;
  }

  public ShapeFace(String faceId, List<FacePoint> facePoints) {
    this.facePoints = facePoints;
    this.faceId = faceId;
  }

  public List<FacePoint> getFacePoints() {
    return facePoints;
  }

  public void setFacePoints(List<FacePoint> facePoints) {
    this.facePoints = facePoints;
  }

  public String getFaceId() {
    return faceId;
  }

  public void setFaceId(String faceId) {
    this.faceId = faceId;
  }

  public FacePoint getFacePoint(int index) {
    return facePoints.get(index);
  }

  @Override
  public String toString() {
    StringBuilder tStr = new StringBuilder(faceId);
    tStr.append(": ");
    for (int i = 0; i < facePoints.size() - 2; i++) {
      FacePoint facePoint = facePoints.get(i);
      tStr.append(facePoint);
      tStr.append(", ");
    }
    tStr.append(facePoints.get(facePoints.size() - 1));
    return tStr.toString();
  }

  public FacePoint getBaryCenter() {
    double wX = .0;
    double wY = .0;
    double wZ = .0;
    for (FacePoint point : facePoints) {
      wX += point.getX();
      wY += point.getY();
      wZ += point.getZ();
    }
    int pointCount = facePoints.size();
    return new FacePoint(wX / pointCount, wY / pointCount, wZ / pointCount);
  }

  // sqrt( (x-x0)^2 + (y-y0)^2 + (z-z0)^2 )
  private double getCameraDistance() {
    FacePoint point = getBaryCenter();

    return Math.sqrt(
        Math.pow(point.getX() - Z_CAMERA_POINT.getX(), 2)
            + Math.pow(point.getY() - Z_CAMERA_POINT.getY(), 2)
            + Math.pow(point.getY() - Z_CAMERA_POINT.getZ(), 2));
  }

  void rebuildFace(Color borderColor, double borderWidth, Paint fillColor) {
    getPoints().removeAll(getPoints());
    setFill(fillColor);
    setSmooth(true);
    setStroke(borderColor);
    setStrokeWidth(borderWidth);
    for (FacePoint facePoint : facePoints) {
      getPoints().addAll(facePoint.get2DPointAsArray());
    }
  }
}
