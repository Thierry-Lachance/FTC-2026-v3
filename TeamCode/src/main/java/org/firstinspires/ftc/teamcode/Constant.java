package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Constant {
    //Limelight Name
    public static String limelightName = "limelight";

    //Webcam Name
    public static String webcamName = "Webcam 1";

    //odo offsets
    public static double xOffset = -180;
    public static double yOffset = 0;

    //Odo name
    public static String odoName = "odo";

    //IMU name
    public static String imuName = "imu";

    //ColorSensor Name
    public static String colorSensor1Name = "co1";
    public static String colorSensor2Name = "co2";

    //Motor Name
    public static String frontLeftMotorName = "fl";
    public static String backLeftMotorName = "bl";
    public static String frontRightMotorName = "fr";
    public static String backRightMotorName = "br";

    public static DcMotorSimple.Direction frontLeftMotorDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction backLeftMotorDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction frontRightMotorDirection = DcMotorSimple.Direction.FORWARD;
    public static DcMotorSimple.Direction backRightMotorDirection = DcMotorSimple.Direction.FORWARD;

    public static String shooterMotorName = "s1";
    public static String intakeMotorName = "i1";
    public static String feederMotorName = "f1";

    //Servo Name
    public static String led0Name = "l0";
    public static String led1Name = "l1";
    public static String led2Name = "l2";
    public static String chamber1Name = "c1";
    public static String chamber2Name = "c2";
    public static String chamber3Name = "c3";

    //Servo Base Position
    public static double chamber1BasePos = 1.00;
    public static double chamber2BasePos = 0.15;
    public static double chamber3BasePos = 0.05;
    //Servo Active Position
    public static double chamber1ActivePos = 0.3;
    public static double chamber2ActivePos = 0.7;
    public static double chamber3ActivePos = 0.5;

    public static double chamber1EngagedPos = 0.70;
    public static double chamber2EngagedPos = 0.40;
    public static double chamber3EngagedPos = 0.3;

    public static double shooterPowerY = -1025.0;
    public static double shooterPowerX = -1180;
    public static double shooterPowerB = -1220.0;
    public static double shooterPowerA = -1475;
    public static double shooterPowerAutoClose = -1025.0;

}
