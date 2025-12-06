package codebase.actions;

import com.qualcomm.robotcore.hardware.CRServo;

import codebase.hardware.Motor;

public class LaunchAction extends SequentialAction {

    private static CRServo loaderServo;
    private static Motor flywheelRIGHT;
    private static Motor flywheelLEFT;

    public LaunchAction() {
        super(
                new SimultaneousAction(
                        new SetMotorPowerAction(flywheelRIGHT,1),
                        new SetMotorPowerAction(flywheelLEFT, 1)
                ),
                new SleepAction(500),
                new StartServoRotationAction(loaderServo),
                new SleepAction(6767),
                new SimultaneousAction(
                        new SetMotorPowerAction(flywheelRIGHT,0),
                        new SetMotorPowerAction(flywheelLEFT,0),
                        new StopServoRotationAction(loaderServo)
                )
        );
    }
    public static void setLaunchActionMotors(CRServo loaderServo,Motor flywheelRIGHT, Motor flywheelLEFT){
        LaunchAction.loaderServo = loaderServo;
        LaunchAction.flywheelRIGHT = flywheelRIGHT;
        LaunchAction.flywheelLEFT = flywheelLEFT;
    }
}
