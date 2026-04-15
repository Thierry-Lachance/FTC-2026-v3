package org.firstinspires.ftc.teamcode.subsystem;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class Intake {
    DcMotor intakeMotor;


    Robot robot;

    public Intake(Robot robot) {
        this.robot = robot;
        intakeMotor = robot.opMode.hardwareMap.get(DcMotor.class, Constants.intakeMotorName);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public Command startIntakeCommand = instant(() -> intakeMotor.setPower(1));
    public Command stopIntakeCommand = instant(() -> intakeMotor.setPower(0));
}
