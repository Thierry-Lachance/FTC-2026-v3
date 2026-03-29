package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.List;

public class Limelight {
    Limelight3A limelight;
    Robot robot;

    boolean greenWasDetected = false;
    boolean purpleWasDetected = true;

    int numberOfConsecutiveMisses = 0;

    public Limelight(Robot robot) {
        this.robot = robot;
        limelight = robot.opMode.hardwareMap.get(Limelight3A.class, Constant.limelightName);

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
        robot.opMode.telemetry.addData("Name", "%s",
                status.getName());
        robot.opMode.telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        robot.opMode.telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            robot.opMode.telemetry.addData("LL Latency", captureLatency + targetingLatency);
            robot.opMode.telemetry.addData("Parse Latency", parseLatency);


            // Access color results
            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
            for (LLResultTypes.ColorResult cr : colorResults) {
                robot.opMode.telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
            }
        } else {
            robot.opMode.telemetry.addData("Limelight", "No data available");
        }

        robot.opMode.telemetry.update();
    }

    public double getBallOffset() {
        if (!robot.opMode.gamepad1.right_bumper || !robot.opMode.gamepad1.left_bumper) {
            if (purpleWasDetected) {//if purple detected track purple
                setLimelightPipeline(0);
                LLResult result = limelight.getLatestResult();
                if (result.isValid()) {
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    if (!colorResults.isEmpty()) {
                        purpleWasDetected = true;
                        for (LLResultTypes.ColorResult cr : colorResults) {
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
    

