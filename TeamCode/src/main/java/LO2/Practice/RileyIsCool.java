import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

import java.lang.Math;

@Teleop
public class rileyiscool extends OpMode{
  private DcMotorEx frontLeft, frontRight, backLeft, backRight;
  private DcMotorEx flywheelRight, flywheelLeft;
  private CRServo leaderServo;
}

@Override
public void init() {
  frontLeft = hardwareMap.get(DcMotorEx.class, "fl")
  frontRight = hardwareMap.get(DcMotorEx.class, "fr")
  backLeft = hardwareMap.get(DcMotorEx.class, "bl")
  backRight = hardwareMap.get(DcMotorEx.class, "br")
  wheelLeft = hardwareMap.get(DcMotorEx.class, "wl")
  wheelRight = hardwareMap.get(DcMotorEx.class, "wr")

  frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
  backLeft.setDirection(DcMotorEx.Direction.REVERSE);
  frontRight.setDirection(DcMotorEx.Direction.FORWARD);
  frontRight.setDirection(DcMotorEx.Direction.FORWARD);
  wheelLeft.setDirection.(DcMotor.Direction.FORWARD);
  wheelRight.setDirection.(DcMotor.Direction.REVERSE);
}

public void driveOmni(double x, double y, double rx) {
  final double MTPS = 4661;
  double maxValue = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(rx), 1);
  double flPower = (y + x + rx) / maxValue;
  double blPower = (y - x + rx) / maxValue;
  double frPower = (y - x - rx) / maxValue;
  double brPower = (y + x - rx) / maxValue;
}

@Override
public void loop() {
  
}
