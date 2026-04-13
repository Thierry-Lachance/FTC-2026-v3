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

    public enum DestinationTeleop {
        GATE,
        HUMAN_BEFORE_INTAKING,
        HUMAN_AFTER_INTAKING,
        NEAR_TEAM_GOAL,
        NEAR_OPP_GOAL,
        FAR_ZONE,
        CENTER_FIELD,

    }

    public enum DestinationAuto {
        PARK_GATE,
        PARK_INSIDE,
        PARK_OUTSIDE,
        PRE_LINE_1,
        PRE_LINE_2,
        PRE_LINE_3,
        POST_LINE_1,
        POST_LINE_2,
        POST_LINE_3,
        GATE_AUTO,
        NEAR_TEAM_GOAL,
        FAR_ZONE
    }

    public enum StartingPosition {
        NEAR_TEAM_GOAL,
        FAR_ZONE
    }

    public enum Divert {
        NONE,
        TOP,
        BOTTOM
    }

    public enum ConstraintLevel {
        HIGH_PRECISION,
        LOW_PRECISION,
        SHORT_PRECISION
    }

    private Pose gatePose;
    private Pose nearTeamGoalPose;
    private Pose farZonePose;

    private Pose nearOppGoalPose;
    private Pose centerFieldPose;
    private Pose humanBeforeIntakingPose;
    private Pose humanAfterIntakingPose;

    private Pose parkGatePose;
    private Pose parkInsidePose;
    private Pose parkOutsidePose;
    private Pose preLine1Pose;
    private Pose preLine2Pose;
    private Pose preLine3Pose;
    private Pose postLine1Pose;
    private Pose postLine2Pose;
    private Pose postLine3Pose;
    private Pose gatePoseAuto;


    private final double[] highPrecisionConstraints = new double[]{0.05, 0.0, 0.03, 1.0, 10000, 5, 1.0};//translational, velocity, heading, tValue, timeout, brakingStrength
    private final double[] lowPrecisionConstraints = new double[]{0.25, 0.0, 0.075, 0.9, 10000, 5, 1.05};
    private final double[] shortPrecisionConstraints = new double[]{0.05, 0.0, 0.03, 1.0, 10000, 18, 3.0};


    public PathManager(Robot robot) {
        this.robot = robot;
        if (robot.teamColor == Robot.TeamColor.RED) {
            //general poses
            gatePose = new Pose(138, 71, 0);
            nearTeamGoalPose = new Pose(96.5, 95.063, -0.85);
            farZonePose = new Pose(84.563, 16.250, -0.4014);

            //teleop specific poses
            humanBeforeIntakingPose = new Pose(14, 29.35, -1.633);
            humanAfterIntakingPose = new Pose(14, 15, -1.645);
            centerFieldPose = new Pose(71.516, 71.469, -0.85);
            nearOppGoalPose = new Pose(47.469, 95.188, -1.2);

            //autonomous specific poses
            parkGatePose = new Pose(115, 70, 0);
            parkInsidePose = new Pose(91, 126, -1.208);
            parkOutsidePose = new Pose(105, 45, 0);
            preLine1Pose = new Pose(111, 84, 0);
            preLine2Pose = new Pose(111, 62, 0);
            preLine3Pose = new Pose(111, 38, 0);
            postLine1Pose = new Pose(138, 84, 0);
            postLine2Pose = new Pose(145, 56, 0);
            postLine3Pose = new Pose(145, 36, 0);
            gatePoseAuto = new Pose(120, 78, 0);
        } else {
            //todo add blue side poses
        }

    }


    public PathChain getPathTeleop(DestinationTeleop destinationTeleop, Divert divert) {
        if (robot.teamColor == Robot.TeamColor.RED) {
            switch (destinationTeleop) {
                case NEAR_TEAM_GOAL:
                    switch (getFieldQuadrant(robot.drivetrain.getFollower().getPose())) {//diversion from top right is not available because it will go out of bounds
                        case TOP_LEFT:
                            if (divert == Divert.TOP) {
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(48, 144), new Pose(96, 144), ConstraintLevel.HIGH_PRECISION);
                            } else if (divert == Divert.BOTTOM) {
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(0, 0), new Pose(144, 47), ConstraintLevel.HIGH_PRECISION);
                            }
                            break;
                        case BOTTOM_LEFT:
                            if (divert == Divert.TOP) {
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(72, 72), new Pose(0, 144), ConstraintLevel.HIGH_PRECISION);
                            } else if (divert == Divert.BOTTOM) {
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(91, 58), new Pose(127, 0), ConstraintLevel.HIGH_PRECISION);
                            }
                            break;
                        case BOTTOM_RIGHT:
                            if (divert == Divert.TOP) {
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(24, 24), new Pose(0, 144), ConstraintLevel.HIGH_PRECISION);
                            } else if (divert == Divert.BOTTOM) {//todo check if diversion from bottom is mandatory
                                return createPathFromRobotPoseAnd2Waypoint(nearTeamGoalPose, new Pose(72, 72), new Pose(133, 86), ConstraintLevel.HIGH_PRECISION);
                            }
                            break;
                    }
                    return CreateDirectPathFromRobotPoseToTarget(nearTeamGoalPose, ConstraintLevel.HIGH_PRECISION);
                case NEAR_OPP_GOAL://TODO
                    return CreateDirectPathFromRobotPoseToTarget(nearOppGoalPose, ConstraintLevel.HIGH_PRECISION);
                case HUMAN_BEFORE_INTAKING://TODO
                    return CreateDirectPathFromRobotPoseToTarget(humanBeforeIntakingPose, ConstraintLevel.HIGH_PRECISION);
                case HUMAN_AFTER_INTAKING://not much use for diversion here but we can add it later if needed
                    return CreateDirectPathFromRobotPoseToTarget(humanAfterIntakingPose, ConstraintLevel.LOW_PRECISION);
                case GATE://TODO
                    return CreateDirectPathFromRobotPoseToTarget(gatePose, ConstraintLevel.LOW_PRECISION);
                case FAR_ZONE://not much use for diversion as there is not much use to divert from top of the field
                    return CreateDirectPathFromRobotPoseToTarget(farZonePose, ConstraintLevel.HIGH_PRECISION);
                case CENTER_FIELD://not much use for bottom of field diversion here but we can add it later if needed
                    switch (getFieldQuadrant(robot.drivetrain.getFollower().getPose())) {
                        case TOP_LEFT:
                            if (divert == Divert.TOP) {
                                return createPathFromRobotPoseAnd1Waypoint(centerFieldPose, new Pose(72, 144), ConstraintLevel.HIGH_PRECISION);
                            } else if (divert == Divert.BOTTOM) {
                                return createPathFromRobotPoseAnd2Waypoint(centerFieldPose, new Pose(24, 24), new Pose(72, 48), ConstraintLevel.HIGH_PRECISION);
                            }
                            break;
                        case TOP_RIGHT:
                            if (divert == Divert.TOP) {
                                return createPathFromRobotPoseAnd1Waypoint(centerFieldPose, new Pose(72, 144), ConstraintLevel.HIGH_PRECISION);
                            } else if (divert == Divert.BOTTOM) {
                                return createPathFromRobotPoseAnd2Waypoint(centerFieldPose, new Pose(120, 24), new Pose(72, 48), ConstraintLevel.HIGH_PRECISION);
                            }
                            break;
                    }
                    return CreateDirectPathFromRobotPoseToTarget(centerFieldPose, ConstraintLevel.HIGH_PRECISION);

            }
        } else {
            switch (destinationTeleop) {//TODO add diversion for blue side
                case NEAR_TEAM_GOAL:
                    return CreateDirectPathFromRobotPoseToTarget(nearTeamGoalPose, ConstraintLevel.HIGH_PRECISION);
                case NEAR_OPP_GOAL:
                    return CreateDirectPathFromRobotPoseToTarget(nearOppGoalPose, ConstraintLevel.HIGH_PRECISION);
                case HUMAN_BEFORE_INTAKING:
                    return CreateDirectPathFromRobotPoseToTarget(humanBeforeIntakingPose, ConstraintLevel.HIGH_PRECISION);
                case HUMAN_AFTER_INTAKING:
                    return CreateDirectPathFromRobotPoseToTarget(humanAfterIntakingPose, ConstraintLevel.LOW_PRECISION);
                case GATE:
                    return CreateDirectPathFromRobotPoseToTarget(gatePose, ConstraintLevel.LOW_PRECISION);
                case FAR_ZONE:
                    return CreateDirectPathFromRobotPoseToTarget(farZonePose, ConstraintLevel.HIGH_PRECISION);
                case CENTER_FIELD:
                    return CreateDirectPathFromRobotPoseToTarget(centerFieldPose, ConstraintLevel.HIGH_PRECISION);

            }
        }
        return null;
    }

    public PathChain getPathAuto(DestinationAuto destinationAuto) {//TODO modify start pose of path based on last action and add auto diversions
        if (robot.teamColor == Robot.TeamColor.RED) {
            switch (destinationAuto) {
                case PARK_GATE:
                    return CreateDirectPathFromRobotPoseToTarget(parkGatePose, ConstraintLevel.HIGH_PRECISION);
                case PARK_INSIDE:
                    return CreateDirectPathFromRobotPoseToTarget(parkInsidePose, ConstraintLevel.HIGH_PRECISION);
                case PARK_OUTSIDE:
                    return CreateDirectPathFromRobotPoseToTarget(parkOutsidePose, ConstraintLevel.HIGH_PRECISION);
                case PRE_LINE_1:
                    return CreateDirectPathFromRobotPoseToTarget(preLine1Pose, ConstraintLevel.SHORT_PRECISION, 0.1);
                case PRE_LINE_2:
                    return CreateDirectPathFromRobotPoseToTarget(preLine2Pose, ConstraintLevel.SHORT_PRECISION, 0.1);
                case PRE_LINE_3:
                    return CreateDirectPathFromRobotPoseToTarget(preLine3Pose, ConstraintLevel.SHORT_PRECISION, 0.1);
                case POST_LINE_1:
                    return CreateDirectPathFromRobotPoseToTargetConstantHeading(postLine1Pose, ConstraintLevel.HIGH_PRECISION);
                case POST_LINE_2:
                    return CreateDirectPathFromRobotPoseToTargetConstantHeading(postLine2Pose, ConstraintLevel.HIGH_PRECISION);
                case POST_LINE_3:
                    return CreateDirectPathFromRobotPoseToTargetConstantHeading(postLine3Pose, ConstraintLevel.HIGH_PRECISION);
                case GATE_AUTO:
                    return CreateDirectPathFromRobotPoseToTarget(gatePoseAuto, ConstraintLevel.HIGH_PRECISION);
                case NEAR_TEAM_GOAL:
                    return CreateDirectPathFromRobotPoseToTarget(nearTeamGoalPose, ConstraintLevel.HIGH_PRECISION);
                case FAR_ZONE:
                    return CreateDirectPathFromRobotPoseToTarget(farZonePose, ConstraintLevel.HIGH_PRECISION);
            }
        } else {
            switch (destinationAuto) {
                case PARK_GATE:
                    return CreateDirectPathFromRobotPoseToTarget(parkGatePose, ConstraintLevel.HIGH_PRECISION);
                case PARK_INSIDE:
                    return CreateDirectPathFromRobotPoseToTarget(parkInsidePose, ConstraintLevel.HIGH_PRECISION);
                case PARK_OUTSIDE:
                    return CreateDirectPathFromRobotPoseToTarget(parkOutsidePose, ConstraintLevel.HIGH_PRECISION);
                case PRE_LINE_1:
                    return CreateDirectPathFromRobotPoseToTarget(preLine1Pose, ConstraintLevel.HIGH_PRECISION);
                case PRE_LINE_2:
                    return CreateDirectPathFromRobotPoseToTarget(preLine2Pose, ConstraintLevel.HIGH_PRECISION);
                case PRE_LINE_3:
                    return CreateDirectPathFromRobotPoseToTarget(preLine3Pose, ConstraintLevel.HIGH_PRECISION);
                case POST_LINE_1:
                    return CreateDirectPathFromRobotPoseToTarget(postLine1Pose, ConstraintLevel.HIGH_PRECISION);
                case POST_LINE_2:
                    return CreateDirectPathFromRobotPoseToTarget(postLine2Pose, ConstraintLevel.HIGH_PRECISION);
                case POST_LINE_3:
                    return CreateDirectPathFromRobotPoseToTarget(postLine3Pose, ConstraintLevel.HIGH_PRECISION);
                case GATE_AUTO:
                    return CreateDirectPathFromRobotPoseToTarget(gatePoseAuto, ConstraintLevel.HIGH_PRECISION);
                case NEAR_TEAM_GOAL:
                    return CreateDirectPathFromRobotPoseToTarget(nearTeamGoalPose, ConstraintLevel.HIGH_PRECISION);
                case FAR_ZONE:
                    return CreateDirectPathFromRobotPoseToTarget(farZonePose, ConstraintLevel.HIGH_PRECISION);

            }
        }
        return null;
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

    private PathChain createPathFromRobotPoseAnd2Waypoint(Pose targetPose, Pose waypoint1, Pose waypoint2, ConstraintLevel constraintLevel) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                robot.drivetrain.getFollower()::getPose,
                                waypoint1,
                                waypoint2,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), 0.5))
                .setTranslationalConstraint(getConstraint(constraintLevel)[0])
                .setVelocityConstraint(getConstraint(constraintLevel)[1])
                .setHeadingConstraint(getConstraint(constraintLevel)[2])
                .setTValueConstraint(getConstraint(constraintLevel)[3])
                .setTimeoutConstraint(getConstraint(constraintLevel)[4])
                .setBrakingStrength(getConstraint(constraintLevel)[5])
                .setBrakingStart(getConstraint(constraintLevel)[6])
                .build();

    }

    private PathChain createPathFromRobotPoseAnd1Waypoint(Pose targetPose, Pose waypoint, ConstraintLevel constraintLevel) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierCurve(
                                robot.drivetrain.getFollower()::getPose,
                                waypoint,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), 0.5))
                .setTranslationalConstraint(getConstraint(constraintLevel)[0])
                .setVelocityConstraint(getConstraint(constraintLevel)[1])
                .setHeadingConstraint(getConstraint(constraintLevel)[2])
                .setTValueConstraint(getConstraint(constraintLevel)[3])
                .setTimeoutConstraint(getConstraint(constraintLevel)[4])
                .setBrakingStrength(getConstraint(constraintLevel)[5])
                .setBrakingStart(getConstraint(constraintLevel)[6])
                .build();

    }

    private PathChain CreateDirectPathFromRobotPoseToTarget(Pose targetPose, ConstraintLevel constraintLevel) {
        return CreateDirectPathFromRobotPoseToTarget(targetPose, constraintLevel, 0.5);
    }

    private PathChain CreateDirectPathFromRobotPoseToTargetConstantHeading(Pose targetPose, ConstraintLevel constraintLevel) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                robot.drivetrain.getFollower()::getPose,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.constant(targetPose.getHeading()))
                .setTranslationalConstraint(getConstraint(constraintLevel)[0])
                .setVelocityConstraint(getConstraint(constraintLevel)[1])
                .setHeadingConstraint(getConstraint(constraintLevel)[2])
                .setTValueConstraint(getConstraint(constraintLevel)[3])
                .setTimeoutConstraint(getConstraint(constraintLevel)[4])
                .setBrakingStrength(getConstraint(constraintLevel)[5])
                .setBrakingStart(getConstraint(constraintLevel)[6])
                .build();

    }

    private PathChain CreateDirectPathFromRobotPoseToTarget(Pose targetPose, ConstraintLevel constraintLevel, double endT) {
        return robot.drivetrain.getFollower().pathBuilder()
                .addPath(
                        new BezierLine(
                                robot.drivetrain.getFollower()::getPose,
                                targetPose
                        )
                )
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.drivetrain.getFollower()::getHeading, targetPose.getHeading(), endT))
                .setTranslationalConstraint(getConstraint(constraintLevel)[0])
                .setVelocityConstraint(getConstraint(constraintLevel)[1])
                .setHeadingConstraint(getConstraint(constraintLevel)[2])
                .setTValueConstraint(getConstraint(constraintLevel)[3])
                .setTimeoutConstraint(getConstraint(constraintLevel)[4])
                .setBrakingStrength(getConstraint(constraintLevel)[5])
                .setBrakingStart(getConstraint(constraintLevel)[6])
                .build();

    }

    private double[] getConstraint(ConstraintLevel constraintLevel) {
        if (constraintLevel == ConstraintLevel.HIGH_PRECISION) {
            return highPrecisionConstraints;
        } else if (constraintLevel == ConstraintLevel.SHORT_PRECISION) {
            return shortPrecisionConstraints;
        } else {
            return lowPrecisionConstraints;
        }
    }


    public Pose getStartingPose(StartingPosition startingPosition) {
        if (robot.teamColor == Robot.TeamColor.RED) {
            switch (startingPosition) {
                case NEAR_TEAM_GOAL:
                    return new Pose(140.6, 108.8, 0);
                case FAR_ZONE:
                    return new Pose(0, 0, 0);//TODO create these poses
            }
        } else {
            switch (startingPosition) {
                case NEAR_TEAM_GOAL://TODO create these poses
                    return new Pose(0, 0, 0);
                case FAR_ZONE:
                    return new Pose(0, 0, 0);
            }
        }
        return null;
    }


}
