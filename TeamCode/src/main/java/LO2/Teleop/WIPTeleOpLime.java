package LO2.Teleop;
//Base level imports

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;
import java.util.Random;

import codebase.Constants;
import codebase.gamepad.Gamepad;

@TeleOp
public class WIPTeleOpLime extends OpMode {
   //creates motor classes
   private DcMotorEx frontLeft, frontRight, backLeft, backRight;
   private DcMotor flywheelRIGHT, flywheelLEFT;
   //Creates Servo Classes
   private CRServo loaderServo;
   private Limelight3A limelight;
   private Gamepad gamepad;


   @Override
   public void init() {
       //defines motors
       frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
       frontRight = hardwareMap.get(DcMotorEx.class, "fr");
       backLeft = hardwareMap.get(DcMotorEx.class, "bl");
       backRight = hardwareMap.get(DcMotorEx.class, "br");
       gamepad = new Gamepad(gamepad1);
       limelight = hardwareMap.get(Limelight3A.class, "limelight");
       limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
       limelight.pipelineSwitch(0);
       limelight.start(); // This tells Limelight to start looking!

       flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr");
       flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl");
//        //defines encoders
       loaderServo = hardwareMap.get(CRServo.class, "ls");




       //configures direction
       frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
       backLeft.setDirection(DcMotorEx.Direction.REVERSE);
       frontRight.setDirection(DcMotorEx.Direction.FORWARD);
       backRight.setDirection(DcMotorEx.Direction.FORWARD);

       frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

       frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       telemetry.addLine("Initiated Version 1.2");
       telemetry.addLine("(^_^)");
       telemetry.addLine("Brad says good luck!");
       telemetry.addLine(Constants.brad());
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
       limelight.start();
   }

   @Override
   public void stop() {
       telemetry.addLine("Stopped - (^_^)");
       telemetry.update();
   }

   //Make sure all variables are in scope.
   @Override
   public void loop() {
       gamepad.loop();

       LLResult result = limelight.getLatestResult();
       area = result.getTa();
       double d = Math.sqrt(Math.hypot(result.getTx(),result.getTy()));
       double y1 = .4579;
       double y2 = .7996;
       double vi = 10;

       double angle = Math.acos( (9.8 * Math.sqrt(d * d + y1 * y1) ) / (vi * Math.sqrt(19.6 * (y2*y2) ) ) );



       //actual code for movement
       //takes value from joysticks
       double y = -gamepad1.left_stick_y; // Forward/Backward
       double x = gamepad1.left_stick_x;  // Strafing
       double rx = gamepad1.right_stick_x; // Rotation

       driveOmni(y,rx,x);


       if (gamepad1.x) {
           flywheelRIGHT.setPower(-.5);
           flywheelLEFT.setPower(.5);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           loaderServo.setPower(1);

       } else if (gamepad1.a) {
           loaderServo.setPower(1);

       } else if (gamepad1.b) {
           loaderServo.setPower(-1);

       } else if (gamepad1.y) {
           flywheelRIGHT.setPower(.5);
           flywheelLEFT.setPower(-.5);
           loaderServo.setPower(-1);
       }
       else {
           flywheelRIGHT.setPower(0);
           flywheelLEFT.setPower(0);
           loaderServo.setPower(0);
       }



       // Telemetry for movement
       //If you add more buttons add more telemetry so we know whats going through
       //Debug purposes only
       telemetry.addData("Gamepad 1:", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
       telemetry.addData("Distance:", d);
       telemetry.addData("Angle",angle);
       telemetry.addData("Area:", area);
       telemetry.update();
   }
}

