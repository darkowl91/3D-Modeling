import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import shape.Cube;
import shape.geometry.GeometryUtils;
import shape.model.FacePoint;
import shape.model.Projection;
import shape.model.Shape;
import utils.UiHelper;

public class MainController implements Initializable {

  private Shape shapeModel;
  private Group axisGroup;
  private Group gridGroup;
  private Group axisTextGroup;
  private Projection projection;
  private Group axisGroupProjection;
  private Group gridGroupProjection;
  private Group axisTextGroupProjection;

  private List<Control> projectionRectangleList;
  private List<Control> projectionAxonometricList;
  private List<Control> projectionObliqueList;
  private List<Control> projectionPerspectiveList;

  private Image imageLightOn;
  private Image imageLightOff;

  @FXML private AnchorPane pane;
  @FXML private ToggleButton uiFullScreen;
  @FXML private ToggleButton uiGridVisible;
  @FXML private ToggleButton uiAxisVisible;
  @FXML private ToggleButton uiAxisTextVisible;
  @FXML private ColorPicker uiShapeColor;
  @FXML private Slider uiShapeBorderWidth;
  @FXML private ComboBox<Integer> uiGridStep;
  @FXML private ComboBox<Integer> uiAxisTextTick;
  @FXML private Slider uiRotateX;
  @FXML private Slider uiRotateY;
  @FXML private Slider uiRotateZ;
  @FXML private TextField uiCurrentAngelX;
  @FXML private TextField uiCurrentAngelY;
  @FXML private TextField uiCurrentAngelZ;
  @FXML private TextField uiTranslateX;
  @FXML private TextField uiTranslateY;
  @FXML private TextField uiTranslateZ;
  @FXML private TextField uiScaleX;
  @FXML private TextField uiScaleY;
  @FXML private TextField uiScaleZ;
  @FXML private ComboBox<String> uiProjectionType;
  @FXML private ToggleGroup uiPlaneToggleGroup;
  @FXML private RadioButton uiPlaneXY;
  @FXML private RadioButton uiPlaneYZ;
  @FXML private RadioButton uiPlaneXZ;
  @FXML private Slider uiAnglePsi;
  @FXML private TextField uiCurrentAnglePsi;
  @FXML private Slider uiAngleFi;
  @FXML private TextField uiCurrentAngleFi;
  @FXML private TextField uiDistanceD;
  @FXML private TextField uiDistanceRo;
  @FXML private Slider uiAnglePerspectiveTheta;
  @FXML private TextField uiCurrentAnglePerspectiveTheta;
  @FXML private Slider uiAnglePerspectiveFi;
  @FXML private TextField uiCurrentAnglePerspectiveFi;
  @FXML private Slider uiAngleAlpha;
  @FXML private TextField uiCurrentAngleAlpha;
  @FXML private TextField uiDistanceToPlane;
  @FXML private AnchorPane uiProjectionPane;
  @FXML private ImageView uiModelSnapshot;
  @FXML private Tab uiTabProjection;
  @FXML private Tab uiTabView;
  @FXML private Tab uiTabProjectionOption;
  @FXML private ColorPicker uiFillColor;
  @FXML private CheckBox uiIsTransparent;
  @FXML private ImageView uiLight;
  @FXML private TextField uiLightX;
  @FXML private TextField uiLightY;
  @FXML private TextField uiLightZ;
  @FXML private ToggleButton uiLightOnOff;
  @FXML private CheckBox uiEnableShadow;

  // Menu
  @FXML
  public void appExit() {
    System.exit(0);
  }

  // Top toolBar
  @FXML
  public void setFullScreen() {
    Main.primaryStage.setFullScreen(uiFullScreen.isSelected());
    Main.primaryStage
        .fullScreenProperty()
        .addListener(
            new ChangeListener<Boolean>() {
              @Override
              public void changed(
                  ObservableValue<? extends Boolean> observableValue,
                  Boolean newValue,
                  Boolean oldValue) {
                uiFullScreen.setSelected(oldValue);
              }
            });
  }

  @FXML
  public void setGridVisible() {
    gridGroup.setVisible(uiGridVisible.isSelected());
    gridGroupProjection.setVisible(uiGridVisible.isSelected());
  }

  @FXML
  public void setAxisVisible() {
    boolean uiAxisVisibleSelected = uiAxisVisible.isSelected();
    axisGroup.setVisible(uiAxisVisibleSelected);
    axisGroupProjection.setVisible(uiAxisVisibleSelected);
    uiAxisTextVisible.setVisible(uiAxisVisibleSelected);
    if (!axisGroup.isVisible()) {
      axisTextGroup.setVisible(false);
      axisGroupProjection.setVisible(false);
      uiAxisTextVisible.setSelected(false);
    }
  }

  @FXML
  public void setAxisTextVisible() {
    axisTextGroup.setVisible(uiAxisTextVisible.isSelected());
    axisTextGroupProjection.setVisible(uiAxisTextVisible.isSelected());
  }

  // View options
  @FXML
  public void changeShapeColor() {
    shapeModel.setShapeBorderColor(uiShapeColor.getValue());
  }

  @FXML
  public void changeShapeBorderWidth() {
    shapeModel.setShapeBorderWidth(uiShapeBorderWidth.getValue());
  }

  @FXML
  public void changeGridStep() {
    gridGroup.getChildren().removeAll(gridGroup.getChildren());
    axisTextGroup.getChildren().removeAll(axisTextGroup.getChildren());
    gridGroup.getChildren().addAll(GeometryUtils.createGrid(uiGridStep.getValue()));
    axisTextGroup
        .getChildren()
        .addAll(GeometryUtils.createAxisText(uiGridStep.getValue(), uiAxisTextTick.getValue()));
  }

  @FXML
  public void changeAxisTextTick() {
    axisTextGroup.getChildren().removeAll(axisTextGroup.getChildren());
    axisTextGroup
        .getChildren()
        .addAll(GeometryUtils.createAxisText(uiGridStep.getValue(), uiAxisTextTick.getValue()));
  }

  @FXML
  public void changeViewProjectionTab() {
    if (uiTabProjectionOption.isSelected()) {
      uiTabProjection.getTabPane().getSelectionModel().select(uiTabProjection);
      if (uiModelSnapshot != null) {
        uiModelSnapshot.setImage(shapeModel.getShapeSnapshot());
      }
    } else {
      uiTabView.getTabPane().getSelectionModel().select(uiTabView);
    }
  }

  @FXML
  public void changeTransparent() {
    boolean uiIsTransparentSelected = uiIsTransparent.isSelected();
    shapeModel.setHiddenLinesVisible(uiIsTransparentSelected);
    uiFillColor.setDisable(uiIsTransparentSelected);
    if (!uiIsTransparentSelected) {
      shapeModel.setShapeFilColor(uiFillColor.getValue());
    }
  }

  @FXML
  public void changeFillColor() {
    shapeModel.setShapeFilColor(uiFillColor.getValue());
  }

  // Model operations
  @FXML
  public void changeShapeRotate(Event event) {
    shapeModel.rotateRx(UiHelper.getAngelFormUi(uiRotateX, uiCurrentAngelX, event));
    shapeModel.rotateRy(UiHelper.getAngelFormUi(uiRotateY, uiCurrentAngelY, event));
    shapeModel.rotateRz(UiHelper.getAngelFormUi(uiRotateZ, uiCurrentAngelZ, event));
  }

  @FXML
  public void changeShapeTranslate() {
    shapeModel.translate(
        UiHelper.getValueAsDouble(uiTranslateX, 0),
        UiHelper.getValueAsDouble(uiTranslateY, 0),
        UiHelper.getValueAsDouble(uiTranslateZ, 0));
  }

  @FXML
  public void changeShapeScale() {
    shapeModel.scale(
        UiHelper.getValueAsScale(uiScaleX),
        UiHelper.getValueAsScale(uiScaleY),
        UiHelper.getValueAsScale(uiScaleZ));
  }

  // Model projections
  public void changeProjectionType() {
    switch (uiProjectionType.getValue()) {
      case Projection.PROJECTION_RECTANGLE:
        UiHelper.setDisable(false, projectionRectangleList);
        UiHelper.setDisable(true, projectionAxonometricList);
        UiHelper.setDisable(true, projectionObliqueList);
        UiHelper.setDisable(true, projectionPerspectiveList);
        break;
      case Projection.PROJECTION_AXONOMETRICK:
        UiHelper.setDisable(true, projectionRectangleList);
        UiHelper.setDisable(false, projectionAxonometricList);
        UiHelper.setDisable(true, projectionObliqueList);
        UiHelper.setDisable(true, projectionPerspectiveList);
        break;
      case Projection.PROJECTION_OBLIQUE:
        UiHelper.setDisable(true, projectionRectangleList);
        UiHelper.setDisable(true, projectionAxonometricList);
        UiHelper.setDisable(false, projectionObliqueList);
        UiHelper.setDisable(true, projectionPerspectiveList);
        break;
      case Projection.PROJECTION_PERSPECTIVE:
        UiHelper.setDisable(true, projectionRectangleList);
        UiHelper.setDisable(true, projectionAxonometricList);
        UiHelper.setDisable(true, projectionObliqueList);
        UiHelper.setDisable(false, projectionPerspectiveList);
        break;
    }
  }

  @FXML
  public void setRectangleProjection() {
    switch (uiPlaneToggleGroup.getSelectedToggle().getUserData().toString()) {
      case Projection.PROJECTION_PLANE_XY:
        projection.buildRectangleProjectionXY();
        break;
      case Projection.PROJECTION_PLANE_XZ:
        projection.buildRectangleProjectionXZ();
        break;
      case Projection.PROJECTION_PLANE_YZ:
        projection.buildRectangleProjectionYZ();
        break;
    }
  }

  @FXML
  public void changeAxonometryProjection(Event event) {
    projection.buildAxonometry(
        UiHelper.getAngelFormUi(uiAnglePsi, uiCurrentAnglePsi, event),
        UiHelper.getAngelFormUi(uiAngleFi, uiCurrentAngleFi, event));
  }

  @FXML
  public void changeScaleneProjection(Event event) {
    projection.buildScalene(
        UiHelper.getAngelFormUi(uiAngleAlpha, uiCurrentAngleAlpha, event),
        UiHelper.getValueAsDouble(uiDistanceToPlane, 1));
  }

  @FXML
  public void changePerspectiveProjection(Event event) {
    projection.buildPerspective(
        UiHelper.getValueAsDouble(uiDistanceD, 0),
        UiHelper.getAngelFormUi(uiAnglePerspectiveTheta, uiCurrentAnglePerspectiveTheta, event),
        UiHelper.getAngelFormUi(uiAnglePerspectiveFi, uiCurrentAnglePerspectiveFi, event),
        UiHelper.getValueAsDouble(uiDistanceRo, 0));
  }

  // Model light
  @FXML
  public void changeLightOnOff() {
    if (uiLight.getImage().equals(imageLightOn)) {
      uiLight.setImage(imageLightOff);
      shapeModel.setLight(false);
    } else {
      shapeModel.setLight(true);
      uiLight.setImage(imageLightOn);
    }
  }

  @FXML
  public void changeLightPoint() {
    double vX = -UiHelper.getValueAsDouble(uiLightX, 250);
    double vY = UiHelper.getValueAsDouble(uiLightY, 250);
    double vZ = UiHelper.getValueAsDouble(uiLightZ, 250);

    if (vX == 0 && vY == 0 && vZ == 0) {
      shapeModel.setLight(false);
    } else {
      shapeModel.setLight(true);
    }

    shapeModel.setLightPoint(new FacePoint(vX, vY, vZ));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // init shape
    shapeModel = new Shape(Cube.getFaceList());

    shapeModel.setTranslateX(GeometryUtils.Size.CENTER_WIDTH);
    shapeModel.setTranslateY(GeometryUtils.Size.CENTER_HEIGHT);
    shapeModel.setShapeBorderColor(Color.BLACK);
    // init view
    axisGroup = GeometryUtils.creteAxis();
    gridGroup = GeometryUtils.createGrid(10);
    axisTextGroup = GeometryUtils.createAxisText(10, 5);
    pane.getChildren().addAll(axisGroup, gridGroup, axisTextGroup, shapeModel);
    FXCollections.reverse(pane.getChildren());
    // init projections
    projection = new Projection(shapeModel);
    projection.setTranslateX(GeometryUtils.Size.CENTER_WIDTH);
    projection.setTranslateY(GeometryUtils.Size.CENTER_HEIGHT);

    axisGroupProjection = GeometryUtils.creteAxis();
    axisTextGroupProjection = GeometryUtils.createAxisText(10, 5);
    gridGroupProjection = GeometryUtils.createGrid(10);

    projection.buildRectangleProjectionXY();
    uiProjectionPane
        .getChildren()
        .addAll(projection, axisTextGroupProjection, axisGroupProjection, gridGroupProjection);
    projectionAxonometricList = new ArrayList<>(4);
    Collections.addAll(
        projectionAxonometricList, uiAngleFi, uiAnglePsi, uiCurrentAngleFi, uiCurrentAnglePsi);
    projectionObliqueList = new ArrayList<>(3);
    Collections.addAll(projectionObliqueList, uiDistanceToPlane, uiAngleAlpha, uiCurrentAngleAlpha);
    projectionPerspectiveList = new ArrayList<>(1);
    Collections.addAll(
        projectionPerspectiveList,
        uiAnglePerspectiveFi,
        uiAnglePerspectiveTheta,
        uiDistanceD,
        uiDistanceRo,
        uiCurrentAnglePerspectiveFi,
        uiCurrentAnglePerspectiveTheta);
    projectionRectangleList = new ArrayList<>(3);
    uiPlaneXY.setUserData(Projection.PROJECTION_PLANE_XY);
    uiPlaneXZ.setUserData(Projection.PROJECTION_PLANE_XZ);
    uiPlaneYZ.setUserData(Projection.PROJECTION_PLANE_YZ);
    Collections.addAll(projectionRectangleList, uiPlaneXY, uiPlaneXZ, uiPlaneYZ);
    imageLightOn = new Image("images/light-on.png");
    imageLightOff = new Image("images/light-off.png");
    pane.setDepthTest(DepthTest.ENABLE);
    uiProjectionPane.setDepthTest(DepthTest.ENABLE);
  }
}
