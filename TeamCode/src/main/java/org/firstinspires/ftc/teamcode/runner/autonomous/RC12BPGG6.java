package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.pathing.PathManager;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RC-12B-PG-G6", group = "RED")
public class RC12BPGG6 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED, PathManager.StartingPosition.NEAR_TEAM_GOAL, new Autonomous.Action[] {
                    Autonomous.Action.SHOOT_BALL_CLOSE,
                    Autonomous.Action.PICK_LINE_1,
                    Autonomous.Action.SHOOT_BALL_CLOSE,
                    Autonomous.Action.PICK_LINE_2,
                    Autonomous.Action.OPEN_GATE,
                    Autonomous.Action.SHOOT_BALL_CLOSE,
                    Autonomous.Action.PICK_LINE_3,
                    Autonomous.Action.SHOOT_BALL_CLOSE,
                    Autonomous.Action.PARK_GATE


        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
