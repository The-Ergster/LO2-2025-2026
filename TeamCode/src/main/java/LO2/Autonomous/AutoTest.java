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

import codebase.Constants;
import codebase.actions.MoveToAction;
import codebase.actions.SequentialAction;
import codebase.geometry.FieldPosition;
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizer;

@Autonomous
public class AutoTest extends OpMode {

    private SequentialAction actionThread;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private DcMotor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    private MecanumDriver driver;
    private PinpointLocalizer localizer;

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
        driver = new MecanumDriver(new Motor(frontLeft),new Motor(frontRight), new Motor(backLeft), new Motor(backRight), Constants.MECANUM_COEFFICIENT_MATRIX,-1);

        localizer.init(new FieldPosition(0,0,0));

        actionThread = new SequentialAction(
                new MoveToAction(driver, localizer, new FieldPosition(5,0,0),1,1,0.1,Math.PI/180)
        );
        actionThread.init();

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


    }

    @Override
    public void loop() {
        actionThread.loop();
        localizer.loop();
    }
}
