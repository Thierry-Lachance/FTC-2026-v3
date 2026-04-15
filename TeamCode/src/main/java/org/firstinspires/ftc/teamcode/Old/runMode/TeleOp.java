package org.firstinspires.ftc.teamcode.Old.runMode;

import org.firstinspires.ftc.teamcode.Old.Robot;

public class TeleOp {
    Robot robot;

    public TeleOp(Robot robot) {
        this.robot = robot;
    }

    private boolean assistedDriving = false;

    private void manualDrive() {
        //drivetrain
        if (robot.opMode.gamepad1.left_trigger > 0.1 && robot.opMode.gamepad1.right_trigger > 0.1)
            robot.drivetrain.strafeToBall(robot.limelight.getBallOffset(), robot.opMode.gamepad1.left_trigger);
        else robot.drivetrain.driveMecanumFieldOriented();
        if (robot.opMode.gamepad1.options) robot.drivetrain.resetFieldOriented();
        if (robot.opMode.gamepad1.a) robot.drivetrain.resetOdoCorner();
        if (robot.opMode.gamepad1.b) robot.drivetrain.resetOdoGoal();

        //intake
        if (robot.opMode.gamepad1.right_trigger > 0.5) robot.intake.startIntake();
        else robot.intake.stopIntake();

        //kicker
        if (robot.intake.isIntaking()) robot.kicker.lowerKicker();
        else if (robot.opMode.gamepad1.dpad_left) robot.kicker.kickChamber1();
        else if (robot.opMode.gamepad1.dpad_down) robot.kicker.kickChamber2();
        else if (robot.opMode.gamepad1.dpad_right) robot.kicker.kickChamber3();
        else robot.kicker.engageKicker();

        //shooter
        if (robot.opMode.gamepad1.x) robot.shooter.startShooterManual();
        if (robot.opMode.gamepad1.y) robot.shooter.stopShooter();


    }

    private void automatedDrive() {
        if (robot.opMode.gamepad2.dpad_down) robot.automatedAction.intakeHuman();
        if (robot.opMode.gamepad2.dpad_up) robot.automatedAction.openGate();
        if (robot.opMode.gamepad2.y) robot.automatedAction.shootClose();
        if (robot.opMode.gamepad2.a) robot.automatedAction.shootFar();
        if (robot.opMode.gamepad2.x) robot.automatedAction.shootCenter();
        if (robot.opMode.gamepad2.b) robot.automatedAction.shootOpp();
    }

    public void run() {
        if (assistedDriving) {
            automatedDrive();
            if (robot.timeToStop()) assistedDriving = false;

        } else {
            manualDrive();
            if (robot.opMode.gamepad1.dpad_up) assistedDriving = true;
        }
        robot.periodic();


    }

}
