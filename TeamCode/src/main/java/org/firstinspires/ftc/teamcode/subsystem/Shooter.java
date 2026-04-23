package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class Shooter {
    DcMotorEx shooterMotor;
    Robot robot;
    double targetVelocity = 0.0;

    public Shooter(Robot robot) {

        this.robot = robot;

        if(robot.teamColor == Robot.TeamColor.RED) {
            shooterMotor = robot.opMode.hardwareMap.get(DcMotorEx.class, Constants.shooterMotor1Name);
        } else {
            shooterMotor = robot.opMode.hardwareMap.get(DcMotorEx.class, Constants.shooterMotor2Name);
        }

        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setVelocityPIDFCoefficients(250, 2, 2, 0.0);
    }

    public void autoStartShooter() {
        targetVelocity = robot.aimBot.getTargetVelocity();
        shooterMotor.setVelocityPIDFCoefficients(robot.aimBot.getPidfCoefficients().p,
                robot.aimBot.getPidfCoefficients().i,
                robot.aimBot.getPidfCoefficients().d,
                robot.aimBot.getPidfCoefficients().f);
        shooterMotor.setVelocity(targetVelocity);
    }

    public void startShooterManual() {
        shooterMotor.setVelocity(-1025);
    }

    public void stopShooter() {
        shooterMotor.setVelocity(0);
    }

    public boolean isReadyToShoot() {
        return shooterMotor.getVelocity() <= targetVelocity + 50 && shooterMotor.getVelocity() >= targetVelocity - 50;
    }

    public void periodic() {
        robot.opMode.telemetry.addData("target speed", targetVelocity);
        robot.opMode.telemetry.addData("shooter speed", shooterMotor.getVelocity());
    }

}
