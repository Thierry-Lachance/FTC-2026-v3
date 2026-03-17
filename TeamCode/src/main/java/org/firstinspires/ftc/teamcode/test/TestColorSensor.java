package org.firstinspires.ftc.teamcode.test;


import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.Constant;

@TeleOp(name = "Test ColorSensor", group = "TEST")
public class TestColorSensor extends LinearOpMode {

    /**
     * The color sensor object.
     */
    private NormalizedColorSensor color_sensor;
    private NormalizedColorSensor color_sensor2;
    DcMotor intakeMotor;
    DcMotor feederMotor;

    /**
     * Variables to store the HSV values.
     */
    float[] hsvValues = {0F, 0F, 0F};
    double hue;
    float[] hsvValues1 = {0F, 0F, 0F};
    double hue1;


    @Override
    public void runOpMode() {

        // Get a reference to the color sensor in the hardware map
        color_sensor = hardwareMap.get(NormalizedColorSensor.class, Constant.colorSensor1Name);

        color_sensor2 = hardwareMap.get(NormalizedColorSensor.class, Constant.colorSensor2Name);

        intakeMotor = hardwareMap.get(DcMotor.class, Constant.intakeMotorName);
        feederMotor = hardwareMap.get(DcMotor.class, Constant.feederMotorName);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        feederMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        feederMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        feederMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // Wait for the start button to be pressed
        waitForStart();

        // Loop while the op mode is active
        while (opModeIsActive()) {
            intakeMotor.setPower(1);
            feederMotor.setPower(-1);
            // Get the normalized RGBA colors from the sensor
            NormalizedRGBA colors = color_sensor.getNormalizedColors();

            // Convert the colors to HSV (Hue, Saturation, Value) using the Android Color class
            Color.colorToHSV(colors.toColor(), hsvValues);

            // The hue value is in the first element of the HSV array
            hue = hsvValues[0];

            // Or use the simpler JavaUtil method to get the hue directly
            // hue = JavaUtil.colorToHue(colors.toColor());


            // Send the hue value to the Driver Station for observation
            telemetry.addData("Hue", "%.3f", hue);
            telemetry.addData("Saturation", "%.3f", hsvValues[1]);
            telemetry.addData("Value (Light)", "%.3f", hsvValues[2]);
            // Get the normalized RGBA colors from the sensor
            NormalizedRGBA colors1 = color_sensor2.getNormalizedColors();

            // Convert the colors to HSV (Hue, Saturation, Value) using the Android Color class
            Color.colorToHSV(colors1.toColor(), hsvValues1);

            // The hue value is in the first element of the HSV array
            hue1 = hsvValues1[0];

            // Or use the simpler JavaUtil method to get the hue directly
            // hue = JavaUtil.colorToHue(colors.toColor());


            // Send the hue value to the Driver Station for observation
            telemetry.addData("Hue", "%.3f", hue1);
            telemetry.addData("Saturation", "%.3f", hsvValues1[1]);
            telemetry.addData("Value (Light)", "%.3f", hsvValues1[2]);


            telemetry.update();
        }
    }
}
