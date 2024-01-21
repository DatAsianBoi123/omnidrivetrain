package com.datasiqn.omnidrivetrain.widget;

import com.datasiqn.omnidrivetrain.data.OmniDriveTrainData;
import edu.wpi.first.shuffleboard.api.components.CurvedArrow;
import edu.wpi.first.shuffleboard.api.util.Vector2D;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.text.DecimalFormat;

@Description(name = "OmniDriveTrain", dataTypes = OmniDriveTrainData.class)
@ParametrizedController("OmniDriveTrain.fxml")
public class OmniDriveTrainWidget extends SimpleAnnotatedWidget<OmniDriveTrainData> {
    private static final DecimalFormat FORMAT = new DecimalFormat("0.000");

    @FXML
    private Pane root;

    @FXML
    private Label leftPower;

    @FXML
    private Label topPower;

    @FXML
    private Label rightPower;

    @FXML
    private Label bottomPower;

    @FXML
    private Rectangle drivetrain;

    @FXML
    private Polygon robotDirection;

    @FXML
    private Pane robotSpeedPane;

    @FXML
    private void initialize() {
        dataProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) return;

            double leftPower = newValue.getLeftPower();
            double topPower = newValue.getTopPower();
            double rightPower = newValue.getRightPower();
            double bottomPower = newValue.getBottomPower();

            this.leftPower.setText(FORMAT.format(leftPower));
            this.topPower.setText(FORMAT.format(topPower));
            this.rightPower.setText(FORMAT.format(rightPower));
            this.bottomPower.setText(FORMAT.format(bottomPower));

            double heading = newValue.getHeading();
            double headingDegrees = Math.toDegrees(heading);
            double width = calculateWidth(root.getWidth(), root.getHeight());

            setLabelPositions(heading, width);

            drivetrain.setRotate(headingDegrees);

            robotDirection.setRotate(headingDegrees);
            setRobotDirectionPosition(heading, width, calculateRobotDirectionLength(width));

            generateRobotSpeedVector(heading, leftPower, topPower, rightPower, bottomPower, width);
        }));

        drivetrain.setStyle("-fx-stroke: -fx-text-base-color");
        robotDirection.setStyle("-fx-fill: -swatch-100");

        root.widthProperty().addListener(((observable, oldValue, newValue) -> onResize(newValue.doubleValue(), root.getHeight())));
        root.heightProperty().addListener(((observable, oldValue, newValue) -> onResize(root.getWidth(), newValue.doubleValue())));
    }

    private void setLabelPositions(double heading, double width) {
        width = width / 2 + 25;
        leftPower.setTranslateX(-width * Math.cos(heading));
        leftPower.setTranslateY(-width * Math.sin(heading));

        topPower.setTranslateX(width * Math.sin(heading));
        topPower.setTranslateY(-width * Math.cos(heading));

        rightPower.setTranslateX(width * Math.cos(heading));
        rightPower.setTranslateY(width * Math.sin(heading));

        bottomPower.setTranslateX(-width * Math.sin(heading));
        bottomPower.setTranslateY(width * Math.cos(heading));
    }

    private void setRobotDirectionPosition(double heading, double width, double triangleLength) {
        width = width / 2 - triangleLength / 2;
        robotDirection.setTranslateX(width * Math.sin(heading));
        robotDirection.setTranslateY(-width * Math.cos(heading));
    }

    private void generateRobotSpeedVector(double heading, double leftPower, double topPower, double rightPower, double bottomPower, double width) {
        Vector2D vector = new Vector2D(topPower + bottomPower, -leftPower - rightPower);
        double magnitude = vector.getMagnitude();
        if (magnitude < 0.1) {
            robotSpeedPane.getChildren().clear();
            return;
        }
        Shape shape = CurvedArrow.createStraight(magnitude * width * 0.1, vector.getAngle() + heading, 0, 7);
        shape.getStyleClass().add("robot-direction-vector");
        robotSpeedPane.getChildren().setAll(shape);
    }

    private void onResize(double width, double height) {
        double newWidth = calculateWidth(width, height);
        drivetrain.setWidth(newWidth);
        drivetrain.setHeight(newWidth);

        OmniDriveTrainData data = getData();
        double heading = data.getHeading();
        setLabelPositions(heading, newWidth);

        generateRobotSpeedVector(heading, data.getLeftPower(), data.getTopPower(), data.getRightPower(), data.getBottomPower(), newWidth);

        double triangleLength = calculateRobotDirectionLength(newWidth);
        ObservableList<Double> points = robotDirection.getPoints();
        points.setAll(
                -triangleLength, 0.0,
                triangleLength, 0.0,
                0.0, -triangleLength
        );
        setRobotDirectionPosition(heading, newWidth, triangleLength);
    }

    private double calculateRobotDirectionLength(double width) {
//        return width * 0.05;
        return 10;
    }

    private double calculateWidth(double width, double height) {
        return Math.min(width - 90, height - 90);
    }

    @Override
    public Pane getView() {
        return root;
    }
}
