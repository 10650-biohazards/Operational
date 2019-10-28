package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import FtcExplosivesPackage.BiohazardNavX;

@TeleOp (name = "TEST YES")
@Disabled
public class oof extends OpMode {

    BiohazardNavX gyro;

    @Override
    public void init() {
        gyro = new BiohazardNavX(hardwareMap, "navX", 0);
    }

    @Override
    public void loop() {
        telemetry.addData("GYRO", gyro.getYaw());
        telemetry.update();
    }
}