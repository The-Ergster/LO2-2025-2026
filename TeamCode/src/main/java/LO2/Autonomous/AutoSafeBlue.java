package LO2.Autonomous;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Autonomous
public class AutoSafeBlue extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private DcMotor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    @Override
    public void init() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr");
        flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl");
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void start(){

        frontLeft.setVelocity(-4661);
        backLeft.setVelocity(-4661);
        frontRight.setVelocity(-4661);
        backRight.setVelocity(-4661);
        try {
            Thread.sleep(267);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);
        flywheelRIGHT.setPower(-0.5);
        flywheelLEFT.setPower(0.5);
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loaderServo.setPower(1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        frontLeft.setVelocity(4661);
        backLeft.setVelocity(0);
        frontRight.setVelocity(-4661);
        backRight.setVelocity(4661);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        frontLeft.setVelocity(4661);
        backLeft.setVelocity(4661);
        frontRight.setVelocity(4661);
        backRight.setVelocity(4661);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);
        flywheelRIGHT.setPower(0);
        flywheelLEFT.setPower(0);
        loaderServo.setPower(0);



    }
    @Override
    public void loop() {

    }
}
