package org.firstinspires.ftc.teamcode.pathing;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Robot;

public class PathManager {
    Robot robot;

    public enum FieldQuadrant {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public enum Destination {
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

    private Pose gatePose;
    private Pose humanBeforeIntakingPose;
    private Pose humanAfterIntakingPose;
    private Pose nearTeamGoalPose;
    private Pose nearOppGoalPose;
    private Pose farZonePose;
    private Pose centerFieldPose;

    public PathManager(Robot robot) {
        this.robot = robot;
        if (robot.teamColor == Robot.TeamColor.RED) {
            gatePose = new Pose(138, 71, 0);
            humanBeforeIntakingPose = new Pose(14, 29.35, -1.633);
            humanAfterIntakingPose = new Pose(14, 15, -1.645);
            nearTeamGoalPose = new Pose(96.5, 95.063, -0.85);
            centerFieldPose = new Pose(71.516, 71.469, -0.85);
            nearOppGoalPose = new Pose(47.469, 95.188, -1.2);
            farZonePose = new Pose(84.563, 16.250, -0.4014);
        }

    }


    public PathChain getPath(Destination destination, Divert divert) {
        switch (destination) {
            case NEAR_TEAM_GOAL:
                switch (getFieldQuadrant(robot.drivetrain.getFollower().getPose())) {//diversion from top right is not available because it will go out of bounds
                    case TOP_LEFT:
                        if (divert == Divert.TOP) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(48, 144), new Pose(96, 144));//ok
                        } else if (divert == Divert.BOTTOM) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(0, 0), new Pose(144, 47));//ok
                        }
                        break;
                    case BOTTOM_LEFT:
                        if (divert == Divert.TOP) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(72, 72), new Pose(0, 144));//ok
                        } else if (divert == Divert.BOTTOM) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(91, 58), new Pose(127, 0));//ok
                        }
                        break;
                    case BOTTOM_RIGHT:
                        if (divert == Divert.TOP) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(24, 24), new Pose(0, 144));//ok
                        } else if (divert == Divert.BOTTOM) {
                            return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(72, 72), new Pose(133, 86));//ok
                        }
                        break;
                }
                return CreateDirectPathFromRobotPoseToTarget(nearTeamGoalPose);
            case NEAR_OPP_GOAL:
                return CreateDirectPathFromRobotPoseToTarget(nearOppGoalPose);
            case HUMAN_BEFORE_INTAKING:
                return CreateDirectPathFromRobotPoseToTarget(humanBeforeIntakingPose);
            case HUMAN_AFTER_INTAKING://not much use for diversion here but we can add it later if needed
                return CreateDirectPathFromRobotPoseToTarget(humanAfterIntakingPose);
            case GATE://TODO
                return CreateDirectPathFromRobotPoseToTarget(gatePose);
            case FAR_ZONE://not much use for diversion as there is not much use to divert from top of the field
                return CreateDirectPathFromRobotPoseToTarget(farZonePose);
            case CENTER_FIELD://not much use for bottom of field diversion here but we can add it later if needed
                switch (getFieldQuadrant(robot.drivetrain.getFollower().getPose())) {
                    case TOP_LEFT:
                        if (divert == Divert.TOP) {
                            return createPathFromRobotPoseAnd1Waypoint(centerFieldPose, new Pose(72, 144));
                        } else if (divert == Divert.BOTTOM) {
                            return createPathFromRobotPoseAnd2Waypoint(centerFieldPose, new Pose(24, 24), new Pose(72, 48));
                        }
                        break;
                    case TOP_RIGHT:
                        if (divert == Divert.TOP) {
                            return createPathFromRobotPoseAnd1Waypoint(centerFieldPose, new Pose(72, 144));
                        } else if (divert == Divert.BOTTOM) {
                            return createPathFromRobotPoseAnd2Waypoint(centerFieldPose, new Pose(120, 24), new Pose(72, 48));
                        }
                        break;
                }
                return CreateDirectPathFromRobotPoseToTarget(centerFieldPose);
        }
        return null;
    }

    public PathChain getPath(Destination destination, Pose robotPose) {
        return getPath(destination, Divert.NONE);
    }

    private FieldQuadrant getFieldQuadrant(Pose pose) {
        if (pose.getX() < 72 && pose.getY() > 72) {
            return FieldQuadrant.TOP_LEFT;
        } else if (pose.getX() > 72 && pose.getY() > 72) {
            return FieldQuadrant.TOP_RIGHT;
        } else if (pose.getX() < 72 && pose.getY() < 72) {
            return FieldQuadrant.BOTTOM_LEFT;
        } else {
            return FieldQuadrant.BOTTOM_RIGHT;
        }
    }

    private PathChain createPathFromRobotPoseAnd2Waypoint(Pose targetPose, Pose waypoint1, Pose waypoint2) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                robot.drivetrain.getFollower()::getPose,
                                waypoint1,
                                waypoint2,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), 0.8))
                .setTranslationalConstraint(3)
                .build();

    }

    private PathChain createPathFromRobotPoseAnd1Waypoint(Pose targetPose, Pose waypoint) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                robot.drivetrain.getFollower()::getPose,
                                waypoint,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), 0.8))
                .setTranslationalConstraint(3)
                .build();

    }

    private PathChain CreateDirectPathFromRobotPoseToTarget(Pose targetPose) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                robot.drivetrain.getFollower()::getPose,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), 0.8))
                .setTranslationalConstraint(3)
                .build();

    }

}
