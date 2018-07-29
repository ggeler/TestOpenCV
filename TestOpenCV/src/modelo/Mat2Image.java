package modelo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Mat2Image {
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static Mat mat = new Mat();
    private static BufferedImage img;

    public Mat2Image() {
    }

    public Mat2Image(Mat mat) {
        getSpace(mat);
    }

    public static Mat getMat() {
    	return mat;
    }
    
    private static void getSpace(Mat m) {
        int type = 0;
        if (m.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (m.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        mat = m;
        int w = m.cols();
        int h = m.rows();
        if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != type)
            img = new BufferedImage(w, h, type);
    }

    public static BufferedImage getImage(Mat mat){
        getSpace(mat);
        WritableRaster raster = img.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return img;
    }

}
