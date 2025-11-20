package org.firstinspires.ftc.teamcode.LO2;
//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Autonomous
public class AutoSafe extends OpMode {
    private DcMotor flywheelRIGHT, flywheelLEFT;
    //Creates Servo Classes
    private CRServo loaderServo;
    @Override
    public void init() {
        flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr");
        flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl");
//        //defines encoders
        loaderServo = hardwareMap.get(CRServo.class, "ls");


        List<String> phrases = new ArrayList<>();

        // Add elements to the list
        phrases.add("Coding: It Works");
        phrases.add("The Universe is gone!!.... Never mind it's still here");
        phrases.add("The Cake Is A Lie!");
        phrases.add("; expected ");
        phrases.add("Shaw!");
        phrases.add("200 killed in rogue servo accident");
        phrases.add("I say potato, you say 'ERROR cannot resolve line'");
        phrases.add("There is no Spoon");
        phrases.add("'Thinking noises'");
        phrases.add("Bring me a shrubbery!");
        phrases.add("Pull The Lever Kronk");
        phrases.add("'gasping noises'");
        phrases.add("'wheel work'");
        phrases.add("robot-core");
        phrases.add("Auto-bots, Roll out!");





        int element = 0;
        Random random = new Random();
        element = random.nextInt(15);

        telemetry.addLine(phrases.get(element));
        telemetry.update();
    }

    public void start(){
        flywheelRIGHT.setPower(-1);
            flywheelLEFT.setPower(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            loaderServo.setPower(1);
    }
    @Override
    public void loop() {

    }
}
