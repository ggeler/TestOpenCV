package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

class FaceKnnBestMatches extends FaceCompare {

	public FaceKnnBestMatches (Mat m1, Mat m2) {
		super(m1, m2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compareMatches() {
		// TODO Auto-generated method stub
		getFaces();
		faceCompareDescriptors();//m1,m2,d1,d2,k1,k2);
		MatOfDMatch matches=faceMatch();//m1,m2,k1,k2);
		Mat outMat=faceDrawMatches(matches);//m1,m2,k1,k2,matches);
		Imgcodecs.imwrite("knnBestMatchesList.jpg",outMat);
	}
	protected MatOfDMatch faceMatch (){//Mat descriptors1, Mat descriptors2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2) {
		List<MatOfDMatch> knnMatchesList = new ArrayList<MatOfDMatch>();
		LinkedList<DMatch> goodMatches = new LinkedList<DMatch>();

        MatOfDMatch returnMatches = new MatOfDMatch();
        System.out.println("------------KNNBestMatches------------");
        matcher.knnMatch(descriptors1, descriptors2, knnMatchesList, 2);
        	    
        for (Iterator<MatOfDMatch> iterator = knnMatchesList.iterator(); iterator.hasNext();) {
            MatOfDMatch matOfDMatch = (MatOfDMatch) iterator.next();
            if (matOfDMatch.toArray()[0].distance / matOfDMatch.toArray()[1].distance < 0.8) { //Radio = 0.8 (lowe's paper) 0.9 default
                goodMatches.add(matOfDMatch.toArray()[0]);
            }
        }
        System.out.print("KNN BestGoodMatchesSize: "+goodMatches.size());
        List<Point> pts1 = new ArrayList<Point>();
        List<Point> pts2 = new ArrayList<Point>();
        for(int i = 0; i<goodMatches.size(); i++){
            pts1.add(keypoints1.toList().get(goodMatches.get(i).queryIdx).pt);
            pts2.add(keypoints2.toList().get(goodMatches.get(i).trainIdx).pt);
        }
        
        Mat outputMask = new Mat();
        MatOfPoint2f pts1Mat = new MatOfPoint2f();
        pts1Mat.fromList(pts1);
        MatOfPoint2f pts2Mat = new MatOfPoint2f();
        pts2Mat.fromList(pts2);
        
        //Mat Homog = 
        Calib3d.findHomography(pts1Mat, pts2Mat, Calib3d.RANSAC, 15, outputMask, 2000, 0.995);

        LinkedList<DMatch> betterMatches = new LinkedList<DMatch>();
        for (int i = 0; i < goodMatches.size(); i++) {
            if (outputMask.get(i, 0)[0] != 0.0) {
                betterMatches.add(goodMatches.get(i));
            }
        }
        System.out.println("KNN BetterMatches: "+betterMatches.size());
		returnMatches.fromList(betterMatches);
		return returnMatches;
	}
}
