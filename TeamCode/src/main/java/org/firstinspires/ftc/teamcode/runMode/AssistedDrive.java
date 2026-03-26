package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;

public class AssistedDrive {
    Robot robot;
    public AssistedDrive(Robot robot) {
        this.robot = robot;

    }
    public void drive() {
        robot.aimBot.setTargets(1);
        robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainPreHuman.get(), 0);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainPostHuman.get(), 0);
        robot.kicker.engageKicker();
        robot.shooter.autonomousStartShooterClose();
        if( robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
        if( robot.opMode.gamepad1.right_trigger > 0.5){
            robot.drivetrain.driveToTargetAuto( robot.drivetrain.rightPath, 0);
        } else if ( robot.opMode.gamepad1.left_trigger > 0.5){
            robot.drivetrain.driveToTargetAuto( robot.aimBot.getPathToTarget(), 0);

        }
        else{
            robot.drivetrain.driveToTargetAuto( robot.drivetrain.leftPath, 0);
        }


        robot.kicker.kickChamberAuto();
        robot.shooter.stopShooter();
        if ( robot.opMode.gamepad1.dpad_left) {
            robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainGate.get(), 500);

        }
    }
}
