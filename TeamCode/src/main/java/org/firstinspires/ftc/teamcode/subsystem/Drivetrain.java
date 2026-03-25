package org.firstinspires.ftc.teamcode.subsystem;

import com.pedropathing.follower.Follower;
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
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

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

    Pose TARGET_X;
    Pose TARGET_Y;
    Pose TARGET_A;
    Pose TARGET_B;
    Pose resetPosition;

    Pose preHuman;
    Pose postHuman;


    Pose gate;

    double previousHeading = 0.0;

    public enum Precision {//TODO check if deprecated
        HIGH,
        LOW
    }

    private final Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this

    private final Supplier<PathChain> pathChainX;
    public final Supplier<PathChain> pathChainY;
    private final Supplier<PathChain> pathChainA;
    private final Supplier<PathChain> pathChainB;

    public final Supplier<PathChain> pathChainPreHuman;
    public final Supplier<PathChain> pathChainPostHuman;


    public final Supplier<PathChain> pathChainGate;

    boolean automatedDrive = false;

    public Drivetrain(LinearOpMode opMode, Robot robot, Robot.TeamColor teamColor) {
        this.opMode = opMode;
        this.robot = robot;
        this.teamColor = teamColor;


        if (teamColor == Robot.TeamColor.RED) {
            targetX = 72;
            targetY = 72;
            multiplier = 1;
            TARGET_X = new Pose(71.516, 71.469, -0.85);
            TARGET_Y = new Pose(96.5, 95.063, -0.85);
            TARGET_A = new Pose(84.563, 16.250, -0.4014);
            TARGET_B = new Pose(47.469, 95.188, -1.2);
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
            TARGET_X = new Pose(71.516, 71.469, -Math.PI/2);
            TARGET_Y = new Pose(96.5, 95.063, -Math.PI/2);
            TARGET_A = new Pose(84.563, 16.250, -0.4014);
            TARGET_B = new Pose(47.469, 95.188, -1.01);
            resetPosition = new Pose(9.594, 8.984, -Math.PI);
        }

        follower = Constants.createFollower(opMode.hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        follower.startTeleopDrive();


        pathChainX = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(TARGET_X.getX(), TARGET_X.getY(), TARGET_X.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, TARGET_X.getHeading(), 0.8))
                .build();

        pathChainY = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(TARGET_Y.getX(), TARGET_Y.getY(), TARGET_Y.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, TARGET_Y.getHeading(), 0.8))
                .build();

        pathChainA = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(TARGET_A.getX(), TARGET_A.getY(), TARGET_A.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, TARGET_A.getHeading(), 0.8))
                .build();

        pathChainB = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(TARGET_B.getX(), TARGET_B.getY(), TARGET_B.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, TARGET_B.getHeading(), 0.8))
                .build();

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


    }

    public void drive() {
        follower.update();
        if (opMode.gamepad1.xWasPressed()) {
            driveToTarget(pathChainX.get());
            automatedDrive = true;
        } else if (opMode.gamepad1.yWasPressed()) {
            driveToTarget(pathChainY.get());
            automatedDrive = true;
        } else if (opMode.gamepad1.aWasPressed()) {
            driveToTarget(pathChainA.get());
            automatedDrive = true;
        } else if (opMode.gamepad1.bWasPressed()) {
            driveToTarget(pathChainB.get());
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
        Pose2D temp;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            temp = new Pose2D(DistanceUnit.INCH, 0, 30, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            temp = new Pose2D(DistanceUnit.INCH, 0, -30, AngleUnit.RADIANS, -Math.PI / 2);

        }
        //  odo.setPosition(temp);
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
        //TODO check for power inversion
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

}
