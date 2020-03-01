package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Competition.Zooker;
import Competition.ZookerMap;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "Disconnect Test")
public class DisconnectTest extends BiohazardTele {

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this, true);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        telemetry.addData("Yes Frint", ZookerMap.frontRange.getDistance(DistanceUnit.INCH));
        telemetry.addData("Yes BAck", ZookerMap.backRange.getDistance(DistanceUnit.INCH));
        telemetry.update();
    }

    @Override
    public void exit() {

    }
}