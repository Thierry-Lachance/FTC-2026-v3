package org.firstinspires.ftc.teamcode.Old.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Old.Robot;


@TeleOp(name = "Zero Kicker", group = "TEST")
public class ZeroKicker extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this, Robot.TeamColor.RED);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            robot.led.setLed1Color(0.5);
            robot.kicker.zeroKicker();
        }
    }
}
