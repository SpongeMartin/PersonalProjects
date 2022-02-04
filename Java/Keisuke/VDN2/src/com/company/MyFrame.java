package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class MyFrame {

    public static void main(String[] args){

        JFrame frame=new JFrame("");

        JButton start = new JButton("Start");
        start.setFocusable(false);
        start.setBounds(320, 410, 100, 40);

        JButton endless = new JButton("Endless");
        endless.setFocusable(false);
        endless.setBounds(520, 410, 100, 40);

        JButton custom = new JButton("Custom");
        custom.setFocusable(false);
        custom.setBounds(420,510,100,40);

        JTextField textX= new JTextField();
        JTextField textY= new JTextField();
        JButton submit = new JButton("Submit");
        submit.setBounds(420,350,100,40);
        textX.setPreferredSize(new Dimension(70,20));
        textY.setPreferredSize(new Dimension(70,20));
        textX.setBounds(520,250,100,25);
        textY.setBounds(320,250,100,25);
        JLabel tekst = new JLabel("Input your preferred grid size");
        tekst.setFont(new Font("Arial",Font.BOLD,20));
        JLabel tekstX = new JLabel("Horizontal length:");
        JLabel tekstY = new JLabel("Vertical length:");
        tekst.setBounds(330,150,400,30);
        tekstX.setBounds(320,225,150,20);
        tekstY.setBounds(520,225,150,20);


        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem mstart = new JMenuItem("Start");
        JMenuItem mexit = new JMenuItem("Exit");
        JMenuItem mendless = new JMenuItem("Endless mode");
        JMenuItem mload = new JMenuItem("Load");
        file.add(mstart);
        file.add(mendless);
        file.add(mload);
        file.add(mexit);
        bar.setBounds(0, 0, 1000, 20);

        bar.add(file);

        ImageIcon image = new ImageIcon("Keisuke.png");
        JLabel label = new JLabel("");
        label.setIcon(image);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(270, 150, 400, 213);


        //-ZAČETNI PANEL--------------------------------------
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.add(start);
        panel.add(endless);
        panel.add(label);
        panel.add(bar);
        panel.add(custom);

        //Regular game page---------------------------------------------------

        JMenuBar bar1 = new JMenuBar();

        JMenu file1 = new JMenu("File");

        JButton newGame = new JButton("New game");
        JButton test = new JButton("Test");
        JButton resiMe = new JButton("Solution");
        JButton backToMenu = new JButton("Back to menu");
        JButton dokoncaj = new JButton("Finish");
        JButton novaIgra = new JButton("New game");
        JButton vrniSe = new JButton("Back to menu");
        JButton vracaj = new JButton("Return");
        JLabel zmaga = new JLabel("You win!");
        JMenuItem mnewgame = new JMenuItem("New game");
        JMenuItem mbackToMenu = new JMenuItem("Back to menu");
        JMenuItem mexit1 = new JMenuItem("Exit");
        JMenuItem msave = new JMenuItem("Save");

        file1.add(mnewgame);
        file1.add(msave);
        file1.add(mbackToMenu);
        file1.add(mexit1);
        bar1.setBounds(0, 0, 2000, 20);
        bar1.add(file1);

        dokoncaj.setFocusable(false);
        dokoncaj.setBounds(10,2,100,40);
        resiMe.setFocusable(false);
        resiMe.setBounds(10,52,100,40);
        novaIgra.setFocusable(false);
        novaIgra.setBounds(10,2,115,40);
        vracaj.setFocusable(false);
        vracaj.setBounds(10,52,115,40);
        test.setFocusable(false);
        test.setBounds(10,102,100,40);
        vrniSe.setFocusable(false);
        vrniSe.setBounds(10,102,115,40);
        backToMenu.setFocusable(false);
        newGame.setFocusable(false);

        JButton resiMe1=new JButton("Solution");
        JButton test1=new JButton("Test");
        JButton check=new JButton("Continue");
        JButton end=new JButton("End");
        resiMe1.setFocusable(false);
        test1.setFocusable(false);
        check.setFocusable(false);
        end.setFocusable(false);
        resiMe1.setBounds(10,52,100,40);
        check.setBounds(10,2,100,40);
        test1.setBounds(10,102,100,40);
        end.setBounds(10,2,100,40);

        backToMenu.setBounds(650, 450, 150, 40);

        newGame.setBounds(200, 450, 150, 40);
        zmaga.setFont(new Font("Dante", Font.ITALIC, 100));
        zmaga.setForeground(Color.GREEN);
        zmaga.setBounds(300, 0, 1000, 300);

        //----------------------------------- GAME
        Random r = new Random();
        int gridX = r.nextInt(6) + 4;
        int gridY = r.nextInt(6) + 4;
        int gridSize = gridX * gridY;
        int blackBox;
        if (gridSize <= 25) {
            blackBox = r.nextInt(3) + 3;
        } else if (25 < gridSize && gridSize <= 49) {
            blackBox = r.nextInt(3) + 6;
        } else {
            blackBox = r.nextInt(3) + 12;
        }
        int stGumbov = gridSize - blackBox;
        JButton[] temporary = new JButton[gridSize];
        JButton[][] resitev = new JButton[gridX][gridY];
        JButton[][] gumbi = new JButton[gridX][gridY];
        System.out.println(stGumbov);
        System.out.println(blackBox);
        System.out.println(gridSize);
        System.out.println(gridX);
        System.out.println(gridY);

        for (int i = 0; i < gridSize; i++) {
            int pravilno = r.nextInt(10);
            temporary[i] = new JButton("" + pravilno);
            temporary[i].setBackground(Color.WHITE);
            temporary[i].setForeground(Color.BLACK);
            temporary[i].setFocusable(false);
            temporary[i].setEnabled(false);
        }

        for (int i = gridSize - blackBox; i < gridSize; i++) {
            temporary[i].setBackground(Color.BLACK);
            temporary[i].setText("");
            temporary[i].setFocusable(false);
            temporary[i].setEnabled(false);
        }
        Collections.shuffle(Arrays.asList(temporary));
        Collections.shuffle(Arrays.asList(temporary));
        Collections.shuffle(Arrays.asList(temporary));

        int stIter = 0;
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                resitev[i][j] = new JButton();
                resitev[i][j] = temporary[stIter++];
            }
        }


        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                gumbi[i][j] = new JButton("");
                if (resitev[i][j].getBackground() == Color.white) {
                    gumbi[i][j].setText("0");
                    gumbi[i][j].setBackground(Color.WHITE);
                    gumbi[i][j].setFocusable(false);
                    gumbi[i][j].setEnabled(true);
                } else {
                    gumbi[i][j].setBackground(Color.BLACK);
                    gumbi[i][j].setFocusable(false);
                    gumbi[i][j].setEnabled(false);
                }
            }
        }

        int ststr = 0;
        boolean boo = true;
        if (resitev[0][0].getBackground()==Color.BLACK){
            boo=false;
        }


        for (int i = 0; i < gridX; i++) {
            for (int j=0; j < gridY; j++){
                if (boo && resitev[i][j].getBackground()==Color.WHITE) {
                    ststr++;
                    boo=false;
                }else if(resitev[i][j].getBackground()==Color.BLACK){
                    boo=true;
                }
            }
            if (i<gridX-1){
                if (resitev[i+1][0].getBackground()!=Color.BLACK && resitev[i][gridY-1].getBackground()!=Color.BLACK) {
                    ststr++;
                }
            }
        }

        int ststrr=0;
        boo = true;
        if (resitev[0][0].getBackground()==Color.BLACK){
            boo=false;
        }

        for (int i = 0; i < gridY; i++) {
            for (int j=0; j < gridX; j++){
                if (boo && resitev[j][i].getBackground()==Color.WHITE) {
                    ststrr++;
                    boo=false;
                }else if(resitev[j][i].getBackground()==Color.BLACK){
                    boo=true;
                }
            }
            if (i<gridY-1){
                if (resitev[0][i+1].getBackground()!=Color.BLACK && resitev[gridX-1][i].getBackground()!=Color.BLACK) {
                    ststrr++;
                }
            }
        }

        String vodoravno = "";

        for (int i = 0;i<gridX;i++) {
            for (int j = 0; j < gridY; j++) {
                if (resitev[i][j].getBackground()==Color.BLACK){
                    vodoravno+=" ";
                }else {
                    vodoravno += resitev[i][j].getText();
                }
            }
            vodoravno+=" ";
        }

        String navpicno="";
        for (int i = 0;i<gridY;i++) {
            for (int j = 0; j < gridX; j++) {
                if (resitev[j][i].getBackground()==Color.BLACK){
                    navpicno+=" ";
                }else {
                    navpicno += resitev[j][i].getText();
                }
            }
            navpicno+=" ";
        }

        String[] vodoravn = vodoravno.split(" ");
        String[] navpicn = navpicno.split(" ");
        Collections.shuffle(Arrays.asList(navpicn));
        Collections.shuffle(Arrays.asList(navpicn));
        Collections.shuffle(Arrays.asList(vodoravn));
        Collections.shuffle(Arrays.asList(vodoravn));

        navpicno = String.join(" ", navpicn);
        vodoravno = String.join(" ", vodoravn);

        JLabel vodoravnaStevila= new JLabel("Horizontal: "+vodoravno);
        JLabel navpicnaStevila=new JLabel("Vetrical: "+navpicno);

        //---------PANELS----------------------------------------------

        JPanel endlessPanel=new JPanel();
        endlessPanel.setBackground(Color.WHITE);
        endlessPanel.setLayout(null);
        endlessPanel.setPreferredSize(new Dimension(120,500));
        endlessPanel.add(resiMe1);
        endlessPanel.add(test1);
        endlessPanel.add(check);

        JPanel resitevEndlessPanel=new JPanel();
        resitevEndlessPanel.setBackground(Color.WHITE);
        resitevEndlessPanel.setLayout(null);
        resitevEndlessPanel.setPreferredSize(new Dimension(120,500));
        resitevEndlessPanel.add(end);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setBounds(0, 20, 800, 650);
        panel1.setLayout(new GridLayout(gridX, gridY));
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                panel1.add(gumbi[i][j]);
            }
        }

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setBounds(0, 20, 800, 650);
        panel2.setLayout(new GridLayout(gridX, gridY));
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                panel2.add(resitev[i][j]);
            }
        }

        JPanel panelCustom = new JPanel();
        panelCustom.setLayout(null);
        panelCustom.add(textX);
        panelCustom.add(textY);
        panelCustom.add(submit);
        panelCustom.add(tekst);
        panelCustom.add(tekstX);
        panelCustom.add(tekstY);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel3.setLayout(null);
        panel3.add(zmaga);
        panel3.add(newGame);
        panel3.add(backToMenu);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(null);
        sidePanel.setPreferredSize(new Dimension(120,500));
        sidePanel.setBackground(Color.WHITE);
        sidePanel.add(resiMe);
        sidePanel.add(dokoncaj);
        sidePanel.add(test);

        JPanel resitevSidePanel=new JPanel();
        resitevSidePanel.setLayout(null);
        resitevSidePanel.setPreferredSize(new Dimension(140,500));
        resitevSidePanel.setBackground(Color.WHITE);
        resitevSidePanel.add(vrniSe);
        resitevSidePanel.add(vracaj);
        resitevSidePanel.add(novaIgra);

        JPanel spodnjiPanel = new JPanel();
        spodnjiPanel.setBackground(Color.WHITE);
        spodnjiPanel.setLayout(new BorderLayout());
        spodnjiPanel.add(vodoravnaStevila,BorderLayout.NORTH);
        spodnjiPanel.add(navpicnaStevila,BorderLayout.SOUTH);

        Poslusalec p=new Poslusalec(mload,msave,resitevEndlessPanel,resiMe1,test1,check,end,resitevSidePanel,vracaj,vrniSe,novaIgra,textX,textY,submit,panelCustom,custom,stGumbov,mendless,endless,endlessPanel,test,blackBox,temporary,r,mnewgame,navpicnaStevila,vodoravnaStevila,mbackToMenu,backToMenu,gridX,gridY,panel2,gridSize,dokoncaj,newGame,resiMe,mexit1,mexit,mstart,gumbi,resitev,start,frame,panel,panel1,spodnjiPanel,sidePanel,bar1,panel3);
        test.addActionListener(p);
        msave.addActionListener(p);
        mload.addActionListener(p);
        test1.addActionListener(p);
        resiMe1.addActionListener(p);
        check.addActionListener(p);
        end.addActionListener(p);
        vrniSe.addActionListener(p);
        vracaj.addActionListener(p);
        novaIgra.addActionListener(p);
        submit.addActionListener(p);
        textX.addActionListener(p);
        textY.addActionListener(p);
        custom.addActionListener(p);
        endless.addActionListener(p);
        mendless.addActionListener(p);
        dokoncaj.addActionListener(p);
        backToMenu.addActionListener(p);
        newGame.addActionListener(p);
        mnewgame.addActionListener(p);
        start.addActionListener(p);
        mexit.addActionListener(p);
        mstart.addActionListener(p);
        resiMe.addActionListener(p);
        mexit1.addActionListener(p);
        mbackToMenu.addActionListener(p);
        for (int i=0;i<gridX;i++){
            for (int j=0;j<gridY;j++){
                resitev[i][j].addActionListener(p);
                gumbi[i][j].addActionListener(p);
            }
        }
        for (int i=0;i<gridSize;i++){
            temporary[i].addActionListener(p);
        }
        //Frame attributes-----------------------------------------------------------------------------
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Keisuke");
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.add(panel);
    }

        public static class Poslusalec implements ActionListener{

            public JFrame frame;
            public JButton[][] gumbi;
            public JButton[][] resitev;
            public JButton[] temporary;
            public JButton custom;
            public JButton endless;
            public JButton newGame;
            public JButton backToMenu;
            public JButton dokoncaj;
            public JButton test;
            public JButton resiMe;
            public JButton start;
            public JButton submit;
            public JButton vracaj;
            public JButton vrniSe;
            public JButton check;
            public JButton resiMe1;
            public JButton test1;
            public JButton end;
            public JButton novaIgra;
            public JMenuItem mbackToMenu;
            public JMenuItem mexit;
            public JMenuItem mexit1;
            public JMenuItem mstart;
            public JMenuItem mnewGame;
            public JMenuItem mendless;
            public JMenuItem msave;
            public JMenuItem mload;
            public JPanel resitevSidePanel;
            public JPanel panel2;
            public JPanel panel1;
            public JPanel panel;
            public JPanel endlessPanel;
            public JPanel resitevEndlessPanel;
            public JPanel sidePanel;
            public JPanel spodnjiPanel;
            public JPanel panel3;
            public JPanel panelCustom;
            public JMenuBar bar1;
            public JTextField textX;
            public JTextField textY;
            public JLabel vodoravnaStevila;
            public JLabel navpicnaStevila;
            public int gridSize;
            public int blackBox;
            public int gridX;
            public int stGumbov;
            public int gridY;
            public int pozicijaX;
            public int pozicijaY;
            public Random r;
            boolean opravljen;
            public Poslusalec(JMenuItem mload,JMenuItem msave,JPanel resitevEndlessPanel,JButton resiMe1,JButton test1,JButton check,JButton end,JPanel resitevSidePanel,JButton vracaj,JButton vrniSe,JButton novaIgra,JTextField textX, JTextField textY,JButton submit,JPanel panelCustom,JButton custom,int stGumbov,JMenuItem mendless,JButton endless,JPanel endlessPanel,JButton test,int blackBox,JButton[] temporary,Random r,JMenuItem mnewGame,JLabel navpicnaStevila,JLabel vodoravnaStevila,JMenuItem mbackToMenu,JButton backToMenu,int gridX,int gridY,JPanel panel2,int gridSize,JButton dokoncaj,JButton newGame,JButton resiMe, JMenuItem mexit1, JMenuItem mexit, JMenuItem mstart,JButton[][] gumbi,JButton[][] resitev,JButton start,JFrame frame,JPanel panel, JPanel panel1, JPanel spodnjiPanel, JPanel sidePanel, JMenuBar bar1, JPanel panel3){
                this.panel2=panel2;
                this.msave=msave;
                this.mload=mload;
                this.resitevSidePanel=resitevSidePanel;
                this.resiMe1=resiMe1;
                this.test1=test1;
                this.check=check;
                this.end=end;
                this.resitevEndlessPanel=resitevEndlessPanel;
                this.vracaj=vracaj;
                this.vrniSe=vrniSe;
                this.novaIgra=novaIgra;
                this.textX=textX;
                this.textY=textY;
                this.submit=submit;
                this.custom=custom;
                this.panelCustom=panelCustom;
                this.endlessPanel=endlessPanel;
                this.mendless=mendless;
                this.endless=endless;
                this.stGumbov=stGumbov;
                this.test=test;
                this.blackBox=blackBox;
                this.r=r;
                this.temporary=temporary;
                this.vodoravnaStevila=vodoravnaStevila;
                this.navpicnaStevila=navpicnaStevila;
                this.backToMenu=backToMenu;
                this.mbackToMenu=mbackToMenu;
                this.gridX=gridX;
                this.gridY=gridY;
                this.gridSize=gridSize;
                this.dokoncaj=dokoncaj;
                this.newGame=newGame;
                this.resiMe=resiMe;
                this.mnewGame=mnewGame;
                this.mexit=mexit;
                this.mexit1=mexit1;
                this.mstart=mstart;
                this.panel=panel;
                this.panel1=panel1;
                this.spodnjiPanel=spodnjiPanel;
                this.sidePanel=sidePanel;
                this.bar1=bar1;
                this.frame=frame;
                this.start=start;
                this.gumbi=gumbi;
                this.resitev=resitev;
                this.panel3=panel3;
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                /*
                Save vmesnik nisem naredil, ker mi je zmanjkalo časa. Princip razumem, vrednosti na gumbih shranim, velikost tabele, ko se prebere shranjeno datoteko,
                se vrednosti prenesejo na dejansko igro.
                */
                if (e.getSource() == msave) {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("save datoteka", "txt");
                    chooser.setFileFilter(filter);
                    int ret = chooser.showSaveDialog(null);

                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        System.out.println("Save file je shranjen v: " + file.getAbsolutePath());

                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                            writer.write("");
                            writer.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                if (e.getSource() == mload){
                    JFileChooser chooser = new JFileChooser();
                    int ret = chooser.showSaveDialog(null);
                    if (ret == JFileChooser.APPROVE_OPTION){

                    }
                }

                //gumb ki prebere vnos v dveh text fieldih, in naredi custom igro za igralca
                if (e.getSource() == submit) {
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            panel1.remove(gumbi[i][j]);
                            panel2.remove(resitev[i][j]);
                        }
                    }
                    gridX = Integer.parseInt(textX.getText());
                    gridY = Integer.parseInt(textY.getText());
                    gridSize = gridX * gridY;
                    panel1.setLayout(new GridLayout(gridX, gridY));
                    panel2.setLayout(new GridLayout(gridX, gridY));
                    if (gridSize <= 25) {
                        blackBox = r.nextInt(3) + 3;
                    } else if (25 < gridSize && gridSize <= 49) {
                        blackBox = r.nextInt(3) + 6;
                    } else {
                        blackBox = r.nextInt(3) + 12;
                    }
                    stGumbov = gridSize - blackBox;
                    temporary = new JButton[gridSize];


                    for (int i = 0; i < gridSize; i++) {
                        int pravilno = r.nextInt(10);
                        temporary[i] = new JButton("" + pravilno);
                        temporary[i].setBackground(Color.WHITE);
                        temporary[i].setForeground(Color.BLACK);
                        temporary[i].setFocusable(false);
                        temporary[i].setEnabled(false);
                    }
                    for (int i = gridSize - blackBox; i < gridSize; i++) {
                        temporary[i].setBackground(Color.BLACK);
                        temporary[i].setText("");
                        temporary[i].setFocusable(false);
                        temporary[i].setEnabled(false);
                    }
                    Collections.shuffle(Arrays.asList(temporary));
                    Collections.shuffle(Arrays.asList(temporary));
                    Collections.shuffle(Arrays.asList(temporary));

                    resitev = new JButton[gridX][gridY];
                    gumbi = new JButton[gridX][gridY];
                    int stIter = 0;
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            resitev[i][j] = new JButton();
                            resitev[i][j] = temporary[stIter++];
                        }
                    }

                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            gumbi[i][j] = new JButton("");
                            if (resitev[i][j].getBackground() == Color.white) {
                                gumbi[i][j].setText("0");
                                gumbi[i][j].setBackground(Color.WHITE);
                                gumbi[i][j].setFocusable(false);
                                gumbi[i][j].setEnabled(true);
                            } else {
                                gumbi[i][j].setBackground(Color.BLACK);
                                gumbi[i][j].setFocusable(false);
                                gumbi[i][j].setEnabled(false);
                            }
                        }
                    }


                    int ststr = 0;
                    boolean boo = true;
                    if (resitev[0][0].getBackground() == Color.BLACK) {
                        boo = false;
                    }
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            if (boo && resitev[i][j].getBackground() == Color.WHITE) {
                                ststr++;
                                boo = false;
                            } else if (resitev[i][j].getBackground() == Color.BLACK) {
                                boo = true;
                            }
                        }
                        if (i < gridX - 1) {
                            if (resitev[i + 1][0].getBackground() != Color.BLACK && resitev[i][gridY - 1].getBackground() != Color.BLACK) {
                                ststr++;
                            }
                        }
                    }

                    int ststrr = 0;
                    boo = true;
                    if (resitev[0][0].getBackground() == Color.BLACK) {
                        boo = false;
                    }

                    for (int i = 0; i < gridY; i++) {
                        for (int j = 0; j < gridX; j++) {
                            if (boo && resitev[j][i].getBackground() == Color.WHITE) {
                                ststrr++;
                                boo = false;
                            } else if (resitev[j][i].getBackground() == Color.BLACK) {
                                boo = true;
                            }
                        }
                        if (i < gridY - 1) {
                            if (resitev[0][i + 1].getBackground() != Color.BLACK && resitev[gridX - 1][i].getBackground() != Color.BLACK) {
                                ststrr++;
                            }
                        }
                    }

                    String vodoravno = "";

                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            if (resitev[i][j].getBackground() == Color.BLACK) {
                                vodoravno += " ";
                            } else {
                                vodoravno += resitev[i][j].getText();
                            }
                        }
                        vodoravno += " ";
                    }

                    String navpicno = "";
                    for (int i = 0; i < gridY; i++) {
                        for (int j = 0; j < gridX; j++) {
                            if (resitev[j][i].getBackground() == Color.BLACK) {
                                navpicno += " ";
                            } else {
                                navpicno += resitev[j][i].getText();
                            }
                        }
                        navpicno += " ";
                    }
                    String[] vodoravn = vodoravno.split(" ");
                    String[] navpicn = navpicno.split(" ");
                    Collections.shuffle(Arrays.asList(navpicn));
                    Collections.shuffle(Arrays.asList(navpicn));
                    Collections.shuffle(Arrays.asList(vodoravn));
                    Collections.shuffle(Arrays.asList(vodoravn));

                    navpicno = String.join(" ", navpicn);
                    vodoravno = String.join(" ", vodoravn);

                    spodnjiPanel.remove(vodoravnaStevila);
                    spodnjiPanel.remove(navpicnaStevila);
                    vodoravnaStevila = new JLabel("Horizontal: " + vodoravno);
                    navpicnaStevila = new JLabel("Vertical: " + navpicno);
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            gumbi[i][j].addActionListener(this);
                            panel1.add(gumbi[i][j]);
                            panel2.add(resitev[i][j]);
                        }
                    }
                    spodnjiPanel.add(vodoravnaStevila, BorderLayout.NORTH);
                    spodnjiPanel.add(navpicnaStevila, BorderLayout.SOUTH);
                    frame.remove(panelCustom);
                    frame.add(panel1, BorderLayout.CENTER);
                    frame.add(bar1, BorderLayout.NORTH);
                    frame.add(spodnjiPanel, BorderLayout.SOUTH);
                    frame.add(sidePanel, BorderLayout.EAST);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                //Gumb, ki vrže igralca na panel z izbiro velikosti igre
                if (e.getSource() == custom) {
                    frame.remove(panel);
                    frame.add(panelCustom);
                    frame.setSize(new Dimension(1000,800));
                    frame.setResizable(false);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                //MenuItem, ki igralca redirecta na first page
                if (e.getSource() == mbackToMenu || e.getSource() == vrniSe) {
                    frame.remove(panel1);
                    frame.remove(panel2);
                    frame.remove(sidePanel);
                    frame.remove(spodnjiPanel);
                    frame.remove(resitevSidePanel);
                    frame.remove(endlessPanel);
                    frame.remove(resitevEndlessPanel);
                    frame.remove(bar1);
                    frame.add(panel);
                    frame.setSize(new Dimension(1000,800));
                    frame.setResizable(false);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                //Gumb na zaslonu, ko uporabnik zmaga redirecta na first page
                if (e.getSource() == backToMenu) {
                    frame.remove(panel3);
                    frame.add(panel);
                    frame.setSize(new Dimension(1000,800));
                    frame.setResizable(false);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                //ACTION LISTENER ZA GUMBE
                for (int i = 0; i < gridX; i++) {
                    for (int j = 0; j < gridY; j++) {
                        if (e.getSource() == gumbi[i][j]) {
                            int tmp = Integer.parseInt(gumbi[i][j].getText()) + 1;
                            if (tmp == 10) tmp = 0;
                            pozicijaX = i;
                            pozicijaY = j;
                            gumbi[i][j].setText("" + tmp);
                            if (gumbi[i][j].getBackground() == Color.GREEN) {
                                gumbi[i][j].setBackground(Color.WHITE);
                            }
                        }
                    }
                }

                //Checker če je gumb na poziciji pravilen
                if (e.getSource() == test) {
                    if (gumbi[pozicijaX][pozicijaY].getText().equals(resitev[pozicijaX][pozicijaY].getText())) {
                        gumbi[pozicijaX][pozicijaY].setBackground(Color.GREEN);
                    }
                }

                //Nova igra
                if (e.getSource() == mnewGame || e.getSource() == newGame || e.getSource() == novaIgra) {
                    frame.remove(panel2);
                    frame.remove(panel1);
                    frame.remove(panel3);
                    frame.remove(resitevSidePanel);
                    frame.add(panel1);
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            panel1.remove(gumbi[i][j]);
                            panel2.remove(resitev[i][j]);
                        }
                    }
                    gridX = r.nextInt(6) + 4;
                    gridY = r.nextInt(6) + 4;
                    gridSize = gridX * gridY;
                    panel1.setLayout(new GridLayout(gridX, gridY));
                    panel2.setLayout(new GridLayout(gridX, gridY));
                    if (gridSize <= 25) {
                        blackBox = r.nextInt(3) + 3;
                    } else if (25 < gridSize && gridSize <= 49) {
                        blackBox = r.nextInt(3) + 6;
                    } else {
                        blackBox = r.nextInt(3) + 12;
                    }
                    stGumbov = gridSize - blackBox;
                    temporary = new JButton[gridSize];


                    for (int i = 0; i < gridSize; i++) {
                        int pravilno = r.nextInt(10);
                        temporary[i] = new JButton("" + pravilno);
                        temporary[i].setBackground(Color.WHITE);
                        temporary[i].setForeground(Color.BLACK);
                        temporary[i].setFocusable(false);
                        temporary[i].setEnabled(false);
                    }
                    for (int i = gridSize - blackBox; i < gridSize; i++) {
                        temporary[i].setBackground(Color.BLACK);
                        temporary[i].setText("");
                        temporary[i].setFocusable(false);
                        temporary[i].setEnabled(false);
                    }
                    Collections.shuffle(Arrays.asList(temporary));
                    Collections.shuffle(Arrays.asList(temporary));
                    Collections.shuffle(Arrays.asList(temporary));

                    resitev = new JButton[gridX][gridY];
                    gumbi = new JButton[gridX][gridY];
                    int stIter = 0;
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            resitev[i][j] = new JButton();
                            resitev[i][j] = temporary[stIter++];
                        }
                    }

                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            gumbi[i][j] = new JButton("");
                            if (resitev[i][j].getBackground() == Color.white) {
                                gumbi[i][j].setText("0");
                                gumbi[i][j].setBackground(Color.WHITE);
                                gumbi[i][j].setFocusable(false);
                                gumbi[i][j].setEnabled(true);
                            } else {
                                gumbi[i][j].setBackground(Color.BLACK);
                                gumbi[i][j].setFocusable(false);
                                gumbi[i][j].setEnabled(false);
                            }
                        }
                    }
                    int ststr = 0;
                    boolean boo = true;
                    if (resitev[0][0].getBackground() == Color.BLACK) {
                        boo = false;
                    }


                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            if (boo && resitev[i][j].getBackground() == Color.WHITE) {
                                ststr++;
                                boo = false;
                            } else if (resitev[i][j].getBackground() == Color.BLACK) {
                                boo = true;
                            }
                        }
                        if (i < gridX - 1) {
                            if (resitev[i + 1][0].getBackground() != Color.BLACK && resitev[i][gridY - 1].getBackground() != Color.BLACK) {
                                ststr++;
                            }
                        }
                    }

                    int ststrr = 0;
                    boo = true;
                    if (resitev[0][0].getBackground() == Color.BLACK) {
                        boo = false;
                    }

                    for (int i = 0; i < gridY; i++) {
                        for (int j = 0; j < gridX; j++) {
                            if (boo && resitev[j][i].getBackground() == Color.WHITE) {
                                ststrr++;
                                boo = false;
                            } else if (resitev[j][i].getBackground() == Color.BLACK) {
                                boo = true;
                            }
                        }
                        if (i < gridY - 1) {
                            if (resitev[0][i + 1].getBackground() != Color.BLACK && resitev[gridX - 1][i].getBackground() != Color.BLACK) {
                                ststrr++;
                            }
                        }
                    }

                    String vodoravno = "";

                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            if (resitev[i][j].getBackground() == Color.BLACK) {
                                vodoravno += " ";
                            } else {
                                vodoravno += resitev[i][j].getText();
                            }
                        }
                        vodoravno += " ";
                    }

                    String navpicno = "";
                    for (int i = 0; i < gridY; i++) {
                        for (int j = 0; j < gridX; j++) {
                            if (resitev[j][i].getBackground() == Color.BLACK) {
                                navpicno += " ";
                            } else {
                                navpicno += resitev[j][i].getText();
                            }
                        }
                        navpicno += " ";
                    }
                    String[] vodoravn = vodoravno.split(" ");
                    String[] navpicn = navpicno.split(" ");
                    Collections.shuffle(Arrays.asList(navpicn));
                    Collections.shuffle(Arrays.asList(navpicn));
                    Collections.shuffle(Arrays.asList(vodoravn));
                    Collections.shuffle(Arrays.asList(vodoravn));

                    navpicno = String.join(" ", navpicn);
                    vodoravno = String.join(" ", vodoravn);

                    spodnjiPanel.remove(vodoravnaStevila);
                    spodnjiPanel.remove(navpicnaStevila);
                    vodoravnaStevila = new JLabel("Horizontal: " + vodoravno);
                    navpicnaStevila = new JLabel("Vertical: " + navpicno);
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            gumbi[i][j].addActionListener(this);
                            panel1.add(gumbi[i][j]);
                            panel2.add(resitev[i][j]);
                        }
                    }
                    spodnjiPanel.add(vodoravnaStevila, BorderLayout.NORTH);
                    spodnjiPanel.add(navpicnaStevila, BorderLayout.SOUTH);
                    frame.add(spodnjiPanel, BorderLayout.SOUTH);
                    frame.add(sidePanel, BorderLayout.EAST);
                    frame.add(bar1, BorderLayout.NORTH);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }


                //Checker če je igralec zmagal
                if (e.getSource() == dokoncaj) {
                    boolean reseno = true;
                    for (int i = 0; i < gridX; i++) {
                        for (int j = 0; j < gridY; j++) {
                            String gumbek = gumbi[i][j].getText();
                            String resitva = resitev[i][j].getText();
                            if (!gumbek.equals(resitva)) {
                                reseno = false;
                            }
                        }
                    }
                    if (reseno) {
                        frame.remove(panel1);
                        frame.remove(bar1);
                        frame.remove(spodnjiPanel);
                        frame.remove(sidePanel);
                        frame.add(panel3);
                        frame.setVisible(false);
                        frame.setVisible(true);
                    }
                }

                //Gumb, ki prikaže panel z rešitvijo
                if (e.getSource() == resiMe) {
                    frame.remove(panel1);
                    frame.remove(sidePanel);
                    frame.add(panel2);
                    frame.add(resitevSidePanel, BorderLayout.EAST);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                if (e.getSource() == vracaj) {
                    frame.remove(panel2);
                    frame.remove(endlessPanel);
                    frame.remove(resitevSidePanel);
                    frame.add(panel1);
                    frame.add(sidePanel, BorderLayout.EAST);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }

                //Gumb in MenuItem, ki zaženeta normalno igro
                if (e.getSource() == start || e.getSource() == mstart) {
                    frame.remove(panel);
                    frame.add(panel1, BorderLayout.CENTER);
                    frame.add(bar1, BorderLayout.NORTH);
                    frame.add(spodnjiPanel, BorderLayout.SOUTH);
                    frame.add(sidePanel, BorderLayout.EAST);
                    frame.setResizable(true);
                    frame.setVisible(false);
                    frame.setVisible(true);
                }


            //Gumb in MenuItem, ki zaženeta endless varianto igre
            if (e.getSource()==endless || e.getSource()==mendless){
                frame.remove(panel);
                for (int i=0;i<gridX;i++){
                    for (int j=0;j<gridY;j++){
                        panel1.remove(gumbi[i][j]);
                        panel2.remove(resitev[i][j]);
                    }
                }
                gridX=5;
                gridY=5;
                gridSize=gridX*gridY;
                panel1.setLayout(new GridLayout(gridX,gridY));
                panel2.setLayout(new GridLayout(gridX,gridY));
                blackBox=5;
                stGumbov=gridSize-blackBox;
                temporary=new JButton[gridSize];


                for (int i=0;i<gridSize;i++){
                    int pravilno = r.nextInt(10);
                    temporary[i]=new JButton(""+pravilno);
                    temporary[i].setBackground(Color.WHITE);
                    temporary[i].setForeground(Color.BLACK);
                    temporary[i].setFocusable(false);
                    temporary[i].setEnabled(false);
                }
                for (int i = gridSize - blackBox; i < gridSize; i++) {
                    temporary[i].setBackground(Color.BLACK);
                    temporary[i].setText("");
                    temporary[i].setFocusable(false);
                    temporary[i].setEnabled(false);
                }
                Collections.shuffle(Arrays.asList(temporary));
                Collections.shuffle(Arrays.asList(temporary));
                Collections.shuffle(Arrays.asList(temporary));

                resitev = new JButton[gridX][gridY];
                gumbi = new JButton[gridX][gridY];
                int stIter = 0;
                for (int i = 0; i < gridX; i++) {
                    for (int j = 0; j < gridY; j++) {
                        resitev[i][j] = new JButton();
                        resitev[i][j] = temporary[stIter++];
                    }
                }

                for (int i = 0; i < gridX; i++) {
                    for (int j = 0; j < gridY; j++) {
                        gumbi[i][j] = new JButton("");
                        if (resitev[i][j].getBackground() == Color.white) {
                            gumbi[i][j].setText("0");
                            gumbi[i][j].setBackground(Color.WHITE);
                            gumbi[i][j].setFocusable(false);
                            gumbi[i][j].setEnabled(true);
                        } else {
                            gumbi[i][j].setBackground(Color.BLACK);
                            gumbi[i][j].setFocusable(false);
                            gumbi[i][j].setEnabled(false);
                        }
                    }
                }
                int ststr = 0;
                boolean boo = true;
                if (resitev[0][0].getBackground()==Color.BLACK){
                    boo=false;
                }


                for (int i = 0; i < gridX; i++) {
                    for (int j=0; j < gridY; j++){
                        if (boo && resitev[i][j].getBackground()==Color.WHITE) {
                            ststr++;
                            boo=false;
                        }else if(resitev[i][j].getBackground()==Color.BLACK){
                            boo=true;
                        }
                    }
                    if (i<gridX-1){
                        if (resitev[i+1][0].getBackground()!=Color.BLACK && resitev[i][gridY-1].getBackground()!=Color.BLACK) {
                            ststr++;
                        }
                    }
                }

                int ststrr=0;
                boo = true;
                if (resitev[0][0].getBackground()==Color.BLACK){
                    boo=false;
                }

                for (int i = 0; i < gridY; i++) {
                    for (int j=0; j < gridX; j++){
                        if (boo && resitev[j][i].getBackground()==Color.WHITE) {
                            ststrr++;
                            boo=false;
                        }else if(resitev[j][i].getBackground()==Color.BLACK){
                            boo=true;
                        }
                    }
                    if (i<gridY-1){
                        if (resitev[0][i+1].getBackground()!=Color.BLACK && resitev[gridX-1][i].getBackground()!=Color.BLACK) {
                            ststrr++;
                        }
                    }
                }

                String vodoravno = "";

                for (int i = 0;i<gridX;i++) {
                    for (int j = 0; j < gridY; j++) {
                        if (resitev[i][j].getBackground()==Color.BLACK){
                            vodoravno+=" ";
                        }else {
                            vodoravno += resitev[i][j].getText();
                        }
                    }
                    vodoravno+=" ";
                }

                String navpicno="";
                for (int i = 0;i<gridY;i++) {
                    for (int j = 0; j < gridX; j++) {
                        if (resitev[j][i].getBackground()==Color.BLACK){
                            navpicno+=" ";
                        }else {
                            navpicno += resitev[j][i].getText();
                        }
                    }
                    navpicno+=" ";
                }
                String[] vodoravn = vodoravno.split(" ");
                String[] navpicn = navpicno.split(" ");
                Collections.shuffle(Arrays.asList(navpicn));
                Collections.shuffle(Arrays.asList(navpicn));
                Collections.shuffle(Arrays.asList(vodoravn));
                Collections.shuffle(Arrays.asList(vodoravn));

                navpicno = String.join(" ", navpicn);
                vodoravno = String.join(" ", vodoravn);

                spodnjiPanel.remove(vodoravnaStevila);
                spodnjiPanel.remove(navpicnaStevila);
                vodoravnaStevila = new JLabel("Horizontal: "+vodoravno);
                navpicnaStevila = new JLabel("Vertical: "+navpicno);
                for (int i=0;i<gridX;i++){
                    for (int j = 0; j<gridY;j++){
                        gumbi[i][j].addActionListener(this);
                        panel1.add(gumbi[i][j]);
                        panel2.add(resitev[i][j]);
                    }
                }
                spodnjiPanel.add(vodoravnaStevila,BorderLayout.NORTH);
                spodnjiPanel.add(navpicnaStevila,BorderLayout.SOUTH);
                frame.add(endlessPanel,BorderLayout.EAST);
                frame.add(panel1,BorderLayout.CENTER);
                frame.add(bar1,BorderLayout.NORTH);
                frame.add(spodnjiPanel,BorderLayout.SOUTH);
                frame.setResizable(true);
                frame.setVisible(false);
                frame.setVisible(true);
            }

            //CHECK ČE JE IGRALEC PRAVILNO ZAKLJUČIL ENDLESS MODE
                for (int z=0;z<gridX;z++){
                    for (int k=0;k<gridY;k++){
                        if (gumbi[z][k].getText().equals(resitev[z][k].getText())){
                            opravljen=true;
                        }else{
                            opravljen=false;
                            break;
                        }
                    }
                }
                if (e.getSource()==check&&opravljen){
                JButton[][] tempB=new JButton[gridX][gridY];
                JButton[][] tempA=new JButton[gridX][gridY];
                for (int i=0;i<gridX;i++){
                    for (int j=0;j<gridY;j++){
                        tempB[i][j]=resitev[i][j];
                        tempA[i][j]=gumbi[i][j];
                    }
                }
                for (int i=0;i<gridX;i++){
                    for (int j=0;j<gridY;j++){
                        panel1.remove(gumbi[i][j]);
                        panel2.remove(resitev[i][j]);
                    }
                }
                gridX++;
                gridY++;
                int prejsni=gridSize;
                gridSize=gridX*gridY;
                panel1.setLayout(new GridLayout(gridX,gridY));
                panel2.setLayout(new GridLayout(gridX,gridY));
                temporary=new JButton[gridSize-prejsni+1];
                resitev=new JButton[gridX][gridY];
                gumbi=new JButton[gridX][gridY];
                if (gridSize<50){
                    blackBox=2;
                }else if (gridSize<101){
                    blackBox=3;
                }

                for (int i=0;i<temporary.length;i++){
                    int pravilno = r.nextInt(10);
                    temporary[i]=new JButton(""+pravilno);
                    temporary[i].setBackground(Color.WHITE);
                    temporary[i].setForeground(Color.BLACK);
                    temporary[i].setFocusable(false);
                    temporary[i].setEnabled(false);
                }
                for (int i=temporary.length-1;i>temporary.length-blackBox;i--){
                    temporary[i].setBackground(Color.BLACK);
                    temporary[i].setText("");
                    temporary[i].setFocusable(false);
                    temporary[i].setEnabled(false);
                }
                Collections.shuffle(Arrays.asList(temporary));
                Collections.shuffle(Arrays.asList(temporary));
                for (int i=0;i<gridX-1;i++){
                    for (int j=0;j<gridY-1;j++){
                        resitev[i][j]=tempB[i][j];
                        gumbi[i][j]=tempA[i][j];
                    }
                }
                int stIter=0;
                for (int i = 0; i < gridX; i++) {
                        resitev[i][gridY-1] = new JButton();
                        resitev[i][gridY-1] = temporary[stIter++];
                        resitev[i][gridY-1].setFocusable(false);
                }
                for (int j=0;j<gridY;j++){
                    resitev[gridX-1][j] = new JButton();
                    resitev[gridX-1][j]=temporary[stIter++];
                    resitev[gridX-1][j].setFocusable(false);
                }

                for (int i = 0; i < gridX; i++) {
                    gumbi[i][gridY-1]=new JButton();
                    if (resitev[i][gridY-1].getBackground()==Color.WHITE) {
                        gumbi[i][gridY-1].setBackground(Color.WHITE);
                        gumbi[i][gridY-1].setText("0");
                        gumbi[i][gridY-1].setFocusable(false);
                    }else if (resitev[i][gridY-1].getBackground()==Color.BLACK){
                        gumbi[i][gridY-1].setBackground(Color.BLACK);
                        gumbi[i][gridY-1].setEnabled(false);
                        gumbi[i][gridY-1].setFocusable(false);
                    }
                }
                for (int j = 0; j < gridY; j++) {
                    gumbi[gridX-1][j]=new JButton();
                    if (resitev[gridX-1][j].getBackground()==Color.WHITE){
                        gumbi[gridX-1][j].setBackground(Color.WHITE);
                        gumbi[gridX-1][j].setText("0");
                        gumbi[gridX-1][j].setFocusable(false);
                    }else if (resitev[gridX-1][j].getBackground()==Color.BLACK){
                        gumbi[gridX-1][j].setBackground(Color.BLACK);
                        gumbi[gridX-1][j].setEnabled(false);
                        gumbi[gridX-1][j].setFocusable(false);
                    }
                }

                int ststr = 0;
                boolean boo = true;
                if (resitev[0][0].getBackground()==Color.BLACK){
                    boo=false;
                }


                for (int i = 0; i < gridX; i++) {
                    for (int j=0; j < gridY; j++){
                        if (boo && resitev[i][j].getBackground()==Color.WHITE) {
                            ststr++;
                            boo=false;
                        }else if(resitev[i][j].getBackground()==Color.BLACK){
                            boo=true;
                        }
                    }
                    if (i<gridX-1){
                        if (resitev[i+1][0].getBackground()!=Color.BLACK && resitev[i][gridY-1].getBackground()!=Color.BLACK) {
                            ststr++;
                        }
                    }
                }

                int ststrr=0;
                boo = true;
                if (resitev[0][0].getBackground()==Color.BLACK){
                    boo=false;
                }

                for (int i = 0; i < gridY; i++) {
                    for (int j=0; j < gridX; j++){
                        if (boo && resitev[j][i].getBackground()==Color.WHITE) {
                            ststrr++;
                            boo=false;
                        }else if(resitev[j][i].getBackground()==Color.BLACK){
                            boo=true;
                        }
                    }
                    if (i<gridY-1){
                        if (resitev[0][i+1].getBackground()!=Color.BLACK && resitev[gridX-1][i].getBackground()!=Color.BLACK) {
                            ststrr++;
                        }
                    }
                }

                String vodoravno = "";

                for (int i = 0;i<gridX;i++) {
                    for (int j = 0; j < gridY; j++) {
                        if (resitev[i][j].getBackground()==Color.BLACK){
                            vodoravno+=" ";
                        }else {
                            vodoravno += resitev[i][j].getText();
                        }
                    }
                    vodoravno+=" ";
                }

                String navpicno="";
                for (int i = 0;i<gridY;i++) {
                    for (int j = 0; j < gridX; j++) {
                        if (resitev[j][i].getBackground()==Color.BLACK){
                            navpicno+=" ";
                        }else {
                            navpicno += resitev[j][i].getText();
                        }
                    }
                    navpicno+=" ";
                }
                String[] vodoravn = vodoravno.split(" ");
                String[] navpicn = navpicno.split(" ");
                Collections.shuffle(Arrays.asList(navpicn));
                Collections.shuffle(Arrays.asList(navpicn));
                Collections.shuffle(Arrays.asList(vodoravn));
                Collections.shuffle(Arrays.asList(vodoravn));

                navpicno = String.join(" ", navpicn);
                vodoravno = String.join(" ", vodoravn);

                spodnjiPanel.remove(vodoravnaStevila);
                spodnjiPanel.remove(navpicnaStevila);
                vodoravnaStevila = new JLabel("Horizontal: "+vodoravno);
                navpicnaStevila = new JLabel("Vertical: "+navpicno);
                for (int i=0;i<gridX;i++){
                    for (int j = 0; j<gridY;j++){
                        gumbi[i][j].addActionListener(this);
                        panel1.add(gumbi[i][j]);
                        panel2.add(resitev[i][j]);
                    }
                }
                spodnjiPanel.add(vodoravnaStevila,BorderLayout.NORTH);
                spodnjiPanel.add(navpicnaStevila,BorderLayout.SOUTH);
                frame.add(spodnjiPanel,BorderLayout.SOUTH);
                frame.add(endlessPanel,BorderLayout.EAST);
                frame.add(bar1,BorderLayout.NORTH);
                frame.setVisible(false);
                frame.setVisible(true);
            }

            //Gumb, ki preveri če je zadnji vnos pravilen
            if (e.getSource()==test1){
                if (gumbi[pozicijaX][pozicijaY].getText().equals(resitev[pozicijaX][pozicijaY].getText())){
                    gumbi[pozicijaX][pozicijaY].setBackground(Color.GREEN);
                }
            }

            //Gumb v endless mode, ki pokaže rešitev
            if (e.getSource()==resiMe1){
                frame.remove(panel1);
                frame.add(panel2);
                frame.remove(endlessPanel);
                frame.add(resitevEndlessPanel,BorderLayout.EAST);
                frame.setVisible(false);
                frame.setVisible(true);
            }
            //Gumb v endless mode, ko pokaže rešitev je konec
            if (e.getSource()==end){
                frame.remove(resitevEndlessPanel);
                frame.remove(spodnjiPanel);
                frame.remove(panel2);
                frame.remove(bar1);
                frame.add(panel);
                frame.setSize(new Dimension(1000,800));
                frame.setResizable(false);
                frame.setVisible(false);
                frame.setVisible(true);
            }

            //Gumb in MenuItem, ki igralca najprej opozori da bo zaprl igro.
            if (e.getSource()==mexit||e.getSource()==mexit1) {
                if (JOptionPane.showOptionDialog(frame, "Are you sure you want to quit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, 0) == 0) {
                    frame.dispose();
                }
            }
            }
        }
    }