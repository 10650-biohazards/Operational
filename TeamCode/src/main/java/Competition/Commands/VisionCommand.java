package Competition.Commands;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import FtcExplosivesPackage.BioCommand;
import FtcExplosivesPackage.BiohazardTele;
import Utilities.PID;
import VisionPipelines.FetchPipeline;
import VisionPipelines.SkystonePipeline;
import VisionPipelines.StackPipeline;

public class VisionCommand extends BioCommand {

    HardwareMap hw;

    stackStatus currStack;

    OpenCvCamera intakeCam;
    OpenCvCamera liftCam;

    PID turnPID = new PID();


    public static boolean fetchOnScreen;
    public static Point fetchLoc;

    public VisionCommand(BiohazardTele op, HardwareMap hw) {
        super(op, "vision");
        this.hw = hw;
    }

    @Override
    public void init() {
        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        intakeCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId);
        intakeCam.openCameraDevice();
        intakeCam.setPipeline(new FetchPipeline());
        intakeCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        int cameraMonitorViewId2 = hw.appContext.getResources().getIdentifier("cameraMonitorViewId2", "id", hw.appContext.getPackageName());
        liftCam = new OpenCvWebcam(hw.get(WebcamName.class, "stoned cam"), cameraMonitorViewId2);
        liftCam.openCameraDevice();
        liftCam.setPipeline(new StackPipeline());
        liftCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);


        turnPID.setup(0.05, 0, 0, 0, 0.5, 0);
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

    public void fetchVision() {
        fetchOnScreen = FetchPipeline.onScreen;
        if (fetchOnScreen) {
            fetchLoc = FetchPipeline.location;
            RotatedRect stone = FetchPipeline.stone;

            if (Math.abs(stone.angle) < 5) {
                if (stone.boundingRect().height > stone.boundingRect().width) {
                    //TARGET SIGHTED. SEEK AND DESTROY
                } else {
                    if (stone.angle < 0) {
                        //SLIDE TO THE LEFT
                    } else {
                        //SLIDE TO THE RIGHT
                    }
                }
            } else {
                if (stone.angle < 0) {
                    //SLIDE TO THE LEFT
                } else {
                    //SLIDE TO THE RIGHT
                }
            }
        }
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
