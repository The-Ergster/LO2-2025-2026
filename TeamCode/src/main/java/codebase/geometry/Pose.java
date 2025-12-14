package codebase.geometry;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Pose extends Point {
    protected double headingRadians;

    public Pose(double y, double x, double heading, AngleUnit angleUnit) {
        super(x,y);
        this.headingRadians = angleUnit.toRadians(heading);
    }

    public Pose(Pose2D pose2D) {
        super(pose2D.getX(DistanceUnit.INCH), pose2D.getY(DistanceUnit.INCH));
        this.headingRadians = pose2D.getHeading(AngleUnit.RADIANS);
    }

    public void setHeading(double heading, AngleUnit angleUnit) {
        this.headingRadians = angleUnit.toRadians(heading);
    }

    public double getHeading(AngleUnit angleUnit) {
        return angleUnit.fromRadians(this.headingRadians);
    }

    public Pose add(Pose pose) {
        return new Pose(
                this.y + pose.y,
                this.x + pose.x,
                this.headingRadians + pose.headingRadians,
                AngleUnit.RADIANS
        );
    }

    public Pose subtract(Pose pose) {
        return new Pose(
                this.y - pose.y,
                this.x - pose.x,
                this.headingRadians - pose.headingRadians,
                AngleUnit.RADIANS
        );
    }

    public static Pose2D toPose2D(Pose pose) {
        return new Pose2D(DistanceUnit.INCH, pose.x, pose.y, AngleUnit.RADIANS, pose.getHeading(AngleUnit.RADIANS));
    }

}
