package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.management.modelmbean.ModelMBeanAttributeInfo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FaceRecognitionEigen {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	 //,im1,im2,im3,im4,im5,im6;
	private List<Mat> imagesList=null;
	private FaceRecognizer model = LBPHFaceRecognizer.create();//10,95);//LBPHFaceRecognizer.create();
	//private FaceRecognizer model = FisherFaceRecognizer.create();//10,95);//LBPHFaceRecognizer.create();
	private MatOfInt labels = null;
	//Mat labels=null;
	
	public FaceRecognitionEigen() {
		// TODO Auto-generated constructor stub
	}
	public String recognize(Mat frame) {
	    assert frame!=null;
	    if (frame==null) System.out.println("m null");
	    int[] labelsInt = new int[1];
	    double[] confidence = new double[1];
	    model.predict(frame, labelsInt, confidence);
	    //double confidence = confidences[0];
	    int label;
	    if (confidence[0]>=35 && confidence[0]<=70) {
	    	label = labelsInt[0];
	    	System.out.println(labelsInt);
	    } else {
	    	label = -1;
	    }
	    System.out.println("frame= "+frame);
	    String labelInfo = model.getLabelInfo(label);
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence[0]);
	            //System.out.println("threshold = "+((BasicFaceRecognizer)model).getThreshold());
	            //stsrem.out.println("Distance= "+((BasicFaceRecognizer)model).)
	            //Mat mean = ((BasicFaceRecognizer)model).getMean().reshape(1, images.get(0).rows());
	            //Mat eigenValues = ((BasicFaceRecognizer)model).getEigenValues();
	            //Mat w =((BasicFaceRecognizer)model).getEigenVectors();
	            //System.out.println("Mean= "+mean);
/*	            for (int i=0;i<w.cols();i++) {
	            	System.out.println("eigenvalue: "+eigenValues.col(i));
	            }
*/
	            //System.out.println("labels= "+((BasicFaceRecognizer)model).getLabels());
	            //label=model.predict_label(frame);
/*	    label=model.predict_label(frame);
	    labelInfo = model.getLabelInfo(label);
	            System.out.println("**");
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)model).getThreshold());
	            Imgcodecs.imwrite("mean.jpg", ((BasicFaceRecognizer)model).getMean().reshape(1, images.get(0).rows()));
*/
	            if (label!=-1 ) return labelInfo;
	            else return null;
	            
	}
	public void inicializar() {//FaceRecognitionEigen f) {
		loadImages();
		train();
	}
	private void train() {
	    System.out.println(labels.dump());
	    model.train(imagesList, labels);
	    //faceRecognizer.save("eigen-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\fisher-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\lbph-faces_trained_data.xml");
	}
	private void loadImages() {
		List<Integer> labelsId = new ArrayList<>();
		int personCount=0;
		imagesList=new ArrayList<Mat>();
		Mat face=null;
		labels=new MatOfInt();
		List<String>namesList=new ArrayList<String>();
		String[] files = {"gaston-1.jpg","gaston-4.jpg","gaston-5.jpg","gaston-1.jpg","gaston-4.jpg","gaston-5.jpg","gaston-1.jpg","gaston-4.jpg","gaston-5.jpg","agus-1.jpg","agus-2.jpg","agus-3.jpg","lau-1.jpg","lau-2.jpg","lau-3.jpg","gaston-1.jpg","gaston-4.jpg","gaston-5.jpg"};//,"1.jpg","gaston.jpg"
		
		for (String f: files) {
			face=FaceCut.resize(FaceCut.cutDetectedFaces(Imgcodecs.imread(f,Imgcodecs.IMREAD_GRAYSCALE)));
			if (face!=null) {	            
			   	Imgproc.equalizeHist(face, face);
			   	imagesList.add(face);
			   	String [] name=f.split("-");
			   	if (!namesList.contains(name[0])) {
			   		namesList.add(name[0]);
			   		labelsId.add(personCount);
			   		model.setLabelInfo(personCount, name[0]);
			   		personCount++;
			   	} else {
			   		int id= namesList.indexOf(name[0]);
			   		labelsId.add(id);
			   		model.setLabelInfo(id, name[0]);
			   	}
			   	System.out.println("M="+face);
			}
		}
		labels.fromList(labelsId);
		System.out.println(labelsId);
		System.out.println(namesList);
	}
}
