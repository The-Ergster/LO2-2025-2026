//Package importing
package LO2.Teleop;
//Base level imports

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
import codebase.actions.SimultaneousAction;
import codebase.gamepad.Gamepad;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
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
    private FieldPosition currentPosition;

    @Override
    public void init() {
        //defines motors
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        flywheelRIGHT = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flywheelLEFT = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));
        //defines servo and IMU
        loaderServo = hardwareMap.get(CRServo.class, "ls");
        imu = hardwareMap.get(IMU.class, "imu");

        gamepad = new Gamepad(gamepad1);
        actionThread = new SimultaneousAction();

        //configures direction
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);

        //configures launch motors
        LaunchAction.setLaunchActionMotors(loaderServo, flywheelRIGHT, flywheelLEFT);

        //physical configuration of IMU/Control Hub
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        //logo of controlHub
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        //USBs
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        //initialization using parameters
        imu.initialize(parameters);
        //clears heading measurement from previous OpMode
        imu.resetYaw();

        //x and y are not updated nor used
        currentPosition = new FieldPosition(0, 0, 0);

        //utils for button press
        gamepad.xButton.onPress(() -> {
            actionThread.add(new LaunchAction(), true, true);
        });
    }

    @Override
    public void loop() {
        gamepad.loop();
        //gets heading from IMU
        currentPosition.setDirection(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        //takes value from joysticks
        MovementVector vector = new MovementVector(
                gamepad.leftJoystick.getY(),
                gamepad.leftJoystick.getX(),
                gamepad.rightJoystick.getX(),
                AngleUnit.RADIANS
        );

        //setting velocity using heading and joysticks
        driver.setAbsolutePower(currentPosition, vector);

        actionThread.loop();

        //utils for button held
        if (gamepad.aButton.isPressed()) {
            loaderServo.setPower(1);
        }
        if (gamepad.bButton.isPressed()) {
            loaderServo.setPower(-1);
        }
        if (gamepad.yButton.isPressed()) {
            flywheelRIGHT.setPower(1);
            flywheelLEFT.setPower(-1);
            loaderServo.setPower(-1);
        }

        // Telemetry
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes
        telemetry.addData("Field Heading (rad):", "heading: %.2f",
                currentPosition.getDirection()
        );
        telemetry.update();
    }
}

