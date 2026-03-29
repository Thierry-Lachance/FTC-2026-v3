package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.AimBot;
import org.firstinspires.ftc.teamcode.pathing.PathManager;
import org.firstinspires.ftc.teamcode.runMode.AutomatedAction;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;
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
    public final PathManager pathManager;

    public final TeleOp teleOp;
    public final Autonomous autonomous;
    public final AutomatedAction automatedAction;

    private ColorPattern colorPattern;
    public final TeamColor teamColor;
    public final RunMode runMode;

    public enum TeamColor {
        RED,
        BLUE
    }

    public enum ColorPattern {
        GPP,
        PGP,
        PPG
    }

    public enum RunMode {
        TELEOP,
        AUTONOMOUS
    }


    public Robot(LinearOpMode opMode, TeamColor teamColor, RunMode runMode) {
        this.opMode = opMode;
        this.teamColor = teamColor;
        this.runMode = runMode;
        drivetrain = new Drivetrain(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        kicker = new Kicker(this);
        led = new Led(this);
        vision = new Vision(this);
        limelight = new Limelight(this);
        aimBot = new AimBot();
        teleOp = new TeleOp(this);
        autonomous = new Autonomous(this);
        automatedAction = new AutomatedAction(this);
        pathManager = new PathManager(this);

    }

    public ColorPattern getColorPattern() {
        return colorPattern;
    }

    public void setColorPattern(ColorPattern colorPattern) {
        this.colorPattern = colorPattern;
    }

    public boolean timeToStop() {
        if (Math.abs(opMode.gamepad1.left_stick_y) > 0.4 || Math.abs(opMode.gamepad1.left_stick_x) > 0.4 || Math.abs(opMode.gamepad1.right_stick_y) > 0.4) {
            drivetrain.getFollower().startTeleopDrive();
            return true;
        }
        return false;
    }

    public void periodic() {
        drivetrain.periodic();
        shooter.periodic();
        led.updateLed();
        limelight.telemetry();
        opMode.telemetry.update();
    }


}
