package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Competition.Commands.DriveCommand;
import Competition.Commands.MechCommand;
import Competition.Commands.VisionCommand;
import Competition.Robot;
import Competition.RobotMap;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "Meet 1 TeleOp")
public class RealTele extends BiohazardTele {

    DriveCommand drive;
    MechCommand mech;
    VisionCommand vision;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this, true);

        drive = new DriveCommand(this);
        mech = new MechCommand(this);
        //vision = new VisionCommand(this, hardwareMap);

        drive.enable();
        mech.enable();
        //vision.enable();
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