package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

    private ColorPattern colorPattern;

    public enum TeamColor {
        RED,
        BLUE
    }

    public enum ColorPattern{
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

    }

    public void runTeleOp() {
        drivetrain.drive();
        intake.intakeIn();
        shooter.shoot();
        kicker.kickChamber();
        led.updateLed();
        limelight.telemetry();
        opMode.telemetry.update();


    }
    public void initTeleOp(){
        drivetrain.initTeleOp();
    }


    public ColorPattern getColorPattern(){
        return colorPattern;
    }

    public void setColorPattern(ColorPattern colorPattern){
        this.colorPattern = colorPattern;
    }

}
