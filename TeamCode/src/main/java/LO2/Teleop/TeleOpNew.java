//Package import
package LO2.Teleop;

//OpMode imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//Hardware Imports
import codebase.gamepad.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

//Action Imports
import codebase.actions.LaunchAction;
import codebase.actions.SimultaneousAction;

//Movement Imports
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;
import codebase.geometry.MovementVector;

//Localization Imports
import codebase.hardware.PinpointModule;
import codebase.pathing.PinpointLocalizer;
import codebase.geometry.FieldPosition;
import codebase.Constants;

@TeleOp
public class TeleOpNew extends OpMode {
    private Gamepad gamepad;
    private MecanumDriver driver;
    private SimultaneousAction actionThread;
    private Motor fl, fr, bl, br;
    private Motor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    private FieldPosition currentPosition;
    private PinpointModule pinpoint;
    private PinpointLocalizer localizer;

    @Override
    public void init() {
        //defines motors using motor class
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        flywheelRIGHT = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flywheelLEFT = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));

        loaderServo = hardwareMap.get(CRServo.class, "ls");

        //creates localizer object using pinpoint odometry computer
        pinpoint = hardwareMap.get(PinpointModule.class,"pinpoint");
        localizer = new PinpointLocalizer(pinpoint,
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD
        );

        gamepad = new Gamepad(gamepad1);
        actionThread = new SimultaneousAction();
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);

        //configures launch motors
        LaunchAction.setLaunchActionMotors(loaderServo, flywheelRIGHT, flywheelLEFT);

        //note: x and y are not updated nor used
        currentPosition = new FieldPosition(0, 0, 0);

        //utils
        gamepad.xButton
                .onPress(() -> actionThread.add(new LaunchAction(), true, true));

        gamepad.aButton
                .whileDown(() -> loaderServo.setPower(1))
                .onRelease(() -> loaderServo.setPower(0));

        gamepad.bButton
                .whileDown(() -> loaderServo.setPower(-1))
                .onRelease(() -> loaderServo.setPower(0));

        gamepad.yButton
                .whileDown(() -> {
                    loaderServo.setPower(-1);
                    flywheelRIGHT.setPower(1);
                    flywheelLEFT.setPower(-1);
                })
                .onRelease(() -> {
                    loaderServo.setPower(0);
                    flywheelRIGHT.setPower(0);
                    flywheelLEFT.setPower(0);
                });
    }

    @Override
    public void loop() {
        gamepad.loop();
        actionThread.loop();

        //gets heading from localizer IMU
        currentPosition.setDirection(localizer.getCurrentPosition().getDirection());
        currentPosition.setX(localizer.getCurrentPosition().getX());
        currentPosition.setY(localizer.getCurrentPosition().getY());

        //takes value from joysticks
        MovementVector vector = new MovementVector(
                gamepad.leftJoystick.getY(),
                gamepad.leftJoystick.getX(),
                gamepad.rightJoystick.getX()
        );
        //parking toggle
        boolean parkingToggled = gamepad.rightBumper.isToggled();
        if (parkingToggled) {
            vector.scalarMultiply(0.5);
        }
        //setting velocity using heading and joysticks
        driver.setAbsolutePower(currentPosition, vector);

        // Telemetry
        //If you add more buttons add more telemetry for debug purposes
        telemetry.addLine("Imagine_Working??");
        telemetry.update();
    }
}
