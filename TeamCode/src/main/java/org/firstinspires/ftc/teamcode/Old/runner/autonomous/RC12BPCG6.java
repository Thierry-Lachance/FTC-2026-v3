package org.firstinspires.ftc.teamcode.Old.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Old.Robot;
import org.firstinspires.ftc.teamcode.Old.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RC-12B-PC-G6", group = "RED")
public class RC12BPCG6 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED, new Autonomous.Action[]{
                Autonomous.Action.START_NEAR_TEAM_GOAL,
                Autonomous.Action.SHOOT_BALL_CLOSE,
                Autonomous.Action.PICK_LINE_1,
                Autonomous.Action.SHOOT_BALL_CLOSE,
                Autonomous.Action.PICK_LINE_2,
                Autonomous.Action.OPEN_GATE,
                Autonomous.Action.SHOOT_BALL_CLOSE,
                Autonomous.Action.PICK_LINE_3,
                Autonomous.Action.SHOOT_BALL_CLOSE,
                Autonomous.Action.PARK_INSIDE


        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
