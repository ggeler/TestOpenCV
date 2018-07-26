package modelo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FaceRecognitionEigen {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private Mat im0,im1,im2,im3,im4,im5,im6;
	private Mat labels;
	private List<Mat> images= new ArrayList<Mat>();
	private FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
	
	public FaceRecognitionEigen() {
		// TODO Auto-generated constructor stub
	}
	public String faceRecognizer(Mat m) {
		loadImages(m);
		trainImages();
		Mat mTmp=new Mat(m.rows(),m.cols(),1);
		Imgproc.cvtColor(m,mTmp,Imgproc.COLOR_BGR2GRAY);
		//Imgproc.chan
		System.out.println("MTmp  Size H:"+mTmp.height()+" W:"+mTmp.width()+" Color: "+mTmp.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
	     

		int predictedLabel = faceRecognizer.predict_label(mTmp);
		return faceRecognizer.getLabelInfo(predictedLabel);
	}
	private void trainImages() {
        
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); 
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer() 
 
        faceRecognizer.train(images, labels);

	}
	private void loadImages(Mat m) {
  	     im0=Imgcodecs.imread("1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im1=Imgcodecs.imread("gaston.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im2=Imgcodecs.imread("gaston1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im3=Imgcodecs.imread("gaston4.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//FaceCut.resize(FaceCut.cutDetectedFaces(
	    
	     im4=Imgcodecs.imread("agus1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im5=Imgcodecs.imread("agus2.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im6=Imgcodecs.imread("agus3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		   System.out.println("M0 Size H:"+im0.height()+" W:"+im0.width()+" Col Row "+im0.cols()+" "+im0.rows()+" Color "+im0.channels());
		   System.out.println("M1 Size H:"+im1.height()+" W:"+im1.width()+im1.channels());
		   System.out.println("M2 Size H:"+im2.height()+" W:"+im2.width());
		   System.out.println("M3 Size H:"+im3.height()+" W:"+im3.width());
		   System.out.println("M4 Size H:"+im4.height()+" W:"+im4.width());
		   System.out.println("M5 Size H:"+im5.height()+" W:"+im5.width());
		   System.out.println("M6 Size H:"+im6.height()+" W:"+im6.width());
		    if (im0!=null) images.add(im0);else System.out.println("null en im0");
		    if (im1!=null) images.add(im1);else System.out.println("null en im1");
		    if (im2!=null) images.add(im2);else System.out.println("null en im2");
		    if (im3!=null) images.add(im3);else System.out.println("null en im3");
	     if (im4!=null) images.add(im4);else System.out.println("null en im4");
	     if (im5!=null) images.add(im5);else System.out.println("null en im5");
	     if (im6!=null) images.add(im6);else System.out.println("null en im6");

	    if (im0==null || im1==null || im2==null || im3==null||im4==null||im5==null||im6==null) System.out.println("algun null en el train");
        labels = new Mat(7, 1, CvType.CV_32SC1);
        labels.put(0,0,0);
        labels.put(1,0,1);
        labels.put(2, 0, 2);
        labels.put(3, 0, 3);
        labels.put(4,0,4);
        labels.put(5,0,5);
        labels.put(6, 0, 6);
        faceRecognizer.setLabelInfo(0, "Gaston");
        faceRecognizer.setLabelInfo(1, "Gaston");
        faceRecognizer.setLabelInfo(2, "Gaston");
        faceRecognizer.setLabelInfo(3, "Gaston");
        faceRecognizer.setLabelInfo(4, "Agus");
        faceRecognizer.setLabelInfo(5, "Agus");
        faceRecognizer.setLabelInfo(6, "Agus");
        
/*        Imgproc.cvtColor(m,m,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat newM=FaceCut.resize(m);
 	   System.out.println("newM Size H:"+newM.height()+" W:"+newM.width());
 		
        images.add(newM);
        labels.put(7, 0, 7);
        faceRecognizer.setLabelInfo(7, "new");*/
        //labels.put(7,0,7);
        
	}
	
	

}
