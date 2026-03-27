package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;

public class AssistedDrive {
    Robot robot;
    public AssistedDrive(Robot robot) {
        this.robot = robot;

    }
    public void drive() {
        if(robot.opMode.gamepad2.dpad_down)intakeHuman();
        if(robot.opMode.gamepad2.dpad_up) openGate();
        if(robot.opMode.gamepad2.y) shootClose();
        if(robot.opMode.gamepad2.a) shootFar();
        if(robot.opMode.gamepad2.x) shootCenter();
        if(robot.opMode.gamepad2.b) shootOpp();

    }

    private void shootClose(){
        robot.aimBot.setTargets(1);
        robot.shooter.autoStartShooter();
        if( robot.opMode.gamepad1.right_trigger > 0.5){
            robot.drivetrain.driveToTargetAuto( robot.drivetrain.rightPath, 0);
        } else if ( robot.opMode.gamepad1.left_trigger > 0.5){
            robot.drivetrain.driveToTargetAuto(robot.drivetrain.leftPath , 0);

        }
        else{
            robot.drivetrain.driveToTargetAuto(robot.aimBot.getPathToTarget(), 0);
        }


        robot.kicker.kickChamberAutoClose();
        robot.shooter.stopShooter();

    }
    private void shootFar(){
        robot.aimBot.setTargets(2);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.aimBot.getPathToTarget(), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();

    }
    private void shootCenter(){
        robot.aimBot.setTargets(0);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.aimBot.getPathToTarget(), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }
    private void shootOpp(){
        robot.aimBot.setTargets(3);
        robot.shooter.autoStartShooter();
        robot.drivetrain.driveToTargetAuto(robot.aimBot.getPathToTarget(), 0);
        robot.kicker.kickChamberAutoFar();
        robot.shooter.stopShooter();
    }
    private void openGate(){
            robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainGate.get(), 500);
    }
    private void intakeHuman(){
        robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainPreHuman.get(), 0);
        robot.intake.startIntake();
        robot.kicker.lowerKicker();
        robot.drivetrain.driveToTargetAuto( robot.drivetrain.pathChainPostHuman.get(), 0);
        robot.kicker.engageKicker();
        if( robot.timeToStop()) return;
        robot.opMode.sleep(200);
        robot.intake.stopIntake();
    }

}
