package org.firstinspires.ftc.teamcode;

import android.util.Log;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;

import FtcExplosivesPackage.ExplosiveTele;
import Gagarin.GagarinRobot;
import Utilities.PID;
import VisionPipelines.JoulePipeline;
import VisionPipelines.SkystonePipeline;

@TeleOp(name = "Top Secret")
public class follow extends ExplosiveTele {
    String TAG = "stalker";

    condition currCond = condition.UNREGISTERED;

    private OpenCvCamera phoneCam;

    private Point oldResult;
    private Point newResult;
    private condition lastResult;

    private PID turnPID = new PID();

    private DcMotor bright, fright, bleft, fleft;

    ModernRoboticsI2cRangeSensor ultra;

    @Override
    public void initHardware() {
        GagarinRobot robot = new GagarinRobot(this, hardwareMap);
        bright = robot.bright;
        fright = robot.fright;
        bleft = robot.bleft;
        fleft = robot.fleft;

        ultra = robot.ultra;

        oldResult = new Point(0, 0);

        turnPID.setup(0.0125, 0, 0, 0.1, 5, 72);
    }

    @Override
    public void initAction() {
        Log.i(TAG, "Init action");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new JoulePipeline());
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void firstLoop() {
        Log.i(TAG, "First loop");
    }

    @Override
    public void bodyLoop() {

        Log.i(TAG, "Begin body loop");


        currCond = findCondition();


        if (currCond == condition.ONTARGET) {
            setPowers(1, -1, 1, -1);
        }
        if (currCond == condition.OFFLEFT) {
            setPowers(1, -1, -1, 1);
        }
        if (currCond == condition.OFFRIGHT) {
            setPowers(-1, 1, 1, -1);
        }
        if (currCond == condition.SLIGHTLEFT) {
            setPowers(1, -1, 0, 0);
        }
        if (currCond == condition.SLIGHTRIGHT) {
            setPowers(0, 0, 1, -1);
        }


        telemetry.addData("Status", currCond);
        telemetry.addData("Last Area", JoulePipeline.lastArea);
        if (JoulePipeline.location != null) {
            telemetry.addData("X", JoulePipeline.location.x);
            telemetry.addData("Y", JoulePipeline.location.y);
        }
        telemetry.update();
    }

    @Override
    public void exit() {
        setPowers(0, 0, 0, 0);
    }

    public void setPowers(double br, double fr, double bl, double fl) {
        bright.setPower(br);
        fright.setPower(fr);
        bleft.setPower(bl);
        fleft.setPower(fl);
    }

    public condition findCondition() {

        newResult = JoulePipeline.location;

        if (newResult == null) {
            if (oldResult.x > 120) {
                return condition.OFFRIGHT;
            } else {
                return condition.OFFLEFT;
            }
        } else {
            oldResult = newResult;

            if (newResult.x > 125) {
                return condition.SLIGHTRIGHT;
            } else if (newResult.x < 115) {
                return condition.SLIGHTLEFT;
            } else {
                return condition.ONTARGET;
            }
        }
    }

    private enum condition {
        ONTARGET,
        OFFLEFT,
        OFFRIGHT,
        SLIGHTRIGHT,
        SLIGHTLEFT,
        UNREGISTERED
    }
}
