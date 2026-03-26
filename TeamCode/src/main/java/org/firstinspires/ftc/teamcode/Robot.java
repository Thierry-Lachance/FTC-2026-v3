package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.AimBot;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Kicker;
import org.firstinspires.ftc.teamcode.subsystem.Led;
import org.firstinspires.ftc.teamcode.subsystem.Limelight;
import org.firstinspires.ftc.teamcode.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.subsystem.Vision;


public class Robot {
    LinearOpMode opMode;

    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public Kicker kicker;
    public Led led;
    public Vision vision;
    public Limelight limelight;

    public AimBot aimBot;
    private ColorPattern colorPattern;

    public enum TeamColor {
        RED,
        BLUE
    }

    public enum ColorPattern {
        GPP,
        PGP,
        PPG
    }

    public Robot(LinearOpMode opMode, TeamColor teamColor) {
        this.opMode = opMode;
        drivetrain = new Drivetrain(opMode, this, teamColor);
        intake = new Intake(opMode);
        shooter = new Shooter(opMode, this);
        kicker = new Kicker(opMode, this);
        led = new Led(opMode, this);
        vision = new Vision(opMode);
        limelight = new Limelight(opMode, this);
        aimBot = new AimBot(teamColor, drivetrain.getFollower());

    }

    public void runTeleOp() {
        if (opMode.gamepad1.dpad_right || opMode.gamepad1.dpad_left) {
            automatedCycle();

        } else {
            manualDrive();

        }

        opMode.telemetry.update();


    }

    public void initTeleOp() {
        drivetrain.initTeleOp();
    }


    public ColorPattern getColorPattern() {
        return colorPattern;
    }

    public void setColorPattern(ColorPattern colorPattern) {
        this.colorPattern = colorPattern;
    }

    private void manualDrive() {
        drivetrain.drive();
        intake.intakeIn();
        shooter.shoot();
        kicker.kickChamber();
        led.updateLed();
        limelight.telemetry();
    }

    private void automatedCycle() {
        aimBot.setTargets(1);
        drivetrain.driveToTargetAuto(drivetrain.pathChainPreHuman.get(), 0);
        intake.startIntake();
        kicker.lowerKicker();
        drivetrain.driveToTargetAuto(drivetrain.pathChainPostHuman.get(), 0);
        kicker.engageKicker();
        shooter.autonomousStartShooterClose();
        if(timeToStop()) return;
        opMode.sleep(200);
        intake.stopIntake();
        if(opMode.gamepad1.right_trigger > 0.5){
            drivetrain.driveToTargetAuto(drivetrain.rightPath, 0);
        } else if (opMode.gamepad1.left_trigger > 0.5){
            drivetrain.driveToTargetAuto(aimBot.getPathToTarget(), 0);

        }
        else{
            drivetrain.driveToTargetAuto(drivetrain.leftPath, 0);
        }


        kicker.kickChamberAuto();
        shooter.stopShooter();
        if (opMode.gamepad1.dpad_left) {
            drivetrain.driveToTargetAuto(drivetrain.pathChainGate.get(), 500);

        }
    }

    public boolean timeToStop(){
        return !opMode.gamepad1.dpad_left && !opMode.gamepad1.dpad_right;
    }


}
