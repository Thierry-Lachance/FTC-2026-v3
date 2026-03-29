package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;

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

    }

    Robot robot;
    Action[] actionList;

    public Autonomous(Robot robot, Action[] actionList) {
        this.robot = robot;
        this.actionList = actionList;

    }

    public void run() {
        for (Action action : actionList) {
            switch (action) {
                case SHOOT_BALL_CLOSE:
                    robot.automatedAction.shootClose();
                    break;
                case SHOOT_BALL_CLOSE_PATTERN:
                    robot.automatedAction.shootClosePattern();
                    break;
                case SHOOT_BALL_FAR:
                    robot.automatedAction.shootFar();
                    break;
                case PICK_LINE_1:
                    robot.automatedAction.intakeLine1();
                    break;
                case PICK_LINE_2:
                    robot.automatedAction.intakeLine2();
                    break;
                case PICK_LINE_3:
                    robot.automatedAction.intakeLine3();
                    break;
                case PICK_HUMAN_PLAYER:
                    break;
                case OPEN_GATE:
                    robot.automatedAction.openGate();
                    break;
                case PARK_INSIDE:
                    robot.automatedAction.parkInside();
                    break;
                case PARK_OUTSIDE:
                    robot.automatedAction.parkOutside();
                    break;
                case PARK_GATE:
                    robot.automatedAction.parkGate();
                    break;

            }
        }
    }
}
