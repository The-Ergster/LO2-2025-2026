package LO2.Practice;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo;

import java.lang.Math;

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
    backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    frontRight.setDirection(DcMotorEx.Direction.FORWARD);
    backRight.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelLeft.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRight.setDirection(DcMotorEx.Direction.REVERSE);
  }
public void driveOmni(double y, double x, double rx){

  final double Max_Ticks_Per_Second = 4661;

  double  maxValue = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
  double flPower = (y + x + rx) / maxValue;
  double frPower = (y - x - rx) / maxValue;
  double blPower = (y - x + rx) / maxValue;
  double brPower = (y + x - rx) / maxValue;
  
  frontLeft.setVelocity(flPower * Max_Ticks_Per_Second);
  backLeft.setVelocity(blPower * Max_Ticks_Per_Second);
  frontRight.setVelocity(frPower * Max_Ticks_Per_Second);
  backRight.setVelocity(brPower * Max_Ticks_Per_Second);
  }
  
@Override
  public void loop(){

    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;
    driveOmni(y, x, rx);

    if (gamepad1.x){
      flywheelLeft.setPower(1);
      flywheelRight.setpower(1);
      try{
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      loaderServo.setPower(1);
    } else if (gamepad1.x){
      //Die
      frontLeft.setPower(1);
      backleft.setPower(1);
      frontBack.setpower(-1);
      backRight.setpower(1);
      flywheelLeft.setPower(-1);
      flywheelRight.setPower(1);
      loaderServo.setPower(-1);
      try{
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      backRight.setpower(-1);
      flywheelLeft.setPower(1);
      flywheelRight.setPower(-1);
      loaderServo.setPower(1);
      try{
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      frontLeft.setPower(0);
      backleft.setPower(0);
      frontBack.setpower(0);
      backRight.setpower(0);
      flywheelLeft.setPower(0);
      flywheelRight.setPower(0);
      loaderServo.setPower(0);
    } else{
      flywheelLeft.setPower(0);
      flywheelRight.setPower(0);
      loaderServo.setpower(0);
      
    }
  }
  
}
