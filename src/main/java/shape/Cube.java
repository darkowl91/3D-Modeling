package shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shape.model.FacePoint;
import shape.model.ShapeFace;

public class Cube {
  public static double size = 150;

  public static List<ShapeFace> getFaceList() {
    List<ShapeFace> faceList = new ArrayList<>(6);

    ShapeFace face1 =
        new ShapeFace(
            "0",
            new FacePoint(-size, size, -size),
            new FacePoint(-size, -size, -size),
            new FacePoint(size, -size, -size),
            new FacePoint(size, size, -size));

    ShapeFace face2 =
        new ShapeFace(
            "1",
            new FacePoint(-size, size, size),
            new FacePoint(-size, -size, size),
            new FacePoint(-size, -size, -size),
            new FacePoint(-size, size, -size));

    ShapeFace face3 =
        new ShapeFace(
            "2",
            new FacePoint(size, size, size),
            new FacePoint(size, -size, size),
            new FacePoint(-size, -size, size),
            new FacePoint(-size, size, size));

    ShapeFace face4 =
        new ShapeFace(
            "3",
            new FacePoint(size, size, -size),
            new FacePoint(size, -size, -size),
            new FacePoint(size, -size, size),
            new FacePoint(size, size, size));

    ShapeFace face5 =
        new ShapeFace(
            "4",
            new FacePoint(size, -size, size),
            new FacePoint(size, -size, -size),
            new FacePoint(-size, -size, -size),
            new FacePoint(-size, -size, size));

    ShapeFace face6 =
        new ShapeFace(
            "5",
            new FacePoint(-size, size, size),
            new FacePoint(-size, size, -size),
            new FacePoint(size, size, -size),
            new FacePoint(size, size, size));

    Collections.addAll(faceList, face1, face2, face3, face4, face5, face6);
    return faceList;
  }
}
