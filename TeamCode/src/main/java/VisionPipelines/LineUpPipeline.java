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

public class LineUpPipeline extends OpenCvPipeline {
    String TAG = "Stack Processor";
    boolean processing = true;

    public static int width = -1;
    public static double xCoord = -1;
    public static double yCoord = -1;
    public static double area = -1;

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {
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

            myRect maxRect = null;
            double maxArea = -1;

            //Imgproc.line(rgbaFrame, new Point(380, rgbaFrame.height()), new Point(380, 0), new Scalar(0, 255, 0), 10);
            //Imgproc.line(rgbaFrame, new Point(420, rgbaFrame.height()), new Point(420, 0), new Scalar(0, 255, 0), 10);


            for (MatOfPoint contour : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(contour));
                Imgproc.rectangle(rgbaFrame, rect.bl(), rect.tr(), new Scalar(255, 255, 255), 3);
                if (rect.area() > maxArea && rect.area() > 1000) {
                    maxRect = rect;
                    maxArea = rect.area();
                }
            }


            if (maxRect != null) {

                Imgproc.rectangle(rgbaFrame, maxRect.bl(), maxRect.tr(), new Scalar(0, 0, 255), 5);

                width = maxRect.width;
                xCoord = maxRect.mid().x;
                yCoord = maxRect.mid().y;
                area = maxRect.area();
            } else {
                width = -1;
                xCoord = -1;
                yCoord = -1;
                area = -1;
            }
        }


        return rgbaFrame;
    }
}