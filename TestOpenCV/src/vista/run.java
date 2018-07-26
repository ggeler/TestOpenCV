package vista;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;

import modelo.FaceRecognitionEigen;

public class run {

	public run() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run2();
	}
	

    public static void run2() { 
	    //Mat grayM1=Imgcodecs.imread("ggant.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    //Mat testImage=Imgcodecs.imread("gaston3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
	    String trainingDir = "/home/gaston/git/TestOpenCV/TestOpenCV";
        File root = new File(trainingDir); 
 
        FilenameFilter imgFilter = new FilenameFilter() { 
            public boolean accept(File dir, String name) { 
                name = name.toLowerCase(); 
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png"); 
            } 
        }; 
 
        File[] imageFiles = root.listFiles(imgFilter); 
 
        List<Mat> images = new ArrayList<Mat>(imageFiles.length); 
 
        Mat labels = new Mat(imageFiles.length, 1, CvType.CV_32SC1); 
        //List<Integer> labelsBuf = labels.get(0, 0);
        //IntBuffer labelsBuf= ;
        
        int counter = 0; 
 
        for (File image : imageFiles) { 
            Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE); 
 
            int label = Integer.parseInt(image.getName().split("\\-")[0]); 
 
            images.add(counter, img); 
 
            //labelsBuf.put(counter, label); 
 
            counter++; 
        } 
 
        FaceRecognizer faceRecognizer = FisherFaceRecognizer.create(); 
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); 
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer() 
 
        faceRecognizer.train(images, labels); 
 
        //int predictedLabel = faceRecognizer.predict(testImage); 
 
       // System.out.println("Predicted label: " + predictedLabel); 
    }

}
