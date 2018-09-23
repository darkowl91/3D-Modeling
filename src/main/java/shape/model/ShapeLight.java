package shape.model;

import java.util.List;

public class ShapeLight {

  public FacePoint getNormal(ShapeFace shapeFace) {
    List<FacePoint> facePoints = shapeFace.getFacePoints();

    FacePoint p1 = facePoints.get(0);
    FacePoint p2 = facePoints.get(1);
    FacePoint p3 = facePoints.get(2);

    return new FacePoint(
        p1.getY() * p2.getZ()
            + p2.getY() * p3.getZ()
            + p3.getY() * p1.getZ()
            - p2.getY() * p1.getZ()
            - p3.getY() * p2.getZ()
            - p1.getY() * p3.getZ(),
        p1.getZ() * p2.getX()
            + p2.getZ() * p3.getX()
            + p3.getZ() * p1.getX()
            - p2.getZ() * p1.getX()
            - p3.getZ() * p2.getX()
            - p1.getZ() * p3.getX(),
        p1.getX() * p2.getY()
            + p2.getX() * p3.getY()
            + p3.getX() * p1.getY()
            - p2.getX() * p1.getY()
            - p3.getX() * p2.getY()
            - p1.getX() * p3.getY());
  }

  public FacePoint getFaceCenter(ShapeFace shapeFace) {
    double x = 0;
    double y = 0;
    double z = 0;
    List<FacePoint> facePoints = shapeFace.getFacePoints();

    for (FacePoint facePoint : facePoints) {
      x += facePoint.getX();
      y += facePoint.getY();
      z += facePoint.getZ();
    }

    return new FacePoint(x / facePoints.size(), y / facePoints.size(), z / facePoints.size());
  }

  public FacePoint getLightVector(FacePoint lightPoint, ShapeFace face) {
    FacePoint center = getFaceCenter(face);
    return new FacePoint(
        lightPoint.getX() - center.getX(),
        lightPoint.getY() - center.getY(),
        lightPoint.getZ() - center.getZ());
  }

  public double getLightIntensity(FacePoint lightPoint, ShapeFace face) {
    FacePoint normal = getNormal(face);
    FacePoint lightVector = getLightVector(lightPoint, face);

    return (normal.getX() * lightVector.getX()
            + normal.getY() * lightVector.getY()
            + normal.getZ() * lightVector.getZ())
        / (getVectorLength(normal) * getVectorLength(lightVector));
  }

  private double getVectorLength(FacePoint facePoint) {
    return Math.sqrt(
        Math.pow(facePoint.getX(), 2)
            + Math.pow(facePoint.getY(), 2)
            + Math.pow(facePoint.getZ(), 2));
  }

  //    public bool IsVisible(Face curGr) {
  //        Point3d VecA = new Point3d();
  //        Point3d VecB = new Point3d();
  //        Point3d norm = new Point3d();
  //        Point3d Vis = new Point3d();
  //        double normL, visL, ResVis;
  //        bool isVis;
  //
  //        VecA.X = curGr.Points[1].X - curGr.Points[0].X;
  //        VecA.Y = curGr.Points[1].Y - curGr.Points[0].Y;
  //        VecA.Z = curGr.Points[1].Z - curGr.Points[0].Z;
  //
  //        VecB.X = curGr.Points[2].X - curGr.Points[1].X;
  //        VecB.Y = curGr.Points[2].Y - curGr.Points[1].Y;
  //        VecB.Z = curGr.Points[2].Z - curGr.Points[1].Z;
  //
  //        norm.X = VecA.Y * VecB.Z - VecA.Z * VecB.Y;
  //        norm.Y = VecA.X * VecB.Z - VecA.Z * VecB.X;
  //        norm.Z = VecA.X * VecB.Y - VecA.Y * VecB.X;
  //
  //        Vis.X = 0;
  //        Vis.Y = 0;
  //        Vis.Z = -1000;
  //
  //        normL = Math.Sqrt(Math.Pow(norm.X, 2) +
  //                Math.Pow(norm.Y, 2) +
  //                Math.Pow(norm.Z, 2));
  //
  //        visL = Math.Sqrt(Math.Pow(Vis.X, 2) +
  //                Math.Pow(Vis.Y, 2) +
  //                Math.Pow(Vis.Z, 2));
  //
  //        if (normL == 0 || visL == 0) {
  //            ResVis = -1;
  //        } else {
  //            ResVis = (norm.X * Vis.X + norm.Y * Vis.Y + norm.Z * Vis.Z) / (normL * visL);
  //        }
  //        isVis = (ResVis > 0);
  //
  //        if (isLight && !isProj) {
  //            visL = Math.Sqrt(Math.Pow(lightPoint.X, 2) +
  //                    Math.Pow(lightPoint.Y, 2) +
  //                    Math.Pow(lightPoint.Z, 2));
  //
  //            normL = normL == 0 ? 0.0001 : normL;
  //            visL = visL == 0 ? 0.0001 : visL;
  //
  //            ResVis = (norm.X * lightPoint.X + norm.Y * lightPoint.Y + norm.Z * lightPoint.Z) /
  // (normL * visL);
  //
  //            curGr.Color = Color.FromArgb((int) (curGr.Color.R * (0.5 + 0.5 * ResVis)),
  //                    (int) (curGr.Color.G * (0.5 + 0.5 * ResVis)),
  //                    (int) (curGr.Color.B * (0.5 + 0.5 * ResVis)));
  //        }
  //        return isVis;
  //    }
  // }
}
