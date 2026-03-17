package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;

@TeleOp(name = "Test Drivetrain", group = "TEST")
public class TestDrivetrain extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            robot.led.setLed1Color(0.3);
            do {
                robot.drivetrain.driveToTarget(new Pose2D(DistanceUnit.MM, 250, 250, AngleUnit.DEGREES, 90), 0.5);
            }
        while (!robot.drivetrain.isAtTarget(Drivetrain.Precision.HIGH));
            robot.led.setLed1Color(0.5);
            sleep(10000);
        }
    }
}
