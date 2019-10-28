package VisionPipelines;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import Utilities.ImageUtil;
import Utilities.myRect;

public class FetchPipeline extends OpenCvPipeline {
    String TAG = "Stack Processor";
    boolean processing = true;

    public static Point location;
    public static boolean onScreen;

    public static RotatedRect stone;

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {
            /*
            //convert to hsv
            Mat hsv = new Mat();
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            //values stored as list of minimum and maximum hsv values, red then green then blue
            List<Scalar> hsvMin = new ArrayList<>();
            List<Scalar> hsvMax = new ArrayList<>();

            //Scalar redMin = new Scalar(150, 50, 100); //red min original
            //Scalar redMax = new Scalar(30, 255, 255); //red max original

            Scalar redMin = new Scalar(150, 50, 150); //red min original
            Scalar redMax = new Scalar(179, 255, 255); //red max original


            List<Mat> rgbaChannels = new ArrayList<>();

            //Keeps track of highest masses for left and right
            double[] maxMass = {Double.MIN_VALUE, Double.MIN_VALUE};

            //Keeps track of the index of the highest mass for lest and right
            int[] maxMassIndex = {3, 3};


            Mat maskedImage;

            //Core's additions
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            //End




            maskedImage = new Mat();
            ImageUtil.hsvInRange(hsv, redMin, redMax, maskedImage);

            Mat contTemp = maskedImage.clone();
            Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            //rgbaChannels.add(maskedImage.clone());


            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            //Core's additions

            double maxSize = Double.MIN_VALUE;
            myRect maxRect = null;

            for (MatOfPoint currCont : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(currCont));

                if (lookingBox.contains(rect.mid())) {
                    //Imgproc.rectangle(rgbaFrame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);

                    if (rect.area() > maxSize) {
                        maxSize = rect.area();
                        maxRect = rect;
                    }
                } else {
                    //Imgproc.rectangle(rgbaFrame, rect.tl(), rect.br(), new Scalar(255, 0, 0), 2);
                }
            }

            if (maxRect != null) {
                location = maxRect.mid();
                Imgproc.circle(rgbaFrame, maxRect.mid(), 5, new Scalar(255, 255, 255), 2);
            } else {
                location = null;
            }
            //End






            if (location != null) {
                lastGoodLoc = location;
                lookingBox = new myRect(new Point(location.x - 20, location.y - 20), new Point(location.x + 20, location.y + 20));
            } else {
            /*if (lastGoodLoc.y < 16) {
                cond = condition.UP;
                lookingBox = new Rect(new Point(176, 16), new Point(0, 0));
            } else if (lastGoodLoc.x > 72) {
                cond = condition.OFFRIGHT;
                lookingBox = new Rect(new Point(176, 144), new Point(160, 0));
            } else {
                cond = condition.OFFLEFT;
                lookingBox = new Rect(new Point(16, 144), new Point(0, 0));
            }*/
            /*}

            Imgproc.rectangle(rgbaFrame, lookingBox.tl(), lookingBox.br(), new Scalar(0, 0, 255), 5);

            Log.i(TAG, lookingBox.mid().toString());*/







            //convert to hsv
            Mat hsv = new Mat();
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            //values stored as list of minimum and maximum hsv values, red then green then blue
            List<Scalar> hsvMin = new ArrayList<>();
            List<Scalar> hsvMax = new ArrayList<>();

            hsvMin.add(new Scalar(10, 100, 100)); //yellow min
            hsvMax.add(new Scalar(29, 255, 255)); //yellow max

            hsvMin.add(new Scalar(0, 0, 0)); //null min
            hsvMax.add(new Scalar(0, 0, 0)); //null max

            hsvMin.add(new Scalar(0, 0, 0)); //null min
            hsvMax.add(new Scalar(0, 0, 0)); //null max


            List<Mat> rgbaChannels = new ArrayList<>();


            Mat maskedImage;

            //Core's additions
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            //End

            for (int i = 0; i < 3; i++) {

                maskedImage = new Mat();

                //Applying HSV limits
                ImageUtil.hsvInRange(hsv, hsvMin.get(i), hsvMax.get(i), maskedImage);

                //Start Core's additions
                Mat contTemp = maskedImage.clone();
                Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                //End Core's addition

                rgbaChannels.add(maskedImage.clone());
            }

            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            double maxSize = Double.MIN_VALUE;
            RotatedRect maxRect = null;

            for (MatOfPoint currCont : contours) {
                RotatedRect rotRect = Imgproc.minAreaRect(new MatOfPoint2f(currCont.toArray()));
                double area = rotRect.size.height * rotRect.size.width;

                if (area > maxSize) {
                    maxSize = area;
                    maxRect = rotRect;
                }
            }

            if (maxRect != null) {
                location = maxRect.center;
                Imgproc.circle(rgbaFrame, maxRect.center, 5, new Scalar(255, 255, 255), 2);
                stone = maxRect;
            } else {
                location = null;
            }
            //End






            if (location != null) {
                onScreen = true;
            } else {
                onScreen = false;

            }
        }

        return rgbaFrame;
    }

    private enum condition {
        ONTARGET,
        OFFLEFT,
        OFFRIGHT,
        SLIGHTRIGHT,
        SLIGHTLEFT,
        UNREGISTERED
    }
}