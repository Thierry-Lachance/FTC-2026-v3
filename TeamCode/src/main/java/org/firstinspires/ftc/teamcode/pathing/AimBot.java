package org.firstinspires.ftc.teamcode.pathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Robot;

public class AimBot {
    private final Target[] targets;
    private int currentTargetIndex = 0;

    public AimBot(Robot.TeamColor teamColor, Follower follower) {
        if(teamColor == Robot.TeamColor.RED){
            targets = new Target[]{
                    new Target(new Pose(71.516, 71.469, -0.85), -1180, 0.5, new PIDFCoefficients(250, 2, 2, 0.0), follower),
                    new Target(new Pose(96.5, 95.063, -0.85), -1025.0, 0.2, new PIDFCoefficients(250, 2, 2, 0.0), follower),
                    new Target(new Pose(84.563, 16.250, -0.4014), -1475, 0.5, new PIDFCoefficients(250, 1, 2, 0.0), follower),
                    new Target(new Pose(47.469, 95.188, -1.2), -1220.0, 0.5, new PIDFCoefficients(250, 2, 2, 0.0), follower)
            };
        } else {

            targets = new Target[] {
                    new Target(new Pose(71.516, 71.469, -Math.PI/2), -1180, 0.5, new PIDFCoefficients(250, 2, 2, 0.0), follower),//old x
                    new Target(new Pose(96.5, 95.063, -Math.PI/2), -1025.0, 0.2, new PIDFCoefficients(250, 2, 2, 0.0), follower),//old y
                    new Target(new Pose(84.563, 16.250, -0.4014), -1475, 0.5, new PIDFCoefficients(250, 1, 2, 0.0), follower),//old a
                    new Target(new Pose(47.469, 95.188, -1.01), -1220.0, 0.5, new PIDFCoefficients(250, 2, 2, 0.0), follower)//old b
            };
        }
    }

    public void setTargets(int targetIndex){
        if(targetIndex < 0 || targetIndex >= targets.length) {
            throw new IllegalArgumentException("Invalid target index");
        }
        currentTargetIndex = targetIndex;
    }

    public double getTargetVelocity() {
        return targets[currentTargetIndex].getTargetVelocity();
    }
    public double getTimeBetweenShots() {
        return targets[currentTargetIndex].getTimeBetweenShots();
    }
    public PathChain getPathToTarget() {
        return targets[currentTargetIndex].getPathToTarget();
    }
    public PIDFCoefficients getPidfCoefficients() {
        return targets[currentTargetIndex].getPidfCoefficients();
    }
}
