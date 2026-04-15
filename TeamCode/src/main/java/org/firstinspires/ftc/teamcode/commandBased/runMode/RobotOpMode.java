package org.firstinspires.ftc.teamcode.commandBased.runMode;


import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.ivy.Scheduler.schedule;

import org.firstinspires.ftc.teamcode.commandBased.Robot;
import org.firstinspires.ftc.teamcode.commandBased.Util.Alliance;

public abstract class RobotOpMode extends OpMode {
    protected Robot robot;

    @Override
    public void init() {
        Scheduler.reset();
        robot = new Robot(  hardwareMap, Alliance.RED);

        schedule();
    }

    @Override
    public void init_loop() {
        Scheduler.execute();
    }

    @Override
    public void loop() {
        Scheduler.execute();
        telemetry.update();
    }
}
