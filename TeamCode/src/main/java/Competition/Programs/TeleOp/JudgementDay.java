package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import Competition.Commands.DriveCommand;
import Competition.Zooker;
import Competition.ZookerMap;
import FtcExplosivesPackage.BiohazardTele;
import VisionPipelines.JudgementPipeline;

@TeleOp(name = "Judgement Day")
public class JudgementDay extends BiohazardTele {

    DriveCommand drive;
    OpenCvCamera phoneCam;

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this, true);

        drive = new DriveCommand(this);
        drive.enable();

        int cameraMonitorViewId2 = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId2", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId2);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new JudgementPipeline());
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        Point position = JudgementPipeline.position;
        if (JudgementPipeline.found) {
            if (position.x > 180) {
                drive.setPows(0, 0, 0.6, 0.6);
            } else if (position.x < 140) {
                drive.setPows(0.6, 0.6, 0, 0);
            } else {
                drive.setPows(0.6, 0.6, 0.6, 0.6);
            }
        } else {
            if (position.y < 25) {
                drive.setPows(0, 0, 0, 0);
            } else if (position.x > 160) {
                drive.setPows(-0.6, -0.6, 0.6, 0.6);
            } else {
                drive.setPows(0.6, 0.6, -0.6, -0.6);
            }
        }
        telemetry.addData("POs", position.toString());
        telemetry.update();
    }

    @Override
    public void exit() {
        drive.setPows(0, 0, 0, 0);
    }
}