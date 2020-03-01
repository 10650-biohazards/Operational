package Competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.ParkSubsystem;
import Competition.Subsystems.VisionSubsystem;
import Competition.Subsystems.IntakeSubsystem;
import Utilities.driveTracker2;

public class Zooker {

    public static DriveSubsystem drive;
    public static VisionSubsystem vision;
    public static HookSubsystem hooker;
    public static IntakeSubsystem intake;
    public static ParkSubsystem park;

    public static driveTracker2 track;

    public static Gamepad driver, manipulator;

    public Zooker(LinearOpMode op) {
        drive = new DriveSubsystem(op);
        vision = new VisionSubsystem(op, op.hardwareMap);
        hooker = new HookSubsystem(op);
        intake = new IntakeSubsystem(op);
        park = new ParkSubsystem(op);
        driver = op.gamepad1;
        manipulator = op.gamepad2;
    }

    public Zooker(LinearOpMode op, boolean teleOp) {
        driver = op.gamepad1;
        manipulator = op.gamepad2;
    }

    public void enable() {
        track = new driveTracker2(drive);
        drive.enable();
        drive.setTracker(track);

        vision.enable();
        intake.enable();
        hooker.enable();
        park.enable();
    }

    public void stopVision() {
        vision.disable();
    }
}
