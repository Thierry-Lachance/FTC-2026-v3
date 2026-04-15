package org.firstinspires.ftc.teamcode.commandBased.subsystem;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Old.pathing.PathingConstants;
import org.firstinspires.ftc.teamcode.commandBased.Util.Alliance;

public class Drivetrain {
    private final Follower follower;
    public Drivetrain(HardwareMap hardwareMap, Alliance alliance) {
        follower = PathingConstants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();

        follower.startTeleopDrive();

    }
    public void driveMecanumFieldOriented(double x, double y, double rotation) {
        follower.setTeleOpDrive(
                -y,
                -x,
                -rotation,
                false
        );
    }
    public void startManualDrive() {
        follower.startTeleopDrive();
    }
    public void resetFieldOriented() {
        Pose currentPose = follower.getPose();
        follower.setPose(new Pose(currentPose.getX(), currentPose.getY(), 0));
    }
    public void update() {
        follower.update();
    }
}
