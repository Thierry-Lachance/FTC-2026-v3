package org.firstinspires.ftc.teamcode.subsystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.PathingConstants;

import java.util.Locale;


public class Drivetrain {

    Robot robot;
    Pose resetPositionHuman;
    Pose resetPositionGoal;


    double previousHeading = 0.0;


    private final Follower follower;
    private double fieldOrientedHeadingOffset = 0.0;


    public Drivetrain(Robot robot, Pose startingPose) {
        this.robot = robot;


        if (robot.teamColor == Robot.TeamColor.RED) {


            resetPositionHuman = new Pose(10.76, 10.76, -3.11);
            resetPositionGoal = new Pose(121.1, 128.74, -2.43);


        } else {
            fieldOrientedHeadingOffset = -Math.PI;
            resetPositionHuman = new Pose(133.5, 11.48, 0);
            resetPositionGoal = new Pose(27.56, 131.15, -0.696);
        }

        follower = PathingConstants.createFollower(robot.opMode.hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        follower.startTeleopDrive();
    }

    public void driveMecanumFieldOriented() {
        follower.setTeleOpDrive(
                -robot.opMode.gamepad1.left_stick_y,
                -robot.opMode.gamepad1.left_stick_x,
                -robot.opMode.gamepad1.right_stick_x,
                false,
                fieldOrientedHeadingOffset
        );
    }


    public void driveToTargetAuto(PathChain path, long timeout) {
        follower.followPath(path);
        while (follower.isBusy() && !robot.timeToStop()) follower.update();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(timeout);

    }

    public void driveToTargetAuto(PathChain path, boolean hold, long timeout) {
        follower.followPath(path);
        while (follower.isBusy() && !robot.timeToStop()) follower.update();
        if (!hold) follower.breakFollowing();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(timeout);

    }

    public void makeTheRobotJumpForward() {
        follower.startTeleopDrive();
        follower.update();
        follower.setTeleOpDrive(
                0.50,
                0,
                0,
                true
        );
        follower.update();
        robot.opMode.sleep(800);
        follower.setTeleOpDrive(
                0,
                0,
                0,
                true
        );
        follower.update();

    }

    public void resetFieldOriented() {
        Pose currentPose = follower.getPose();
        follower.setPose(new Pose(currentPose.getX(), currentPose.getY(), 0));
    }

    public void resetOdoCorner() {
        follower.setPose(resetPositionHuman);

    }

    public void resetOdoGoal() {
        follower.setPose(resetPositionGoal);

    }
    public void setStartingPose(Pose pose) {
        follower.setStartingPose(pose);
        follower.setPose(pose);
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

    public Follower getFollower() {
        return follower;
    }

    public void periodic() {
        follower.update();
        printRobotPos();
    }
}
