package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;

public class FaceOldMatch {

	public void elviejoComparadorCOmpleto() {
		System.out.println("Hola");

	}
	private void compareImagesOriginal(Mat m1) {
/*	    MatOfDMatch matches = new MatOfDMatch();
	    MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
	    MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
	    Mat descriptors1 = new Mat();
	    Mat descriptors2 = new Mat();
	    List<DMatch> bestMatchesList = new ArrayList<DMatch>();
	    List<DMatch> matchesList; //= matches.toList();
	    MatOfDMatch matchesFiltered = new MatOfDMatch();
        	 
	    Double max_dist = 0.0;
        Double min_dist = 100.0;
	    Double threshold = 3 * min_dist;
        Double threshold2 = 2 * min_dist;
   
	    /*Mat grayM1 = new Mat(m1.height(),m1.width(),0);
	    Imgproc.cvtColor(m1, grayM1, Imgproc.COLOR_RGB2GRAY);*/
 	    
/*	    Mat grayM22=Imgcodecs.imread("gaston4.jpg");//,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat grayM12=Imgcodecs.imread("gaston3.jpg");//,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    
	    Mat grayM1=FaceCut.cutDetectedFaces(grayM12);
	    Mat grayM2=FaceCut.cutDetectedFaces(grayM22);
	    if (grayM1==null || grayM2==null) {
	    	System.out.println("Alguna imagen no detecta cara - exit");
	    	return;
	    }
	    detector.detect(grayM1, keypoints1);
	    detector.detect(grayM2, keypoints2);
	    System.out.println("Detected M1: "+grayM1.size()+" - M2: "+grayM2.size()+" Blobs" );

	    List<KeyPoint> m1KeyPoints=keypoints1.toList();
	    List<KeyPoint> m2KeyPoints=keypoints2.toList();
	    System.out.println("M1 Keypoints: "+m1KeyPoints.size() );
	    System.out.println("M2 Keypoints: "+m2KeyPoints.size() );

	    extractor.compute(grayM1, keypoints1, descriptors1);
	    extractor.compute(grayM2, keypoints2, descriptors2);
	    System.out.println("M1 Descriptor Size: "+descriptors1.size());
	    System.out.println("M2 Descriptor Size: "+descriptors2.size());
	    
	    matcher.match(descriptors1, descriptors2, matches);
	    System.out.println("matches.size() : " + matches.size());
        System.out.println("matches : " + matches);
        
	    for (DMatch d: matches.toArray()) {
	    	Double dist=(double) d.distance;
	    	//if (dist>0) System.out.print("Dist:"+dist+" - ");
	    	if (dist<min_dist && dist!=0) min_dist=dist;
	    	if (dist>max_dist) max_dist=dist;
	    }
	    System.out.println("\nmax_dist : " + max_dist);
	    System.out.println("min_dist : " + min_dist);

	    threshold = 3 * min_dist;
        threshold2 = 2 * min_dist;
        if (threshold > 75) {
            threshold = 75.0;
        } else if (threshold2 >= max_dist) {
            threshold = min_dist * 1.1;
        } else if (threshold >= max_dist) {
            threshold = threshold2 * 1.4;
        }
        System.out.println("Threshold : " + threshold);
 
	    matchesList = matches.toList();
        for (int i = 0; i < matchesList.size(); i++) {//DMatch d: matches.toArray()) {
            Double dist = (double) matchesList.get(i).distance;//d.distance;
            if (dist < threshold) {
                bestMatchesList.add(matches.toList().get(i));
                //System.out.println(String.format(i + " best match added : %s", dist));
            }
        }
        
        matchesFiltered.fromList(bestMatchesList);
        System.out.println("matchesFiltered.size() : " + matchesFiltered.size());
        if (matchesFiltered.rows() >= 1) {
            System.out.println("match found");
        } else {
            System.out.println("match not found");
        }
        
        List<MatOfDMatch> knnMatchesList = new ArrayList<MatOfDMatch>();
        matcher.knnMatch(descriptors1, descriptors2, knnMatchesList, 2);
        Mat outputMat = new Mat();
        Features2d.drawMatches2(grayM1, keypoints1, grayM2, keypoints2, knnMatchesList, outputMat);
        Imgcodecs.imwrite("knnMatchesList.jpg",outputMat);
        
        LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
        for (Iterator<MatOfDMatch> iterator = knnMatchesList.iterator(); iterator.hasNext();) {
            MatOfDMatch matOfDMatch = (MatOfDMatch) iterator.next();
            if (matOfDMatch.toArray()[0].distance / matOfDMatch.toArray()[1].distance < 0.8) { //Radio = 0.8 (lowe's papper) 0.9 default
                good_matches.add(matOfDMatch.toArray()[0]);
            }
        }
        
        List<Point> pts1 = new ArrayList<Point>();
        List<Point> pts2 = new ArrayList<Point>();
        for(int i = 0; i<good_matches.size(); i++){
            pts1.add(keypoints1.toList().get(good_matches.get(i).queryIdx).pt);
            pts2.add(keypoints2.toList().get(good_matches.get(i).trainIdx).pt);
        }
        
        Mat outputMask = new Mat();
        MatOfPoint2f pts1Mat = new MatOfPoint2f();
        pts1Mat.fromList(pts1);
        MatOfPoint2f pts2Mat = new MatOfPoint2f();
        pts2Mat.fromList(pts2);
        
        //Mat Homog = 
        Calib3d.findHomography(pts1Mat, pts2Mat, Calib3d.RANSAC, 15, outputMask, 2000, 0.995);

        // outputMask contains zeros and ones indicating which matches are filtered
        LinkedList<DMatch> better_matches = new LinkedList<DMatch>();
        for (int i = 0; i < good_matches.size(); i++) {
            if (outputMask.get(i, 0)[0] != 0.0) {
                better_matches.add(good_matches.get(i));
            }
        }
     // DRAWING OUTPUT
        Mat outputImg = new Mat();
        // this will draw all matches, works fine
        MatOfDMatch better_matches_mat = new MatOfDMatch();
        better_matches_mat.fromList(better_matches);
        Features2d.drawMatches(grayM1, keypoints1, grayM2, keypoints2, better_matches_mat, outputImg);

        // save image
        Imgcodecs.imwrite("better_matches_mat.jpg", outputImg);
        
        
        
        //outputMat=null;
        //Mat outputMat = new Mat();
        Features2d.drawMatches(grayM1, keypoints1, grayM2, keypoints2, matchesFiltered, outputMat);
        Imgcodecs.imwrite("matchesfiltered.jpg",outputMat);
        Features2d.drawMatches(grayM1, keypoints1, grayM2, keypoints2, matches, outputMat);
        Imgcodecs.imwrite("mathes.jpg",outputMat);
        /*
        //Ratio Test
        Double ratio = 0.8; // As in Lowe's paper (can be tuned)
        int matchessize=matchesList2.size();
        for (int x = 0; x < matchessize; ++x)
        {
        	for ( int y=0;matchesList2.iterator().hasNext();y++) .toArray()
        		if (d.distance < ratio * d.) {
        			bestMatchesList.push_back(d);
        			System.out.println("MatchesList2: "+matchesList2);
        		}
        	}
        }
        
        matchesFiltered.fromList(bestMatchesList);
        System.out.println("matchesFiltered.size() : " + matchesFiltered.size());
        if (matchesFiltered.rows() >= 1) {
            System.out.println("match found");
        } else {
            System.out.println("match not found");
        }
        
	    /*Mat newM1 = new Mat(grayM1.height(),grayM1.width(),0);
	    Face.drawFacemarks(grayM1,newM1);
	    Imgcodecs.imwrite("facemarks.jpg", newM1);*/
	}

}

