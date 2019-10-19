package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import FtcExplosivesPackage.BiohazardNavX;

@TeleOp (name = "TEST YES")
public class oof extends OpMode {

    BiohazardNavX gyro;

    boolean first = true;

    @Override
    public void init() {
        gyro = new BiohazardNavX(hardwareMap, "navX", 0);
    }

    @Override
    public void loop() {
        telemetry.addData("GYRO", gyro.getYaw());
        telemetry.update();

        if (gyro.getYaw() == 0.0 && first) {
            MediaPlayer player = MediaPlayer.create(hardwareMap.appContext, R.raw.oofwiisports);
            player.setLooping(false);
            player.seekTo(0);
            player.start();
            first = false;
        }
    }
}