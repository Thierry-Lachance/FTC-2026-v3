package org.firstinspires.ftc.teamcode.runner.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.runMode.Auto;


@Autonomous(name = "NEW Blue", group = "NEW", preselectTeleOp = "BLUE")
public class NewBlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Auto auto = new Auto(this, Robot.TeamColor.BLUE, Auto.StartingPosition.CLOSE, new Auto.Action[]{
                Auto.Action.SHOOT_BALL_CLOSE,
                Auto.Action.PICK_LINE_1,
                Auto.Action.SHOOT_BALL_CLOSE,
                Auto.Action.PICK_LINE_2,
                Auto.Action.SHOOT_BALL_CLOSE,
                Auto.Action.PARK_GATE
        });
        auto.initAuto();//TODO check which init works
        waitForStart();
        auto.initAuto();
        if (isStopRequested()) return;
        auto.runAuto();
    }
}
