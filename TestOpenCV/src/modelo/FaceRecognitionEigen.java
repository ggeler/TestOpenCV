package modelo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.BasicFaceRecognizer;
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
	private int[] intLabels;
	private List<Mat> images= new ArrayList<Mat>();
	private FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();//10,95);//LBPHFaceRecognizer.create();
	
	//Nuevas
	List<Integer> label2List = new ArrayList<>();
    MatOfInt labels2 = new MatOfInt();

	
	public FaceRecognitionEigen() {
		// TODO Auto-generated constructor stub
	}
	public String faceRecognizer(Mat m) {
			Mat mTmp=new Mat();//m.rows(),m.cols(),1);
		Imgproc.cvtColor(m,mTmp,Imgproc.COLOR_BGR2GRAY);
		//Imgproc.chan
		System.out.println("MTmp  Size H:"+mTmp.height()+" W:"+mTmp.width()+" Color: "+mTmp.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
		double[] confidence = null;
		
		
		faceRecognizer.predict(m, intLabels, confidence);
		int predictedLabel = faceRecognizer.predict_label(mTmp);
		System.out.println("predicted: "+predictedLabel);
		return faceRecognizer.getLabelInfo(predictedLabel);
	}
	public void recognize(Mat m) {
	    //FaceRecognizer recognizer = EigenFaceRecognizer.create()();
	    //faceRecognizer.read("eigen-faces_trained_data.xml");
	    
	    //FaceRecognizer recognizer = Face.createFisherFaceRecognizer();
	    //recognizer.load("C:\\face-data\\fisher-faces_trained_data.xml");
	    
	    //FaceRecognizer recognizer = Face.createLBPHFaceRecognizer();
	    //recognizer.load("C:\\face-data\\lbph-faces_trained_data.xml");

	    //String filename = "E:\\haarcascades\\haarcascade_frontalface_alt_tree.xml";
	    //String filename = "E:\\haarcascades\\haarcascade_frontalcatface.xml";
	    //String filename = "E:\\lbpcascades\\lbpcascade_frontalface_improved.xml";
	    //CascadeClassifier classifier = new CascadeClassifier(filename);
	    //VideoCapture capture = new VideoCapture(0);
	    
	    //Mat frame = new Mat();
	    Mat gray = new Mat();
	    
	    //OpenCVUtil.VideoWindow window = OpenCVUtil.createWindow();
	    //MatOfRect faces  = new MatOfRect();
	    int[] labelsInt2 = new int[1];
	    double[] confidences2 = new double[1];
	    Size size = new Size(92, 112);
	    //while (capture.read(frame)) {
	        //Imgproc.pyrDown(frame, frame);
	    System.out.println("M  Size H:"+m.height()+" W:"+m.width()+" Color: "+m.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
		 //Imgproc.pyrDown(m, m);
	        Imgproc.cvtColor(m, gray, Imgproc.COLOR_BGR2GRAY);

	        //classifier.detectMultiScale(gray, faces);

	        //Rect[] rects = faces.toArray();
	        //Mat submat = null;

	        //System.out.println("");
	        //for (Rect rect : rects) {
	            //Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 2);
	            //submat = gray.submat(rect).clone();
	        Mat destination = new Mat();//(gray.rows(), gray.cols(), gray.type());    
	        Imgproc.equalizeHist(gray, destination);
	            //Imgproc.resize(submat, submat, size, 1, 1, Imgproc.INTER_CUBIC);
	        System.out.println("gray  Size H:"+destination.height()+" W:"+destination.width()+" Color: "+destination.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
	        System.out.println("M  Size H:"+m.height()+" W:"+m.width()+" Color: "+m.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
			
	            faceRecognizer.predict(destination, labelsInt2, confidences2);
	            int label = labelsInt2[0];
	            double confidence = confidences2[0];
	            
	            String labelInfo = faceRecognizer.getLabelInfo(label);
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)faceRecognizer).getThreshold());
	            label=faceRecognizer.predict_label(destination);
	            labelInfo = faceRecognizer.getLabelInfo(label);
	            System.out.println("**");
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)faceRecognizer).getThreshold());
	            
	            //int x = (int) Math.max(rect.tl().x - 10, 0);
	            //int y = (int) Math.max(rect.tl().y - 10, 0);
	            //Imgproc.putText(frame, String.format("Predict = %s", labelInfo), new Point(x, y), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0), 2);
	        //}
	        //if (submat != null) {
	        //    window.showVideo(capture, frame, gray, submat);
	        //} else {
	        //    window.showVideo(capture, frame, gray);
	        //}
//	    }
//	    capture.release();
//	}
		
	}
	public void inicializar(FaceRecognitionEigen f) {
		loadImages();
		//trainImages();
		train();

	}
	private void trainImages() {
        
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); 
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer() 
 
        faceRecognizer.train(images, labels);

	}
	private void train() {
	    //List<Mat> imgs = new ArrayList<>();
	    

	    
	    System.out.println(labels2.dump());
	    
	    // train 
	    //FaceRecognizer recognizer = Face.createEigenFaceRecognizer(); //80, 200
	    //FaceRecognizer recognizer = EigenFaceRecognizer.create();//4000
	    //FaceRecognizer recognizer = Face.createLBPHFaceRecognizer();//3, 8, 8, 8, 180

	    // label info

	    faceRecognizer.train(images, labels2);
	    //faceRecognizer.save("eigen-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\fisher-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\lbph-faces_trained_data.xml");

	}
	private void loadImages() {
		int personCount=1;
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
		    if (im0!=null) {	            
		    	Imgproc.equalizeHist(im0, im0);
		    	label2List.add(personCount++);
		    	images.add(im0); }
            else System.out.println("null en im0");
		    if (im1!=null) {	            
		    	Imgproc.equalizeHist(im1, im1);
		    	label2List.add(personCount++);
		    	images.add(im1); }
		    else System.out.println("null en im1");
		    if (im2!=null) {	            
		    	Imgproc.equalizeHist(im2, im2);
		    	label2List.add(personCount++);
		    	images.add(im2); }
		    else System.out.println("null en im2");
		    if (im3!=null) {	            
		    	Imgproc.equalizeHist(im3, im3);
		    	label2List.add(personCount++);
		    	images.add(im3); }
		    else System.out.println("null en im3");
	     if (im4!=null) {	            
		    	Imgproc.equalizeHist(im4, im4);
		    	label2List.add(personCount++);
		    	images.add(im4); }
	     else System.out.println("null en im4");
	     if (im5!=null) {	            
		    	Imgproc.equalizeHist(im5, im5);
		    	label2List.add(personCount++);
		    	images.add(im5); }
	     else System.out.println("null en im5");
	     if (im6!=null) {	            
		    	Imgproc.equalizeHist(im6, im6);
		    	label2List.add(personCount++);
		    	images.add(im6); }
	     else System.out.println("null en im6");

	     labels2.fromList(label2List);
		    //String[] names = {"W-S", "B-A", "T-Y"};
//		    for (Integer integer : label2List) {
//		        faceRecognizer.setLabelInfo(integer, integer.toString());
//		    }
/*	    intLabels[0]=0;
        intLabels[1]=1;
        
	    labels = new Mat(7, 1, CvType.CV_32SC1);
        labels.put(0,0,0);
        labels.put(1,0,0);
       labels.put(2, 0, 0);
        labels.put(3, 0, 0);
        labels.put(4,0,1);
        labels.put(5,0,1);
        labels.put(6, 0, 1);*/
        faceRecognizer.setLabelInfo(0, "Gaston0");
        faceRecognizer.setLabelInfo(1, "Gaston1");
        faceRecognizer.setLabelInfo(2, "Gaston2");
        faceRecognizer.setLabelInfo(3, "Gaston3");
        faceRecognizer.setLabelInfo(4, "Agus1");
        faceRecognizer.setLabelInfo(5, "Agus2");
        faceRecognizer.setLabelInfo(6, "Agus3");
        
/*        Imgproc.cvtColor(m,m,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat newM=FaceCut.resize(m);
 	   System.out.println("newM Size H:"+newM.height()+" W:"+newM.width());
 		
        images.add(newM);
        labels.put(7, 0, 7);
        faceRecognizer.setLabelInfo(7, "new");*/
        //labels.put(7,0,7);
        
	}
	
	

}
