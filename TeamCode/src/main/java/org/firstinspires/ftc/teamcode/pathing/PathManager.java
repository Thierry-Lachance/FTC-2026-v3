package org.firstinspires.ftc.teamcode.pathing;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

public class PathManager {
    public enum FieldQuadrant {
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
        TOP,
        BOTTOM
    }
    public PathManager(){

    }
    public Path getPath(Destination destination, FieldQuadrant currentPose, Divert divert){
        return null;
    }

    public FieldQuadrant getFieldQuadrant(Pose pose){
        if(pose.getX() < 72 && pose.getY() > 72){
            return FieldQuadrant.TOP_LEFT;
        } else if(pose.getX() > 72 && pose.getY() > 72){
            return FieldQuadrant.TOP_RIGHT;
        } else if(pose.getX() < 72 && pose.getY() < 72){
            return FieldQuadrant.BOTTOM_LEFT;
        } else {
            return FieldQuadrant.BOTTOM_RIGHT;
        }
    }

}
