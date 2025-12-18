package codebase.pathing;

import codebase.Loop;
import codebase.geometry.Point;
import codebase.geometry.Pose;

public interface LocalizerFC <T extends Point> extends Loop {
    void setFieldCentric(T pose);

    T getFieldCentric();

    void init(Pose currentPose);
    boolean isDoneInitializing();
}
