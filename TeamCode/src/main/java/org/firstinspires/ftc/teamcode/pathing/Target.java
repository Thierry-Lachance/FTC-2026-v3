package org.firstinspires.ftc.teamcode.pathing;



import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Supplier;

public class Target {
    private final double targetVelocity;
    private final Supplier<PathChain> pathToTarget;
    private final double timeBetweenShots;
    private final Follower follower;
    private final PIDFCoefficients pidfCoefficients;

    public Target(Pose targetPose, double targetVelocity, double timeBetweenShots, PIDFCoefficients pidfCoefficients, Follower follower) {
        this.follower = follower;
        this.targetVelocity = targetVelocity;
        this.timeBetweenShots = timeBetweenShots;
        this.pidfCoefficients = pidfCoefficients;

        pathToTarget = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(targetPose.getX(), targetPose.getY(), targetPose.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, targetPose.getHeading(), 0.8))
                .build();

    }
    public Target(Pose targetPose, Pose waypoint, double targetVelocity, double timeBetweenShots, PIDFCoefficients pidfCoefficients, Follower follower) {
        this.follower = follower;
        this.targetVelocity = targetVelocity;
        this.timeBetweenShots = timeBetweenShots;
        this.pidfCoefficients = pidfCoefficients;

        pathToTarget = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(targetPose.getX(), targetPose.getY(), targetPose.getHeading()))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, targetPose.getHeading(), 0.8))
                .build();

    }

    public PathChain getPathToTarget() {
        return pathToTarget.get();
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public double getTimeBetweenShots() {
        return timeBetweenShots;
    }
    public PIDFCoefficients getPidfCoefficients() {
        return pidfCoefficients;
    }
}
