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

public class IntakePipeline extends OpenCvPipeline {
    String TAG = "INTAKE PIPELINE";
    boolean processing = true;

    public static int slowThresh = 180;
    public int lastY = 0;
    public static int minY = 140;
    public double lastTime = System.currentTimeMillis();

    public static myRect stoneRect;
    public static double stoneSpeed;
    public static boolean centPresent;
    public static boolean leftPresent;
    public static boolean rightPresent;

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

            Scalar yellowMin = new Scalar(10, 50, 100); //yellow min
            Scalar yellowMax = new Scalar(29, 255, 255); //yellow max

            List<Mat> rgbaChannels = new ArrayList<>();

            Mat maskedImage = new Mat();

            //PROCESSING FOR LOOKER MECHANISM
            int leftWeight = 0, rightWeight = 0, centWeight = 0;
            int centX = 150, leftX = 40, rightX = 230;
            int[] xVals = {centX, leftX, rightX};
            int[] weights = {centWeight, leftWeight, rightWeight};
            boolean[] presents = {centPresent, leftPresent, rightPresent};

            //Applying HSV limits
            ImageUtil.hsvInRange(hsv, yellowMin, yellowMax, maskedImage);

            for (int i = 0; i < 3; i++) {
                for (int y = maskedImage.height() - 1; y > minY; y--) {
                    if (maskedImage.get(y, xVals[i])[0] != 0) weights[i]++;
                }
                presents[i] = weights[i] > 20;
            }

            centPresent = presents[0];
            leftPresent = presents[1];
            rightPresent = presents[2];




            //PROCESSING FOR STONE LOCATION
            Mat contTemp = maskedImage.clone();
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            myRect maxRect = null;
            double maxArea = 0;
            for (MatOfPoint currCont : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(currCont));

                if (rect.area() > maxArea && rect.area() > 100) {
                    maxRect = rect;
                    maxArea = rect.area();
                }
            }

            if (maxRect != null) {
                int yDiff = maxRect.y - lastY;
                double timeDiff = System.currentTimeMillis() - lastTime;
                lastTime = System.currentTimeMillis();

                stoneSpeed = (double) yDiff / (double)((double) timeDiff / (double) 1000);
            }

            stoneRect = maxRect;


            rgbaChannels.add(maskedImage.clone());
            rgbaChannels.add(maskedImage.clone());
            rgbaChannels.add(maskedImage.clone());

            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            Imgproc.line(rgbaFrame, new Point(rgbaFrame.width() / 3, slowThresh), new Point((2 * rgbaFrame.width()) / 3, slowThresh), new Scalar(0, 0, 255), 1);
            if (maxRect != null) {
                Imgproc.rectangle(rgbaFrame, ImageUtil.myRectToRect(maxRect), new Scalar(0, 255, 255));
                Imgproc.circle(rgbaFrame, maxRect.mid(), 2, new Scalar(255, 255, 0), 2);
            }

            Imgproc.line(rgbaFrame, new Point(rgbaFrame.width(), minY), new Point(0, minY), new Scalar(255, 255, 255), 1);
            Imgproc.line(rgbaFrame, new Point(centX, rgbaFrame.height()), new Point(centX, 0), new Scalar(0, 255, 0), 1);
            Imgproc.line(rgbaFrame, new Point(leftX, rgbaFrame.height()), new Point(leftX, 0), new Scalar(0, 255, 0), 1);
            Imgproc.line(rgbaFrame, new Point(rightX, rgbaFrame.height()), new Point(rightX, 0), new Scalar(0, 255, 0), 1);


            for (Mat mat : rgbaChannels) {
                mat.release();
            }

            hsv.release();
            maskedImage.release();
            contTemp.release();
            hierarchy.release();

        }

        return rgbaFrame;
    }
}