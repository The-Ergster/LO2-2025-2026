package codebase.actions;

import static codebase.Constants.DIRECTION_PID_COEFFICIENTS;
import static codebase.Constants.MOVEMENT_PID_COEFFICIENTS;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.controllers.PIDController;
import codebase.geometry.Angles;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.geometry.Pose;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.Localizer;
import codebase.pathing.LocalizerFC;

public class MoveToAction implements Action {
    private final MecanumDriver driver;
    private final LocalizerFC<Pose> localizerFC;


    /**
     * The speed to move horizontally/vertically or some combination of the two in inches/sec
     */
    private final double movementSpeed;

    /**
     * The max rotational speed of the robot in radians/sec
     */
    private final double rotationalSpeed;

    private final double maxDistanceError;
    private final double maxRotationalError;


    private final Pose destinationPose;

    public MoveToAction(MecanumDriver driver, LocalizerFC<Pose> localizerFC, Pose destinationPose){
        this(driver, localizerFC, destinationPose, 1.25,1.25,30,30);
    }
    public MoveToAction(MecanumDriver driver, LocalizerFC<Pose> localizerFC, Pose destinationPose, double movementSpeed, double rotationalSpeed, double maxDistanceError, double maxRotationalError) {
        this.driver = driver;
        this.localizerFC = localizerFC;
        this.destinationPose = destinationPose;
        this.movementSpeed = movementSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.maxDistanceError = maxDistanceError;
        this.maxRotationalError = maxRotationalError;
    }

    @Override
    public void init() {}

    @Override
    public void loop() {
        Pose currentPose = this.localizerFC.getFieldCentric();
        double dy = this.destinationPose.getY() - currentPose.getY();
        double dx = this.destinationPose.getX() - currentPose.getX();
        double dh = Angles.angleDifference(
                currentPose.getHeading(AngleUnit.RADIANS),
                this.destinationPose.getHeading(AngleUnit.RADIANS),
                AngleUnit.RADIANS
        );

        double vy = this.movementSpeed / (1 + Math.pow(0.5 * Math.E, -dy)) - (this.movementSpeed /2);
        double vx = this.movementSpeed / (1 + Math.pow(0.5 * Math.E, -dx)) - (this.movementSpeed /2);
        double vh = this.movementSpeed / (1 + Math.pow(0.5 * Math.E, -dh)) - (this.rotationalSpeed /2);



        MovementVector vector = new MovementVector(vy, vx, vh, AngleUnit.RADIANS);

        this.driver.setVelocityFieldCentric(currentPose, vector);
    }

    @Override
    public boolean isComplete() {
        Pose fieldCentricPose = this.localizerFC.getFieldCentric();

        boolean isDistanceComplete = fieldCentricPose.distanceTo(this.destinationPose)<this.maxDistanceError;
        boolean isRotationComplete = Math.abs(this.destinationPose.getHeading(AngleUnit.DEGREES) - fieldCentricPose.getHeading(AngleUnit.DEGREES)) < this.maxRotationalError;
        boolean isComplete = isDistanceComplete&&isRotationComplete;

        if (isComplete) {
            driver.stop();
        }

        return isComplete;
    }

}
