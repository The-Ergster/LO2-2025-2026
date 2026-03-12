package LO2.Practice;

//Base level imports
import con.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.robotcore.hardware.CRServo;


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
    frontRightt.setDirection(DcMotorEx.Direction.FORWARD);
    backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    backRight.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelLeft.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRight.setDirection(DcMotorEx.Direction.FORWARD);
  }

@Override
  public void loop(){
    
  }


}

