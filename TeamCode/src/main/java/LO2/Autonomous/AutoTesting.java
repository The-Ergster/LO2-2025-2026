package LO2.Autonomous;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import

import org.firstinspires.ftc.robotcore.external.Telemetry;

import codebase.Constants;
import codebase.actions.CustomAction;
import codebase.actions.LaunchAction;
import codebase.actions.MoveToAction;
import codebase.actions.SequentialAction;
import codebase.actions.SleepAction;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.hardware.Motor;
import codebase.hardware.PinpointModule;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizer;

@Autonomous
public class AutoTesting extends OpMode {

    private SequentialAction actionThread;
    private Motor fl, fr, bl, br;
    private MecanumDriver driver;
    private PinpointModule pinpoint;
    private PinpointLocalizer localizer;
    private Telemetry.Item positionDisplay;

    @Override
    public void init() {
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        pinpoint = hardwareMap.get(PinpointModule.class, "pinpoint");
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);
        localizer = new PinpointLocalizer(pinpoint,
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD
        );

        actionThread = new SequentialAction(
                new CustomAction(() -> {
                    driver.setRelativePower(new MovementVector(-0.5, 0, 0));
                }),
                new SleepAction(1050),
                new CustomAction(() -> {
                    driver.stop();
                }),
                new LaunchAction(),
                new CustomAction(() -> {
                    driver.setRelativePower(new MovementVector(0, -0.5, 0));
                }),
                new SleepAction(600),
                new CustomAction(() -> {
                    driver.stop();
                })
        );

        FieldPosition startPosition;
        startPosition = new FieldPosition(0,0,0);

        localizer.init(startPosition);
        actionThread.init();
        positionDisplay = telemetry.addData("pos:",localizer.getCurrentPosition());
    }

    @Override
    public void loop () {
        localizer.loop();
        actionThread.loop();
        positionDisplay.setValue(localizer.getCurrentPosition());
    }
}