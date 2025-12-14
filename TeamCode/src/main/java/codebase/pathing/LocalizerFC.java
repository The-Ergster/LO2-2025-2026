package codebase.pathing;

import codebase.Loop;
import codebase.geometry.Point;

public interface LocalizerFC <T extends Point> extends Loop {
    void setFieldCentric(T pose);

    T getFieldCentric();

    boolean isDoneInitializing();
}
