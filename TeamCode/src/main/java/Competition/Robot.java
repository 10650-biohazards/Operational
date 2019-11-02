package Competition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.VisionSubsystem;
import Competition.Subsystems.IntakeSubsystem;
import Utilities.driveTracker2;

public class Robot {

    public static DriveSubsystem drive;
    public static VisionSubsystem vision;
    public static HookSubsystem hooker;
    public static IntakeSubsystem intake;

    public static driveTracker2 track;

    public static Gamepad driver, manipulator;

    public Robot(LinearOpMode op) {
        drive = new DriveSubsystem(op);
        vision = new VisionSubsystem(op, op.hardwareMap);
        hooker = new HookSubsystem(op);
        intake = new IntakeSubsystem(op);
        driver = op.gamepad1;
        manipulator = op.gamepad2;
    }

    public Robot(LinearOpMode op, boolean teleOp) {
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
    }
}
