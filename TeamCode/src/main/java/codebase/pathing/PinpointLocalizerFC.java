package codebase.pathing;

import com.qualcomm.hardware.lynx.LynxNackException;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import codebase.geometry.ConversionsFC;
import codebase.geometry.FieldPosition;
import codebase.geometry.MovementVector;
import codebase.geometry.Pose;
import codebase.hardware.PinpointModule;

public class PinpointLocalizerFC implements LocalizerFC<Pose> {

    private final PinpointModule pinpointModule;

    /**
     *
     * The center of rotation of the robot is the point it rotates around when spinning.
     * It can be found by finding the intersection of the two lines made by diagonal wheel pairs.
     *
     * @param pinpointModule The PinPoint device
     * @param xPodOffsetFromCenter The distance in the x-direction (right is positive) of the forward-backward pod from the robot's center of rotation
     * @param xDirection The direction the y-pod (which measures delta in the y direction) is oriented
     * @param yPodOffsetFromCenter The distance in the y-direction (forward is positive) of the right-left pod from the robot's center of rotation
     * @param yDirection The direction the x-pod (which measures delta in the x direction) is oriented
     * @param encoderResolution The number of ticks per mm of the encoders attached to the PinPoint
     */
    public PinpointLocalizerFC(PinpointModule pinpointModule, double xPodOffsetFromCenter, PinpointModule.EncoderDirection xDirection, double yPodOffsetFromCenter, PinpointModule.EncoderDirection yDirection, double encoderResolution) {
        this.pinpointModule = pinpointModule;
        this.pinpointModule.setEncoderResolution(encoderResolution);
        this.pinpointModule.setOffsets(xPodOffsetFromCenter,yPodOffsetFromCenter);
        this.pinpointModule.setEncoderDirections(xDirection, yDirection);
    }

    /**
     *
     * The center of rotation of the robot is the point it rotates around when spinning.
     * It can be found by finding the intersection of the two lines made by diagonal wheel pairs.
     *
     * @param pinpointModule The PinPoint device
     * @param xPodOffsetFromCenter The distance in the x-direction (right is positive) of the forward-backward pod from the robot's center of rotation
     * @param xDirection The direction the y-pod (which measures delta in the y direction) is oriented
     * @param yPodOffsetFromCenter The distance in the y-direction (forward is positive) of the right-left pod from the robot's center of rotation
     * @param yDirection The direction the x-pod (which measures delta in the x direction) is oriented
     * @param pods The type of pods you are using
     *
     *             THIS IS NOT DONE // REVISIT X AND Y AS WE SWITCHED TO FTC FIELD COORDINATE SYSTEM
     */
    public PinpointLocalizerFC(PinpointModule pinpointModule, double xPodOffsetFromCenter, PinpointModule.EncoderDirection xDirection, double yPodOffsetFromCenter, PinpointModule.EncoderDirection yDirection, PinpointModule.GoBildaOdometryPods pods) {
        this.pinpointModule = pinpointModule;
        this.pinpointModule.setEncoderResolution(pods);
        this.pinpointModule.setOffsets(-xPodOffsetFromCenter,yPodOffsetFromCenter);
        this.pinpointModule.setEncoderDirections(xDirection,yDirection);
    }


    @Override
    public void setFieldCentric(Pose pose) {
        pinpointModule.setPosition(Pose.toPose2D(ConversionsFC.convertStandardToField(pose)));
    }

    @Override
    public Pose getFieldCentric() {
        return ConversionsFC.convertFieldToStandard(new Pose(pinpointModule.getPosition()));
    }

    @Override
    public void init(Pose initialPose) {
        setFieldCentric(initialPose);

        try{
            pinpointModule.update();
        }catch (LynxNackException ignored){

        }
    }


    public Pose getVelocity() {
        return ConversionsFC.convertFieldToStandard(
                new Pose(pinpointModule.getVelocity())
        );
    }

    public String status() {
        return pinpointModule.getDeviceStatus().toString();
    }

    public double getFrequency() {
        return pinpointModule.getFrequency();
    }

    @Override
    public void loop() {
        try {
            pinpointModule.update();
        } catch (LynxNackException e) {
        }
    }

    public boolean isDoneInitializing() {
        return pinpointModule.getDeviceStatus() == PinpointModule.DeviceStatus.READY;
    }
}