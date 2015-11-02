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
        Color[] colarr={Color.white,Color.green,Color.red};
        GameData gamedata;
        Datahandler datahandler;
        Data data;
        private boolean offline=false;
        private JButton tick;
        private JCheckBox filtertick;
        private JSlider slider;
        private JTextField halv;
        private JTextField hel;
        private JTextField antr;
        private Utills utills;
        JMenuBar menuBar;
        Image bgImage = Toolkit.getDefaultToolkit().createImage("bdd.jpg");
        public Datapanel(){
            utills=new Utills();
            setPreferredSize(new Dimension(panelWidth, panelHeight));
            setLayout(null);
            setBackground(bc);
            data=new Data();
            datahandler=new Datahandler();
            menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            initMenu();
            dataMenu();
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
            JMenuItem t = new JMenuItem("Topptipset");
            JMenuItem p = new JMenuItem("Powerplay");
            fileMenu.add(s);
            fileMenu.add(e);
            fileMenu.add(t);
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
            t.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setTT();
                }
            });
            p.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setPP();
                }
            });
        }

        private void dataMenu(){
            JMenu datamenu=new JMenu("Dataverktyg");
            menuBar.add(datamenu);
            JMenuItem radutd=new JMenuItem("Radutdelning");
            datamenu.add(radutd);
            radutd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Radutd r = new Radutd();
                }
            });
            JMenuItem msyscheck=new JMenuItem("M system stats");
            datamenu.add(msyscheck);
            msyscheck.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Msysch r=new Msysch();
                }
            });
            JMenuItem spikarmenuitem=new JMenuItem("Spikar");
            datamenu.add(spikarmenuitem);
            spikarmenuitem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    spikar s = new spikar();
                }
            });

            JMenuItem dodgersmenuitem=new JMenuItem("Dodgers");
            datamenu.add(dodgersmenuitem);
            dodgersmenuitem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dodgers d=new dodgers();
                }
            });
            JMenuItem oddsMod=new JMenuItem("Ändra odds");
            datamenu.add(oddsMod);
            oddsMod.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    OddsMod o=new OddsMod();
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
                    Utdelningar u = new Utdelningar();
                }
            });
            final JCheckBoxMenuItem off=new JCheckBoxMenuItem("Offline");
            settings.add(off);
            off.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    offline = off.getState();
                }
            });
        }

        public class Msysch extends JFrame{
            public Msysch(){
                setSize(510, 110);
                setLayout(null);
                setVisible(true);
                final JTextField jt=new JTextField();
                jt.setBounds(10, 40, 100, 30);
                JLabel ulabel=new JLabel("Mata in ett m system (ex 02464342)");
                ulabel.setBounds(10, 5, 350, 30);
                final JLabel rlabel=new JLabel("");
                rlabel.setBounds(200,35,400,50);
                JButton btn=new JButton("ok");
                btn.setBounds(125, 40, 55, 30);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String s = jt.getText();
                        if (s.length() != gamedata.wodds.length){
                            rlabel.setText("Fel antal tecken");
                            return;
                        }
                        else{
                            for (int i=0;i<s.length();i++){
                                if(!(s.charAt(i)=='0' || s.charAt(i)=='1' || s.charAt(i)=='2' || s.charAt(i)=='3'  || s.charAt(i)=='4' || s.charAt(i)=='5' || s.charAt(i)=='6')){
                                    rlabel.setText("Fel inmatning");
                                    return;
                                }
                            }
                        }
                        int[] n= new int[s.length()];
                        for (int i=0;i<s.length();i++){
                            if(s.charAt(i)=='0')n[i]=0;
                            if(s.charAt(i)=='1')n[i]=1;
                            if(s.charAt(i)=='2')n[i]=2;
                            if(s.charAt(i)=='3')n[i]=3;
                            if(s.charAt(i)=='4')n[i]=4;
                            if(s.charAt(i)=='5')n[i]=5;
                            if(s.charAt(i)=='6')n[i]=6;
                        }
                        double[] res=datahandler.getMRowstats(n,gamedata);
                        rlabel.setText("Rader: "+(int)res[2]+", Utd: "+utills.round(res[0],2)+", %: "+utills.round(res[1],4));
                    }
                });

                add(rlabel);
                add(ulabel);
                add(jt);
                add(btn);
                setVisible(true);
            }
        }

        public class OddsMod extends JFrame{
           public OddsMod(){
               if(gamedata.wvalue.length==13)setSize(320, 610);
               else setSize(320, 410);
               setLayout(null);
               JLabel nrLabels[]=new JLabel[gamedata.wvalue.length];
               final JTextField text[]=new JTextField[gamedata.wvalue.length];
               final JCheckBox checkBox[]=new JCheckBox[gamedata.wvalue.length];
               JButton okButton=new JButton("Ok");
               if(gamedata.wvalue.length==13)okButton.setBounds(100,510,80,60);
               else okButton.setBounds(100, 325, 80, 40);
               this.add(okButton);
               for(int i=0;i<text.length;i++){
                   nrLabels[i]=new JLabel(String.valueOf(i+1));
                   nrLabels[i].setBounds(10,35*(i+1),20,20);
                   this.add(nrLabels[i]);
                   checkBox[i]=new JCheckBox("Kombo");
                   checkBox[i].setBounds(200,35*(i+1),100,20);
                   this.add(checkBox[i]);
                   text[i]=new JTextField("");
                   text[i].setBounds(40,35*(i+1),140,30);
                   this.add(text[i]);
               }
               okButton.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent actionEvent) {
                       for(int i=0;i<gamedata.wvalue.length;i++){
                           if(!(text[i].getText().equals(""))){
                               String s= text[i].getText();
                               double[] odds=utills.stripodds(s);
                               if(checkBox[i].isSelected()){
                                   gamedata.wodds[i][0]=utills.round((gamedata.wodds[i][0]+odds[0])/2.00,2);
                                   gamedata.wodds[i][1]=utills.round((gamedata.wodds[i][1]+odds[1])/2.00,2);
                                   gamedata.wodds[i][2]=utills.round((gamedata.wodds[i][2]+odds[2])/2.00,2);
                               }
                               else{
                                   gamedata.wodds[i][0]=utills.round(odds[0],2);
                                   gamedata.wodds[i][1]=utills.round(odds[1],2);
                                   gamedata.wodds[i][2]=utills.round(odds[2],2);
                               }
                               gamedata.value[i][0]=utills.round(gamedata.crossed[i][0]*gamedata.wodds[i][0],2);
                               gamedata.value[i][1]=utills.round(gamedata.crossed[i][1]*gamedata.wodds[i][1],2);
                               gamedata.value[i][2]=utills.round(gamedata.crossed[i][2]*gamedata.wodds[i][2],2);
                               gamedata.modOdds[i]=true;
                           }
                       }Datapanel.this.repaint();
                   }
               });
               setVisible(true);
           }
        }

        //spikarray, 1:hemmalalg, 2, kryss, 3, bortalag!!!!!!!
        public class spikar extends JFrame{
            public spikar(){
                setSize(310, 110);
                setLayout(null);
                final JTextField jt=new JTextField();
                jt.setBounds(10, 40, 200, 30);
                JLabel ulabel=new JLabel("Spikar");
                ulabel.setBounds(10, 5, 150, 30);
                final JLabel rlabel=new JLabel("");
                rlabel.setBounds(160, 35, 200, 50);
                JButton btn=new JButton("ok");
                btn.setBounds(225, 40, 55, 30);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String s=jt.getText();
                        int []res=new int[gamedata.crossed.length];
                        for (int i=0;i<s.length();i=i+2){
                            if(s.charAt(i)>=97 &&s.charAt(i)<=97+gamedata.crossed.length){
                                if(Character.getNumericValue(s.charAt(i+1))==0||Character.getNumericValue(s.charAt(i+1))==1||Character.getNumericValue(s.charAt(i+1))==2){
                                    res[s.charAt(i)-97]=Character.getNumericValue(s.charAt(i+1))+1;
                                }
                                else{
                                    rlabel.setText("Input Error");
                                    return;
                                }
                            }
                            else {
                                System.out.println("hej");
                                rlabel.setText("Input Error");
                                return;
                            }
                        }
                       gamedata.spikar=res;
                    }
                });

                add(rlabel);
                add(ulabel);
                add(jt);
                add(btn);
                setVisible(true);
            }
        }

        //spikarray, 1:hemmalalg, 2, kryss, 3, bortalag!!!!!!!
        public class dodgers extends JFrame{
            public dodgers(){
                setSize(310, 110);
                setLayout(null);
                setVisible(true);
                final JTextField jt=new JTextField();
                jt.setBounds(10, 40, 200, 30);
                JLabel ulabel=new JLabel("Dodgers");
                ulabel.setBounds(10, 5, 150, 30);
                final JLabel rlabel=new JLabel("");
                rlabel.setBounds(160, 35, 200, 50);
                JButton btn=new JButton("ok");
                btn.setBounds(225, 40, 55, 30);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String s=jt.getText();
                        int []res=new int[gamedata.crossed.length];
                        for (int i=0;i<s.length();i=i+2){
                            if(s.charAt(i)>=97 &&s.charAt(i)<=97+gamedata.crossed.length){
                                if(Character.getNumericValue(s.charAt(i+1))==0||Character.getNumericValue(s.charAt(i+1))==1||Character.getNumericValue(s.charAt(i+1))==2){
                                    res[s.charAt(i)-97]=Character.getNumericValue(s.charAt(i+1))+1;
                                }
                                else{
                                    rlabel.setText("Input Error");
                                    return;
                                }
                            }
                            else {
                                System.out.println("hej");
                                rlabel.setText("Input Error");
                                return;
                            }
                        }
                        gamedata.dodgers=res;
                    }
                });
                add(rlabel);
                add(ulabel);
                add(jt);
                add(btn);
                setVisible(true);
            }
        }


        public class Radutd extends JFrame{
            public Radutd(){
                setSize(400, 110);
                setLayout(null);
                setVisible(true);
                final JTextField jt=new JTextField();
                jt.setBounds(10, 40, 100, 30);
                JLabel ulabel=new JLabel("Mata in rad (ex 1x2x1x21)");
                ulabel.setBounds(10, 5, 350, 30);
                final JLabel rlabel=new JLabel("");
                rlabel.setBounds(200,35,400,50);
                JButton btn=new JButton("ok");
                btn.setBounds(125, 40, 55, 30);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String s = jt.getText();
                        if (s.length() != gamedata.wodds.length){
                            rlabel.setText("Fel antal tecken");
                            return;
                        }
                        else{
                            for (int i=0;i<s.length();i++){
                                if(!(s.charAt(i)=='1' || s.charAt(i)=='X' || s.charAt(i)=='x' || s.charAt(i)=='2' )){
                                    rlabel.setText("Fel inmatning");
                                    return;
                                }
                            }
                        }
                        int[] n= new int[s.length()];
                        for (int i=0;i<s.length();i++){
                            if(s.charAt(i)=='1')n[i]=0;
                            if(s.charAt(i)=='X')n[i]=1;
                            if(s.charAt(i)=='x')n[i]=1;
                            if(s.charAt(i)=='2')n[i]=2;
                        }
                        double[] res=datahandler.getRowstats(n,gamedata);
                        rlabel.setText("Utd: "+utills.round(res[0],2)+", %: "+utills.round(res[1],4));
                    }
                });

                add(rlabel);
                add(ulabel);
                add(jt);
                add(btn);
                setVisible(true);
            }
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

        public class Details extends JFrame{
            public Details() {
                setSize(907, 510);
                setLayout(null);
                setVisible(true);
                if(gamedata.wvalue.length==8){
                    JLabel[] labels = new JLabel[120];

                    double[] sumch = new double[labels.length];
                    for (int i = labels.length - 1; i >= 0; i--) {
                        if (i == labels.length - 1) sumch[i] = gamedata.utdchans[i];
                        else {
                            sumch[i] = sumch[i + 1] + gamedata.utdchans[i];
                        }
                    }
                    for (int i = 0; i < labels.length; i++) {
                        String num = utills.fs(String.valueOf(750 * (i + 1)), 5);
                        labels[i] = new JLabel(num + ":   " + utills.fs(String.valueOf(gamedata.utdarray[i]), 4) + " / " + utills.fs(String.valueOf(utills.round(gamedata.utdchans[i], 2)), 4) + "% / " + utills.fs(String.valueOf(utills.round(sumch[i], 2)), 4) + "%");
                        if (i < (labels.length / 3.00)) {
                            labels[i].setBounds(20, -20 + (i * 10), 220, 100);
                        } else if (i < ((2 * labels.length) / 3.00)) {
                            labels[i].setBounds(240, -20 + (i - labels.length / 3) * 10, 220, 100);
                        } else {
                            labels[i].setBounds(450, -20 + ((i - labels.length * 2 / 3) * 10), 220, 100);
                        }
                        this.add(labels[i]);
                    }
                }
                JLabel[] vlabels = new JLabel[gamedata.rowValArray.length];
                for (int i = 0; i < vlabels.length; i++) {
                    vlabels[i] = new JLabel(String.valueOf(i+": "+gamedata.rowValArray[i]));
                    vlabels[i].setBounds(680, -20 + (i * 10), 220, 100);
                    this.add(vlabels[i]);
                }
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
            return slider.getValue()/50.00;
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
            tick=new JButton("...");
            tick.setBounds((int) ((double) panelWidth * 0.90), (int) ((double) panelHeight * 0.57), 60, 30);
            add(tick);
            JButton beastButton=new JButton("Kör");
            beastButton.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.72),75,30);
            add(beastButton);
            beastButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    datahandler.beast(antr.getText(), gamedata, getSlider(),filtertick.isSelected());
                    repaint();
                }
            });
            tick.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Details details = new Details();
                }
            });
            filtertick=new JCheckBox();
            filtertick.setBounds((int) ((double) panelWidth * 0.87), (int) ((double) panelHeight * 0.57), 20, 20);
            add(filtertick);
        }



        private void setStryk(){
            GameData old=data.Stryk;
            data.collectStrykData(offline);
            if(data.access==false)return;
            gamedata=utills.mergeData(data.Stryk,old);
           // tick.setEnabled(false);
            repaint();
        }

        private void setEU(){
            GameData old=data.EU;
            data.collectEUData(offline);
            if(data.access==false)return;
            gamedata=utills.mergeData(data.EU,old);
            //tick.setEnabled(false);
            repaint();

        }

        private void setTT(){
            GameData old=data.TT;
            data.collectTTData(offline);
            if(data.access==false)return;
            gamedata=utills.mergeData(data.TT,old);
           // tick.setEnabled(true);
            repaint();
        }

        private void setPP(){
            GameData old=data.PP;
            data.collectPPData(offline);
            if(data.access==false)return;
            gamedata=utills.mergeData(data.PP,old);
            //tick.setEnabled(true);
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
                if(gamedata.modOdds[i]==true){
                    g.setColor(Color.red);
                    g.drawString((String.valueOf(gamedata.wodds[i][0])+"   "+String.valueOf(gamedata.wodds[i][1])+"   "+String.valueOf(gamedata.wodds[i][2])), (int)((double)panelWidth*0.20), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                }
                else {
                    g.setColor(colarr[gamedata.oddsCh[i][0]]);
                    g.drawString(String.valueOf(gamedata.wodds[i][0]),(int)((double)panelWidth*0.20), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                    g.setColor(colarr[gamedata.oddsCh[i][1]]);
                    g.drawString(String.valueOf(gamedata.wodds[i][1]),(int)((double)panelWidth*0.24), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                    g.setColor(colarr[gamedata.oddsCh[i][2]]);
                    g.drawString(String.valueOf(gamedata.wodds[i][2]),(int)((double)panelWidth*0.28), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));

                }
                g.setColor(Color.white);
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString(String.valueOf(String.valueOf(gamedata.oddsC[i])), (int) ((double) panelWidth * 0.35), (int) (((double) panelHeight * 0.065) + (i * (double) panelHeight / 18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.setColor(colarr[gamedata.crossedCh[i][0]]);
                g.drawString(String.valueOf(gamedata.crossed[i][0]),(int)((double)panelWidth*0.42), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                g.setColor(colarr[gamedata.crossedCh[i][1]]);
                g.drawString(String.valueOf(gamedata.crossed[i][1]),(int)((double)panelWidth*0.46), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                g.setColor(colarr[gamedata.crossedCh[i][2]]);
                g.drawString(String.valueOf(gamedata.crossed[i][2]),(int)((double)panelWidth*0.50), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
               // g.drawString((String.valueOf(gamedata.crossed[i][0]) + "   " + String.valueOf(gamedata.crossed[i][1]) + "   " + String.valueOf(gamedata.crossed[i][2])), (int) ((double) panelWidth * 0.42), (int) (((double) panelHeight * 0.065) + (i * (double) panelHeight / 18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                if(gamedata.modOdds[i]==true){
                    g.setColor(Color.red);
                    g.drawString((String.valueOf(gamedata.value[i][0]) + "   " + String.valueOf(gamedata.value[i][1]) + "   " + String.valueOf(gamedata.value[i][2])), (int) ((double) panelWidth * 0.57), (int) (((double) panelHeight * 0.065) + (i * (double) panelHeight / 18.0)));
                }
                else{
                    g.setColor(colarr[gamedata.valueCh[i][0]]);
                    g.drawString(String.valueOf(gamedata.value[i][0]),(int)((double)panelWidth*0.57), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                    g.setColor(colarr[gamedata.valueCh[i][1]]);
                    g.drawString(String.valueOf(gamedata.value[i][1]),(int)((double)panelWidth*0.63), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                    g.setColor(colarr[gamedata.valueCh[i][2]]);
                    g.drawString(String.valueOf(gamedata.value[i][2]),(int)((double)panelWidth*0.69), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
                }
                g.setColor(Color.white);
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
