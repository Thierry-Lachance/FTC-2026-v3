package org.firstinspires.ftc.teamcode.pathing;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class AimBot {
    private final Target[] targets;
    private int currentTargetIndex = 0;

    public AimBot() {
        targets = new Target[]{
                new Target(PathManager.Destination.CENTER_FIELD, -1180, 0.5, new PIDFCoefficients(250, 2, 2, 0.0)),
                new Target(PathManager.Destination.NEAR_TEAM_GOAL, -1025.0, 0.2, new PIDFCoefficients(250, 2, 2, 0.0)),
                new Target(PathManager.Destination.FAR_ZONE, -1475, 0.5, new PIDFCoefficients(250, 1, 2, 0.0)),
                new Target(PathManager.Destination.NEAR_OPP_GOAL, -1220.0, 0.5, new PIDFCoefficients(250, 2, 2, 0.0))
        };
    }

    public void setTargets(int targetIndex) {
        if (targetIndex < 0 || targetIndex >= targets.length) {
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

    public PathManager.Destination getDestination() {
        return targets[currentTargetIndex].getDestination();
    }

    public PIDFCoefficients getPidfCoefficients() {
        return targets[currentTargetIndex].getPidfCoefficients();
    }
}
