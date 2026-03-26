package org.firstinspires.ftc.teamcode.subsystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.Constants;

import java.util.Locale;
import java.util.function.Supplier;


public class Drivetrain {
    
    Robot robot;
    Pose resetPosition;

    Pose preHuman;
    Pose postHuman;

    Pose shoot = new Pose(96.5, 95.063, -Math.PI/2);
    Pose right = new Pose(105, 57);
    Pose left = new Pose(45,110);




    Pose gate;

    double previousHeading = 0.0;



    private final Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this


    public final Supplier<PathChain> pathChainPreHuman;
    public final Supplier<PathChain> pathChainPostHuman;


    public final Supplier<PathChain> pathChainGate;


    public final PathChain rightPath;
    public final PathChain leftPath;


    boolean automatedDrive = false;

    public Drivetrain(Robot robot) {
        this.robot = robot;



        if (robot.teamColor == Robot.TeamColor.RED) {


            resetPosition = new Pose(6.1, 28.39, -Math.PI);
            startingPose = resetPosition;

            preHuman = new Pose(14,29.35, -1.633);
            postHuman = new Pose(14, 15, -1.645);


            gate = new Pose(138,71,0);


        } else {


            resetPosition = new Pose(9.594, 8.984, -Math.PI);
        }

        follower = Constants.createFollower(robot.opMode.hardwareMap);
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
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, -0.85, 0.8))
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
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, -0.85, 0.8))
                .build();
    }

    public void drive() {
        follower.update();
        if (robot.opMode.gamepad1.xWasPressed()) {
            robot.aimBot.setTargets(0);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (robot.opMode.gamepad1.yWasPressed()) {
            robot.aimBot.setTargets(1);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (robot.opMode.gamepad1.aWasPressed()) {
            robot.aimBot.setTargets(2);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if (robot.opMode.gamepad1.bWasPressed()) {
            robot.aimBot.setTargets(3);
            driveToTarget(robot.aimBot.getPathToTarget());
            automatedDrive = true;
        } else if ((robot.opMode.gamepad1.left_stick_x > 0.1 || robot.opMode.gamepad1.left_stick_x < -0.1 ||
                robot.opMode.gamepad1.left_stick_y > 0.1 || robot.opMode.gamepad1.left_stick_y < -0.1 ||
                robot.opMode.gamepad1.right_stick_x > 0.1 || robot.opMode.gamepad1.right_stick_x < -0.1) & automatedDrive) {
            automatedDrive = false;
            follower.startTeleopDrive();

        } else if (robot.opMode.gamepad1.left_trigger > 0.1 && robot.opMode.gamepad1.right_trigger > 0.1) {
            strafeToBall(robot.limelight.getBallOffset(), robot.opMode.gamepad1.left_trigger);
        }else if(!automatedDrive){
            driveMecanumFieldOriented(robot.opMode.gamepad1);
        }


        if (robot.opMode.gamepad1.options) {
            resetFieldOriented();
        }
        if (robot.opMode.gamepad1.right_bumper || robot.opMode.gamepad1.left_bumper) {
            resetOdoCorner(resetPosition);

        }
        printRobotPos();
    }

    public void initTeleOp() {

    }

    public void driveMecanumFieldOriented(Gamepad gamepad) {
        follower.setTeleOpDrive(
                -robot.opMode.gamepad1.left_stick_y,
                -robot.opMode.gamepad1.left_stick_x,
                -robot.opMode.gamepad1.right_stick_x,
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
        robot.opMode.sleep(timeout);

    }

    public void resetFieldOriented() {
        Pose currentPose = follower.getPose();
        follower.setPose(new Pose(currentPose.getX(), currentPose.getY(), 0));
    }

    public void resetOdoCorner(Pose resetPose) {
        follower.setPose(resetPose);

    }

    public void printRobotPos() {
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading());
        robot.opMode.telemetry.addData("ROBOT Position", data);

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

    public void update() {
        follower.update();
    }

    public boolean isBusy() {
        return follower.isBusy();
    }

    public Follower getFollower(){
        return follower;
    }
}
