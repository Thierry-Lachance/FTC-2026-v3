package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.runMode.Auto;
import org.firstinspires.ftc.teamcode.Robot;


@Autonomous(name = "NEW red FAR", group = "NEW", preselectTeleOp = "RED")
public class NewRedAutoFar extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Auto auto = new Auto(this, Robot.TeamColor.RED, Auto.StartingPosition.FAR, new Auto.Action[]{
                Auto.Action.PARK_OUTSIDE,
                Auto.Action.SHOOT_BALL_FAR

        });
        auto.initAuto();//TODO check which init works
        waitForStart();
        auto.initAuto();
        if (isStopRequested()) return;
        auto.runAuto();
    }
}
