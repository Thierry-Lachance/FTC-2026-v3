package org.firstinspires.ftc.teamcode.Old.runMode;

import org.firstinspires.ftc.teamcode.Old.Robot;
import org.firstinspires.ftc.teamcode.Old.pathing.PathManager;

public class AutomatedAction {
    Robot robot;

    public AutomatedAction(Robot robot) {
        this.robot = robot;

    }

    public void shootClose() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(robot.aimBot.getDestination(), getDivert()), 0);
        robot.kicker.kickChamberAutoClose();
        if (robot.runMode == Robot.RunMode.TELEOP) robot.shooter.stopShooter();

    }

    public void shootClosePattern() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(robot.aimBot.getDestination(), getDivert()), 0);
        robot.kicker.kickChamberAutoPattern(robot.getPatternInsideRobot());
        robot.shooter.stopShooter();

    }

    public void shootFar() {
        robot.aimBot.setTargets(2);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(robot.aimBot.getDestination(), getDivert()), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();

    }

    public void shootCenter() {
        robot.aimBot.setTargets(0);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(robot.aimBot.getDestination(), getDivert()), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    public void shootOpp() {
        robot.aimBot.setTargets(3);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(robot.aimBot.getDestination(), getDivert()), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }

    public void openGate() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(PathManager.DestinationTeleop.GATE, getDivert()), 0);
        robot.drivetrain.getFollower().update();
    }


    public void intakeHuman() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(PathManager.DestinationTeleop.HUMAN_BEFORE_INTAKING, getDivert()), 0);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathTeleop(PathManager.DestinationTeleop.HUMAN_AFTER_INTAKING, getDivert()), 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
    }

    public void parkInsideAuto() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PARK_INSIDE), 0);
    }

    public void parkOutsideAuto() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PARK_OUTSIDE), 0);
    }

    public void parkGateAuto() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PARK_GATE), 0);
    }

    public void intakeLine1Auto() {
        robot.drivetrain.getFollower().setMaxPower(0.8);
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PRE_LINE_1), false, 0);
        robot.drivetrain.getFollower().setMaxPower(0.4);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.POST_LINE_1), false, 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
        robot.drivetrain.getFollower().setMaxPower(1.0);
        robot.setPatternInsideRobot(Robot.ColorPattern.GPP);

    }

    public void intakeLine2Auto() {
        robot.drivetrain.getFollower().setMaxPower(0.7);
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PRE_LINE_2), false, 0);
        robot.drivetrain.getFollower().setMaxPower(0.4);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.POST_LINE_2), false, 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
        robot.drivetrain.getFollower().setMaxPower(1.0);
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PRE_LINE_2), false, 0);
        robot.setPatternInsideRobot(Robot.ColorPattern.PGP);


    }

    public void intakeLine3Auto() {
        robot.drivetrain.getFollower().setMaxPower(0.7);
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.PRE_LINE_3), false, 0);
        robot.drivetrain.getFollower().setMaxPower(0.4);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.POST_LINE_3), false, 0);
        robot.kicker.engageKicker();
        if (robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
        robot.drivetrain.getFollower().setMaxPower(1.0);
        robot.setPatternInsideRobot(Robot.ColorPattern.PPG);

    }

    public void openGateAuto() {
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.GATE_AUTO), 0);
        robot.drivetrain.getFollower().update();
        robot.drivetrain.makeTheRobotJumpForward();
    }

    public void shootCloseAuto() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.NEAR_TEAM_GOAL), 0);
        robot.kicker.kickChamberAutoClose();
        if (robot.runMode == Robot.RunMode.TELEOP) robot.shooter.stopShooter();

    }

    public void shootClosePatternAuto() {
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.NEAR_TEAM_GOAL), 0);
        if (robot.getMatchColorPattern() == Robot.ColorPattern.UNKNOWN)
            robot.setMatchColorPattern(robot.vision.detectPattern());
        robot.kicker.kickChamberAutoPattern(robot.getPatternInsideRobot());
        robot.shooter.stopShooter();

    }

    public void shootFarAuto() {
        robot.aimBot.setTargets(2);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.pathManager.getPathAuto(PathManager.DestinationAuto.FAR_ZONE), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();

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
