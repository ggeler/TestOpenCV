package vista;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;

public class run {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public run() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run2();
	}
	

    public static void run2() { 
	    //Mat grayM1=Imgcodecs.imread("ggant.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    List<Mat> images= new ArrayList<Mat>();
	    
	    
 String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
	    
	    Mat im0=Imgcodecs.imread("1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat im1=Imgcodecs.imread("gaston.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat im2=Imgcodecs.imread("gaston1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat im3=Imgcodecs.imread("gaston2.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
	    images.add(im0);
	    images.add(im1);
	    images.add(im2);
	    images.add(im3);
	    
	    Mat im4=Imgcodecs.imread("agus1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat im5=Imgcodecs.imread("agus2.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat im6=Imgcodecs.imread("agus3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
	    images.add(im4);
	    images.add(im5);
	    images.add(im6);

	    
        Mat labels = new Mat(7, 1, CvType.CV_32SC1);
        labels.put(0,0,0);
        labels.put(1,0,1);
        labels.put(2, 0, 2);
        labels.put(3, 0, 3);
        labels.put(4,0,4);
        labels.put(5,0,5);
        labels.put(6, 0, 6);
      
        
       
        FaceRecognizer faceRecognizer = EigenFaceRecognizer.create(); 
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); 
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer() 
 
        faceRecognizer.train(images, labels);
        faceRecognizer.setLabelInfo(0, "Gaston");
        faceRecognizer.setLabelInfo(1, "Gaston");
        faceRecognizer.setLabelInfo(2, "Gaston");
        faceRecognizer.setLabelInfo(3, "Gaston");
        faceRecognizer.setLabelInfo(4, "Agus");
        faceRecognizer.setLabelInfo(5, "Agus");
        faceRecognizer.setLabelInfo(6, "Agus");
               
        String st= faceRecognizer.getLabelInfo(0);
        System.out.println("st: "+st);
        
        Mat im=Imgcodecs.imread("agus1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
        int predictedLabel = faceRecognizer.predict_label(im); 
 
       System.out.println("Predicted label: " + predictedLabel); 
       System.out.println("Nombre: "+faceRecognizer.getLabelInfo(predictedLabel));
    }

}
