package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;

public class TeleOp {
    Robot robot;

    public TeleOp(Robot robot) {
        this.robot = robot;
    }

    public void drive() {
        robot.drivetrain.drive();
        robot.intake.intakeIn();
        robot.shooter.shoot();
        robot.kicker.kickChamber();
        robot.led.updateLed();
        robot.limelight.telemetry();
    }
}
