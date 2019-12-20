package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "Complex Test 2")
public class ComplexTest2 extends ExplosiveAuto {

    public DcMotor bright, fright, bleft, fleft;

    DriveSubsystem drive;
    VisionSubsystem vision;
    curveProcessor3 curve;
    HookSubsystem hooker;
    Utility u = new Utility(this);
    double P = 0.5;
    private Gamepad driver;
    private boolean down = true;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(1.5, -2.625, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        vision = Robot.vision;
        hooker = Robot.hooker;

        bleft = robotMap.bleft;
        fleft = robotMap.fleft;
        bright = robotMap.bright;
        fright = robotMap.fright;

        driver = robot.driver;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {
        hooker.enable();
    }
    @Override
    public void body() throws InterruptedException {
        waitForStart();
        while(driver.a == false){
            if(driver.left_bumper == true && down){
                P -= 0.05;
                down = false;
            }
            if(driver.left_bumper == false && !down){
                down = true;
            }
            if(driver.right_bumper == true && down){
                P += 0.05;
                down = false;
            }
            if(driver.right_bumper == false && !down){
                down = true;
            }
            telemetry.addData("Proportional", P);
            telemetry.update();
        }
        drive.complexMove(3000, -5, -5, 0, 90, 0.4, 0.6, true, P);
        drive.complexMove(500,0,0,90,90,0.6,0.6,true, P);
        hooker.hook();
        u.waitMS(1000);
        drive.complexMove(-4200, 0, 0, 90, 90, 0.4, 0.4, true, P);
        hooker.release();
        u.waitMS(1500);
    }

    @Override
    public void exit() throws InterruptedException {
        bleft.setPower(0.0);
        fleft.setPower(0.0);
        bright.setPower(0.0);
        fright.setPower(0.0);
    }
}
