package org.firstinspires.ftc.teamcode.LO2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//code


@TeleOp
public class FlywheelShadow extends OpMode {

    private DcMotor flywheelUP, flywheelDOWN;
    @Override
    public void init() {

        flywheelUP = hardwareMap.get(DcMotorEx.class, "wu");
        flywheelDOWN = hardwareMap.get(DcMotorEx.class, "wd");
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            flywheelUP.setPower(-1);
            flywheelDOWN.setPower(1);
        } else {
            flywheelUP.setPower(0);
            flywheelDOWN.setPower(0);
        }
    }
}
