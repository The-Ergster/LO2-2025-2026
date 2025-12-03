package org.firstinspires.ftc.teamcode.LO2.Autonomous;


import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@Autonomous
public class AUTObotsRO extends OpMode {

    private DcMotorEx frontLeft, frontRight, backLeft, backRight;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

    }

    public void forward(double x) {


        final double MAX_TICKS_PER_SECOND = 4661;



        //math stuff for movement

        double flPower = (1);
        double blPower = (1);
        double frPower = (1);
        double brPower = (1);


        frontLeft.setVelocity(flPower * MAX_TICKS_PER_SECOND);
        backLeft.setVelocity(blPower * MAX_TICKS_PER_SECOND);
        frontRight.setVelocity(frPower * MAX_TICKS_PER_SECOND);
        backRight.setVelocity(brPower * MAX_TICKS_PER_SECOND);

        sleep((long) (700*x));

        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);

    }


    public void backward(double x) {


        final double MAX_TICKS_PER_SECOND = 4661;



        //math stuff for movement

        double flPower = (-1);
        double blPower = (-1);
        double frPower = (-1);
        double brPower = (-1);


        frontLeft.setVelocity(flPower * MAX_TICKS_PER_SECOND);
        backLeft.setVelocity(blPower * MAX_TICKS_PER_SECOND);
        frontRight.setVelocity(frPower * MAX_TICKS_PER_SECOND);
        backRight.setVelocity(brPower * MAX_TICKS_PER_SECOND);

        sleep((long) (700*x));

        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);

    }



    @Override
    public void loop() {

    }


    @Override
    public void start(){
        telemetry.addLine("Placeholder");

        forward(1);

    }
}
