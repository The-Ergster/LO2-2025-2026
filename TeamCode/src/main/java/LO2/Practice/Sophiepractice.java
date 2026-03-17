package LO2.Practice;

//Base level imports
import con.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.robotcore.hardware.CRServo;

import java.lang.Math;

@TeleOp
public class Sophiepractice extends OpMode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelRight, flywheelLeft;
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
    frontRight.setDirection(DcMotorEx.Direction.FORWARD);
    backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    backRight.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelLeft.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRight.setDirection(DcMotorEx.Direction.FORWARD);
  }

public void driveOmni(double x, double y, double rx){

  final double MTPS = 4661;

  double maxValue = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rx), 1);
  double flPower = (y + x + rx) / maxValue;
  double blPower = (y - x + rx) / maxValue;
  double frPower = (y - x - rx) / maxValue;
  double brPower = (y + x - rx) / maxValue;

  frontLeft.setVelocity(flPower * MTPS);
  frontRight.setVelocity(frPower * MTPS);
  backLeft.setVelocity(blPower * MTPS);
  backRight.setVelocity(brPower * MTPS);
}

@Override
  public void loop(){

    double x = gamepad1.left_stick_x;
    double y = gamepad1.left_stick_y;
    double rx = gamepad1.right_stick_x;
    driveOmni(x, y, rx);

    if (gamepad1.x){
      flywheelRight.setPower(1);
      flywheelLeft.setPower(1);
      try{
        Thread.sleep(1500);
      } catch (InterruptedEXception e) {
        throw new RuntimeException(e);
      }
      loaderServo.setPower(1);
    } else if (gamepad1.x){
      // copied elliot‽ 
      loaderServo.setPower(1);
      try{
        Thread.sleep(10000000000000);
      } catch (InterruptedEXception e) {
        throw new RuntimeException(e);
      }
      loaderServo.setPower(-1);
    } else {
      flywheelRight.setPoower(0);
      flywheelLeft.setPower(0);
      loaderServo.setPower(0);
    }
  }


}

