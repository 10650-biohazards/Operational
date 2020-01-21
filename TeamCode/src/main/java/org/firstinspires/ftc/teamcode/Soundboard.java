package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Random;

public class Soundboard {

    Random rand = new Random();

    HardwareMap hw;

    //int[] sounds = {R.raw.jeporady, R.raw.soundgold, R.raw.matchnoises};
    int[] strtPts = {0, 0, 0};

    public enum SkystoneSound {FIRSTSOUND, SECONDSOUND, THIRDSOUND}

    public Soundboard(HardwareMap hardwareMap) {
        hw = hardwareMap;
    }

    public void playRand(boolean startStop) {
        //int pos = rand.nextInt(sounds.length);
        //MediaPlayer player = MediaPlayer.create(hw.appContext, sounds[pos]);
        //player.setLooping(false);
        //player.seekTo(strtPts[pos]);
        if (startStop) {
            //player.start();
        } else {
            //player.stop();
        }
    }

    public void playSound(int sound, int startPt, boolean startStop) {
        /*MediaPlayer jeopardy = MediaPlayer.create(hw.appContext, R.raw.jeporady);
        MediaPlayer nowthatsalotofdamage = MediaPlayer.create(hw.appContext, R.raw.nowthatsalotofdamage);
        MediaPlayer oofwiisports = MediaPlayer.create(hw.appContext, R.raw.oofwiisports);
        MediaPlayer tunaktunaktun = MediaPlayer.create(hw.appContext, R.raw.tunaktunaktun);
        if (sound == 1) {
            jeopardy.setLooping(false);
            jeopardy.seekTo(startPt);
            if (startStop) {
                jeopardy.start();
            } else {
                jeopardy.stop();
            }
        } else if (sound == 2) {
            nowthatsalotofdamage.setLooping(false);
            nowthatsalotofdamage.seekTo(startPt);
            if (startStop) {
                nowthatsalotofdamage.start();
            } else {
                nowthatsalotofdamage.stop();
            }
        } else if (sound == 3) {
            oofwiisports.setLooping(false);
            oofwiisports.seekTo(startPt);
            if (startStop) {
                oofwiisports.start();
            } else {
                oofwiisports.stop();
            }
        } else if (sound == 4) {
            tunaktunaktun.setLooping(false);
            tunaktunaktun.seekTo(startPt);
            if (startStop) {
                oofwiisports.start();
            } else {
                oofwiisports.stop();
            }
        } else if (sound == 5) {

        } else if (sound == 6) {

        } else if (sound == 7) {

        } else if (sound == 8) {

        } else if (sound == 9) {

        } else if (sound == 10) {

        } else if (sound == 11) {

        } else if (sound == 12) {

        } else if (sound == 13) {

        } else if (sound == 14) {

        } else if (sound == 15) {

        } else if (sound == 16) {

        } else if (sound == 17) {

        } else if (sound == 18) {

        } else if (sound == 19) {

        } else if (sound == 20) {

        }*/
    }

    public void PlaySkystoneSound(SkystoneSound FirstSecondThird) {
        /*if (FirstSecondThird == SkystoneSound.FIRSTSOUND) {
            MediaPlayer First = MediaPlayer.create(hw.appContext, R.raw.wearenumberone);
            First.setLooping(false);
            First.start();
        } else if (FirstSecondThird == SkystoneSound.SECONDSOUND){
            MediaPlayer Second = MediaPlayer.create(hw.appContext, R.raw.werenumbertwo);
            Second.setLooping(false);
            Second.start();
        } else if (FirstSecondThird == SkystoneSound.THIRDSOUND){
            MediaPlayer Third = MediaPlayer.create(hw.appContext, R.raw.thatsthreequickmaths);
            Third.setLooping(false);
            Third.start();
        }*/
    }
}