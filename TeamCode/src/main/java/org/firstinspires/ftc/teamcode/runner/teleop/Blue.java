package org.firstinspires.ftc.teamcode.runner.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "BLUE", group = "COMPETITION")
public class Blue extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.BLUE);
        waitForStart();
        robot.initTeleOp();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            robot.runTeleOp();
        }
    }
}