package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Random;

public class Soundboard {

    Random rand = new Random();

    HardwareMap hw;

    int[] sounds  = {R.raw.jeporady, R.raw.soundgold, R.raw.matchnoises};
    int[] strtPts = {0, 0, 0};

    public Soundboard(HardwareMap hardwareMap) {
        hw = hardwareMap;
    }

    public void playRand() {
        int pos = rand.nextInt(sounds.length);
        MediaPlayer player = MediaPlayer.create(hw.appContext, sounds[pos]);
        player.setLooping(false);
        player.seekTo(strtPts[pos]);
        player.start();
    }

    public void playSound(int sound, int startPt) {

    }
}