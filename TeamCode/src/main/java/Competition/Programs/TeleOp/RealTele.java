package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Competition.Commands.DriveCommand;
import Competition.Commands.MechCommand;
import Competition.Commands.VisionCommand;
import Competition.Zooker;
import Competition.ZookerMap;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "Zooker TeleOp")
public class RealTele extends BiohazardTele {

    DriveCommand drive;
    MechCommand mech;
    VisionCommand vision;

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this, true);

        drive = new DriveCommand(this);
        mech = new MechCommand(this);
        vision = new VisionCommand(this, hardwareMap);

        drive.enable();
        mech.enable();
        vision.enable();
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