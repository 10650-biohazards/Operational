package FtcExplosivesPackage;

import android.media.MediaPlayer;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.R;

public class BiohazardNavX implements ExplosivePIDEnabledHardware {
    private static BiohazardNavX instance = null;
    private NavxMicroNavigationSensor imu = null;
    public double startAng, startPitch;
    HardwareMap hw;
    boolean first = true;

    public BiohazardNavX(HardwareMap hw, String name, double startAng){
        imu = hw.get(NavxMicroNavigationSensor.class, name);
        this.startAng = startAng;
        this.hw = hw;
    }

    public static BiohazardNavX getInstance(HardwareMap hw, String name){
        if(instance == null){
            instance = new BiohazardNavX(hw, name, 0);
        }
        return instance;
    }

    public double getYaw(){
        try{
            if (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle == 0.0 && first) {
                //MediaPlayer player = MediaPlayer.create(hw.appContext, R.raw.oofwiisports);
                //player.setLooping(false);
                //player.seekTo(0);
                //player.start();
                first = false;
            }
            return startAng - imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        } catch(Exception e){
            android.util.Log.d("Zooker", "GYRO ERROR: " + e.getMessage());
            return getYaw();
        }
    }

    public double getPitch() {
        try{
            return startPitch - (imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle * -1);
        } catch (Exception e) {
            android.util.Log.d("Zooker", "GYRO ERROR: " + e.getMessage());
            return getPitch();
        }
    }

    public void resetPitch() {
        startPitch = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
    }

    public double getRaw() {
        return -imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    @Override
    public void close(){
        imu = null;
        instance = null;
    }

    @Override
    public double getLatency(){
        return (System.nanoTime() - imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).acquisitionTime)/1000000;
    }

    public double getUpdatedYaw(){
        while((System.nanoTime() - imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).acquisitionTime)/1000000 > 5){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    @Override
    public double getOutput() {
        return getYaw();
    }
}