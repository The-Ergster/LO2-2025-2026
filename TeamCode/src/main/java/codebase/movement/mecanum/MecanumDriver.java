package codebase.movement.mecanum;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.gamepad.Gamepad;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.geometry.Pose;
import codebase.hardware.Motor;

/**
 * Driver class for controlling a mecanum drive system
 * Supports both power-based and velocity-based control modes for relative and absolute movements.
 * Absolute movement refers to field-centric movement. For example, a positive vertical means "up" on the field.
 * Relative movement refers to robot-centric movement. For example, a positive vertical means "forwards" for the robot.
 */
public class MecanumDriver {
    public final Motor fl;
    public final Motor fr;
    public final Motor bl;
    public final Motor br;
    /** Coefficient matrix for mecanum drive adjustments. */
    public final MecanumCoefficientMatrix mecanumDriveCoefficients;
    /** Maximum allowable wheel velocity in inches per second. */
    private final double maxWheelVelocity;

    /**
     * Constructs a MecanumDriver with the specified motors, coefficient matrix, and maximum wheel velocity.
     *
     * @param fl Front-left motor.
     * @param fr Front-right motor.
     * @param bl Back-left motor.
     * @param br Back-right motor.
     * @param mecanumDriveCoefficients Coefficient matrix for drive adjustments.
     * @param maxWheelVelocity Maximum wheel velocity in inches per second.
     */
    public MecanumDriver(
            Motor fl,
            Motor fr,
            Motor bl,
            Motor br,
            MecanumCoefficientMatrix mecanumDriveCoefficients,
            double maxWheelVelocity
    ) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.mecanumDriveCoefficients = mecanumDriveCoefficients;
        this.maxWheelVelocity = maxWheelVelocity;

        fl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        fl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public MecanumDriver(
            Motor fl,
            Motor fr,
            Motor bl,
            Motor br,
            MecanumCoefficientMatrix mecanumDriveCoefficients
    ){
        this(fl,fr,bl,br,mecanumDriveCoefficients,-1);
    }

    /**
     * Sets the velocities for all motors.
     *
     * @param fl Velocity for front-left motor in inches per second.
     * @param fr Velocity for front-right motor in inches per second.
     * @param bl Velocity for back-left motor in inches per second.
     * @param br Velocity for back-right motor in inches per second.
     */
    public void setMotorVelocities(double fl, double fr, double bl, double br) {
        this.fl.setVelocity(fl);
        this.fr.setVelocity(fr);
        this.bl.setVelocity(bl);
        this.br.setVelocity(br);
    }

    /**
     * Sets the power levels for all motors.
     *
     * @param fl Power for front-left motor (-1 to 1).
     * @param fr Power for front-right motor (-1 to 1).
     * @param bl Power for back-left motor (-1 to 1).
     * @param br Power for back-right motor (-1 to 1).
     */
    public void setMotorPowers(double fl, double fr, double bl, double br) {
        this.fl.setPower(fl);
        this.fr.setPower(fr);
        this.bl.setPower(bl);
        this.br.setPower(br);
    }

    /**
     * Sets relative power inputs for the drive system.
     * Normalizes the powers to ensure they do not exceed 1.0 in absolute value.
     *
     * @param powerInput MovementVector containing normalized power inputs (-1 to 1).
     */
    public void setRelativePower(MovementVector powerInput) {
        MecanumCoefficientSet coefficientSet = this.mecanumDriveCoefficients.calculateCoefficientsWithPower(
                powerInput.getVerticalVelocity(),
                powerInput.getHorizontalVelocity(),
                powerInput.getRotationalVelocity()
        ).downScale(1);

        this.setMotorPowers(
                coefficientSet.fl,
                coefficientSet.fr,
                coefficientSet.bl,
                coefficientSet.br
        );
    }

    /**
     * Sets relative velocities for the drive system.
     * Normalizes the velocities to ensure they do not exceed the maximum wheel velocity.
     *
     * @param velocity MovementVector containing velocity inputs (inches/second or radians/second).
     */
    public void setRelativeVelocity(MovementVector velocity) {
        MecanumCoefficientSet coefficientSet = this.mecanumDriveCoefficients.calculateCoefficientsWithVelocity(
                velocity.getVerticalVelocity(),
                velocity.getHorizontalVelocity(),
                velocity.getRotationalVelocity()
        ).downScale(maxWheelVelocity);

        this.setMotorVelocities(
                coefficientSet.fl,
                coefficientSet.fr,
                coefficientSet.bl,
                coefficientSet.br
        );
    }


    /**
     * Sets absolute velocities relative to the field, transforming them to robot-relative velocities.
     *
     * @param currentPose Current field position including direction.
     * @param vector MovementVector containing absolute velocity inputs (inches/second or radians/second). (vertical - x, horizontal - y)
     */

    //Trigonometric Equations that were Derived
    public void setVelocityFieldCentric(Pose currentPose, MovementVector vector) {
        double theta = currentPose.getHeading(AngleUnit.RADIANS);
        double forwardRelative = vector.getVerticalVelocity() * Math.cos(theta) + vector.getHorizontalVelocity() * Math.sin(theta);
        double rightwardRelative = -vector.getVerticalVelocity() * Math.sin(theta) + vector.getHorizontalVelocity() * Math.cos(theta);

        this.setRelativeVelocity(
                new MovementVector(
                        forwardRelative,
                        rightwardRelative,
                        vector.getRotationalVelocity(),
                        vector.getAngleUnit()
                )
        );
    }

    /**
     * Stops all motors by setting powers to zero.
     */
    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }
}