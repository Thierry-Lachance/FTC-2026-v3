package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.PathManager;

public class AutomatedAction {
    Robot robot;

    public AutomatedAction(Robot robot) {
        this.robot = robot;

    }

    public void shootClose() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoClose();
        robot.shooter.stopShooter();

    }

    public void shootFar() {
        robot.aimBot.setTargets(2);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();

    }

    public void shootCenter() {
        robot.aimBot.setTargets(0);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    public void shootOpp() {
        robot.aimBot.setTargets(3);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(robot.aimBot.getDestination(), getDivert()), 0);//todo test this shit
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    public void openGate() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.GATE, getDivert()), 500);
    }

    public void intakeHuman() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.HUMAN_BEFORE_INTAKING, getDivert()), 0);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.HUMAN_AFTER_INTAKING, getDivert()), 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
    }

    public void parkInside() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.PARK_INSIDE, getDivert()), 0);
    }

    public void parkOutside() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.PARK_OUTSIDE, getDivert()), 0);
    }

    public void parkGate() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPath(PathManager.Destination.PARK_GATE, getDivert()), 0);
    }

    private PathManager.Divert getDivert() {
        if (robot.runMode == Robot.RunMode.AUTONOMOUS) return PathManager.Divert.NONE;
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
