package org.firstinspires.ftc.teamcode.runner.autonomous.blue.close;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "BC-12B-PG-G6", group = "BLUE")
public class BC12BPGG6 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.BLUE, new Autonomous.Action[]{
                Autonomous.Action.START_NEAR_TEAM_GOAL,
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
