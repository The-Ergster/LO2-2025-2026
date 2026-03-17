// Wawa<
package LO2.Practice;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import java.lang.Math;

@TeleOp
public class code extends OpMode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelRight, flywheelLeft;
  private CRServo loaderServo;
}
@Override
  public void init(){
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
    flywheelLeft.setDirection(DcMotorEx.Direction.FORWARD);
    flywheelRight.setDirection(DcMotorEx.Direction.REVERSE);
  }
  public void driveOmni(double x, double y, double rx){
    final double maxtps = 4661;
    double maxValue = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx), 1);
    double flPower = (y + x + rx) / maxValue;
    double blPower = (y - x + rx) / maxValue;
    double frPower = (y - x - rx) / maxValue;
    double brPower = (y + x - rx) / maxValue;

    frontLeft.setVelocity(flPower * maxtps);
    backLeft.setVelocity(blPower * maxtps);
    frontRight.setVelocity(frPower * maxtps);
    backRight.setVelocity(brPower * maxtps);
  }
@Override
  public void loop(){
    double y = gamepad1.left_stick_y;
    double y = gamepad1.left_stick_x;
    double y = gamepad1.right_stick_x;
    driveOmni(x, y, rx);
    if(gamepad1.x){
      flywheelRight.setPower(1);
      flywheelLeft.setPower(1);
      try{
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } else if {
        // Wawa
      } else {
        // Wawa
      }
    }
  }
// >Wawa
