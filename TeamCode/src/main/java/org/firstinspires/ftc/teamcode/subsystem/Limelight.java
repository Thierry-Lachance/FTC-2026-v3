package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.List;

public class Limelight {
    Limelight3A limelight;
    LinearOpMode opMode;
    Robot robot;

    boolean greenWasDetected = false;
    boolean purpleWasDetected = true;

    int numberOfConsecutiveMisses = 0;

    public Limelight(LinearOpMode opMode, Robot robot) {
        this.opMode = opMode;
        this.robot = robot;
        limelight = opMode.hardwareMap.get(Limelight3A.class, Constant.limelightName);

        setLimelightPipeline(0);
        limelight.setPollRateHz(100);
        limelight.start();
    }

    public void setLimelightPipeline(int pipeline) {
        if (limelight.getStatus().getPipelineIndex() != pipeline) {
            limelight.pipelineSwitch(pipeline);
        }
    }

    public void telemetry() {
        LLStatus status = limelight.getStatus();
        opMode.telemetry.addData("Name", "%s",
                status.getName());
        opMode.telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        opMode.telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            opMode.telemetry.addData("LL Latency", captureLatency + targetingLatency);
            opMode.telemetry.addData("Parse Latency", parseLatency);


            // Access color results
            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
            for (LLResultTypes.ColorResult cr : colorResults) {
                opMode.telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
            }
        } else {
            opMode.telemetry.addData("Limelight", "No data available");
        }

        opMode.telemetry.update();
    }

    public double getBallRotationOffset() {
        if (!opMode.gamepad1.right_bumper || !opMode.gamepad1.left_bumper) {
            if (purpleWasDetected) {//if purple detected track purple
                setLimelightPipeline(0);
                LLResult result = limelight.getLatestResult();
                if (result.isValid()) {
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    if (!colorResults.isEmpty()) {
                        purpleWasDetected = true;
                        for (LLResultTypes.ColorResult cr : colorResults) {
                            opMode.telemetry.addData("returned", cr.getTargetXDegrees());
                            return cr.getTargetXDegrees();
                        }
                    }

                } else {
                    numberOfConsecutiveMisses++;
                    if (numberOfConsecutiveMisses > 3) {
                        purpleWasDetected = false;
                        greenWasDetected = true;
                        numberOfConsecutiveMisses = 0;
                    }

                }
            } else if (greenWasDetected) {//if green detected track green
                setLimelightPipeline(1);
                LLResult result = limelight.getLatestResult();
                if (result.isValid()) {
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    if (!colorResults.isEmpty()) {
                        greenWasDetected = true;
                        for (LLResultTypes.ColorResult cr : colorResults) {
                            opMode.telemetry.addData("returned", cr.getTargetXDegrees());
                            return cr.getTargetXDegrees();
                        }
                    }
                } else {
                    numberOfConsecutiveMisses++;
                    if (numberOfConsecutiveMisses > 3) {
                        greenWasDetected = false;
                        purpleWasDetected = true;
                        numberOfConsecutiveMisses = 0;
                    }

                }


            }
            return 0;
        }
        return 0;
    }

}
    

