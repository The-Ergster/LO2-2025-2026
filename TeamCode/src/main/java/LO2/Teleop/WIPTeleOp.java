package LO2.Teleop;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import codebase.gamepad.Gamepad;

//Fun stuff
import java.util.ArrayList;
import java.util.Random;
@TeleOp
public class WIPTeleOp extends OpMode {
    //creates motor classes
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private DcMotor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    private Gamepad gamepad;
    private boolean rbPressedLast;
    private boolean rbPressedNow;

    @Override
    public void init() {
        //defines motors
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        gamepad = new Gamepad(gamepad1);

        flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr");
        flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl");
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");

        rbPressedLast = false;


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
        ArrayList<String> phrases = new ArrayList<>();

        // Add elements to the list
        phrases.add("Coding: It Works");
        phrases.add("The Universe is gone!!.... Never mind it's still here");
        phrases.add("The Cake Is A Lie!");
        phrases.add("; expected ");
        phrases.add("Shaw!");
        phrases.add("200 killed in rogue servo accident");
        phrases.add("I say potato, you say 'ERROR cannot resolve line'");
        phrases.add("There is no Spoon");
        phrases.add("'Thinking noises'");
        phrases.add("Bring me a shrubbery!");
        phrases.add("Give me a second, I'm thinking");





        int element;
        Random random = new Random();
        element = random.nextInt(11);


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
        gamepad.loop();
        rbPressedNow =  gamepad1.right_bumper;

        //actual code for movement
        //takes value from joysticks
        double y = -gamepad1.left_stick_y; // Forward/Backward
        double x = gamepad1.left_stick_x;  // Strafing
        double rx = gamepad1.right_stick_x; // Rotation

        driveOmni(y, rx, x);


        if (gamepad1.x) {
            flywheelRIGHT.setPower(-1);
            flywheelLEFT.setPower(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            loaderServo.setPower(1);

        } else if (gamepad1.a) {
            loaderServo.setPower(1);

        } else if (gamepad1.b) {
            loaderServo.setPower(-1);

        } else if (gamepad1.y) {
            flywheelRIGHT.setPower(1);
            flywheelLEFT.setPower(-1);
            loaderServo.setPower(-1);
        }
        else {
            flywheelRIGHT.setPower(0);
            flywheelLEFT.setPower(0);
            loaderServo.setPower(0);
        }

        if (rbPressedNow && !rbPressedLast){
            frontLeft.setVelocity(-4661);
            backLeft.setVelocity(-4661);
            frontRight.setVelocity(-4661);
            backRight.setVelocity(-4661);
            try {
                Thread.sleep(159);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        rbPressedLast = rbPressedNow;

        // Telemetry for movement
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes only
        telemetry.addData("Gamepad 1", "Left Y: %.2f | Left X: %.2f | Right X: %.2f", y, x, rx);
        telemetry.addData("Power", loaderServo.getPower());

        telemetry.update();
    }
}

