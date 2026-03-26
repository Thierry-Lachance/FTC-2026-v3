package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

public class Led {
    Robot robot;
    Servo led0;
    Servo led1;
    Servo led2;

    double rgbValue = 0.3;
    double modifier = 0.01;


    public Led(Robot robot) {
        this.robot = robot;
        led0 = robot.opMode.hardwareMap.get(Servo.class, Constant.led0Name);
        led1 = robot.opMode.hardwareMap.get(Servo.class, Constant.led1Name);
        led2 = robot.opMode.hardwareMap.get(Servo.class, Constant.led2Name);

    }
    public void setLed0Color(double colorPos) {
        led0.setPosition(colorPos);
    }

    public void setLed1Color(double colorPos) {
        led1.setPosition(colorPos);
    }

    public void setLed2Color(double colorPos) {
        led2.setPosition(colorPos);
    }
    public void updateLed() {

        if (robot.intake.isIntaking()) {
            setLed1Color(0.7);// Purple
        } else {
            if (robot.shooter.isReadyToShoot()) {

                setLed1Color(0.5);// Green
            } else {
                setLed1Color(0.3);// Red
            }
        }
        if(robot.opMode.getRuntime() >= 105){
           //make the led falsh white when the match is about to end
            if ((int)(robot.opMode.getRuntime() * 10) % 2 == 0) {
                setLed0Color(1.0);
                setLed2Color(1.0);
            } else {
                setLed0Color(0.0);
                setLed2Color(0.0);
            }
        }
        else if(robot.opMode.getRuntime() >= 90){
             setLed0Color(0.9);
             setLed2Color(0.9);
        }
        else {
            if (rgbValue > 0.69) {
                modifier = -0.005;
            } else if (rgbValue < 0.3) {
                modifier = 0.005;
            }
            rgbValue += modifier;
            setLed0Color(rgbValue);
            setLed2Color(rgbValue);
        }
    }
}
