package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

@TeleOp(name = "Vision Test")
public class VisionCommandTest extends BiohazardTele {

    VisionCommand vision;

    OpenCvCamera phoneCam;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this, true);

        int cameraMonitorViewId2 = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId2", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId2);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new IntakePipeline());
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

    }

    @Override
    public void exit() {

    }
}