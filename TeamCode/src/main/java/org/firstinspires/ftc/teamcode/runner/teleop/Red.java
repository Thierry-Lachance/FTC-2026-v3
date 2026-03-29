package org.firstinspires.ftc.teamcode.runner.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "RED", group = "COMPETITION")
public class Red extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            robot.run();
        }
    }
}