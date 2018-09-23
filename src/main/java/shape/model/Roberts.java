package shape.model;

import java.util.ArrayList;
import java.util.List;

public class Roberts {

  private static final FacePoint VIEW_POINT = new FacePoint(0, 0, 1000);

  private static double findVisibilityValue(
      FacePoint v1, FacePoint v2, FacePoint v3, FacePoint barycenter) {

    // find vector coordinates placed in shape Face
    FacePoint vec1 =
        new FacePoint(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());

    FacePoint vec2 =
        new FacePoint(v3.getX() - v2.getX(), v3.getY() - v2.getY(), v3.getZ() - v2.getZ());

    // find plane coefficients
    double A = vec1.getY() * vec2.getZ() - vec2.getY() * vec1.getZ();
    double B = vec1.getZ() * vec2.getX() - vec2.getZ() * vec1.getX();
    double C = vec1.getX() * vec2.getY() - vec2.getX() * vec1.getY();
    double D = -(A * v1.getX() + B * v1.getY() + C * v1.getZ());

    // change area sign
    double m =
        -Math.signum(A * barycenter.getX() + B * barycenter.getY() + C * barycenter.getZ() + D);

    // change area direction
    A *= m;
    B *= m;
    C *= m;
    D *= m;

    return A * Roberts.VIEW_POINT.getX()
        + B * Roberts.VIEW_POINT.getY()
        + C * Roberts.VIEW_POINT.getZ()
        + D;
  }

  public static double findVisibilityValue(ShapeFace shapeFace) {
    return findVisibilityValue(
        shapeFace.getFacePoint(0),
        shapeFace.getFacePoint(1),
        shapeFace.getFacePoint(2),
        shapeFace.getBaryCenter());
  }

  public static boolean isVisible(ShapeFace shapeFace) {
    return findVisibilityValue(shapeFace) > 0;
  }

  public static List<ShapeFace> sort(List<ShapeFace> faces) {
    List<ShapeFace> facesBack = new ArrayList<>();
    List<ShapeFace> facesFront = new ArrayList<>();
    for (ShapeFace face : faces) {
      if (isVisible(face)) {
        facesFront.add(face);
      } else {
        facesBack.add(face);
      }
    }
    facesBack.addAll(facesFront);
    return facesBack;
  }
}
