package org.firstinspires.ftc.teamcode.LO2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class ahhh extends OpMode {

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init() {

        telemetry.addLine("Initiated");
        telemetry.update();
    }

    @Override
    public void loop() {

    }
}
