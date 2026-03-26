package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constant;
import org.firstinspires.ftc.teamcode.Robot;

public class Kicker {
    LinearOpMode opMode;
    Robot robot;
    Servo chamber1Servo, chamber2Servo, chamber3Servo;

    private boolean shooting = false;
    private int nbOfBallShot = 0;
    private double lastShotTime = 0.0;


    private boolean shooting2 = false;
    private int nbOfBallShot2 = 0;
    private double lastShotTime2 = 0.0;


    public Kicker(LinearOpMode opMode, Robot robot) {
        this.opMode = opMode;
        this.robot = robot;
        
        chamber1Servo = opMode.hardwareMap.get(Servo.class, Constant.chamber1Name);
        chamber2Servo = opMode.hardwareMap.get(Servo.class, Constant.chamber2Name);
        chamber3Servo = opMode.hardwareMap.get(Servo.class, Constant.chamber3Name);

    }

    public void zeroKicker() {
        chamber1Servo.setPosition(1.0);
        chamber2Servo.setPosition(0);
        chamber3Servo.setPosition(0);
    }

    public void lowerKicker() {
        chamber1Servo.setPosition(Constant.chamber1BasePos);
        chamber2Servo.setPosition(Constant.chamber2BasePos);
        chamber3Servo.setPosition(Constant.chamber3BasePos);
    }

    public void engageKicker() {
        chamber1Servo.setPosition(Constant.chamber1EngagedPos);
        chamber2Servo.setPosition(Constant.chamber2EngagedPos);
        chamber3Servo.setPosition(Constant.chamber3EngagedPos);
    }

    public void kickChamber1() {
        chamber1Servo.setPosition(Constant.chamber1ActivePos);
    }

    public void kickChamber2() {
        chamber2Servo.setPosition(Constant.chamber2ActivePos);
    }

    public void kickChamber3() {
        chamber3Servo.setPosition(Constant.chamber3ActivePos);
    }

    public void kickChamber() {
        if (robot.intake.isIntaking()) {
            lowerKicker();
        }
        else {
            if (robot.shooter.isReadyToShoot()) {
                if(opMode.gamepad2.a && !shooting) {
                    shooting = true;
                    lastShotTime = opMode.getRuntime()-1.0;
                    nbOfBallShot = 0;
                }
                if (shooting) {
                    shootFastAFV2();
                }

            } else {
                shooting = false;

            }

             if (robot.shooter.isReadyToShoot() && opMode.gamepad2.bWasPressed()) {//robot.shooter.isReadyToShoot() && robot.drivetrain.checkIfAlignedWithGoal() &&
                shooting2 = true;
                lastShotTime2 = opMode.getRuntime() - 1.0;
                nbOfBallShot2 = 0;


            }
             if(shooting2){
                 shootFastAFV3();
             }
            if (opMode.gamepad2.dpad_left) {
                kickChamber1();

            }
            if (opMode.gamepad2.dpad_up) {
                kickChamber2();

            }
            if (opMode.gamepad2.dpad_right) {
                kickChamber3();

            }
        }
        if(!robot.intake.isIntaking() && !shooting && !shooting2){
            engageKicker();
        }
    }

    public void kickChamberAuto() {
        int numberOfBallShot = 0;
        double lastShotTime = opMode.getRuntime();

        while (numberOfBallShot < 3 && opMode.opModeIsActive() && !robot.timeToStop()) {
            if (robot.shooter.isReadyToShoot() && (opMode.getRuntime() - lastShotTime) >= 0.2) {
                switch (numberOfBallShot) {
                    case 0:
                        kickChamber1();
                        break;
                    case 1:
                        kickChamber2();
                        break;
                    case 2:
                        kickChamber3();
                        break;
                }
                numberOfBallShot++;
                lastShotTime = opMode.getRuntime();
            }

        }
        if(!robot.timeToStop())opMode.sleep(200);
        engageKicker();
    }
    public void kickChamberAutoPattern(Robot.ColorPattern patternInsideRobot) {
        int numberOfBallShot = 0;
        double lastShotTime = opMode.getRuntime();
        int[] chamberOrder = new int[3];
        switch (patternInsideRobot){
            case GPP:
              switch (robot.getColorPattern()){
                    case GPP:
                        chamberOrder = new int[]{1, 2, 3};
                        break;
                    case PGP:
                        chamberOrder = new int[]{3,1,2};
                        break;
                    case PPG:
                        chamberOrder = new int[]{3,2,1};
                        break;
              }
                break;
            case PGP:
                switch (robot.getColorPattern()){
                    case GPP:
                        chamberOrder = new int[]{2,1,3};
                        break;
                    case PGP:
                        chamberOrder = new int[]{1,2,3};
                        break;
                    case PPG:
                        chamberOrder = new int[]{1,3,2};
                        break;
                }
                break;
            case PPG:
                switch (robot.getColorPattern()){
                    case GPP:
                        chamberOrder = new int[]{3,2,1};
                        break;
                    case PGP:
                        chamberOrder = new int[]{1,3,2};
                        break;
                    case PPG:
                        chamberOrder = new int[]{1,2,3};
                        break;
                }
                break;
        }
        while (numberOfBallShot < 3 && opMode.opModeIsActive()) {
            if (robot.shooter.isReadyToShoot() && ((opMode.getRuntime() - lastShotTime) >= 1.0 || numberOfBallShot == 0)) {
                switch (chamberOrder[numberOfBallShot]){
                    case 1:
                        kickChamber1();
                        break;
                    case 2:
                        kickChamber2();
                        break;
                    case 3:
                        kickChamber3();
                        break;
                }
                numberOfBallShot++;
                lastShotTime = opMode.getRuntime();
            }

        }
        opMode.sleep(500);
        engageKicker();
    }

    public void shootFastAFV2() {
        if (nbOfBallShot < 3 && (opMode.getRuntime() - lastShotTime) >= 0.2) {
            lastShotTime = opMode.getRuntime();
            switch (nbOfBallShot) {
                case 0:
                    kickChamber1();
                    break;
                case 1:
                    kickChamber2();
                    break;
                case 2:
                    kickChamber3();
                    break;

            }
            nbOfBallShot++;
        }
    }
    public void shootFastAFV3() {
        if (nbOfBallShot2 < 4 && (opMode.getRuntime() - lastShotTime2) >= robot.aimBot.getTimeBetweenShots()) {
            lastShotTime2 = opMode.getRuntime();
            switch (nbOfBallShot2) {
                case 0:
                    kickChamber2();
                    break;
                case 1:
                    kickChamber1();
                    break;
                case 2:
                    kickChamber3();

                    break;
                case 3:
                    shooting2 = false;
                    break;

            }
            nbOfBallShot2++;
        }
    }
}
