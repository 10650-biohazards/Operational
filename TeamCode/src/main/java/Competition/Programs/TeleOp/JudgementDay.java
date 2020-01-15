package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import Competition.Commands.DriveCommand;
import Competition.Commands.MechCommand;
import Competition.Commands.VisionCommand;
import Competition.Robot;
import Competition.RobotMap;
import FtcExplosivesPackage.BiohazardTele;
import VisionPipelines.IntakePipeline;
import VisionPipelines.JudgementPipeline;

@TeleOp(name = "Judgement Day")
public class JudgementDay extends BiohazardTele {

    DriveCommand drive;
    OpenCvCamera phoneCam;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this, true);

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
                drive.setPows(0, 0, 0.5, 0.5);
            } else if (position.x < 140) {
                drive.setPows(0.5, 0.5, 0, 0);
            } else {
                drive.setPows(0.5, 0.5, 0.5, 0.5);
            }
        } else {
            if (position.y < 25) {
                drive.setPows(0, 0, 0, 0);
            } else if (position.x > 160) {
                drive.setPows(-0.5, -0.5, 0.5, 0.5);
            } else {
                drive.setPows(0.5, 0.5, -0.5, -0.5);
            }
        }
        telemetry.addData("POs", position.toString());
        telemetry.update();
    }

    @Override
    public void exit() {

    }
}