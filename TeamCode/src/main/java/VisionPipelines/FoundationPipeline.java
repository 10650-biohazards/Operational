package VisionPipelines;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import Utilities.ImageUtil;
import Utilities.myRect;

public class FoundationPipeline extends OpenCvPipeline {
    String TAG = "INTAKE PIPELINE";
    boolean processing = true;

    public static int slowThresh = 180;
    public int lastY = 0;
    public static int minY = 140;
    public double lastTime = System.currentTimeMillis();
    public static boolean present;
    public static int weight;
    public static boolean red = true;

    public static void setRed(boolean red) {
        FoundationPipeline.red = red;
    }

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {

            Imgproc.line(rgbaFrame, new Point(), new Point(), new Scalar(255, 255, 255), 5);

            //convert to hsv
            Mat hsv = new Mat();
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            Scalar min;
            Scalar max;
            Scalar min2;
            Scalar max2;

            if (red) {
                min = new Scalar(150, 50, 0); //red min
                max = new Scalar(179, 255, 255); //red max

                min2 = new Scalar(0, 50, 0); //red min
                max2 = new Scalar(30, 255, 255); //red max
            } else {
                min = new Scalar(100, 50, 0); //blue min
                max = new Scalar(150, 255, 255); //blue max

                min2 = new Scalar(0, 0, 0); //blue min
                max2 = new Scalar(1, 1, 1); //blue max
            }



            List<Mat> rgbaChannels = new ArrayList<>();

            Mat maskedImage = new Mat();
            Mat maskedImage2 = new Mat();

            //PROCESSING FOR LOOKER MECHANISM
            //int yVal = 220;
            int yVal = 5;
            int weight = 0;

            //Applying HSV limits
            ImageUtil.hsvInRange(hsv, min, max, maskedImage);
            ImageUtil.hsvInRange(hsv, min2, max2, maskedImage2);

            for (int x = maskedImage.width() - 1; x > minY; x--) {
                if (maskedImage.get(yVal, x)[0] != 0) weight++;
                if (maskedImage2.get(yVal, x)[0] != 0) weight++;
            }
            present = weight > 100;
            this.weight = weight;


            rgbaChannels.add(maskedImage.clone());
            rgbaChannels.add(maskedImage2.clone());
            rgbaChannels.add(maskedImage.clone());

            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);


            Imgproc.line(rgbaFrame, new Point(0, yVal), new Point(rgbaFrame.width(), yVal), new Scalar(255, 255, 255), 3);


            for (Mat mat : rgbaChannels) {
                mat.release();
            }

            hsv.release();
            maskedImage.release();

        }

        return rgbaFrame;
    }
}