package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "AUTO", group = "RED")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED, Robot.RunMode.AUTONOMOUS);
        waitForStart();
        if (isStopRequested()) return;
        robot.teleOp.run();
    }
}
