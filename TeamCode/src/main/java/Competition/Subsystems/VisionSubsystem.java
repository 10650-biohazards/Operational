package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import Competition.VisionPipelines.SkystonePipeline;
import Competition.VisionPipelines.StackPipeline;
import FtcExplosivesPackage.Subsystem;

public class VisionSubsystem extends Subsystem {

    OpenCvCamera intakeCam;
    HardwareMap hw;

    public VisionSubsystem(OpMode op, HardwareMap hw) {
        super(op);
        this.hw = hw;
    }

    @Override
    public void enable() {
        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);

        intakeCam.openCameraDevice();

        intakeCam.setPipeline(new SkystonePipeline());

        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    public int grabSkyPos() {
        return SkystonePipeline.result;
    }

    @Override
    public void disable() {

    }

    @Override
    public void stop() {

    }
}
