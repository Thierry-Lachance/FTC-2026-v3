package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

public class Intake {
    DcMotor intakeMotor;
    DcMotor feederMotor;

    Robot robot;

    public Intake(Robot robot) {
        this.robot = robot;
        intakeMotor = robot.opMode.hardwareMap.get(DcMotor.class, Constant.intakeMotorName);
        feederMotor = robot.opMode.hardwareMap.get(DcMotor.class, Constant.feederMotorName);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        feederMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        feederMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        feederMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void intakeIn() {
        intakeMotor.setPower(robot.opMode.gamepad1.right_trigger);
        feederMotor.setPower(robot.opMode.gamepad1.right_trigger);
    }

    public boolean isIntaking() {
        return intakeMotor.getPower() != 0;
    }

    public void startIntake() {
        intakeMotor.setPower(1);
        feederMotor.setPower(1);
    }

    public void stopIntake() {
        intakeMotor.setPower(0);
        feederMotor.setPower(0);
    }
}
