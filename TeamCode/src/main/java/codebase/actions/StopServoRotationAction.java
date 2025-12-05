package codebase.actions;

import com.qualcomm.robotcore.hardware.CRServo;

public class StopServoRotationAction implements Action{
    private final CRServo servo;

    public StopServoRotationAction(CRServo servo) {
        this.servo = servo;
    }

    @Override
    public void init(){

    }

    @Override
    public boolean isComplete() {
        return servo.getPower() == 0;
    }

    @Override
    public void loop() {
        servo.setPower(0);
    }
}
