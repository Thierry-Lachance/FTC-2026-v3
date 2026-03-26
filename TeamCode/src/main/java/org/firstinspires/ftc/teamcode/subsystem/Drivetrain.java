package org.firstinspires.ftc.teamcode.subsystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.Constants;

import java.util.Locale;
import java.util.function.Supplier;


public class Drivetrain {

    LinearOpMode opMode;
    Robot robot;


    Robot.TeamColor teamColor;

    double targetX;
    double targetY;
    double multiplier = 1.0;
    double additionner = 0.0;


    Pose resetPosition;

    Pose preHuman;
    Pose postHuman;

    Pose shoot = new Pose(96.5, 95.063, -Math.PI/2);
    Pose right = new Pose(105, 57);
    Pose left = new Pose(45,110);




    Pose gate;

    double previousHeading = 0.0;

    public enum Precision {//TODO check if deprecated
        HIGH,
        LOW
    }

    private final Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this


    public final Supplier<PathChain> pathChainPreHuman;
    public final Supplier<PathChain> pathChainPostHuman;


    public final Supplier<PathChain> pathChainGate;


    public final PathChain rightPath;
    public final PathChain leftPath;


    boolean automatedDrive = false;

    public Drivetrain(LinearOpMode opMode, Robot robot, Robot.TeamColor teamColor) {
        this.opMode = opMode;
        this.robot = robot;
        this.teamColor = teamColor;


        if (teamColor == Robot.TeamColor.RED) {
            targetX = 72;
            targetY = 72;
            multiplier = 1;

            resetPosition = new Pose(6.1, 28.39, -Math.PI);
            startingPose = resetPosition;

            preHuman = new Pose(14,29.35, -1.633);
            postHuman = new Pose(14, 15, -1.645);


            gate = new Pose(138,71,0);


        } else {
            targetX = -72;
            targetY = 72;
            multiplier = -1;
            additionner = -Math.PI;

            resetPosition = new Pose(9.594, 8.984, -Math.PI);
        }

        follower = Constants.createFollower(opMode.hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        follower.startTeleopDrive();




        pathChainPreHuman = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, preHuman)))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, preHuman.getHeading(), 0.8))
                .build();
        pathChainPostHuman = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, postHuman)))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, postHuman.getHeading(), 0.8))
                .build();

        pathChainGate = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, gate)))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, gate.getHeading(), 0.8))
                .build();

        rightPath = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                follower::getPose,
                                new Pose(9.609, 92.031),
                                new Pose(98.047, -0.125),
                                new Pose(125.234, 78.828),
                                new Pose(96.500, 95.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .build();

        leftPath = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                follower::getPose,
                                new Pose(86.391, 93.156),
                                new Pose(2.000, 142.188),
                                new Pose(110.047, 137.609),
                                new Pose(96.500, 95.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .build();


    }

    public void drive() {
        follower.update();
        if (opMode.gamepad1.xWasPressed()) {
            robot.aimBot.setTargets(0);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (opMode.gamepad1.yWasPressed()) {
            robot.aimBot.setTargets(1);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (opMode.gamepad1.aWasPressed()) {
            robot.aimBot.setTargets(2);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (opMode.gamepad1.bWasPressed()) {
            robot.aimBot.setTargets(3);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if ((opMode.gamepad1.left_stick_x > 0.1 || opMode.gamepad1.left_stick_x < -0.1 ||
                opMode.gamepad1.left_stick_y > 0.1 || opMode.gamepad1.left_stick_y < -0.1 ||
                opMode.gamepad1.right_stick_x > 0.1 || opMode.gamepad1.right_stick_x < -0.1) & automatedDrive) {
            automatedDrive = false;
            follower.startTeleopDrive();

        } else if (opMode.gamepad1.left_trigger > 0.1 && opMode.gamepad1.right_trigger > 0.1) {
            strafeToBall(robot.limelight.getBallRotationOffset(), opMode.gamepad1.left_trigger);
        } else if (opMode.gamepad1.left_trigger > 0.1 && opMode.gamepad1.right_trigger < 0.1) {
            smartCorrectAngleToShoot();
        } else if(!automatedDrive){
            driveMecanumFieldOriented(opMode.gamepad1);
        }


        if (opMode.gamepad1.options) {
            resetFieldOriented();
        }
        if (opMode.gamepad1.right_bumper || opMode.gamepad1.left_bumper) {
            resetOdoCorner(resetPosition);

        }
        opMode.telemetry.addData("robotx", follower.getPose().getX());
        opMode.telemetry.addData("roboty", follower.getPose().getY());
        opMode.telemetry.addData("roboth", follower.getPose().getHeading());

    }

    public void initTeleOp() {

    }

    public void driveMecanumFieldOriented(Gamepad gamepad) {
        follower.setTeleOpDrive(
                -opMode.gamepad1.left_stick_y,
                -opMode.gamepad1.left_stick_x,
                -opMode.gamepad1.right_stick_x,
                false
        );
    }

    public void driveToTarget(PathChain path) {
        follower.followPath(path);
        automatedDrive = true;
    }
    public void driveToTargetAuto(PathChain path, long timeout) {
        follower.followPath(path);
        automatedDrive = true;
        while (isBusy() && !robot.timeToStop()) update();
        if(robot.timeToStop()) return;
        opMode.sleep(timeout);

    }

    public void resetFieldOriented() {
        //TODO check usage
        Pose currentPose = follower.getPose();
        follower.setPose(new Pose(currentPose.getX(), currentPose.getY(), 0));
    }


    public void resetOdo(Pose2D resetPose) {

        //TODO implement

    }

    public void resetOdoCorner(Pose resetPose) {
        follower.setPose(resetPose);

    }
    public void resetOdoCenter(Pose resetPose) {
        follower.setPose(new Pose(72,72, -Math.PI/2));

    }

    public void printRobotPos() {
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading());
        opMode.telemetry.addData("ROBOT Position", data);

    }


    public boolean isAtTarget(Precision precision) {
        double posTolerance, velTolerance, angleTolerance;
        if (precision == Precision.HIGH) {
            posTolerance = 25;
            velTolerance = 10;
            angleTolerance = 0.15;
        } else {
            posTolerance = 30;
            velTolerance = 75;
            angleTolerance = 0.25;
        }

        // odo.update();
       /* return Math.abs(odo.getPosX() - targetPose.getX(DistanceUnit.MM)) < posTolerance &&
                Math.abs(odo.getPosY() - targetPose.getY(DistanceUnit.MM)) < posTolerance &&
                Math.abs(odo.getPosition().getHeading(AngleUnit.RADIANS) - targetPose.getHeading(AngleUnit.RADIANS)) < angleTolerance &&
                Math.abs(odo.getVelX()) < velTolerance &&
                Math.abs(odo.getVelY()) < velTolerance;*/
        return false;
    }

    public void strafeToBall(double ballOffset, double speed) {
        if (ballOffset == 0) {
            ballOffset = previousHeading;
        } else {
            previousHeading = ballOffset;
        }

        double strafePower = ballOffset / 40;

        follower.setTeleOpDrive(
                speed,
                -strafePower,
                0,
                true
        );
    }

    public boolean checkIfInTriangle() {

        double x = follower.getPose().getX();
        double y = follower.getPose().getY();
        return x <= -y && x <= y;

    }
    public void update() {
        follower.update();
    }

    public void smartCorrectAngleToShoot() {
        double x = follower.getPose().getX();
        double y = follower.getPose().getY();

        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        opMode.telemetry.addData("distance x", d);
        opMode.telemetry.addData("distance y", h);
        double target = (1 * Math.PI / 2 - Math.atan(h / d)) + additionner;
        if (Math.abs(target) > Math.PI) {
            target = -2 * Math.PI + target;
        }
        target = multiplier * target;
        double error = target - follower.getPose().getHeading();

        //TODO check for power inversion
        follower.setTeleOpDrive(
                -opMode.gamepad1.left_stick_y,
                -opMode.gamepad1.left_stick_x,
                error,
                false
        );

    }

    public boolean checkIfAlignedWithGoal() {
        double tolerance = 0.25;
        double x = follower.getPose().getX()-72;
        double y = follower.getPose().getY()-72;

        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        opMode.telemetry.addData("distance x", d);
        opMode.telemetry.addData("distance y", h);
        double target = (1 * Math.PI / 2 - Math.atan(h / d)) + additionner;
        if (Math.abs(target) > Math.PI) {
            target = -2 * Math.PI + target;
        }
        target = multiplier * target;
        double error = target - follower.getPose().getHeading();
        opMode.telemetry.addData("angle error", error);
        return Math.abs(error) < tolerance;


    }

    public double calculateShooterPower() {
        double x = follower.getPose().getX();
        double y = follower.getPose().getY();
        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        double distance = Math.sqrt(d * d + h * h);
        opMode.telemetry.addData("distance to goal", distance);

        return getShooterPower(distance);
    }

    public static double getShooterPower(double distanceInches) {
        return -0.00216713 * distanceInches * distanceInches * distanceInches
                + 0.6849218 * distanceInches * distanceInches
                - 63.05278661 * distanceInches
                + 2825.23407233;
    }
    public boolean isBusy() {
        return follower.isBusy();
    }

    public Follower getFollower(){
        return follower;
    }
}
