package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;

import Competition.Commands.VisionCommand;
import Competition.Zooker;
import Competition.ZookerMap;
import FtcExplosivesPackage.BiohazardTele;
import VisionPipelines.FoundationPipeline;
import VisionPipelines.IntakePipeline;

@TeleOp(name = "Vision Test")
public class VisionCommandTest extends BiohazardTele {

    VisionCommand vision;

    OpenCvCamera intakeCam;

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this, true);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hardwareMap.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);
        intakeCam.openCameraDevice();
        intakeCam.setPipeline(new FoundationPipeline());
        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        telemetry.addData("NOM NOM", FoundationPipeline.present);
        telemetry.addData("HAPPY NOM", FoundationPipeline.weight);
        telemetry.update();
    }

    @Override
    public void exit() {

    }
}