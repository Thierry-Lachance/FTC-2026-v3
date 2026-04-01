package org.firstinspires.ftc.teamcode;

import com.pedropathing.geometry.Pose;
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
import org.firstinspires.ftc.teamcode.subsystem.Lidar;
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
    public final Lidar lidar;

    public final AimBot aimBot;
    public final PathManager pathManager;

    public TeleOp teleOp;
    public Autonomous autonomous;
    public final AutomatedAction automatedAction;

    private ColorPattern matchColorPattern = ColorPattern.UNKNOWN;
    private ColorPattern patternInsideRobot = ColorPattern.UNKNOWN;
    public final TeamColor teamColor;
    public final RunMode runMode;

    public enum TeamColor {
        RED,
        BLUE
    }

    public enum ColorPattern {
        GPP,
        PGP,
        PPG,
        UNKNOWN
    }

    public enum RunMode {
        TELEOP,
        AUTONOMOUS
    }


    public Robot(LinearOpMode opMode, TeamColor teamColor) {
        this.opMode = opMode;
        this.teamColor = teamColor;
        this.runMode = RunMode.TELEOP;
        drivetrain = new Drivetrain(this, new Pose(0,0,0));
        intake = new Intake(this);
        shooter = new Shooter(this);
        kicker = new Kicker(this);
        led = new Led(this);
        vision = new Vision(this);
        limelight = new Limelight(this);
        lidar = new Lidar(this);
        aimBot = new AimBot();
        teleOp = new TeleOp(this);
        automatedAction = new AutomatedAction(this);
        pathManager = new PathManager(this);

    }

    public Robot(LinearOpMode opMode, TeamColor teamColor, PathManager.StartingPosition startingPosition, Autonomous.Action[] actionList) {
        this.opMode = opMode;
        this.teamColor = teamColor;
        this.runMode = RunMode.AUTONOMOUS;

        intake = new Intake(this);
        shooter = new Shooter(this);
        kicker = new Kicker(this);
        led = new Led(this);
        vision = new Vision(this);
        limelight = new Limelight(this);
        lidar = new Lidar(this);
        aimBot = new AimBot();
        autonomous = new Autonomous(this, actionList);
        automatedAction = new AutomatedAction(this);
        pathManager = new PathManager(this);
        drivetrain = new Drivetrain(this, pathManager.getStartingPose(startingPosition));


    }

    public ColorPattern getMatchColorPattern() {
        return matchColorPattern;
    }

    public ColorPattern getPatternInsideRobot() {
        return patternInsideRobot;
    }

    public void setMatchColorPattern(ColorPattern matchColorPattern) {
        this.matchColorPattern = matchColorPattern;
    }

    public void setPatternInsideRobot(ColorPattern patternInsideRobot) {
        this.patternInsideRobot = patternInsideRobot;
    }

    public void run(){
        if(runMode == RunMode.TELEOP) {
            teleOp.run();
        } else {
            autonomous.run();
        }
    }

    public boolean timeToStop() {
        periodic();
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
        lidar.update();
        opMode.telemetry.update();
    }


}
