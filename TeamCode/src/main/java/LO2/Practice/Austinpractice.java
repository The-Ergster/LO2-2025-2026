LO2.Practice;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo;



@TeleOp
  public class code extends OpMode{
private DcMOtorEx frontLeft, frontRight, backLeft, backRight;
private DcMOtorEx flywheelRight, flywheelLeft;
private CRServo loaderServo;

@Override
    public void init(){
     frontLeft = hardwareMap.get(DcMotorEx.class, "fl")
       frontRight = hardwareMap.get(DcMotorEx.class, "fr")
       backLeft = hardwareMap.get(DcMotorEx.class, "bl")
      backRight = hardwareMap.get(DcMotorEx.class, "br")
       flywheelRIGHT = hardwareMap.get(DcMotorEx.class, "wr")
       flywheelLEFT = hardwareMap.get(DcMotorEx.class, "wl")
frontLeft.setDirection(
  frontRight.setDirection(
  backLeft.setDirection(
backRight.setDirection(
    flywheelRIGHT.setDirection(
    flywheelLEFT.setDirection(
       frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
    }
@Override
    public void loop(){

    }
    
  }
