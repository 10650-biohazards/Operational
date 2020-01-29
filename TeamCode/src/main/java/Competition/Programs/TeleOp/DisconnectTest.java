package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Competition.Robot;
import Competition.RobotMap;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "Disconnect Test")
public class DisconnectTest extends BiohazardTele {

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this, true);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        telemetry.addData("Yes Frint", RobotMap.frontRange.getDistance(DistanceUnit.INCH));
        telemetry.addData("Yes BAck", RobotMap.backRange.getDistance(DistanceUnit.INCH));
        telemetry.update();
    }

    @Override
    public void exit() {

    }
}