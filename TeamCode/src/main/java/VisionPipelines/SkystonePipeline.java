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

public class SkystonePipeline extends OpenCvPipeline {
    String TAG = "Stack Processor";
    boolean processing = true;

    public static int result = -1;

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {
            //THIS GOOD FOR PHONE
            //int xMax = 105, xMin = 100;
            //int startY = 0, stoneWidth = 40, buffer = 5;

            //HEY, THIS GOOD FOR WEBCAM
            int yMax = 150, yMin = 170;
            int startX = 0, stoneWidth = 70, buffer = 20;


            //Slots for phone
            //ArrayList<myRect> slots = new ArrayList<>();
            //for (int i = 0; i < 3; i++) {
            //    slots.add(new myRect(new Point(xMax, startY + (stoneWidth * i) + buffer), new Point(xMin, startY + (stoneWidth * (i + 1)) - buffer)));
            //}

            //Slots for webcam
            ArrayList<myRect> slots = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                slots.add(new myRect(new Point(startX + (stoneWidth * i) + buffer, yMax), new Point(startX + (stoneWidth * (i + 1)) - buffer, yMin)));
            }


            Point location;
            double rectAngle;

            //convert to hsv for phone
            //Mat hsv = new Mat();
            //Imgproc.line(rgbaFrame, new Point(0, startY + stoneWidth ), new Point(rgbaFrame.width(), startY + stoneWidth), new Scalar(0, 0, 0), 10);
            //Imgproc.line(rgbaFrame, new Point(0, startY + (stoneWidth * 2)), new Point(rgbaFrame.width(), startY + (stoneWidth * 2)), new Scalar(0, 0, 0), 10);
            //Imgproc.line(rgbaFrame, new Point(0, startY + (stoneWidth * 3)), new Point(rgbaFrame.width(), startY + (stoneWidth * 3)), new Scalar(0, 0, 0), 10);
            //Imgproc.rectangle(rgbaFrame, new Point(xMin + 5, 0), new Point(xMin - 5, rgbaFrame.height()), new Scalar(0, 0, 0), -1);
            //Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //convert to hsv for webcam
            Mat hsv = new Mat();
            Imgproc.line(rgbaFrame, new Point(startX + stoneWidth,0 ), new Point(startX + stoneWidth, rgbaFrame.height()), new Scalar(0, 0, 0), 2);
            Imgproc.line(rgbaFrame, new Point(startX + (stoneWidth * 2),0 ), new Point(startX + (stoneWidth * 2), rgbaFrame.height()), new Scalar(0, 0, 0), 2);
            Imgproc.line(rgbaFrame, new Point(startX + (stoneWidth * 3),0 ), new Point(startX + (stoneWidth * 3), rgbaFrame.height()), new Scalar(0, 0, 0), 2);
            Imgproc.line(rgbaFrame, new Point(0, yMin - 5), new Point(rgbaFrame.width(), yMin - 5), new Scalar(0, 0, 0), 2);
            Imgproc.line(rgbaFrame, new Point(0, yMax + 5), new Point(rgbaFrame.width(), yMax + 5), new Scalar(0, 0, 0), 2);
            Imgproc.rectangle(rgbaFrame, new Point(0,yMin + 10), new Point(rgbaFrame.width(), yMin + 20), new Scalar(0, 0, 0), 2);
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            //values stored as list of minimum and maximum hsv values, red then green then blue
            List<Scalar> hsvMin = new ArrayList<>();
            List<Scalar> hsvMax = new ArrayList<>();

            hsvMin.add(new Scalar(10, 150, 100)); //yellow min
            hsvMax.add(new Scalar(29, 255, 255)); //yellow max

            hsvMin.add(new Scalar(0, 0, 0)); //red min
            hsvMax.add(new Scalar(0/2, 0, 0)); //red max

            hsvMin.add(new Scalar(0, 0, 0)); //blue min
            hsvMax.add(new Scalar(0, 0, 0)); //blue max


            List<Mat> rgbaChannels = new ArrayList<>();

            //Keeps track of highest masses for left and right
            double[] maxMass = {Double.MIN_VALUE, Double.MIN_VALUE};

            //Keeps track of the index of the highest mass for lest and right
            int[] maxMassIndex = {3, 3};


            Mat maskedImage = null;
            Mat colSum = new Mat();
            double mass;
            int[] data = new int[3];

            //Core's additions
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            //End

            Mat contTemp = null;

            for (int i = 0; i < 3; i++) {
                maskedImage = new Mat();

                //Applying HSV limits
                ImageUtil.hsvInRange(hsv, hsvMin.get(i), hsvMax.get(i), maskedImage);

                //Start Core's additions
                if (contTemp != null) {
                    contTemp.release();
                }
                contTemp = maskedImage.clone();
                Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                //End Core's addition

                rgbaChannels.add(maskedImage.clone());
            }


            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            //Core's additions
            ArrayList<myRect> stones = new ArrayList<>();
            for (MatOfPoint currCont : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(currCont));

                Point right = new Point(rect.mid().x + ((double)rect.width / 4), rect.mid().y);
                Point left = new Point(rect.mid().x + ((double)rect.width / 4), rect.mid().y);
                if (rect.area() > 200
                        && rgbaFrame.get((int)left.y, (int)left.x)[0] == 255
                        && rgbaFrame.get((int)right.y, (int)right.x)[0] == 255) {
                    Imgproc.rectangle(rgbaFrame, rect.bl(), rect.tr(), new Scalar(0, 255, 0), 3);
                    Imgproc.rectangle(rgbaFrame, new Point(rect.mid().x + 1, rect.mid().y + 1), new Point(rect.mid().x - 1, rect.mid().y - 1), new Scalar(255, 255, 255), 3);
                    stones.add(rect);
                }
            }

            boolean[] stonePresent = {false, false, false};
            for (myRect slot : slots) {
                Imgproc.rectangle(rgbaFrame, slot.bl(), slot.tr(), new Scalar(0, 0, 255), 3);
            }
            for (myRect currRect : stones) {
                for (int i = 0; i < 3; i++) {
                    if (slots.get(i).contains(currRect.mid())) {
                        stonePresent[i] = true;
                    }
                }
            }

            result = -1;
            for (int i = 0; i < 3; i++) {
                if (!stonePresent[i]) {
                    result = i;
                }
            }

            hsv.release();
            if (maskedImage != null) {
                maskedImage.release();
            }
            colSum.release();
            contTemp.release();
            hierarchy.release();
        }

        return rgbaFrame;
    }
}