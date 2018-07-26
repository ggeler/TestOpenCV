package modelo;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.CvType;

public class FaceRecognitionEigen {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public FaceRecognitionEigen() {
		// TODO Auto-generated constructor stub
	}
	public void faceRecognizer() {
		FaceRecognizer model =  EigenFaceRecognizer.create();
	    Mat grayM1=Imgcodecs.imread("ggant.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat grayM2=Imgcodecs.imread("gaston3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    List<Mat> listMat = new ArrayList<Mat>();
		listMat.add(grayM2);
		listMat.add(grayM1);
		Mat labels= new Mat(2,1,CvType.CV_32SC1);
		//IntBuffer labelsBuff = labels.
		
		
		
		model.train(listMat, labels);
		int labelIdx=model.predict_label(grayM2);
		System.out.println("Predicted label: "+labelIdx);
		
	}
	
	

}
