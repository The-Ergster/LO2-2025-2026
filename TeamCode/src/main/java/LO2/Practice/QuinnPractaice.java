package LO2.Practice; 

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; 

import com.qualcomm.robotcore.hardware.DcMotor; 
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.CRServo; 



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
    }
  @Override
    public void loop(){

    }

  
}
