package org.firstinspires.ftc.teamcode.runMode;

import org.firstinspires.ftc.teamcode.Robot;
import static com.pedropathing.ivy.Scheduler.schedule;

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
        if (robot.opMode.gamepad1.options) schedule(robot.drivetrain.resetFieldOrientedCommand);
        if (robot.opMode.gamepad1.a) schedule(robot.drivetrain.resetOdoCornerCommand);
        if (robot.opMode.gamepad1.b) schedule(robot.drivetrain.resetOdoGoalCommand);

        //intake
        if (robot.opMode.gamepad1.right_trigger > 0.5) schedule(robot.automatedAction.stopIntaking);
        else schedule(robot.automatedAction.stopIntaking);

        //kicker

        if (robot.opMode.gamepad1.dpadLeftWasPressed()) schedule(robot.kicker.kickChamber1Command);
        else if (robot.opMode.gamepad1.dpadDownWasPressed()) schedule(robot.kicker.kickChamber2Command);
        else if (robot.opMode.gamepad1.dpadRightWasPressed()) schedule(robot.kicker.kickChamber3Command);
        else if (robot.opMode.gamepad1.dpadLeftWasReleased() ||
                robot.opMode.gamepad1.dpadDownWasReleased() ||
                robot.opMode.gamepad1.dpadRightWasReleased())
            schedule(robot.kicker.engageKickerCommand);

        //shooter
        if (robot.opMode.gamepad1.x) schedule(robot.shooter.startShooterManualCommand);
        if (robot.opMode.gamepad1.y) schedule(robot.shooter.stopShooterCommand);


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
