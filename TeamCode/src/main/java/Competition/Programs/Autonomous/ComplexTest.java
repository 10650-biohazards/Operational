package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.Node;
import DubinsCurve.curveProcessor3;
import DubinsCurve.myPoint;
import FtcExplosivesPackage.ExplosiveAuto;

@Autonomous(name = "Complex Test")
public class ComplexTest extends ExplosiveAuto {

    public DcMotor bright, fright, bleft, fleft;

    DriveSubsystem drive;
    VisionSubsystem vision;
    curveProcessor3 curve;

    LinearOpMode op;

    private double P = 0.5;

    private boolean down = true;
    private boolean downdown = true;

    private Gamepad driver;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(1.5, -2.625, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        vision = Robot.vision;
        bleft = robotMap.bleft;
        fleft = robotMap.fleft;
        bright = robotMap.bright;
        fright = robotMap.fright;

        driver = robot.driver;

        curve = new curveProcessor3(drive, telemetry, this);


    }

    @Override
    public void initAction() {

    }
    @Override
    public void body() throws InterruptedException {
        waitForStart();
        while(driver.a == false){
            if(driver.left_bumper == true && down){
                P -= 0.05;
                down = false;
            }
            else if(driver.left_bumper == false && !down){
                down = true;
            }
            else if(driver.right_bumper == true && downdown){
                P += 0.05;
                downdown = false;
            }
            else if(driver.right_bumper == false && !downdown){
                downdown = true;
            }

            telemetry.addData("Proportional", P);
            telemetry.update();
        }
        drive.complexMove(3000,0,0,0,90,0,0,true, P);

    }

    @Override
    public void exit() throws InterruptedException {
        bleft.setPower(0.0);
        fleft.setPower(0.0);
        bright.setPower(0.0);
        fright.setPower(0.0);
    }
}
