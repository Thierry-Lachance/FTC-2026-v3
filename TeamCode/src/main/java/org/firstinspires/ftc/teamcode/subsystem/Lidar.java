package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class Lidar {
    Robot robot;
    private DistanceSensor sensorDistance;
    public Lidar(Robot robot){
        this.robot = robot;
        sensorDistance = robot.opMode.hardwareMap.get(DistanceSensor.class, Constants.lidarName);

    }
    public void update() {
        robot.opMode.telemetry.addData("range", String.format("%.01f mm", sensorDistance.getDistance(DistanceUnit.MM)));
    }
}
