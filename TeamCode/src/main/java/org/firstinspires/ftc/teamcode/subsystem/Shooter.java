package org.firstinspires.ftc.teamcode.subsystem;


import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.lazy;

import com.pedropathing.ivy.Command;
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

        shooterMotor = robot.opMode.hardwareMap.get(DcMotorEx.class, Constants.shooterMotorName);

        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooterMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setVelocityPIDFCoefficients(250, 2, 2, 0.0);
    }

    public Command startShooterManualCommand = instant(() -> shooterMotor.setVelocity(-1025));
    public Command stopShooterCommand = instant(() -> shooterMotor.setVelocity(0));

    public Command rampUpShooter = lazy(() -> {
        double targetVelocity = robot.aimBot.getTargetVelocity();
        return Command.build()
                .setStart(() ->{
                    shooterMotor.setVelocityPIDFCoefficients(robot.aimBot.getPidfCoefficients().p,
                            robot.aimBot.getPidfCoefficients().i,
                            robot.aimBot.getPidfCoefficients().d,
                            robot.aimBot.getPidfCoefficients().f);
                    shooterMotor.setVelocity(targetVelocity);
                })
                .setExecute(() -> {})
                .setDone(() -> shooterMotor.getVelocity() <= targetVelocity + 50 && shooterMotor.getVelocity() >= targetVelocity - 50);
    });



    public void periodic() {
        robot.opMode.telemetry.addData("target speed", targetVelocity);
        robot.opMode.telemetry.addData("shooter speed", shooterMotor.getVelocity());
    }

}
