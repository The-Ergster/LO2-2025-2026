//Package importing
package LO2.Teleop;
//Base level imports

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;

//Fun stuff

import codebase.Constants;
import codebase.actions.LaunchAction;
import codebase.actions.SetServoPowerAction;
import codebase.actions.SimultaneousAction;
import codebase.gamepad.Gamepad;
import codebase.geometry.MovementVector;
import codebase.hardware.Motor;
import codebase.movement.mecanum.MecanumDriver;

@TeleOp
public class TeamTeleOp extends OpMode {

    private Gamepad gamepad;
    private MecanumDriver driver;
    private SimultaneousAction actionThread;
    //creates motor classes
    private Motor fl, fr, bl, br;
    private Motor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;


    @Override
    public void init() {
        //defines motors
        fl = new Motor(hardwareMap.get(DcMotorEx.class, "fl"));
        fr = new Motor(hardwareMap.get(DcMotorEx.class, "fr"));
        bl = new Motor(hardwareMap.get(DcMotorEx.class, "bl"));
        br = new Motor(hardwareMap.get(DcMotorEx.class, "br"));

        flywheelRIGHT = new Motor(hardwareMap.get(DcMotorEx.class, "wr"));
        flywheelLEFT = new Motor(hardwareMap.get(DcMotorEx.class, "wl"));
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");

        gamepad = new Gamepad(gamepad1);
        actionThread = new SimultaneousAction();

        //configures direction
        driver = new MecanumDriver(fl,fr,bl,br, Constants.MECANUM_COEFFICIENT_MATRIX);
        LaunchAction.setLaunchActionMotors(loaderServo,flywheelRIGHT,flywheelLEFT);
    }




    //Make sure all variables are in scope.
    @Override
    public void loop() {

        //actual code for movement
        //takes value from joysticks
        driver.setRelativePower(new MovementVector(
                gamepad.leftJoystick.getY(),
                gamepad.leftJoystick.getX(),
                gamepad.rightJoystick.getX()
        ));
        gamepad.loop();
        actionThread.loop();


        if (gamepad.xButton.isPressed()) {
            actionThread.add(new LaunchAction(),true, true);
        }
        if (gamepad.aButton.isPressed()) {
            actionThread.add(new SetServoPowerAction(loaderServo,1),true,true);
        }
        if (gamepad.bButton.isPressed()){
            actionThread.add(new SetServoPowerAction(loaderServo,-1),true,true);
        }



        // Telemetry for movement
        //If you add more buttons add more telemetry so we know whats going through
        //Debug purposes only
        telemetry.addData("Gamepad:", "Left Y: %.2f | Left X: %.2f | Right X: %.2f",
                gamepad.leftJoystick.getY(),
                gamepad.leftJoystick.getX(),
                gamepad.rightJoystick.getX());
        telemetry.update();
    }
}

