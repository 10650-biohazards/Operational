package Competition.Programs.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.VisionSubsystem;
import FtcExplosivesPackage.BiohazardTele;

@TeleOp(name = "TEST")
public class test extends BiohazardTele {

    DcMotor one, two;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);

        robot.enable();

        VisionSubsystem vision = Robot.vision;
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