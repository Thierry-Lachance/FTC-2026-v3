package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Constant;

public class Intake {
    DcMotor intakeMotor;
    DcMotor feederMotor;
    LinearOpMode opMode;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, Constant.intakeMotorName);
        feederMotor = opMode.hardwareMap.get(DcMotor.class, Constant.feederMotorName);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        feederMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        feederMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        feederMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void intakeIn() {
        intakeMotor.setPower(opMode.gamepad1.right_trigger);
        feederMotor.setPower(opMode.gamepad1.right_trigger);
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
