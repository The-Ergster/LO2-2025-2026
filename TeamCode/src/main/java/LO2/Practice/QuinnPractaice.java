package LO2.Practice; 

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; 

import com.qualcomm.robotcore.hardware.DcMotor; 
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo; 

import java.lang.Math;



@TeleOp
public class QuinnPractaice extends Opmode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelRight, flywheelLeft; 
  private CRServo loaderServo; 

  @Override
    public void init(){
      frontLeft = hardwearMap.get(DcMotorEx.class, "fl");
      frontRight = hardwearMap.get(DcMotorEx.class, "fr");
      backLeft = hardwearMap.get(DcMotorEx.class, "bl");
      backRight = hardwearMap.get(DcMotorEx.class, "br");
      flywheelRight = hardwareMap.get(DcMotorEx.class, "wr");
        flywheelLeft = hardwareMap.get(DcMotorEx.class, "wl");

      frontLeft.setDiection(DcMotorEx.Direction.REVERSE); 
      frontRight.setDiection(DcMotorEx.Direction.FORWARD);
      backLeft.setDiection(DcMotorEx.Direction.REVERSE);
      backRight.setDiection(DcMotorEx.Direction.FORWARD);
      flywheelLeft.setDiection(DcMotorEx.Direction.FORWARD);
      flywheelRight.setDiection(DcMotorEx.Direction.REVERSE);
    }
  public void driveOmni(double y, double rx, double x) {

    final double MTPS = 4661;

    double maxVal = Math.max(Math.abs(y) + Math.abs(rx) + Math.abs(x), 1);
    double flPower = (y + x + rx) / maxValue;
    double blPower = (y - x + rx) / maxValue;
    double frPower = (y - x + rx) / maxValue;
    double brPower = (y + x + rx) / maxValue;

    frontLeft.setVelocity(flPower * MTPS); 
    backLeft.setVelocity(blPower * MTPS);
    frontRight.setVelocity(frPower * MTPS);
    backRight.setVelocity(brPower * MTPS);
  }
  
  @Override
    public void loop(){

      double y = gamepad1.left_stick_y; 
      double rx = gamepad1.right_stick_x;
      double x = gamepad1.left_stick_x;
      driveOmni(y, rx, x); 
      
      if (gamepad1.x) { 
          flywheelRight.setPower(1);
          flywheelLeft.setPower(1);
        // YOU SHOULD KILL YOUR SELF NOW (to the robot)
        try{
           Thread.sleep(1500)
        } catch (InterruptedException e) {
            throw new runtimeException(e);
        }
        loaderServo.setPower(1);
      } else if {
        loaderServo.setPower(-1)
           // GREAD SECOND \\
          // GOD DAM THE SUN \\
        try{
           Thread.sleep(999999999999999999999999) // also GOD DAM THE SUN \\ 
        } catch (InterruptedException e) {
            throw new runtimeException(e);
        }
        loaderServo.setPower(1);
      } else {
        flywheelRight.setPower(0);
          flywheelLeft.setPower(0);
        loaderServo.setPower(0);
      }
      
    }

  
}
