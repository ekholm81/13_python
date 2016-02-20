import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by mq on 2015-09-06.
 */
public class Plotter extends JFrame {
    File file;
    Color blank=new Color(255,255,255);
    Color filled=new Color(150,76,3);
    int[][][] coords;
    int[] betala;
    int[] ren;
    int[] con;
    int[] bolag;
    JCheckBox cb;
    boolean kali=false;
    ArrayList<int[]> msys=new ArrayList<int[]>();
    public Plotter(int n){
        try{
            BufferedReader br = new BufferedReader(new FileReader("conf.txt"));
            final JLabel actionLabel;
            setSize(320, 340);
            setLayout(null);
            actionLabel = new JLabel("actionlabel");
            actionLabel.setBounds(30, 250, 200, 50);
            add(actionLabel);
            JLabel label00 =new JLabel("ruta 0x0");
            label00.setBounds(10, 70, 90, 20);
            JLabel label02=new JLabel("ruta 0x2");
            label02.setBounds(10, 90, 90, 20);
            JLabel label80=new JLabel("ruta 8x0");
            label80.setBounds(10, 110, 90, 20);
            JLabel labelBet=new JLabel("Betala");
            labelBet.setBounds(10, 130, 90, 20);
            JLabel labelRen=new JLabel("Rensa");
            labelRen.setBounds(10, 150, 90, 20);
            JLabel labelBol=new JLabel("Bolag");
            labelBol.setBounds(10, 170, 90, 20);
            JLabel labelCon=new JLabel("Confirm");
            labelCon.setBounds(10, 190, 90, 20);
            add(label00);
            add(label02);
            add(label80);
            add(labelBet);
            add(labelBol);
            add(labelCon);
            add(labelRen);


            final JTextField oox=new JTextField(br.readLine());
            oox.setBounds(110, 70, 90, 20);
            final JTextField ooy=new JTextField(br.readLine());
            ooy.setBounds(210, 70, 90, 20);

            final JTextField o2x=new JTextField(br.readLine());
            o2x.setBounds(110, 90, 90, 20);
            final JTextField boy=new JTextField(br.readLine());
            boy.setBounds(210, 110, 90, 20);

            final JTextField betx=new JTextField(br.readLine());
            betx.setBounds(110, 130, 90, 20);
            final JTextField bety=new JTextField(br.readLine());
            bety.setBounds(210, 130, 90, 20);

            final JTextField rensax=new JTextField(br.readLine());
            rensax.setBounds(110, 150, 90, 20);
            final JTextField rensay=new JTextField(br.readLine());
            rensay.setBounds(210, 150, 90, 20);

            final JTextField bolagx = new JTextField(br.readLine());
            bolagx.setBounds(110, 170, 90, 20);

            final JTextField bolagy = new JTextField(br.readLine());
            bolagy.setBounds(210, 170, 90, 20);

            final JTextField confx=new JTextField(br.readLine());
            confx.setBounds(110, 190, 90, 20);






            final JTextField confy=new JTextField(br.readLine());
            confy.setBounds(210, 190, 90, 20);

            cb=new JCheckBox("bolag");
            cb.setBounds(10, 220, 90, 20);
            add(cb);
            add(confx);
            add(confy);
            add(oox);
            add(o2x);
            add(betx);
            add(ooy);
            add(boy);
            add(bety);
            add(rensax);
            add(rensay);
            add(bolagx);
            add(bolagy);
            JButton kal=new JButton("Kalibrera");
            kal.setBounds(10, 10, 90, 40);
            add(kal);
            JButton mata=new JButton("Plott");
            mata.setBounds(110, 10, 90, 40);
            add(mata);
            JButton testB=new JButton("Test");
            testB.setBounds(210, 10, 90, 40);
            add(testB);
            setVisible(true);
            coords=new int[8][3][2];
            betala=new int[2];
            ren=new int[2];
            con=new int[2];
            bolag= new int[2];
            kal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        msys.clear();
                        FileReader inputFile = new FileReader("o.txt");
                        BufferedReader bufferReader = new BufferedReader(inputFile);
                        String line;
                        while ((line = bufferReader.readLine()) != null) {
                            int[] arr = new int[8];
                            for (int i = 0; i < 8; i++) {
                                arr[i] = Character.getNumericValue(line.charAt(i));
                            }
                            msys.add(arr);

                        }
                    } catch (Exception e) {
                    }
                    for (int i = 0; i < msys.size(); i++) {
                        for (int j = 0; j < 8; j++) System.out.print(msys.get(i)[j]);
                        System.out.println(" ");
                    }
                    actionLabel.setText("antal sys: " + msys.size());
                    int xdis = (Integer.parseInt(o2x.getText()) - Integer.parseInt(oox.getText())) / 2;
                    int ydis = (Integer.parseInt(boy.getText()) - Integer.parseInt(ooy.getText())) / 7;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 3; j++) {
                            coords[i][j][0] = Integer.parseInt(oox.getText()) + (j * xdis);
                            coords[i][j][1] = Integer.parseInt(ooy.getText()) + (i * ydis);
                        }
                    }
                    betala[0] = Integer.parseInt(betx.getText());
                    betala[1] = Integer.parseInt(bety.getText());
                    ren[0] = Integer.parseInt(rensax.getText());
                    ren[1] = Integer.parseInt(rensay.getText());
                    bolag[0]=Integer.parseInt(bolagx.getText());
                    bolag[1]=Integer.parseInt(bolagy.getText());
                    con[0] = Integer.parseInt(confx.getText());
                    con[1] = Integer.parseInt(confy.getText());
                    kali = true;
                    try{
                        PrintWriter writer = new PrintWriter("conf.txt", "UTF-8");
                        writer.println(oox.getText().toString());
                        writer.println(ooy.getText().toString());
                        writer.println(o2x.getText().toString());
                        writer.println(boy.getText().toString());
                        writer.println(betx.getText().toString());
                        writer.println(bety.getText().toString());
                        writer.println(rensax.getText().toString());
                        writer.println(rensay.getText().toString());
                        writer.println(bolagx.getText().toString());
                        writer.println(bolagy.getText().toString());
                        writer.println(confx.getText().toString());
                        writer.println(confy.getText().toString());
                        writer.close();
                    }catch (Exception e){}
                }



            });

            mata.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    if (!kali) return;
                    for (int i = 0; i < msys.size(); i++) {
                        actionLabel.setText(String.valueOf(msys.get(i)[0])+String.valueOf(msys.get(i)[1])+String.valueOf(msys.get(i)[2])+String.valueOf(msys.get(i)[3])+String.valueOf(msys.get(i)[4])+String.valueOf(msys.get(i)[5])+String.valueOf(msys.get(i)[6])+String.valueOf(msys.get(i)[7]));
                        int[][]mrad=new int[8][3];
                        for (int j = 0; j < 8; j++){
                            mrad[j]=getMsign(msys.get(i)[j]);
                        }
                        matarad(mrad);
                    }

                }
            });

        }catch (Exception e){}
    }

    private void matarad(int[][] msys){
        try{
            Robot robot = new Robot();

            for(int i=0;i<8;i++) {
                for (int j = 0; j < 3; j++) {
                    if(msys[i][j]==1){
                        robot.mouseMove(coords[i][j][0], coords[i][j][1]);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        Thread.sleep(140);
                    }
                }
            }
            Thread.sleep(1750);
            robot.mouseMove(betala[0], betala[1]);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(500);
            robot.mouseMove(betala[0], betala[1]);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(700);
            robot.mouseMove(con[0], con[1]);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            if(cb.isSelected()){
                Thread.sleep(2300);
                robot.mouseMove(bolag[0], bolag[1]);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            Thread.sleep(1500);
            robot.mouseMove(ren[0], ren[1]);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(1500);


        }
        catch (Exception e){}
    }

    int[] getMsign(int a){
        int []b =new int[3];
        if(a==0)b=new int[] {1,0,0};
        if(a==1)b=new int[] {0,1,0};
        if(a==2)b=new int[] {0,0,1};
        if(a==3)b=new int[] {1,1,0};
        if(a==4)b=new int[] {1,0,1};
        if(a==5)b=new int[] {0,1,1};
        if(a==6)b=new int[] {1,1,1};
        return b;
    }

    public void mus(){
        try {
            while (true) {
                Thread.sleep(1000);
                Point b = MouseInfo.getPointerInfo().getLocation();
                System.out.println(b.getX()+" : "+b.getY());
            }
        }catch(Exception e){}
    }


    //0 not checked, 1 checked, 2 error
    public int color(int x, int y){
        try{
            Rectangle screenRect = new Rectangle(x,y,1,1);
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            Color c=new Color(capture.getRGB(0,0,1,1,null,0,1)[0]);
            if(compC(c,blank))return 0;
            if(compC(c,filled))return 1;
            return 2;
        }catch (Exception e){}
       return 0;
    }

    public boolean compC(Color a, Color b){
        return ((a.getRed()-b.getRed())*(a.getRed()-b.getRed())+(a.getBlue()-b.getBlue())*(a.getBlue()-b.getBlue())+(a.getGreen()-b.getGreen())*(a.getGreen()-b.getGreen())<200);
    }

    public void getMouse(){
        try{
            /*System.out.println("5");
            Thread.sleep(1000);
            System.out.println("4");
            Thread.sleep(1000);
            System.out.println("3");
            Thread.sleep(1000);
            System.out.println("2");
            Thread.sleep(1000);
            System.out.println("1");*/
            while (true) {
                Thread.sleep(1000);
                Point b = MouseInfo.getPointerInfo().getLocation();
                System.out.println( color((int) b.getX(), (int) b.getY()));
            }
        }catch (Exception e){}
    }


    public static void main(String[] args) {
        Plotter p=new Plotter(8);
        //p.getMouse();
       // p.mus();
    }
}

