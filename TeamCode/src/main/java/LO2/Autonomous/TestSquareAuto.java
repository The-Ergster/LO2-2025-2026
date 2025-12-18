package LO2.Autonomous;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import codebase.Constants;
import codebase.actions.LaunchAction;
import codebase.actions.MoveToAction;
import codebase.actions.SequentialAction;
import codebase.actions.SleepAction;
import codebase.geometry.Pose;
import codebase.hardware.Motor;
import codebase.hardware.PinpointModule;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizerFC;

@Autonomous
public class TestSquareAuto extends OpMode {

    private SequentialAction actionThread;
    private Motor fl, fr, bl, br;
    private MecanumDriver driver;
    private PinpointLocalizerFC localizer;
    private PinpointModule pinpoint;
    private Motor flywheelRIGHT,flywheelLEFT;
    private CRServo loaderServo;

    @Override
    public void init() {
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        flywheelRIGHT = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flywheelLEFT = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");
        pinpoint = hardwareMap.get(PinpointModule.class, "pinpoint");
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);
        localizer = new PinpointLocalizerFC(pinpoint,
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD
        );
        LaunchAction.setLaunchActionMotors(loaderServo, flywheelRIGHT, flywheelLEFT);


        actionThread = new SequentialAction(
                new MoveToAction(driver, localizer, new Pose(0, 6.2, 0, AngleUnit.DEGREES), 0.365, 1, 0.1, Math.PI / 180),
                new SleepAction(1000),
                new LaunchAction(),
                new MoveToAction(driver, localizer, new Pose(-19.513, -93, -0.916, AngleUnit.DEGREES), 0.365, 0.5, 0.1, Math.PI / 180)
        );

        localizer.init(new Pose(0,0,0, AngleUnit.RADIANS));
        actionThread.init();
    }

    @Override
    public void loop() {
        if (localizer.isDoneInitializing()) {
            localizer.loop();
        }
        actionThread.loop();
    }
}
