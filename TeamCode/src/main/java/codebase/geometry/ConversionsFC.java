package codebase.geometry;

import static java.lang.Math.sin;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.geometry.MovementVector;
import codebase.geometry.Pose;

public class ConversionsFC {
    public static MovementVector changeToRobotCenteredCoordinates(MovementVector absoluteVelocity, Pose currentPose) {
        return changeToRobotCenteredCoordinates(
                absoluteVelocity.getVerticalVelocity(),
                absoluteVelocity.getHorizontalVelocity(),
                absoluteVelocity.getRotationalVelocity(),
                currentPose.getHeading(AngleUnit.RADIANS)
        );
    }

    //Trigonometric Equations again
    public static MovementVector changeToRobotCenteredCoordinates(double lateral, double horizontal, double currentAngle) {
        double forwardRelative = lateral * Math.cos(currentAngle) + horizontal * sin(currentAngle);
        double rightwardRelative = -lateral * sin(currentAngle) + horizontal * Math.cos(currentAngle);
        return new MovementVector(forwardRelative,rightwardRelative,0,AngleUnit.DEGREES);
    }

    public static MovementVector changeToRobotCenteredCoordinates(double lateral, double horizontal, double rotational, double currentAngle) {
        double forwardRelative = lateral * Math.cos(currentAngle) + horizontal * sin(currentAngle);
        double rightwardRelative = -lateral * sin(currentAngle) + horizontal * Math.cos(currentAngle);
        return new MovementVector(forwardRelative,rightwardRelative,rotational, AngleUnit.DEGREES);
    }


    /**
     * Used to convert forward and rightward coordinates into field-relative vertical and horizontal coordinates.
     *
     * @param forward   The distance the robot drives in its forward direction.
     * @param rightward The Distance the robot drives in its rightward direction.
     * @param currentAngle IN RADIANS, otherwise it will break! 0 radians is forward.
     * @return The movement vector that has been adjusted to field centric coordinates.
     */
    public static MovementVector changeToFieldCenteredCoordinates(double forward, double rightward,double currentAngle){
        //converts x and y positions from robot-relative to field-relative
        double deltaX = forward * Math.sin(currentAngle) + rightward * Math.cos(currentAngle);
        double deltaY = forward * Math.cos(currentAngle) - rightward * Math.sin(currentAngle);
        return new MovementVector(deltaY, deltaX, 0, AngleUnit.DEGREES);
    }

    public static MovementVector changeToFieldCenteredCoordinates(MovementVector relativeVelocity, Pose currentPose){
        return changeToFieldCenteredCoordinates(
                relativeVelocity.getVerticalVelocity(),
                relativeVelocity.getHorizontalVelocity(),
                currentPose.getHeading(AngleUnit.RADIANS)
        );
    }

    public static Pose convertFieldToStandard(Pose pose) {
        return new Pose(-pose.getX(), pose.getY(), -pose.getHeading(AngleUnit.RADIANS) + Math.PI, AngleUnit.RADIANS);
    }

    public static Pose convertStandardToField(Pose pose) {
        return new Pose(pose.getX(), -pose.getY(), -pose.getHeading(AngleUnit.RADIANS) + Math.PI, AngleUnit.RADIANS);
    }
}
