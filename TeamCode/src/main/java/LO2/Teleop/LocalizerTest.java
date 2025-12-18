package LO2.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import codebase.Constants;
import codebase.gamepad.Gamepad;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.hardware.Motor;
import codebase.hardware.PinpointModule;
import codebase.movement.mecanum.MecanumDriver;
import codebase.pathing.Localizer;
import codebase.pathing.PinpointLocalizer;

@TeleOp
public class LocalizerTest extends OpMode {
    private Motor fl,fr,bl,br;

    private MecanumDriver driver;
    private Gamepad gamepad;

    private Localizer localizer;
    private Telemetry.Item positionDisplay;

    @Override
    public void init(){
        this.fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        this.fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        this.br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));
        this.bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));

        driver = new MecanumDriver(fl,fr,bl,br, Constants.MECANUM_COEFFICIENT_MATRIX,Constants.MAX_WHEEL_VELOCITY_INCHES_PER_SECOND);
        gamepad = new Gamepad(gamepad1);

        localizer = new PinpointLocalizer(hardwareMap.get(PinpointModule.class,"pinpoint"),
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        localizer.init(new FieldPosition(0,0,0));
        positionDisplay = telemetry.addData("pos:",localizer.getCurrentPosition());
    }

    @Override
    public void loop(){
        positionDisplay.setValue(localizer.getCurrentPosition());
        localizer.loop();
        driver.setRelativePower(new MovementVector(gamepad.leftJoystick.getY(),gamepad.leftJoystick.getX(),gamepad.rightJoystick.getX()));
        gamepad.loop();
    }
}
