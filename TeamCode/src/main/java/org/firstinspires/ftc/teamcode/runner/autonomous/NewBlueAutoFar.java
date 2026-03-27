package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Auto;


@Autonomous(name = "NEW Blue Far", group = "NEW", preselectTeleOp = "BLUE")
public class NewBlueAutoFar extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Auto auto = new Auto(this, Robot.TeamColor.BLUE, Auto.StartingPosition.FAR, new Auto.Action[]{
                Auto.Action.SHOOT_BALL_FAR,
                Auto.Action.PARK_OUTSIDE
        });
        auto.initAuto();//TODO check which init works
        waitForStart();
        auto.initAuto();
        sleep(20000);
        if (isStopRequested()) return;
        auto.runAuto();
    }
}
