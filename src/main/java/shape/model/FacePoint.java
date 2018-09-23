package shape.model;

import java.util.Comparator;

import javafx.geometry.Point3D;

public class FacePoint extends Point3D {

  public static final Comparator<FacePoint> Z_FACE_POINT_COMPARATOR =
      new Comparator<FacePoint>() {
        @Override
        public int compare(FacePoint fp1, FacePoint fp2) {
          if (fp1.getZ() > fp2.getZ()) {
            return 1;
          } else if (fp1.getZ() == fp2.getZ()) {
            return 0;
          } else {
            return -1;
          }
        }
      };

  public FacePoint(double v, double v2, double v3) {
    super(v, v2, v3);
  }

  public FacePoint(double[] coordinatesAsArray) {
    super(coordinatesAsArray[0], coordinatesAsArray[1], coordinatesAsArray[2]);
  }

  public Double[] get2DPointAsArray() {
    return new Double[] {getX(), getY()};
  }

  public double[] get3DPointAsArray() {
    return new double[] {getX(), getY(), getZ()};
  }

  @Override
  public String toString() {
    return "[ "
        + String.format("%.3f", getX())
        + "; "
        + String.format("%.3f", getY())
        + "; "
        + String.format("%.3f", getZ())
        + "; "
        + "]";
  }
}
