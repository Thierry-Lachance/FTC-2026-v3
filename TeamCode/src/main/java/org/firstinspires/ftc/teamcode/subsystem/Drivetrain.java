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


    public Drivetrain(Robot robot, Pose startingPose) {
        this.robot = robot;


        if (robot.teamColor == Robot.TeamColor.RED) {


            resetPositionHuman = new Pose(6.1, 28.39, -Math.PI);
            resetPositionGoal = new Pose(115,136.6,-2.455);


        } else {


            resetPositionHuman = new Pose(9.594, 8.984, -Math.PI);
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
                false
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
        if(!hold) follower.breakFollowing();
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
