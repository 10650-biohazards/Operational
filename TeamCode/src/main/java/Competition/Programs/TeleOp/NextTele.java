package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Competition.Commands.DriveCommand;
import Competition.Commands.MechCommand;
import Competition.Commands.VisionCommand;
import Competition.FinalMap;
import Competition.FinalRobot;
import Competition.Zooker;
import Competition.ZookerMap;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "Next Robot TeleOp")
public class NextTele extends BiohazardTele {

    DriveCommand drive;
    MechCommand mech;
    VisionCommand vision;

    @Override
    public void initHardware() {
        FinalMap robotMap = new FinalMap(hardwareMap);
        FinalRobot robot = new FinalRobot(this, true);

        drive = new DriveCommand(this);

        drive.enable();
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