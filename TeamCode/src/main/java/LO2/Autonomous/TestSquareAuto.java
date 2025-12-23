package LO2.Autonomous;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;

import codebase.Constants;
import codebase.actions.LaunchAction;
import codebase.actions.MoveToAction;
import codebase.actions.SequentialAction;
import codebase.actions.SleepAction;
import codebase.geometry.FieldPosition;
import codebase.hardware.Motor;
import codebase.hardware.PinpointModule;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizer;

@Autonomous
public class TestSquareAuto extends OpMode {

    private SequentialAction actionThread;
    private Motor fl, fr, bl, br;
    private MecanumDriver driver;
    private PinpointLocalizer localizer;
    private PinpointModule pinpoint;
    private Motor flyf,flyb;
    private CRServo loaderServo;

    @Override
    public void init() {
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        flyf = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flyb = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));
        loaderServo = hardwareMap.get(CRServo.class, "ls");
        pinpoint = hardwareMap.get(PinpointModule.class, "pinpoint");
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);
        localizer = new PinpointLocalizer(pinpoint,
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD
        );
        LaunchAction.setLaunchActionMotors(loaderServo, flyf, flyb);
        MoveToAction.setDriverAndLocalizer(driver,localizer);

        FieldPosition startPosition;
        startPosition = new FieldPosition(-72 + (14 + 4 * Math.sqrt(2)), 72 - (14 + 4 * Math.sqrt(2)), -Math.PI / 4);
        //blue
        startPosition.y *= -1;


        actionThread = new SequentialAction(
                new MoveToAction(new FieldPosition(0, 6.2, 0), 0.365, 1, 0.1, Math.PI / 180),
                new SleepAction(1000),
                new LaunchAction(),
                new MoveToAction(new FieldPosition(-19.513, -93, -0.916), 0.365, 0.5, 0.1, Math.PI / 180)
        );

        localizer.init(startPosition);
        actionThread.init();
    }

    @Override
    public void loop() {
        localizer.loop();
        actionThread.loop();
    }
}
