package org.firstinspires.ftc.teamcode.pathing;


import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Target {
    private final double targetVelocity;
    private final PathManager.DestinationTeleop destinationTeleop;
    private final double timeBetweenShots;
    private final PIDFCoefficients pidfCoefficients;

    public Target(PathManager.DestinationTeleop destinationTeleop, double targetVelocity, double timeBetweenShots, PIDFCoefficients pidfCoefficients) {
        this.targetVelocity = targetVelocity;
        this.timeBetweenShots = timeBetweenShots;
        this.pidfCoefficients = pidfCoefficients;
        this.destinationTeleop = destinationTeleop;
    }

    public PathManager.DestinationTeleop getDestination() {
        return destinationTeleop;
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
