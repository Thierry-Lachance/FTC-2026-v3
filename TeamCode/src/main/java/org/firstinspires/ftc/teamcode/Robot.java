package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.AimBot;
import org.firstinspires.ftc.teamcode.runMode.AssistedDrive;
import org.firstinspires.ftc.teamcode.runMode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Kicker;
import org.firstinspires.ftc.teamcode.subsystem.Led;
import org.firstinspires.ftc.teamcode.subsystem.Limelight;
import org.firstinspires.ftc.teamcode.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.subsystem.Vision;


public class Robot {
    public final LinearOpMode opMode;

    public final Drivetrain drivetrain;
    public final Intake intake;
    public final Shooter shooter;
    public final Kicker kicker;
    public final Led led;
    public final Vision vision;
    public final Limelight limelight;

    public final AimBot aimBot;

    public final TeleOp teleOp;
    public final AssistedDrive assistedDrive;

    private ColorPattern colorPattern;
    public final TeamColor teamColor;
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
        this.teamColor = teamColor;
        drivetrain = new Drivetrain(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        kicker = new Kicker(this);
        led = new Led(this);
        vision = new Vision(this);
        limelight = new Limelight(this);
        aimBot = new AimBot(teamColor, drivetrain.getFollower());
        teleOp = new TeleOp(this);
        assistedDrive = new AssistedDrive(this);


    }

    public void runTeleOp() {
        if (opMode.gamepad1.dpad_right || opMode.gamepad1.dpad_left) {
            assistedDrive.drive();

        } else {
           teleOp.drive();
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

    public boolean timeToStop(){
        return !opMode.gamepad1.dpad_left && !opMode.gamepad1.dpad_right;
    }


}
