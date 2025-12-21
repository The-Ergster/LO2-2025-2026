package codebase.geometry;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class FieldPosition extends Point {

    /**
     * The direction of the field position defined as radians counterclockwise from directly to the right of the field
     */
    public double direction;

    public FieldPosition(double x, double y, double direction) {
        super(x, y);
        this.direction = direction;
    }

    public void setDirection(double yaw) {
        this.direction = yaw;
    }

    public double getDirection(){
        return this.direction;
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("(%f, %f, %f) (in, in, rad)", x, y, direction);
    }
}