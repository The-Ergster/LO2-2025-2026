package LO2.Teleop;

//Base level imports
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import codebase.Constants;
import codebase.gamepad.Gamepad;

@TeleOp
public class WIPTeleOpLime extends OpMode {
   //creates motor classes
   private DcMotorEx frontLeft, frontRight, backLeft, backRight;
   private DcMotorEx flywheelRIGHT, flywheelLEFT;
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
       flywheelLEFT.setDirection(DcMotorEx.Direction.FORWARD);
       flywheelRIGHT.setDirection(DcMotorEx.Direction.REVERSE);

       frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       flywheelRIGHT.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
       flywheelLEFT.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

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

    public void driveOmni(double y, double rx, double x, double scale) {

        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


        //power is on scale of -1 to 1
        //adds up input from controller (y, x, rx) divides by the biggest value between whatever the x, y and rx add up to or 1.
        double maxValue = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double flPower = (y + x + rx) / maxValue;
        double blPower = (y - x + rx)  / maxValue;
        double frPower = (y - x - rx)  / maxValue;
        double brPower = (y + x - rx)/ maxValue;

        //Sets velocity on a scale from -MAX_TICKS_PER_SECOND to +MAX_TICKS_PER_SECOND
        frontLeft.setPower(flPower  * scale);
        frontRight.setPower(frPower  * scale);
        backLeft.setPower(blPower  * scale);
        backRight.setPower(brPower  * scale);
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
       double area = result.getTa();
       //regression
       double distance = 2.43*Math.pow(area, 4) - 14.90409*Math.pow(area, 3) + 35.43912*Math.pow(area, 2) - 40.1374*area + 19.76406;

       //thanks will in meters/sec
       double velocity = (9.8*Math.pow(distance, 2)) / (2 * 1.143 * Math.pow(Math.cos(80),2) - distance * Math.sin(20));

       double rpm = (2*Math.PI * 0.048 / 60) / velocity;

       //actual code for movement
       //takes value from joysticks
       double y = gamepad.leftJoystick.getY(); // Forward/Backward
       double x = gamepad.leftJoystick.getX();  // Strafing
       double rx = gamepad.rightJoystick.getX(); // Rotation
       double parking = gamepad.rightJoystickButton.isToggled() ? 0.5 : 1.0;

       if(gamepad.rightJoystickButton.isToggled()) {
           driveOmni(y,rx,x,parking);
       } else {
           driveOmni(y,rx,x);
       }

       if (gamepad.xButton.isPressed()) {
           flywheelRIGHT.setVelocity(875);
           flywheelLEFT.setVelocity(875);
           try {
               Thread.sleep(1200);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           loaderServo.setPower(1);

       } else if (gamepad.aButton.isPressed()) {
           loaderServo.setPower(1);

       } else if (gamepad.bButton.isPressed()) {
           loaderServo.setPower(-1);

       } else if (gamepad.yButton.isPressed()) {
           flywheelRIGHT.setPower(0.5);
           flywheelLEFT.setPower(-0.5);
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
       telemetry.addData("velocity", velocity);
       telemetry.addData("rpm", rpm);
       telemetry.addData("Area:", area);
       telemetry.update();
   }
}