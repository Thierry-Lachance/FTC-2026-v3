package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.PathManager;

public class AssistedDrive {
    Robot robot;

    public AssistedDrive(Robot robot) {
        this.robot = robot;

    }

    public void drive() {
        if (robot.opMode.gamepad2.dpad_down) intakeHuman();
        if (robot.opMode.gamepad2.dpad_up) openGate();
        if (robot.opMode.gamepad2.y) shootClose();
        if (robot.opMode.gamepad2.a) shootFar();
        if (robot.opMode.gamepad2.x) shootCenter();
        if (robot.opMode.gamepad2.b) shootOpp();

    }

    private void shootClose() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoClose();
        robot.shooter.stopShooter();

    }

    private void shootFar() {
        robot.aimBot.setTargets(2);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();

    }

    private void shootCenter() {
        robot.aimBot.setTargets(0);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    private void shootOpp() {
        robot.aimBot.setTargets(3);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    private void openGate() {
        robot.drivetrain.driveToTargetAuto(robot.drivetrain.pathChainGate, 500);
    }

    private void intakeHuman() {
        robot.drivetrain.driveToTargetAuto(robot.drivetrain.pathChainPreHuman, 0);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.drivetrain.pathChainPostHuman, 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
    }

    private PathManager.Divert getDivert() {
        if (robot.teamColor == Robot.TeamColor.RED) {
            if (robot.opMode.gamepad1.right_trigger > 0.5) return PathManager.Divert.BOTTOM;
            else if (robot.opMode.gamepad1.left_trigger > 0.5) return PathManager.Divert.TOP;
            else return PathManager.Divert.NONE;
        } else {
            if (robot.opMode.gamepad1.right_trigger > 0.5) return PathManager.Divert.TOP;
            else if (robot.opMode.gamepad1.left_trigger > 0.5) return PathManager.Divert.BOTTOM;
            else return PathManager.Divert.NONE;

        }
    }

}
