package org.firstinspires.ftc.teamcode.pathing;

import com.pedropathing.paths.Path;

public class PathManager {
    public enum fieldQuadrant {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
    public enum Destination{
        GATE,
        HUMAN_BEFORE_INTAKING,
        HUMAN_AFTER_INTAKING,
        NEAR_TEAM_GOAL,
        NEAR_OPP_GOAL,
        FAR_ZONE,
        CENTER_FIELD
    }
    public enum Divert {
        NONE,
        RIGHT,
        LEFT
    }
    public PathManager(){

    }
    public Path getPath(Destination destination, fieldQuadrant currentPose, Divert divert){
        return null;
    }

}
