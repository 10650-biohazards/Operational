package Competition.Commands;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import Competition.VisionPipelines.SkystonePipeline;
import Competition.VisionPipelines.StackPipeline;
import FtcExplosivesPackage.BioCommand;
import FtcExplosivesPackage.BiohazardTele;

public class VisionCommand extends BioCommand {

    HardwareMap hw;

    stackStatus currStack;

    OpenCvCamera intakeCam;
    OpenCvCamera liftCam;

    public VisionCommand(BiohazardTele op, HardwareMap hw) {
        super(op, "vision");
        this.hw = hw;
    }

    @Override
    public void init() {
        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);
        intakeCam.openCameraDevice();
        intakeCam.setPipeline(new SkystonePipeline());
        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        int cameraMonitorViewId2 = hw.appContext.getResources().getIdentifier("cameraMonitorViewId2", "id", hw.appContext.getPackageName());
        liftCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId2);
        liftCam.openCameraDevice();
        liftCam.setPipeline(new StackPipeline());
        liftCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        intakeVision();
        //if (Robot.driver.a) {
            stackVision();
        //}
    }

    public void stackVision() {
        int width = StackPipeline.width;
        double xCoord = StackPipeline.xCoord;

        if (xCoord > 420) {
            currStack = VisionCommand.stackStatus.OFFRIGHT;
        } else if (xCoord < 380) {
            currStack = VisionCommand.stackStatus.OFFLEFT;
        } else {
            if (width > 300) {
                currStack = VisionCommand.stackStatus.DONE;
            } else {
                currStack = VisionCommand.stackStatus.ADVANCE;
            }
        }
    }

    public void intakeVision() {

    }

    public void processVuforia() {

    }

    @Override
    public void stop() {

    }

    public enum stoneStatus {
        NONE,
        ONTARGET,
        TILTRIGHT,
        TILTLEFT
    }

    public enum stackStatus {
        NONE,
        ADVANCE,
        OFFLEFT,
        OFFRIGHT,
        DONE
    }
}
