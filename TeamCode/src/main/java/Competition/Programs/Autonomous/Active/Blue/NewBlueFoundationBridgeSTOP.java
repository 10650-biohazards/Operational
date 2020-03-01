package Competition.Programs.Autonomous.Active.Blue;

import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

//@Autonomous (name = "New Blue Foundation STOP | Bridge Park", group = "blue")
public class NewBlueFoundationBridgeSTOP extends ExplosiveAuto {

    int secs = 0;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {

    }

    @Override
    public void initAction() {
        double startTime = System.currentTimeMillis();
        while (!opModeIsActive()) {

            boolean ready = startTime + 500 < System.currentTimeMillis();
            if (gamepad1.a && ready) {
                secs++;
                startTime = System.currentTimeMillis();
            }

            telemetry.addData("Pick a time! Any time!", secs);
            telemetry.update();
        }
    }

    @Override
    public void body() throws InterruptedException {
        telemetry.addData("Well, I guess you hit the play button", secs);
        telemetry.update();

        u.waitMS(secs*1000);

        while (opModeIsActive()) {
            telemetry.addData("OP MODE HAS BEGUN!", "");
            telemetry.update();
        }
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
