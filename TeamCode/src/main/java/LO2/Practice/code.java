package LO2.Practice;

//Base level imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//Motor Import
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
//Servo Import
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp
public class code extends OpMode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelRight, flywheelLeft;
  private CRServo loaderServo;

@Override
  public void init(){
    //bloodyNose = shawn.elbow(Human.mammal, "Elliot");
    frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
    frontRight = hardwareMap.get(DcMotorEx.class, "fr");
    backLeft = hardwareMap.get(DcMotorEx.class, "bl");
    backRight = hardwareMap.get(DcMotorEx.class, "br");
    flywheelRight = hardwareMap.get(DcMotorEx.class, "wr");
    flywheelLeft = hardwareMap.get(DcMotorEx.class, "wl");

    frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
    backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    frontRight.setDirection(DcMotorEx.Direction.FORWARD);
    backRight.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelLEFT.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRIGHT.setDirection(DcMotorEx.Direction.REVERSE);
  }
@Override
  public void loop(){
    
  }
  
}
  

