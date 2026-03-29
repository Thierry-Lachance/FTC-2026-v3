package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@TeleOp(name = "AUTO", group = "RED")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED, new Autonomous.Action[] {
                    Autonomous.Action.SHOOT_BALL_CLOSE_PATTERN,
                    Autonomous.Action.OPEN_GATE,
                    Autonomous.Action.PARK_GATE
        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
