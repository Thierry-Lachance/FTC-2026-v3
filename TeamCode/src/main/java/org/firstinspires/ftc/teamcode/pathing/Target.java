package org.firstinspires.ftc.teamcode.pathing;


import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Target {
    private final double targetVelocity;
    private final PathManager.Destination destination;
    private final double timeBetweenShots;
    private final Follower follower;
    private final PIDFCoefficients pidfCoefficients;

    public Target(PathManager.Destination destination, double targetVelocity, double timeBetweenShots, PIDFCoefficients pidfCoefficients, Follower follower) {
        this.follower = follower;
        this.targetVelocity = targetVelocity;
        this.timeBetweenShots = timeBetweenShots;
        this.pidfCoefficients = pidfCoefficients;
        this.destination = destination;
    }

    public PathManager.Destination getDestination() {
        return destination;
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
