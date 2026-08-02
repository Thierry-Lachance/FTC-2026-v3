package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class Intake {
    DcMotorEx intakeMotor;


    Robot robot;

    public Intake(Robot robot) {
        this.robot = robot;
        intakeMotor = robot.opMode.hardwareMap.get(DcMotorEx.class, Constants.intakeMotorName);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setCurrentAlert(4.5, CurrentUnit.AMPS);

    }

    public boolean isIntaking() {
        return intakeMotor.getPower() != 0;
    }

    public void startIntake() {

            intakeMotor.setPower(1);


    }

    public void stopIntake() {
        intakeMotor.setPower(0);
    }
}
