package org.firstinspires.ftc.teamcode.Old.pathing;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class AimBot {
    private final Target[] targets;
    private int currentTargetIndex = 0;

    public AimBot() {
        targets = new Target[]{
                new Target(PathManager.DestinationTeleop.CENTER_FIELD, -1180, 0.5, new PIDFCoefficients(250, 2, 2, 0.0)),
                new Target(PathManager.DestinationTeleop.NEAR_TEAM_GOAL, -1025.0, 0.2, new PIDFCoefficients(250, 2, 2, 0.0)),
                new Target(PathManager.DestinationTeleop.FAR_ZONE, -1475, 0.5, new PIDFCoefficients(250, 1, 2, 0.0)),
                new Target(PathManager.DestinationTeleop.NEAR_OPP_GOAL, -1220.0, 0.5, new PIDFCoefficients(250, 2, 2, 0.0))
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

    public PathManager.DestinationTeleop getDestination() {
        return targets[currentTargetIndex].getDestination();
    }

    public PIDFCoefficients getPidfCoefficients() {
        return targets[currentTargetIndex].getPidfCoefficients();
    }
}
