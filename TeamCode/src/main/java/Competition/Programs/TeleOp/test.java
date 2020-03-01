package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.VisionSubsystem;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "TEST")
public class test extends BiohazardTele {

    DcMotor one, two;

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);

        robot.enable();
    }

    @Override
    public void initAction() {
        ZookerMap.theBooker.setPosition(0.3);
    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        ZookerMap.theBooker.setPosition(ZookerMap.theBooker.getPosition() + (gamepad1.right_stick_y * 0.001));
        telemetry.addData("", ZookerMap.theBooker.getPosition());
        telemetry.update();
    }

    @Override
    public void exit() {

    }
}