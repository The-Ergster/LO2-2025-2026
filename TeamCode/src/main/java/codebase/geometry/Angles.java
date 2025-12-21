package codebase.geometry;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
public final class Angles {
    public static double angleDifference(double fromAngle, double toAngle) {
        double diff = toAngle - fromAngle;

        // Normalize to [-2PI, 2PI]
        diff = diff % (Math.PI * 2);

        // Convert to [-PI, PI] range
        if (diff > Math.PI) {
            diff -= (Math.PI * 2);
        } else if (diff <= -Math.PI) {
            diff += (Math.PI * 2);
        }

        return diff;
    }

    public static double normalizeAngle(double angle) {
        double twoPi = 2.0 * Math.PI;
        angle = angle % twoPi;

        if (angle < 0) {
            angle += twoPi;
        }
        return angle;
    }

    private Angles() {}
}
