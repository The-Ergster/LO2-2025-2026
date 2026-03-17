package LO2.Practice;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo;

import java.lang.Math;

@TeleOp
public class jackpractice extends OpMode{
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
public void driveOmni(double y, double rx, double x){

  final double MTPS = 4661;

  double maxValue = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
  double flpower = (y + x + rx) / maxValue;
  double blpower = (y - x + rx) / maxValue;
  double frpower = (y - x - rx) / maxValue;
  double brpower = (y + x - rx) / maxValue;
  
  frontLeft.setVelocity(flPower * MTPS);
  frontRight.setVelocity(frPower * MTPS);
  backLeft.setVelocity(blPower * MTPS);
  backRight.setVelocity(brPower * MTPS);
}  
  
  @Override
  public void loop() {
   
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;
    driveOmni(y, x, rx);

    if (gamepad1.x){
      flywheelRight.setPower(1);
      flywheelLeft.setPower(1);
      try{
        Thread.sleep(1500)
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }  
      loaderServo.setPower(1)
    } else if (gamepad1.y){
     //Makes the flywheel motors go in opposite directions
      try{
        Thread.sleep(1500)
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } 
      flywheelLeft.setPower(-1);
      flywheelRight.setPower(1);
  } else{
    flywheelRight.setPower(0);
    flywheelLeft.setPower(0);
    loaderServo.setPower(0); 
  }
}    
