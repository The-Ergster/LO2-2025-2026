package codebase.actions;

import com.qualcomm.robotcore.hardware.CRServo;

public class SetServoPowerAction implements Action{
    private final CRServo servo;
    private final double power;

    public SetServoPowerAction(CRServo servo, double power) {
        this.servo = servo;
        this.power = power;
    }

    @Override
    public void init(){

    }

    @Override
    public boolean isComplete() {
        return servo.getPower() == power;
    }

    @Override
    public void loop() {
        servo.setPower(power);
    }
}
