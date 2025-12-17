//Package importing
package LO2.Teleop;
//Base level imports

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.Constants;
import codebase.actions.LaunchAction;
import codebase.actions.SetServoPowerAction;
import codebase.actions.SimultaneousAction;
import codebase.gamepad.Gamepad;
import codebase.geometry.MovementVector;
import codebase.geometry.Pose;
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;

@TeleOp
public class TeleOpNew extends OpMode {
    private Gamepad gamepad;
    private MecanumDriver driver;
    private SimultaneousAction actionThread;
    //creates motor classes
    private Motor fl, fr, bl, br;
    private Motor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    private IMU imu;
    private Pose currentPose;
    private double MAX_TICKS_PERSEC = 4661;


    @Override
    public void init() {
        //defines motors
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));

        flywheelRIGHT = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flywheelLEFT = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));
        loaderServo = hardwareMap.get(CRServo.class, "ls");
        imu = hardwareMap.get(IMU.class, "imu");

        gamepad = new Gamepad(gamepad1);
        actionThread = new SimultaneousAction();

        //configures direction
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);
        LaunchAction.setLaunchActionMotors(loaderServo, flywheelRIGHT, flywheelLEFT);

        //physical configuration of IMU/Control Hub
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        //logo of controlHub
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        //USBs
                        RevHubOrientationOnRobot.UsbFacingDirection.UP));
        //typical initialization and clearing of heading measurement
        imu.initialize(parameters);
        imu.resetYaw();

        currentPose = new Pose(0, 0, 0, AngleUnit.RADIANS);
    }


    //Make sure all variables are in scope.
    @Override
    public void loop() {
        //updating heading and pose using setHeading constructor, specifically calls for heading (yaw)
        currentPose.setHeading(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS), AngleUnit.RADIANS);

        //takes value from joysticks
        MovementVector vector = new MovementVector(
                gamepad.leftJoystick.getY() * MAX_TICKS_PERSEC,
                gamepad.leftJoystick.getX() * MAX_TICKS_PERSEC,
                gamepad.rightJoystick.getX() * MAX_TICKS_PERSEC,
                AngleUnit.RADIANS
        );

        //setting velocity using Mecanum class
        driver.setVelocityFieldCentric(currentPose, vector);

        //utils
        if (gamepad.xButton.isPressed()) {
            actionThread.add(new LaunchAction(), true, true);
        }
        if (gamepad.aButton.isPressed()) {
            actionThread.add(new SetServoPowerAction(loaderServo, 1), true, true);
        }
        if (gamepad.bButton.isPressed()) {
            actionThread.add(new SetServoPowerAction(loaderServo, -1), true, true);
        }
        actionThread.loop();

        gamepad.loop();

        // Telemetry for movement
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes only
        telemetry.addData("Gamepad:", "Left Y: %.2f | Left X: %.2f | Right X: %.2f",
                gamepad.leftJoystick.getY(),
                gamepad.leftJoystick.getX(),
                gamepad.rightJoystick.getX());
        telemetry.addData("Pose:", currentPose);
        telemetry.update();
    }
}

