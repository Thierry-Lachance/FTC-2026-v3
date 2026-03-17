package org.firstinspires.ftc.teamcode.subsystem;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Vision {

    private AprilTagProcessor aprilTag;
    LinearOpMode opMode;

    private final Position cameraPosition = new Position(DistanceUnit.INCH,
            -2.0, -1, 12.25, 0);
    private final YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            90, -70, 0, 0);

    public Vision(LinearOpMode opMode) {
        this.opMode = opMode;
        initAprilTag();

    }

    private void initAprilTag() {
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(opMode.hardwareMap.get(WebcamName.class, Constant.webcamName));
        builder.addProcessor(aprilTag);
        builder.setCameraResolution(new Size(640, 480));

        VisionPortal visionPortal = builder.build();

    }

    public Robot.ColorPattern detectPattern() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                switch (detection.id) {
                    case 21:
                        return Robot.ColorPattern.GPP;

                    case 22:
                        return Robot.ColorPattern.PGP;

                    case 23:
                        return Robot.ColorPattern.PPG;

                }
            }
        }
        return Robot.ColorPattern.GPP;
    }


}
