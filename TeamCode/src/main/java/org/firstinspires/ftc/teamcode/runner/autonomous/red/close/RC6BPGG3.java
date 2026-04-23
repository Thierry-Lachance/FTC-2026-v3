package org.firstinspires.ftc.teamcode.runner.autonomous.red.close;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RC-6B-PG-G3", group = "RED")
public class RC6BPGG3 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED, new Autonomous.Action[]{
                Autonomous.Action.START_NEAR_TEAM_GOAL,
                Autonomous.Action.SHOOT_BALL_CLOSE_PATTERN,
                Autonomous.Action.PICK_LINE_1,
                Autonomous.Action.OPEN_GATE,
                Autonomous.Action.SHOOT_BALL_CLOSE_PATTERN,
                Autonomous.Action.PARK_GATE


        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
