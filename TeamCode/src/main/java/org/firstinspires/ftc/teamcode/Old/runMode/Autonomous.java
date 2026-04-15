package org.firstinspires.ftc.teamcode.Old.runMode;

import org.firstinspires.ftc.teamcode.Old.Robot;
import org.firstinspires.ftc.teamcode.Old.pathing.PathManager;

public class Autonomous {
    public enum Action {
        SHOOT_BALL_CLOSE_PATTERN,
        SHOOT_BALL_CLOSE,
        SHOOT_BALL_FAR,
        PICK_LINE_1,
        PICK_LINE_2,
        PICK_LINE_3,
        PICK_HUMAN_PLAYER,
        OPEN_GATE,
        PARK_INSIDE,
        PARK_OUTSIDE,
        PARK_GATE,
        START_NEAR_TEAM_GOAL,
        START_FAR

    }

    Robot robot;
    Action[] actionList;

    public Autonomous(Robot robot, Action[] actionList) {
        this.robot = robot;
        this.actionList = actionList;
        robot.kicker.engageKicker();
        robot.setPatternInsideRobot(Robot.ColorPattern.GPP);

    }

    public void run() {
        for (Action action : actionList) {
            switch (action) {
                case SHOOT_BALL_CLOSE:
                    robot.automatedAction.shootCloseAuto();
                    break;
                case SHOOT_BALL_CLOSE_PATTERN:
                    robot.automatedAction.shootClosePatternAuto();
                    break;
                case SHOOT_BALL_FAR:
                    robot.automatedAction.shootFarAuto();
                    break;
                case PICK_LINE_1:
                    robot.automatedAction.intakeLine1Auto();
                    break;
                case PICK_LINE_2:
                    robot.automatedAction.intakeLine2Auto();
                    break;
                case PICK_LINE_3:
                    robot.automatedAction.intakeLine3Auto();
                    break;
                case PICK_HUMAN_PLAYER:
                    break;
                case OPEN_GATE:
                    robot.automatedAction.openGateAuto();
                    break;
                case PARK_INSIDE:
                    robot.automatedAction.parkInsideAuto();
                    break;
                case PARK_OUTSIDE:
                    robot.automatedAction.parkOutsideAuto();
                    break;
                case PARK_GATE:
                    robot.automatedAction.parkGateAuto();
                    break;
                case START_NEAR_TEAM_GOAL:
                    robot.drivetrain.setStartingPose(robot.pathManager.getStartingPose(PathManager.StartingPosition.NEAR_TEAM_GOAL));
                    break;
                case START_FAR:
                    robot.drivetrain.setStartingPose(robot.pathManager.getStartingPose(PathManager.StartingPosition.FAR_ZONE));
                    break;

            }
        }
    }
}
