package org.firstinspires.ftc.teamcode.subsystem;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class Lidar {
    Robot robot;
    private DistanceSensor sensorDistance;
    private Servo lidarServo;

    private double currentLidarAngle = 0.0;
    private double lastTimeOfMove = -1;

    private boolean scanningForward = true; //true if the lidar is currently scanning from 0 to 189, false if it's scanning from 189 to 0

    private final double lidarMaxAngle = 189.0; //TODO check if the full range of the servo is 189 degrees, if not change this value
    private final double lidarScanTime = 500.0; //time in ms it takes for the lidar to scan from 0 to 189 degrees or from 189 to 0 degrees
    private final double degreesPerMs = lidarMaxAngle / lidarScanTime; //degrees the lidar moves per ms

    public Lidar(Robot robot) {
        this.robot = robot;
        sensorDistance = robot.opMode.hardwareMap.get(DistanceSensor.class, Constants.lidarName);
        lidarServo = robot.opMode.hardwareMap.get(Servo.class, Constants.lidarRotationServoName);
        rotateLidarMin();

    }

    public void update() {
        robot.opMode.telemetry.addData("range", String.format("%.01f mm", sensorDistance.getDistance(DistanceUnit.MM)));
    }

    public void rotateLidarMax() {
        lidarServo.setPosition(1);
    }

    public void rotateLidarMin() {
        lidarServo.setPosition(0);
    }
    public double degToPos(double degrees) {
        return Math.max(0.0, Math.min(1.0, degrees / lidarMaxAngle));
    }
    private void moveLidar(double degreesToMove){
        //rotate the lidar by the degreesToMove, but make sure to not rotate it past the max or min angle
        if(scanningForward) {
            currentLidarAngle += degreesToMove;
            if (currentLidarAngle >= lidarMaxAngle) {
                currentLidarAngle = lidarMaxAngle;

                scanningForward = false;
                lastTimeOfMove = robot.opMode.getRuntime();
            }
        }else{
            currentLidarAngle -= degreesToMove;
            if (currentLidarAngle <= 0) {
                currentLidarAngle = 0;

                scanningForward = true;
                lastTimeOfMove = robot.opMode.getRuntime();
            }
        }
        lidarServo.setPosition(degToPos(currentLidarAngle));


    }
    public void scan(){
        double cycleTime;
        if(lastTimeOfMove == -1){
            cycleTime = 0;
        } else {
            cycleTime = robot.opMode.getRuntime() - lastTimeOfMove;
        }

       //convert the cycle time ms to degrees we want the servo to turn from 0 to 189 in 500ms
        double degreesToTurn = cycleTime * degreesPerMs;
        robot.opMode.telemetry.addData("cycleTime", cycleTime);
        moveLidar(degreesToTurn);
       // estimateObstaclePose(sensorDistance.getDistance(DistanceUnit.INCH));



    }
    public Pose estimateObstaclePose(double distanceInch){
        Pose robotPose = robot.drivetrain.getFollower().getPose();
        // offset of lidar to the right of the tracking point (in inches)
        final double lidarOffsetRightInch = 8.0;

        // robotPose.getHeading() is in radians
        double headingRad = robotPose.getHeading();

        // lidar origin = robot position + offset to the right:
        // right direction = heading - 90deg => (sin(heading), -cos(heading))
        double lidarOriginX = robotPose.getX() + lidarOffsetRightInch * Math.sin(headingRad);
        double lidarOriginY = robotPose.getY() - lidarOffsetRightInch * Math.cos(headingRad);

        // Map servo angle to offset from robot heading (same convention as before)
        double lidarAngleRelativeToRobotDeg = -currentLidarAngle;
        double lidarAngleGlobalRad = headingRad + Math.toRadians(lidarAngleRelativeToRobotDeg);

        // project obstacle from lidar origin
        double obstacleX = lidarOriginX + distanceInch * Math.cos(lidarAngleGlobalRad);
        double obstacleY = lidarOriginY + distanceInch * Math.sin(lidarAngleGlobalRad);

        return new Pose(obstacleX, obstacleY, robotPose.getHeading());
    }



}

