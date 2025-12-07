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
import codebase.actions.SequentialAction;
import codebase.actions.CustomAction;
import codebase.actions.SleepAction;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.PinpointLocalizer;

@Autonomous
public class TestSquareBlue extends OpMode {

    private SequentialAction actionThread;
    private Motor frontLeft, frontRight, backLeft, backRight;
    private MecanumDriver driver;
    private PinpointLocalizer localizer;

    @Override
    public void init() {

        frontLeft = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        frontRight = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        backLeft = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        backRight = new Motor(hardwareMap.get(DcMotorEx.class, "br"));

        driver = new MecanumDriver(frontLeft,frontRight, backLeft, backRight, Constants.MECANUM_COEFFICIENT_MATRIX);

        localizer.init(new FieldPosition(0,0,0));

        actionThread = new SequentialAction(
                new CustomAction(() ->{
                    driver.setRelativePower(new MovementVector(-0.5,0,0));
                }),
                new SleepAction(1050),
                new CustomAction(()->{
                    driver.stop();
                }),
                new LaunchAction(),
                new CustomAction(() ->{
                    driver.setRelativePower(new MovementVector(-0.5,0,3));
                }),
                new SleepAction(600),
                new CustomAction(()->{
                    driver.stop();
                })
        );
        actionThread.init();



    }

    @Override
    public void loop() {
        actionThread.loop();
        localizer.loop();
    }
}
