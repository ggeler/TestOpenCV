package vista;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class run2 {
	private static void train() {
	    List<Mat> imgs = new ArrayList<>();
	    List<Integer> labelList = new ArrayList<>();
	    MatOfInt labels = new MatOfInt();
	    String pathToImages = "/home/gaston/git/TestOpenCV/TestOpenCV";

	    File dir = new File(pathToImages);
	    File[] dirs = dir.listFiles();
	    int personCount = 1;
	    assert dirs != null;
	    for (File file : dirs) {
	        File[] faces = file.listFiles();
	        assert faces != null;
	        for (File face : faces) {
	            Mat faceMat = Imgcodecs.imread(face.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
	            Imgproc.equalizeHist(faceMat, faceMat);
	            imgs.add(faceMat);
	            labelList.add(personCount);
	        }
	        personCount++;
	    }
	    labels.fromList(labelList);
	    System.out.println(labels.dump());
	    
	    // train 
	    //FaceRecognizer recognizer = Face.createEigenFaceRecognizer(); //80, 200
	    FaceRecognizer recognizer = EigenFaceRecognizer.create();//4000
	    //FaceRecognizer recognizer = Face.createLBPHFaceRecognizer();//3, 8, 8, 8, 180

	    // label info
	    String[] names = {"W-S", "B-A", "T-Y"};
	    for (Integer integer : labelList) {
	        recognizer.setLabelInfo(integer, names[integer-1]);
	    }
	    recognizer.train(imgs, labels);
	    recognizer.save("C:\\face-data\\eigen-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\fisher-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\lbph-faces_trained_data.xml");
	}

	private static void recognize() {
	    FaceRecognizer recognizer = EigenFaceRecognizer.create()();
	    //recognizer.load("C:\\face-data\\eigen-faces_trained_data.xml");
	    
	    //FaceRecognizer recognizer = Face.createFisherFaceRecognizer();
	    //recognizer.load("C:\\face-data\\fisher-faces_trained_data.xml");
	    
	    //FaceRecognizer recognizer = Face.createLBPHFaceRecognizer();
	    //recognizer.load("C:\\face-data\\lbph-faces_trained_data.xml");

	    //String filename = "E:\\haarcascades\\haarcascade_frontalface_alt_tree.xml";
	    String filename = "E:\\haarcascades\\haarcascade_frontalcatface.xml";
	    //String filename = "E:\\lbpcascades\\lbpcascade_frontalface_improved.xml";
	    CascadeClassifier classifier = new CascadeClassifier(filename);
	    VideoCapture capture = new VideoCapture(0);
	    
	    Mat frame = new Mat();
	    Mat gray = new Mat();
	    
	    //OpenCVUtil.VideoWindow window = OpenCVUtil.createWindow();
	    MatOfRect faces  = new MatOfRect();
	    int[] labels = new int[1];
	    double[] confidences = new double[1];
	    Size size = new Size(92, 112);
	    while (capture.read(frame)) {
	        //Imgproc.pyrDown(frame, frame);
	        Imgproc.pyrDown(frame, frame);
	        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

	        classifier.detectMultiScale(gray, faces);

	        Rect[] rects = faces.toArray();
	        Mat submat = null;

	        System.out.println("");
	        for (Rect rect : rects) {
	            Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 2);
	            submat = gray.submat(rect).clone();
	            Imgproc.equalizeHist(submat, submat);
	            Imgproc.resize(submat, submat, size, 1, 1, Imgproc.INTER_CUBIC);

	            recognizer.predict(submat, labels, confidences);
	            int label = labels[0];
	            double confidence = confidences[0];
	            String labelInfo = recognizer.getLabelInfo(label);
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)recognizer).getThreshold());

	            int x = (int) Math.max(rect.tl().x - 10, 0);
	            int y = (int) Math.max(rect.tl().y - 10, 0);
	            Imgproc.putText(frame, String.format("Predict = %s", labelInfo), new Point(x, y), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0), 2);
	        }
	        if (submat != null) {
	            window.showVideo(capture, frame, gray, submat);
	        } else {
	            window.showVideo(capture, frame, gray);
	        }
	    }
	    capture.release();
	}
}
