//Package importing
package org.firstinspires.ftc.teamcode.LO2;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.Servo;

//Fun stuff
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@TeleOp
public class WIPteleop extends OpMode {
    //creates motor classes
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private DcMotor flywheelUP, flywheelDOWN;
    //Creates Servo Classes
    private Servo loaderServo;


    @Override
    public void init() {
        //defines motors
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        flywheelUP = hardwareMap.get(DcMotorEx.class, "wu");
        flywheelDOWN = hardwareMap.get(DcMotorEx.class, "wd");
        //defines encoders
        loaderServo = hardwareMap.get(Servo.class, "ls");


        //configures direction
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Initiated Version 1.2");
        telemetry.addLine("(^_^)");
        telemetry.addLine("Brad says good luck!");
        List<String> phrases = new ArrayList<>();

        // Add elements to the list
        phrases.add("Coding: It Works");
        phrases.add("The Universe is over.... Never mind it's still here");
        phrases.add("The Cake Is Lie!");
        phrases.add("; expected ");
        phrases.add("Shaw!");



        int element = 0;
        Random random = new Random();
        element = random.nextInt(6);


        telemetry.addLine(phrases.get(element));
        telemetry.update();
    }


    public void driveOmni(double y, double rx, double x) {

        final double MAX_TICKS_PER_SECOND = 4661;


        //math stuff for movement
        //power is on scale of -1 to 1
        //adds up input from controller (y, x, rx) divides by the biggest value between whatever the x, y and rx add up to or 1.
        double maxValue = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double flPower = (y + x + rx) / maxValue;
        double blPower = (y - x + rx) / maxValue;
        double frPower = (y - x - rx) / maxValue;
        double brPower = (y + x - rx) / maxValue;




        //Sets velocity on a scale from -MAX_TICKS_PER_SECOND to +MAX_TICKS_PER_SECOND
        frontLeft.setVelocity(flPower * MAX_TICKS_PER_SECOND);
        backLeft.setVelocity(blPower * MAX_TICKS_PER_SECOND);
        frontRight.setVelocity(frPower * MAX_TICKS_PER_SECOND);
        backRight.setVelocity(brPower * MAX_TICKS_PER_SECOND);
    }

    @Override
    public void start() {
        telemetry.addLine("Go go go!!!!! - (^_^)");
        telemetry.update();
    }

    @Override
    public void stop() {
        telemetry.addLine("Stopped - (^_^)");


        telemetry.update();


    }

    //Make sure all variables are in scope.
    @Override
    public void loop() {

        //actual code for movement
        //takes value from joysticks
        double y = -gamepad1.left_stick_y; // Forward/Backward
        double x = gamepad1.left_stick_x;  // Strafing
        double rx = gamepad1.right_stick_x; // Rotation

        driveOmni(y, rx, x);


        if (gamepad1.x) {
            flywheelUP.setPower(-1);
            flywheelDOWN.setPower(1);
            loaderServo.setPosition(1);
        } else {
            flywheelUP.setPower(0);
            flywheelDOWN.setPower(0);
            loaderServo.setPosition(-1);

        }
        double servopos = loaderServo.getPosition();


        // Telemetry for movement
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes only
        telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
        telemetry.addData("Loading Servo Position", servopos);

        telemetry.update();
    }
}
