package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvWebcam;

import FtcExplosivesPackage.Subsystem;
import VisionPipelines.FoundationPipeline;
import VisionPipelines.LineUpPipeline;
import VisionPipelines.OtherLineUpPipeline;
import VisionPipelines.SkystonePipeline;

public class VisionSubsystem extends Subsystem {

    OpenCvCamera intakeCam;
    public OpenCvCamera phoneCam;
    HardwareMap hw;

    public VisionSubsystem(OpMode op, HardwareMap hw) {
        super(op);
        this.hw = hw;
    }

    @Override
    public void enable() {
        /*int cameraMonitorViewId2 = hw.appContext.getResources().getIdentifier("cameraMonitorViewId2", "id", hw.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId2);
        phoneCam.openCameraDevice();
        phoneCam.setPipeline(new OtherLineUpPipeline());
        phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);*/
    }

    public void enableSkystone() {
        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);
        intakeCam.openCameraDevice();
        intakeCam.setPipeline(new SkystonePipeline());
        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public void enableFoundation() {

        //phoneCam.stopStreaming();

        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);
        intakeCam.openCameraDevice();
        intakeCam.setPipeline(new FoundationPipeline());
        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public int grabSkyPos() {
        return SkystonePipeline.result;
    }

    public double getStoneX() {
        return LineUpPipeline.xCoord;
    }

    public double getStoneY() {
        return LineUpPipeline.yCoord;
    }

    public boolean linedUp() {
        return OtherLineUpPipeline.linedUp;
    }

    @Override
    public void disable() {
        intakeCam.stopStreaming();
        phoneCam.stopStreaming();
    }

    @Override
    public void stop() {

    }
}
