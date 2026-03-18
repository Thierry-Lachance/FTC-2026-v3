package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;



public class Drivetrain {

    LinearOpMode opMode;
    Robot robot;

    Pose2D targetPose = new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0);

    Robot.TeamColor teamColor;

    double targetX;
    double targetY;




    Pose2D TARGET_X;
    Pose2D TARGET_Y;
    Pose2D TARGET_A;
    Pose2D TARGET_B;
    Pose2D resetPosition;

    double previousHeading = 0.0;

    public enum Precision {
        HIGH,
        LOW
    }
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this

    private Supplier<PathChain> pathChain;

    boolean automatedDrive = false;

    public Drivetrain(LinearOpMode opMode, Robot robot, Robot.TeamColor teamColor) {
        this.opMode = opMode;
        this.robot = robot;
        this.teamColor = teamColor;


        if (teamColor == Robot.TeamColor.RED) {
            targetX = -70.5;
            targetY = 70.5;

            TARGET_X = new Pose2D(DistanceUnit.INCH, -4.1, 0, AngleUnit.RADIANS, 0.808);
            TARGET_Y = new Pose2D(DistanceUnit.INCH, -26.7, 13.55, AngleUnit.RADIANS, 0.69);
            TARGET_A = new Pose2D(DistanceUnit.INCH, 56.65, 13.8, AngleUnit.RADIANS, 1.200);
            TARGET_B = new Pose2D(DistanceUnit.INCH, -17.8, -16.7, AngleUnit.RADIANS, 0.52);
            resetPosition = new Pose2D(DistanceUnit.INCH, 58.5, -63, AngleUnit.RADIANS, -Math.PI/2);
            startingPose = new Pose(144, 144, 0);
        } else {
            targetX = -70.5;
            targetY = -70.5;

            TARGET_X = new Pose2D(DistanceUnit.INCH, -6.0, 1.0, AngleUnit.RADIANS, 2.298);
            TARGET_Y = new Pose2D(DistanceUnit.INCH, -26.7, -13.55, AngleUnit.RADIANS, 2.4);
            TARGET_A = new Pose2D(DistanceUnit.INCH, 55, -13.75, AngleUnit.RADIANS, 1.954);
            TARGET_B = new Pose2D(DistanceUnit.INCH, -14.5, 13.5, AngleUnit.RADIANS, 2.55);
            resetPosition = new Pose2D(DistanceUnit.INCH, 58.5, 63, AngleUnit.RADIANS, Math.PI/2);
        }

        follower = Constants.createFollower(opMode.hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        follower.startTeleopDrive();

        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(45, 98))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(45), 0.8))
                .build();


    }

    public void drive() {

        /*if (opMode.gamepad1.x) {
            driveToTarget(TARGET_X, 0.4);
        } else if (opMode.gamepad1.a) {
            driveToTarget(TARGET_A, 0.5);
        } else if (opMode.gamepad1.b) {
            driveToTarget(TARGET_B, 0.4);
        } else if (opMode.gamepad1.yWasPressed()) {
            driveToTarget(TARGET_Y, 0.4);
        } else if (opMode.gamepad1.left_trigger > 0.1 && opMode.gamepad1.right_trigger > 0.1) {
            strafeToBall(robot.limelight.getBallRotationOffset(), opMode.gamepad1.left_trigger);
        } else if (opMode.gamepad1.left_trigger > 0.1 && opMode.gamepad1.right_trigger < 0.1) {
            smartCorrectAngleToShoot();
        } else {
            previousHeading = 0.0;
            driveMecanumFieldOriented(opMode.gamepad1);
        }*/
        if(opMode.gamepad1.yWasPressed()){
            automatedDrive = true;
        }
        else if (opMode.gamepad1.aWasPressed()){
            automatedDrive = false;
        }
        if (automatedDrive){
            driveToTarget(null, 0.0);
        }
        else {
            driveMecanumFieldOriented(opMode.gamepad1);
        }


        if (opMode.gamepad1.options) {
            resetFieldOriented();
        }
        if (opMode.gamepad1.right_bumper || opMode.gamepad1.left_bumper) {
            resetOdo(robot.limelight.getRobotPoseFromLL());
        } else if (opMode.gamepad1.dpad_up) {
            resetOdoCorner(resetPosition);
        }



    }
    public void initTeleOp(){
        Pose2D temp;
        if (teamColor == Robot.TeamColor.RED) {//TODO add the correct positions
            temp = new Pose2D(DistanceUnit.INCH, 0, 30, AngleUnit.RADIANS, Math.PI / 2);
        } else {
            temp = new Pose2D(DistanceUnit.INCH, 0, -30, AngleUnit.RADIANS, -Math.PI / 2);

        }
      //  odo.setPosition(temp);
    }

    public void driveMecanumFieldOriented(Gamepad gamepad) {
        follower.setTeleOpDrive(
                -opMode.gamepad1.left_stick_y,
                -opMode.gamepad1.left_stick_x,
                -opMode.gamepad1.right_stick_x,
                false
        );
        follower.update();

    }

    public void driveToTarget(Pose2D targetPose, double speed) {
      //todo implement
        follower.followPath(pathChain.get());
        automatedDrive = true;
        follower.update();
    }

    public void resetFieldOriented() {
        Pose currentPose = follower.getPose();
        follower.setStartingPose(new Pose(currentPose.getX(), currentPose.getY(), 0));
    }




    public void resetOdo(Pose2D resetPose) {

       //TODO implement

    }
    public void resetOdoCorner(Pose2D resetPose) {
        //TODO implement
        if (resetPose != null) {

           // Pose2D tempPos = new Pose2D(DistanceUnit.INCH, resetPose.getX(DistanceUnit.INCH), resetPose.getY(DistanceUnit.INCH), AngleUnit.RADIANS, 2*Math.atan(Math.tan((getYaw()+Math.PI/2)/2)));
            //Pose2D tempPos = new Pose2D(DistanceUnit.INCH, resetPose.getX(DistanceUnit.INCH), resetPose.getY(DistanceUnit.INCH), AngleUnit.RADIANS, -Math.PI/2);
            //Pose2D tempPos = new Pose2D(DistanceUnit.INCH, resetPose.getX(DistanceUnit.INCH), resetPose.getY(DistanceUnit.INCH), AngleUnit.RADIANS, 2*Math.PI*(((getYaw()+(Math.PI)/2)/(2*Math.PI))+Math.floor((getYaw()+(3*Math.PI)/2)/(2*Math.PI))));

           // odo.setPosition(resetPose);
        }
      //  odo.update();

    }

    public void printRobotPos() {
       // Pose2D pos = odo.getPosition();
       // String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.INCH), pos.getY(DistanceUnit.INCH), pos.getHeading(AngleUnit.RADIANS));
       // opMode.telemetry.addData("ROBOT Position", data);

    }


    public boolean isAtTarget(Precision precision) {
        double posTolerance,  velTolerance,  angleTolerance;
        if (precision == Precision.HIGH) {
            posTolerance = 25;
            velTolerance = 10;
            angleTolerance = 0.15;
        }
        else{
            posTolerance = 30;
            velTolerance = 75;
            angleTolerance = 0.25;
        }

       // odo.update();
       /* return Math.abs(odo.getPosX() - targetPose.getX(DistanceUnit.MM)) < posTolerance &&
                Math.abs(odo.getPosY() - targetPose.getY(DistanceUnit.MM)) < posTolerance &&
                Math.abs(odo.getPosition().getHeading(AngleUnit.RADIANS) - targetPose.getHeading(AngleUnit.RADIANS)) < angleTolerance &&
                Math.abs(odo.getVelX()) < velTolerance &&
                Math.abs(odo.getVelY()) < velTolerance;*/
        return false;
    }




    public void moveToBall(double ballOffset, double speed) {
        if (ballOffset == 0) {
            ballOffset = previousHeading;
        } else {
            previousHeading = ballOffset;
        }

        speed = (1 - Math.abs(ballOffset) / 40) * speed;

        double frontLeftPower = ballOffset / 40;
        double backLeftPower = ballOffset / 40;
        double frontRightPower = -ballOffset / 40;
        double backRightPower = -ballOffset / 40;

       /* frontLeft.setPower(frontLeftPower + speed);
        backLeft.setPower(backLeftPower + speed);
        frontRight.setPower(frontRightPower + speed);
        backRight.setPower(backRightPower + speed);*/

    }
    public void strafeToBall(double ballOffset, double speed) {
        if (ballOffset == 0) {
            ballOffset = previousHeading;
        } else {
            previousHeading = ballOffset;
        }

        speed = ((1 - Math.abs(ballOffset) / 40) * speed);

        double frontLeftPower = ballOffset / 40;
        double backLeftPower = -ballOffset / 40;
        double frontRightPower = -ballOffset / 40;
        double backRightPower = ballOffset / 40;

       /* frontLeft.setPower(frontLeftPower + speed);
        backLeft.setPower(backLeftPower + speed);
        frontRight.setPower(frontRightPower + speed);
        backRight.setPower(backRightPower + speed);*/

    }
  /*  public boolean checkIfInTriangle() {
       // odo.update();
       // double x = odo.getPosX();
       // double y = odo.getPosY();
       // return x <= -y && x <= y;

    }
*/

    public void smartCorrectAngleToShoot() {
       /* odo.update();

        //calculate angle offset
        double x = odo.getPosX() / 25.4;
        double y = odo.getPosY() / 25.4;

        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        opMode.telemetry.addData("distance x", d);
        opMode.telemetry.addData("distance y", h);
        double target = (1 * Math.PI / 2 - Math.atan(h / d)) + additionner;
        if (Math.abs(target) > Math.PI) {
            target = -2 * Math.PI + target;
        }
        target = multiplier * target;
        double error = target - odo.getPosition().getHeading(AngleUnit.RADIANS);

        //calculate driver input
        double gx = opMode.gamepad1.left_stick_x;
        double gy = -opMode.gamepad1.left_stick_y;
        double rx = opMode.gamepad1.right_stick_x;

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = gx * Math.cos(-botHeading) - gy * Math.sin(-botHeading);
        double rotY = gx * Math.sin(-botHeading) + gy * Math.cos(-botHeading);

        rotX = rotX * 1.1;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;


        frontLeft.setPower(frontLeftPower - error*2);
        backLeft.setPower(backLeftPower - error*2);
        frontRight.setPower(frontRightPower + error*2);
        backRight.setPower(backRightPower + error*2);*/
    }

    public boolean checkIfAlignedWithGoal() {
       /* odo.update();
        double tolerance = 0.25;
        double x = odo.getPosX() / 25.4;
        double y = odo.getPosY() / 25.4;

        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        opMode.telemetry.addData("distance x", d);
        opMode.telemetry.addData("distance y", h);
        double target = (1 * Math.PI / 2 - Math.atan(h / d)) + additionner;
        if (Math.abs(target) > Math.PI) {
            target = -2 * Math.PI + target;
        }
        target = multiplier * target;
        double error = target - odo.getPosition().getHeading(AngleUnit.RADIANS);
        return Math.abs(error) < tolerance;*/
        return false;

    }

    public double calculateShooterPower() {
        /*double x = odo.getPosX() / 25.4;
        double y = odo.getPosY() / 25.4;
        double d = Math.abs(targetX - x);
        double h = Math.abs(targetY - y);
        double distance = Math.sqrt(d * d + h * h);
        opMode.telemetry.addData("distance to goal", distance);

        return getShooterPower(distance);*/
        return 0.0;
    }

    public static double getShooterPower(double distanceInches) {
        return -0.00216713 * distanceInches * distanceInches * distanceInches
                + 0.6849218 * distanceInches * distanceInches
                - 63.05278661 * distanceInches
                + 2825.23407233;
    }


}
