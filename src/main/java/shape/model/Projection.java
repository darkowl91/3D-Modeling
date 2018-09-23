package shape.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import shape.geometry.Transforms;

public class Projection extends Group {

  public static final String PROJECTION_AXONOMETRICK = "Axonometric";
  public static final String PROJECTION_OBLIQUE = "Oblique";
  public static final String PROJECTION_PERSPECTIVE = "Perspective";
  public static final String PROJECTION_PLANE_XY = "XY";
  public static final String PROJECTION_PLANE_XZ = "XZ";
  public static final String PROJECTION_PLANE_YZ = "YZ";
  public static final String PROJECTION_RECTANGLE = "Rectangular";
  private final Shape shapeForm;
  private final List<ShapeFace> projectionFaces;
  private ShapeLight shapeLight = new ShapeLight();
  private boolean bOkToLight = false;
  private boolean isPerspective = false;

  public Projection(Shape shapeForm) {
    this.shapeForm = shapeForm;
    projectionFaces = new ArrayList<>();
  }

  public void buildRectangleProjectionXY() {
    projectionFaces.clear();
    for (ShapeFace face : shapeForm.getShapeFaces()) {
      ShapeFace shapeFace = Transforms.createRectangleXY(face);
      shapeFace.setFill(face.getFill());
      projectionFaces.add(shapeFace);
    }
    rebuildProjection();
  }

  public void buildRectangleProjectionXZ() {
    projectionFaces.clear();
    List<ShapeFace> shapeFaces = shapeForm.getShapeFaces();
    Collections.sort(shapeFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Y);
    for (ShapeFace face : shapeFaces) {
      ShapeFace shapeFace = Transforms.createRectangleXZ(face);
      shapeFace.setFill(face.getFill());
      projectionFaces.add(shapeFace);
    }
    rebuildProjection();
  }

  public void buildRectangleProjectionYZ() {
    projectionFaces.clear();
    List<ShapeFace> shapeFaces = shapeForm.getShapeFaces();
    Collections.sort(shapeFaces, ShapeFace.SHAPE_FACE_COMPARATOR_X);
    for (ShapeFace face : shapeFaces) {
      ShapeFace shapeFace = Transforms.createRectangleYZ(face);
      shapeFace.setFill(face.getFill());
      projectionFaces.add(shapeFace);
    }
    rebuildProjection();
  }

  public void buildAxonometry(double anglePsi, double angleFI) {
    projectionFaces.clear();
    List<ShapeFace> shapeFaces = shapeForm.getShapeFaces();
    for (ShapeFace face : shapeFaces) {
      ShapeFace shapeFace =
          Transforms.doTransformationTo(Transforms.getRotateRyMatrix(-anglePsi), face);
      ShapeFace face1 =
          Transforms.doTransformationTo(Transforms.getRotateRxMatrix(-angleFI), shapeFace);
      face1.setFill(face.getFill());
      projectionFaces.add(face1);
    }
    Collections.sort(projectionFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Z);
    rebuildProjection();
  }

  public void buildPerspective(double d, double theta, double fi, double ro) {
    projectionFaces.clear();
    List<ShapeFace> shapeFaces = shapeForm.getShapeFaces();
    for (ShapeFace face : shapeFaces) {
      ShapeFace shapeFace = Transforms.doPerspectiveTransformationTo(d, theta, fi, ro, face);
      shapeFace.setFill(face.getFill());
      projectionFaces.add(shapeFace);
    }
    Collections.sort(projectionFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Z);
    Collections.reverse(projectionFaces);
    rebuildProjection();
  }

  public void buildScalene(double angleAlpha, double l) {
    projectionFaces.clear();
    for (ShapeFace face : shapeForm.getShapeFaces()) {
      ShapeFace shapeFace =
          Transforms.doTransformationTo(Transforms.scaleneMatrix(-angleAlpha, l), face);
      //  ShapeFace shapeFace1 =
      // Transforms.doTransformationTo(Transforms.getRotateRyMatrix(angleAlpha), shapeFace);
      ShapeFace shapeFace2 =
          Transforms.doTransformationTo(
              Transforms.getRotateRxMatrix(-Math.toDegrees(Math.atan(l))), shapeFace);
      shapeFace2.setFill(face.getFill());
      projectionFaces.add(shapeFace2);
    }
    Collections.sort(projectionFaces, ShapeFace.SHAPE_FACE_COMPARATOR_Z);
    rebuildProjection();
  }

  private void rebuildProjection() {
    getChildren().removeAll(getChildren());
    for (ShapeFace face : projectionFaces) {
      //  Color newColor = shapeForm.getShapeFilColor();
      // if (shapeForm.isLight() && bOkToLight) {
      //                FacePoint lightPoint = shapeForm.getLightPoint();
      //                if (isPerspective) {
      //                    lightPoint = new FacePoint(-shapeForm.getLightPoint().getX(),
      //                           - shapeForm.getLightPoint().getY(),
      //                            -shapeForm.getLightPoint().getZ());
      //                }
      //                double lightIntensity = shapeLight.getLightIntensity(lightPoint, face);
      //                newColor = Color.rgb(
      //                        (int) ((shapeForm.getShapeFilColor().getRed() * 255) * (0.5 + 0.5 *
      // lightIntensity)),
      //                        (int) ((shapeForm.getShapeFilColor().getGreen() * 255) * (0.5 + 0.5
      // * lightIntensity)),
      //                        (int) ((shapeForm.getShapeFilColor().getBlue() * 255) * (0.5 + 0.5 *
      // lightIntensity)));
      //            }

      face.rebuildFace(
          shapeForm.getShapeBorderColor(), shapeForm.getShapeBorderWidth(), face.getFill());
      getChildren().addAll(face);
    }
  }
}
