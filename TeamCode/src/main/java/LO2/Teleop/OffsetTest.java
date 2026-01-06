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
import codebase.pathing.PinpointLocalizer;

@TeleOp
public class OffsetTest extends OpMode {
    private Motor fl,fr,bl,br;

    private MecanumDriver driver;
    private Gamepad gamepad;

    private PinpointModule pinpoint;
    private PinpointLocalizer localizer;
    private Telemetry.Item positionDisplay;

    @Override
    public void init() {
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"),1200,2.5);
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"),1200,2.5);
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"),1200,2.5);
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"),1200,2.5);
        gamepad = new Gamepad(gamepad1);
        pinpoint = hardwareMap.get(PinpointModule.class,"pinpoint");
        driver = new MecanumDriver(fl, fr, bl, br, Constants.MECANUM_COEFFICIENT_MATRIX);
        localizer = new PinpointLocalizer(pinpoint,
                Constants.PINPOINT_X_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                Constants.PINPOINT_Y_OFFSET, PinpointModule.EncoderDirection.FORWARD,
                PinpointModule.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        localizer.init(new FieldPosition(0,0,0));

        positionDisplay = telemetry.addData("Offsets:","X.in %.2f | Y.in %.2f",
                localizer.getXOffset(pinpoint),
                localizer.getYOffset(pinpoint)
        );
        telemetry.update();
    }

    @Override
    public void loop(){
        localizer.loop();
        positionDisplay.setValue(localizer.getCurrentPosition());
        gamepad.loop();

        driver.setRelativePower(new MovementVector(0,0, gamepad.rightJoystick.getX()));
    }
}
