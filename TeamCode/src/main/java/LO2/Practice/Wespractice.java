package LO2.Practice;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp
public class Wespractice extends OpMode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelLeft, flywheelRight;
  private CRServo loaderServo;
  
  @Override
  public void init(){
    
    frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
    frontRight = hardwareMap.get(DcMotorEx.class, "fr");
    backLeft = hardwareMap.get(DcMotorEx.class, "bl");
    backRight = hardwareMap.get(DcMotorEx.class, "br");
    flywheelLeft = hardwareMap.get(DcMotorEx.class, "wl");
    flywheelRight = hardwareMap.get(DcMotorEx.class, "wr");

    frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
    baclLeft.setDirection(DcMotorEx.Direction.REVERSE);
    backLeft.setDirection(DcMotorEx.Direction.FORWARD);
    backRight.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelLEFT.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRIGHT.setDirection(DcMotorEx.Direction.REVERSE);
  }
@Override
  public void loop(){

  }
  
}
