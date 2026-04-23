package org.firstinspires.ftc.teamcode.runner.autonomous.blue.far;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "BC-3B-PF-GNA", group = "BLUE")
public class BC3BPFGNA extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.BLUE, new Autonomous.Action[]{
                Autonomous.Action.START_FAR,
                Autonomous.Action.SHOOT_BALL_FAR,
                Autonomous.Action.PARK_OUTSIDE


        });
        waitForStart();
        if (isStopRequested()) return;
        robot.run();
    }
}
