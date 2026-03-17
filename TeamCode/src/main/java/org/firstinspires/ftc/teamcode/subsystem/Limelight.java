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

    public Pose2D getRobotPoseFromLL() {
        if (opMode.gamepad1.right_bumper || opMode.gamepad1.left_bumper) {
            setLimelightPipeline(2);
            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {
                Pose3D pose3D = result.getBotpose();
                opMode.telemetry.addData("limelight X", pose3D.getPosition().x);
                opMode.telemetry.addData("limelight Y", pose3D.getPosition().y);
                opMode.telemetry.addData("limelight Yaw", pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
                return new Pose2D(DistanceUnit.METER, pose3D.getPosition().x, pose3D.getPosition().y, AngleUnit.RADIANS, pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
            } else {
                return null;
            }
        }
        return null;
    }

    public Pose2D getRobotPoseFromLLMT2() {
        if (opMode.gamepad1.right_bumper || opMode.gamepad1.left_bumper) {
            double yaw = robot.drivetrain.getYaw();
            limelight.updateRobotOrientation(yaw);
            setLimelightPipeline(2);
            LLResult result = limelight.getLatestResult();
            if (result.isValid()) {
                Pose3D pose3D = result.getBotpose_MT2();
                opMode.telemetry.addData("limelight X", pose3D.getPosition().x);
                opMode.telemetry.addData("limelight Y", pose3D.getPosition().y);
                opMode.telemetry.addData("limelight Yaw", pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
                return new Pose2D(DistanceUnit.METER, pose3D.getPosition().x, pose3D.getPosition().y, AngleUnit.RADIANS, pose3D.getOrientation().getYaw(AngleUnit.RADIANS));
            } else {
                return null;
            }
        }
        return null;
    }
    public Robot.ColorPattern getColorPatternFromLL(){
        setLimelightPipeline(3);
        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
           //get tag id from LLResult
            switch (result.getFiducialResults().get(0).getFiducialId()){
                case 21:
                    return Robot.ColorPattern.GPP;
                case 22:
                    return Robot.ColorPattern.PGP;
                case 23:
                    return Robot.ColorPattern.PPG;
                default:
                    return null;


            }
        } else {
            return null;
        }
    }

}
    

