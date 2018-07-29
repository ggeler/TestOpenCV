package vista;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.VideoCap;
import modelo.FaceRecognitionEigen;

public class MyFrame extends JFrame {
    private JPanel contentPane;
    private VideoCap videoCap; 
    private FaceRecognitionEigen fr=new FaceRecognitionEigen();
    private class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
                try { Thread.sleep(30);
                } catch (InterruptedException e) {    }
            }
        }
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        videoCap = new VideoCap(fr);
        fr.inicializar();
        new MyThread().start();
    }

    public void paint(Graphics g){
        g = contentPane.getGraphics();
        //videoCap.getDetectedFaceOneFrame();
        //videoCap.
        g.drawImage(videoCap.detectFaces(), 0, 0, this);
    }
}
