package codebase.geometry;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
public final class Angles {
    public static double angleDifference(double fromAngle, double toAngle, AngleUnit angleUnit) {
        fromAngle = angleUnit.toDegrees(fromAngle);
        toAngle = angleUnit.toDegrees(toAngle);
        double diff = toAngle - fromAngle;

        // Normalize to [-2PI (-360), 2PI (360)]
        diff = diff % 360;

        // Convert to [-PI (-180), PI (180)] range
        if (diff > 180) {
            diff -= 360;
        } else if (diff <= 180) {
            diff += 360;
        }

        return angleUnit.fromDegrees(diff);
    }

    private Angles() {}
}
