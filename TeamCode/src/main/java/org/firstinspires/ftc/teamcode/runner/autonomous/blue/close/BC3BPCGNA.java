package org.firstinspires.ftc.teamcode.runner.autonomous.blue.close;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "BC-3B-PC-GNA", group = "BLUE")
public class BC3BPCGNA extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.BLUE, new Autonomous.Action[]{
                Autonomous.Action.START_NEAR_TEAM_GOAL,
                Autonomous.Action.SHOOT_BALL_CLOSE_PATTERN,
                Autonomous.Action.PARK_INSIDE


        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
