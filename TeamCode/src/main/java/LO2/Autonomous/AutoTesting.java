package LO2.Autonomous;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.Constants;
import codebase.actions.LaunchAction;
import codebase.actions.MoveToAction;
import codebase.actions.SequentialAction;
import codebase.actions.SleepAction;
import codebase.geometry.FieldPosition;
import codebase.geometry.Pose;
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizer;
import codebase.pathing.PinpointLocalizerFC;

@Autonomous
public class AutoTesting extends OpMode {

    private SequentialAction actionThread;
    private Motor fl, fr, bl, br;
    private DcMotor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    private MecanumDriver driver;
    private PinpointLocalizerFC localizer;

    @Override
    public void init() {

        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr");
        flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl");
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");

        driver = new MecanumDriver(fl,fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);

        actionThread = new SequentialAction(
                new MoveToAction(driver, localizer, new Pose(0,-3,0, AngleUnit.DEGREES),0.365,1,0.1,Math.PI/180),
                new SleepAction(1000),
                new LaunchAction(),
                new MoveToAction(driver, localizer, new Pose(-3,-10,-0.3,AngleUnit.DEGREES), 0.365,0.5,0.1,Math.PI/180)
        );
        actionThread.init();
    }

    @Override
    public void loop() {
        actionThread.loop();
        localizer.loop();
    }
}
