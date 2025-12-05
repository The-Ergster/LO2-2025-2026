package codebase.actions;

import com.qualcomm.robotcore.hardware.CRServo;

public class StartServoRotationAction implements Action{
    private final CRServo servo;

    public StartServoRotationAction(CRServo servo) {
        this.servo = servo;
    }

    @Override
    public void init(){

    }

    @Override
    public boolean isComplete() {
        return servo.getPower() == 1;
    }

    @Override
    public void loop() {
        servo.setPower(1);
    }
}
