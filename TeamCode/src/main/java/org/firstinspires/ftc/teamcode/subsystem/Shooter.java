package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

public class Shooter {
    DcMotorEx shooterMotor;
    LinearOpMode opMode;
    Robot robot;
    double targetVelocity = 0.0;
    boolean shooting = false;


    public Shooter(LinearOpMode opMode, Robot robot) {
        this.opMode = opMode;
        this.robot = robot;

        shooterMotor = opMode.hardwareMap.get(DcMotorEx.class, Constant.shooterMotorName);

        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setVelocityPIDFCoefficients(250, 2, 2, 0.0);
    }

    public void shoot() {

        if (opMode.gamepad2.x) {
            shooting = true;
            shooterMotor.setVelocityPIDFCoefficients(robot.aimBot.getPidfCoefficients().p,
                    robot.aimBot.getPidfCoefficients().i,
                    robot.aimBot.getPidfCoefficients().d,
                    robot.aimBot.getPidfCoefficients().f);
            targetVelocity = robot.aimBot.getTargetVelocity();
        } else if (opMode.gamepad2.y) {
            shooting = false;
        }
        if (shooting) {
            shooterMotor.setVelocity(targetVelocity);
        } else {

            stopShooter();
        }
        opMode.telemetry.addData("target speed", targetVelocity);
        opMode.telemetry.addData("shooter speed", shooterMotor.getVelocity());

    }


    public void autonomousStartShooterClose() {
        targetVelocity = Constant.shooterPowerAutoClose;
        shooterMotor.setVelocityPIDFCoefficients(250, 2, 2, 0.0);
        shooterMotor.setVelocity(targetVelocity);
    }
    public void autonomousStartShooterFar() {
        targetVelocity = -1475;
        shooterMotor.setVelocityPIDFCoefficients(250, 2, 2, 0.0);
        shooterMotor.setVelocity(targetVelocity);
    }

    public void stopShooter() {
        shooterMotor.setVelocity(0);
    }

    public boolean isReadyToShoot() {
        return shooterMotor.getVelocity() <= targetVelocity + 50 && shooterMotor.getVelocity() >= targetVelocity - 50;
    }


}
