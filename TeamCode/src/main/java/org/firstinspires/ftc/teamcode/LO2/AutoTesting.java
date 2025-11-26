package org.firstinspires.ftc.teamcode.LO2;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class AutoTesting extends OpMode {
    //creates motor class
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;


    @Override
    public void init() {
        //defines motors
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
//        flywheelUP = hardwareMap.get(DcMotorEx.class, "wu");
//        flywheelDOWN = hardwareMap.get(DcMotorEx.class, "wd");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Initiated Version 1.2");
        telemetry.update();
    }

    public void driveOmni(double y, double rx, double x) {
        //braking
        final double MAX_TICKS_PER_SECOND = 4661;


        //math stuff for movement
        double maxValue = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//Ok so essentially the x value has to switch for a thing due to code reversing it earlier, keep this in mind when debugging.
        double flPower = (y) / maxValue;
        double blPower = (y) / maxValue;
        double frPower = (y) / maxValue;
        double brPower = (y) / maxValue;

        double flPowerX = (x) / maxValue;
        double blPowerX = (-x) / maxValue;
        double frPowerX = (-x) / maxValue;
        double brPowerX = (x) / maxValue;





//        double flPower = (y - x + rx) / maxValue;
//        double blPower = (y + x + rx) / maxValue;
//        double frPower = (y - x - rx) / maxValue;
//        double brPower = (y + x - rx) / maxValue;



        frontLeft.setVelocity(flPower * MAX_TICKS_PER_SECOND);
        backLeft.setVelocity(blPower * MAX_TICKS_PER_SECOND);
        frontRight.setVelocity(frPower * MAX_TICKS_PER_SECOND);
        backRight.setVelocity(brPower * MAX_TICKS_PER_SECOND);
    }

    @Override
    public void start() {
        telemetry.addLine("Go go go!!!!!");
        telemetry.update();
    }

    @Override
    public void stop() {
        telemetry.addLine("Stopped");
        telemetry.update();
    }

    @Override
    public void loop() {
        boolean yMove = gamepad1.a;

        boolean nyMove = gamepad1.b;

        boolean xPos = gamepad1.x;

        boolean xNeg = gamepad1.y;



        //Fun fact, this one line is what drives everything
        //actual code for movement
        if (yMove) {
            double y = 1;
            double x = 0;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

            telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
            telemetry.update();
        } else if (nyMove){
            // for negative speed/going backwards on the game pad
            double y = - 1;
            double x = 0;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

            telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
            telemetry.update();
        } else {
            double y =0;
            double x = 0;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

        }

        //Fun fact, this does strafing
        if(xPos){
            double y = 0;
            double x = 1;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

            telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
            telemetry.update();
        } else if (xNeg) {
            double y = 0.0;
            double x = -1;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

            telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
            telemetry.update();
        } else {
            double y = 0;
            double x = 0;  // Strafing
            double rx = 0;
            driveOmni(y, rx, x);

            telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
            telemetry.update();
        }



         // Forward/Backward
         // Rotation



//        if (gamepad1.x) {
//            flywheelUP.setPower(-1);
//            flywheelDOWN.setPower(1);
//        } else {
//            flywheelUP.setPower(0);
//            flywheelDOWN.setPower(0);
//        }


        // Telemetry for movement
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes only

    }
}
