package org.firstinspires.ftc.teamcode.runMode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;

public class Auto {
    public enum Action {
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

    public enum StartingPosition {
        CLOSE,
        FAR
    }

    LinearOpMode opMode;
    Robot robot;

    Action[] actionList;
    Robot.TeamColor teamColor;
    StartingPosition startingPosition;


    boolean patternSet = false;
    Robot.ColorPattern patternInsideRobot = Robot.ColorPattern.GPP;

    public Auto(LinearOpMode opMode, Robot.TeamColor teamColor, StartingPosition startingPosition, Action[] actionList) {
        this.actionList = actionList;
        this.teamColor = teamColor;
        this.startingPosition = startingPosition;
        this.opMode = opMode;

        robot = new Robot(opMode, teamColor);

    }

    public void initAuto() {
        switch (startingPosition) {//TODO add the start positions
            case CLOSE:
                if (teamColor == Robot.TeamColor.RED) {
                    // Initialization code for red close starting position
                    robot.drivetrain.resetOdo(new Pose2D(DistanceUnit.INCH, -35.65, 51.00, AngleUnit.RADIANS, Math.PI / 2));
                } else {
                    // Initialization code for blue close starting position
                    robot.drivetrain.resetOdo(new Pose2D(DistanceUnit.INCH, -35.65, -51.00, AngleUnit.RADIANS, -Math.PI / 2));
                }
                break;
            case FAR:
                if (teamColor == Robot.TeamColor.RED) {
                    // Initialization code for red close starting position
                    robot.drivetrain.resetOdo(new Pose2D(DistanceUnit.INCH, 60, 29, AngleUnit.RADIANS, 1.609));
                } else {
                    // Initialization code for blue close starting position
                    robot.drivetrain.resetOdo(new Pose2D(DistanceUnit.INCH, 57.777, -30.276, AngleUnit.RADIANS, -1.564));
                }
                break;
        }
        robot.drivetrain.printRobotPos();
        opMode.telemetry.update();
        robot.kicker.engageKicker();
    }
    /*public void initAuto(){
        drivetrain.resetOdo(new Pose2D(DistanceUnit.INCH, -35.65, 51.00, AngleUnit.RADIANS, Math.PI/2));
        drivetrain.printRobotPos();
        opMode.telemetry.update();
        kicker.engageKicker();
    }*/

    public void runAuto() {
        for (Action action : actionList) {

          /*  switch (action) {
                case SHOOT_BALL_CLOSE:
                    robot.led.setLed1Color(0.5);
                    // Code to shoot ball from close range
                    shootBallClose();
                    break;
                case SHOOT_BALL_FAR:
                    robot.led.setLed1Color(0.5);
                    // Code to shoot ball from far range
                    shootBallFar();
                    break;
                case PICK_LINE_1:
                    robot.led.setLed1Color(0.611);
                    // Code to pick ball from line 1
                    pickLine1();
                    break;
                case PICK_LINE_2:
                    robot.led.setLed1Color(0.611);
                    // Code to pick ball from line 2
                    pickLine2();
                    break;
                case PICK_LINE_3:
                    robot.led.setLed1Color(0.611);
                    // Code to pick ball from line 3
                    pickLine3();
                    break;
                case PICK_HUMAN_PLAYER:
                    robot.led.setLed1Color(0.7);
                    // Code to pick ball from human player station
                    pickHumanPlayer();
                    break;
                case OPEN_GATE:
                    robot.led.setLed1Color(0.338);
                    // Code to open gate
                    openGate();
                    break;
                case PARK_INSIDE:
                    robot.led.setLed1Color(0.3);
                    // Code to park inside the designated area
                    parkInside();
                    break;
                case PARK_OUTSIDE:
                    robot.led.setLed1Color(0.3);
                    // Code to park outside the designated area
                    parkOutside();
                    break;
                case PARK_GATE:
                    robot.led.setLed1Color(0.3);
                    // Code to park at the gate
                    parkAtGate();
                    break;
            }
            */

        }
    }
/*
    private void shootBallClose() {
        // Implementation for shooting ball from close range
        robot.shooter.autonomousStartShooterClose();
        Pose2D parkPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            parkPos = new Pose2D(DistanceUnit.INCH, -26.7, 13.55, AngleUnit.RADIANS, 0.69);
        } else {
            parkPos = new Pose2D(DistanceUnit.INCH, -26.7, -13.55, AngleUnit.RADIANS, 2.6);

        }
        do {// drive to park position
            robot.drivetrain.driveToTarget(parkPos);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH));
        if(!patternSet){
            patternSet = true;
            robot.setColorPattern(robot.vision.detectPattern());
        }
       // robot.kicker.kickChamberAuto();
        robot.kicker.kickChamberAutoPattern(patternInsideRobot);
    }

    private void shootBallFar()  {
        robot.shooter.autonomousStartShooterFar();
    Pose2D parkPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
        parkPos = new Pose2D(DistanceUnit.INCH, 56.65, 13.8, AngleUnit.RADIANS, 1.200);
    } else {
        parkPos = new Pose2D(DistanceUnit.INCH, 55, -13.75, AngleUnit.RADIANS, 1.954);

    }
        do {// drive to park position
        robot.drivetrain.driveToTarget(parkPos, 0.5);
    } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH));
        robot.kicker.kickChamberAuto();
}

    private void pickLine1() {
        // Implementation for picking ball from line 1
        Pose2D prePos;
        Pose2D postPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            prePos = new Pose2D(DistanceUnit.INCH, -10.5, 20, AngleUnit.RADIANS, Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, -8, 45, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            prePos = new Pose2D(DistanceUnit.INCH, -8, -20, AngleUnit.RADIANS, -Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, -8, -45, AngleUnit.RADIANS, -Math.PI / 2);

        }
        double startTime = opMode.getRuntime();
        do {// drive to pre position
            robot.drivetrain.driveToTarget(prePos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH) && !timeout(startTime, 5));
        robot.kicker.lowerKicker();
        robot.intake.startIntake();
        double startTime2 = opMode.getRuntime();
        do {//drive through
            robot.drivetrain.driveToTarget(postPos, 0.35);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW) && !timeout(startTime2, 5));
        robot.intake.stopIntake();
        robot.kicker.engageKicker();
        patternInsideRobot = Robot.ColorPattern.PPG;
    }

    private void pickLine2() {
        // Implementation for picking ball from line 2
        Pose2D prePos;
        Pose2D postPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            prePos = new Pose2D(DistanceUnit.INCH, 15.5, 20, AngleUnit.RADIANS, Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, 15.5, 55, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            prePos = new Pose2D(DistanceUnit.INCH, 15.5, -20, AngleUnit.RADIANS, -Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, 15.5, -55, AngleUnit.RADIANS, -Math.PI / 2);

        }
        double startTime = opMode.getRuntime();
        do {// drive to pre position
            robot.drivetrain.driveToTarget(prePos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH) && !timeout(startTime, 5));
        robot.kicker.lowerKicker();
        robot.intake.startIntake();
        double startTime2 = opMode.getRuntime();
        do {//drive through
            robot.drivetrain.driveToTarget(postPos, 0.35);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW) && !timeout(startTime2, 5));
        robot.intake.stopIntake();
        robot.kicker.engageKicker();
        patternInsideRobot = Robot.ColorPattern.PGP;
    }

    private void pickLine3() {
        // Implementation for picking ball from line 3
        Pose2D prePos;
        Pose2D postPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            prePos = new Pose2D(DistanceUnit.INCH, 36, 20, AngleUnit.RADIANS, Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, 38, 57.5, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            prePos = new Pose2D(DistanceUnit.INCH, 38, -20, AngleUnit.RADIANS, -Math.PI / 2);
            postPos = new Pose2D(DistanceUnit.INCH, 38, -57.5, AngleUnit.RADIANS, -Math.PI / 2);

        }
        double startTime = opMode.getRuntime();
        do {// drive to pre position
            robot.drivetrain.driveToTarget(prePos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH) && !timeout(startTime, 5));
        robot.kicker.lowerKicker();
        robot.intake.startIntake();
        double startTime2 = opMode.getRuntime();
        do {//drive through
            robot.drivetrain.driveToTarget(postPos, 0.35);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH) && !timeout(startTime2, 5));
        robot.intake.stopIntake();
        robot.kicker.engageKicker();
        patternInsideRobot = Robot.ColorPattern.GPP;
    }

    private void pickHumanPlayer() {
        // Implementation for picking ball from human player station
    }

    private void openGate() {
        // Implementation for opening gate
        Pose2D preGatePos;
        Pose2D postGatePos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            preGatePos = new Pose2D(DistanceUnit.INCH, -1, 38, AngleUnit.RADIANS, Math.PI / 2);
            postGatePos = new Pose2D(DistanceUnit.INCH, -1, 48, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            preGatePos = new Pose2D(DistanceUnit.INCH, -1, -38, AngleUnit.RADIANS, -Math.PI / 2);
            postGatePos = new Pose2D(DistanceUnit.INCH, -1, -48, AngleUnit.RADIANS, -Math.PI / 2);

        }
        double startTime = opMode.getRuntime();
        do {// drive to pre-gate position
            robot.drivetrain.driveToTarget(preGatePos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH) && !timeout(startTime, 5));

        double startTime2 = opMode.getRuntime();
        do {//drive through gate
            robot.drivetrain.driveToTarget(postGatePos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW) && !timeout(startTime2, 5));
        opMode.sleep(500);

    }

    private void parkInside() {
        // Implementation for parking inside the designated area
        Pose2D parkPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            parkPos = new Pose2D(DistanceUnit.INCH, -46.5, 13, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            parkPos = new Pose2D(DistanceUnit.INCH, -46.5, -13, AngleUnit.RADIANS, -Math.PI / 2);

        }
        do {// drive to park position
            robot.drivetrain.driveToTarget(parkPos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW));
    }

    private void parkOutside() {
        // Implementation for parking outside the designated area
        Pose2D parkPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            parkPos = new Pose2D(DistanceUnit.INCH, 40, 20, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            parkPos = new Pose2D(DistanceUnit.INCH, 40, -20, AngleUnit.RADIANS, -Math.PI / 2);

        }
        do {// drive to park position
            robot.drivetrain.driveToTarget(parkPos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW));
    }

    private void parkAtGate() {
        // Implementation for parking outside the designated area
        Pose2D parkPos;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            parkPos = new Pose2D(DistanceUnit.INCH, 0, 30, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            parkPos = new Pose2D(DistanceUnit.INCH, 0, -30, AngleUnit.RADIANS, -Math.PI / 2);

        }
        do {// drive to park position
            robot.drivetrain.driveToTarget(parkPos, 0.5);
        } while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.LOW));
    }
    private boolean timeout(double startTime, double timeoutDuration) {
        return (opMode.getRuntime() - startTime) >= timeoutDuration;
    }
*/
}
