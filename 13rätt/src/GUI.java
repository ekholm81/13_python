import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by mq on 2014-12-20.
 */
public class GUI extends JFrame{
    private int panelHeight=450;
    private int panelWidth=950;
    public GUI(){
        setTitle("13 rätt är respekt");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Datapanel panel =new Datapanel();
        add(panel);
        setSize(panelWidth,panelHeight);
        setVisible(true);
        setResizable(false);
        try{
            setIconImage(ImageIO.read(new File("svs.jpg")));
        }catch (Exception e){}

        //pack();
    }

    private class Datapanel extends JPanel{
        Color bc= new Color(30,32,33);
        GameData gamedata;
        Datahandler datahandler;
        Data data;
        private JToggleButton tick;
        private JSlider slider;
        private JTextField halv;
        private JTextField hel;
        private JTextField antr;
        private Utills utills;
        JMenuBar menuBar;
        Image bgImage = Toolkit.getDefaultToolkit().createImage("bdd.jpg");
        public Datapanel(){
            utills=new Utills();
            setPreferredSize(new Dimension(panelWidth,panelHeight));
            setLayout(null);
            setBackground(bc);
            data=new Data();
            datahandler=new Datahandler();
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            setPP();
            initMenu();
            settingsMenu();
            initslider();
            initTextbox();
            initlabels();
            initButtons();
            repaint();
        }

        private void initMenu(){
            JMenu fileMenu = new JMenu("Spel");
            menuBar.add(fileMenu);
            JMenuItem s = new JMenuItem("Stryktipset");
            JMenuItem e = new JMenuItem("Europatipset");
            JMenuItem p = new JMenuItem("Topptipset");
            fileMenu.add(s);
            fileMenu.add(e);
            fileMenu.add(p);
            s.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setStryk();
                }
            });
            e.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setEU();
                }
            });
            p.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setPP();
                }
            });
        }

        private void settingsMenu(){
            JMenu settings=new JMenu("Inställningar");
            menuBar.add(settings);
            JMenuItem utdelning=new JMenuItem("Utdelningar");
            settings.add(utdelning);
            utdelning.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Utdelningar u=new Utdelningar();
                }
            });
        }

        public class Utdelningar extends JFrame {
            public Utdelningar(){
                setSize(150, 110);
                setLayout(null);
                setVisible(true);
                final JTextField jt=new JTextField(String.valueOf(gamedata.utdelning*100));
                jt.setBounds(10, 40, 50, 30);
                JButton btn=new JButton("ok");
                btn.setBounds(65, 40, 55, 30);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        gamedata.utdelning=Double.parseDouble(jt.getText())/100.00;
                        setVisible(false);
                        dispose();
                    }
                });
                JLabel ulabel=new JLabel("Ändra utdelning");
                ulabel.setBounds(10,5,150,30);
                add(ulabel);
                add(jt);
                add(btn);
                setVisible(true);
            }
        }

        private void initslider(){
            slider=new JSlider();
            slider.setBounds((int)((double)panelWidth*0.77),(int)((double)panelHeight * 0.80),215,20);
            slider.setValue(0);
            add(slider);
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    repaint();
                }
            });
        }

        private double getSlider(){
            return slider.getValue()/70.00;
        }

        private void initTextbox(){
            halv =new JTextField("");
            halv.setBounds((int) ((double) panelWidth * 0.77), (int) ((double) panelHeight * 0.15), 30, 20);
          //  add(halv);
            hel =new JTextField("");
            hel.setBounds((int) ((double) panelWidth * 0.83), (int) ((double) panelHeight * 0.15), 30, 20);
          //  add(hel);
            antr=new JTextField("");
            antr.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.67),60,20);
            add(antr);
        }

        private void initlabels(){
            JLabel halvlabel=new JLabel("halv");
            JLabel hellabel=new JLabel("hel");
            JLabel slabel=new JLabel("vikta sannolikhet");
            JLabel antalraderlabel=new JLabel("antal rader");
            halvlabel.setBounds((int) ((double) panelWidth * 0.77), (int) ((double) panelHeight * 0.11), 30, 20);
            hellabel.setBounds((int) ((double) panelWidth * 0.83), (int) ((double) panelHeight * 0.11), 30, 20);
            slabel.setBounds((int)((double)panelWidth*0.77),(int)((double)panelHeight * 0.21),150,20);
            antalraderlabel.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.63),150,20);
         //   add(halvlabel);
         //   add(hellabel);
         //   add(slabel);
            antalraderlabel.setBackground(Color.white);
          //  add(antalraderlabel);

        }

        private void initButtons(){
            tick=new JToggleButton("inv");
            tick.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.57),60,30);
            add(tick);
            JButton beastButton=new JButton("Go");
            beastButton.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.72),75,30);
            add(beastButton);
            beastButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    datahandler.beast(antr.getText(),gamedata, getSlider(),tick.isSelected());
                    repaint();
                }
            });

        }



        private void setStryk(){
            data.collectStrykData();
            if(data.access==false)return;
            gamedata=data.Stryk;
            repaint();
        }

        private void setEU(){
            data.collectEUData();
            if(data.access==false)return;
            gamedata=data.EU;
            repaint();

        }

        private void setPP(){
            data.collectPPData();
            if(data.access==false)return;
            gamedata=data.PP;
            repaint();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, -10, null);
            g.setColor(Color.white);
            if(gamedata==null){
                System.out.println("NULL");
                return;
            }
            for(int i=0;i<gamedata.games.length;i++){
                g.drawString(String.valueOf(i+1)+". ",(int)((double)panelWidth*0.01),(int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            //matchsträngar
            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((gamedata.games[i]), (int)((double)panelWidth*0.05), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.wodds[i][0])+"   "+String.valueOf(gamedata.wodds[i][1])+"   "+String.valueOf(gamedata.wodds[i][2])), (int)((double)panelWidth*0.20), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.crossed[i][0]) + "   " + String.valueOf(gamedata.crossed[i][1]) + "   " + String.valueOf(gamedata.crossed[i][2])), (int) ((double) panelWidth * 0.35), (int) (((double) panelHeight * 0.065) + (i * (double) panelHeight / 18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.value[i][0]) + "   " + String.valueOf(gamedata.value[i][1]) + "   " + String.valueOf(gamedata.value[i][2])), (int) ((double) panelWidth * 0.50), (int) (((double) panelHeight * 0.065) + (i * (double) panelHeight / 18.0)));
            }

            g.drawString("Spelstopp: " + gamedata.spelstopp, (int) ((double) panelWidth * 0.03), (int) ((double) panelHeight * 0.85));
            g.drawString("Oms: " + gamedata.omsättning, (int) ((double) panelWidth * 0.28), (int) ((double) panelHeight * 0.85));
            g.setColor(Color.white);
            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.rad[i][0])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.76), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight * 0.050)));
                if(gamedata.rad[i][1])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.7925), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight * 0.050)));
                if(gamedata.rad[i][2])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.824), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight * 0.050)));
            }
            g.setColor(Color.red);
            for(int i=0;i<gamedata.games.length;i++){
                g.drawString(String.valueOf(gamedata.dtecken[i][0]),(int) ((double) panelWidth * 0.76), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
                g.drawString(String.valueOf(gamedata.dtecken[i][1]),(int) ((double) panelWidth * 0.7925), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
                g.drawString(String.valueOf(gamedata.dtecken[i][2]),(int) ((double) panelWidth * 0.824), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
            }
            g.setColor(Color.white);
            g.drawString(String.valueOf("Alla rätt: "+gamedata.sannolikhet13+"%"),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.50));
            g.drawString(String.valueOf("Exp val: "+gamedata.expval),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.55));
            if(gamedata.games.length>8) {
                g.drawString(String.valueOf("10:   "+gamedata.sannolikhet10+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.35));
                g.drawString(String.valueOf("11:   "+gamedata.sannolikhet11+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.40));
                g.drawString(String.valueOf("12:   "+gamedata.sannolikhet12+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.45));
            }
            else {
                g.drawString(String.valueOf("5:   "+gamedata.sannolikhet10+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.35));
                g.drawString(String.valueOf("6:   "+gamedata.sannolikhet11+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.40));
                g.drawString(String.valueOf("7:   "+gamedata.sannolikhet12+"%"), (int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.45));
            }
            g.drawString(String.valueOf("Subtrahera odds: "+utills.round((getSlider()),2)),(int)((double)panelWidth*0.60),(int)((double)panelHeight*0.85));
        }

    }
}
