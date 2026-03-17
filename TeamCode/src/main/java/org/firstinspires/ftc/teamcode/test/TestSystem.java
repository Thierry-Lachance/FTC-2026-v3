package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Test system", group = "TEST")
public class TestSystem extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            robot.shooter.autonomousStartShooterClose();
            sleep(2000);
            if (robot.shooter.isReadyToShoot()) {
                robot.led.setLed1Color(0.5);
            }
            sleep(2000);
            robot.shooter.stopShooter();
            robot.intake.startIntake();
            sleep(2000);
            robot.intake.stopIntake();
            robot.kicker.engageKicker();
            sleep(1000);
            robot.kicker.kickChamber1();
            sleep(1000);
            robot.kicker.kickChamber2();
            sleep(1000);
            robot.kicker.kickChamber3();
            sleep(1000);
            robot.kicker.lowerKicker();
            sleep(1000);
        }
    }
}
