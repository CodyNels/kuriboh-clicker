import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.Image;
import java.awt.event.MouseEvent;


public class KuribohClickerPanel extends JPanel{
    private final int DELAY = 5000;
    BufferedImage kuriboh;
    int pointsPerClick = 1;
    double score = 0;

    int pogCost = 50;
    JButton pog = new JButton("Pot of Greed: " + pogCost);

    int sSCost = 25;
    JButton sinSerp = new JButton("Sinister Serpent: " + sSCost);
    double sinAddAuto = 0;

    int sanganCost = 150;
    JButton sangan = new JButton("Sangan: " + sanganCost);
    int sanAddAuto = 0;

    int goldCost = 200;
    JButton goldSarc = new JButton("Gold Sarcophagus: " + goldCost);
    int goldBoost = 800;

    // int goatCost = 50;
    // JButton scapeGoat = new JButton("Scapegoat: " + goatCost);

    // int multCost = 50;
    // JButton multiply = new JButton("Multiply: " + multCost);

    int costDownCost = 500;
    JButton costDown = new JButton("Cost Down: " + costDownCost);

    boolean autoRunning = false;
    public static final DecimalFormat df1 = new DecimalFormat( "#.#" );
    boolean sphereUnlocked = false;
    boolean junkUnlocked = false;
    boolean astralUnlocked = false;
    boolean pendUnlocked = false;
    boolean linkUnlocked = false;
    boolean wingUnlocked = false;
    boolean wing6Unlocked = false;
    boolean wing9Unlocked = false;
    boolean wing10Unlocked = false;

    JButton sphereKuriboh = new JButton("Sphere Kuriboh");
    JButton junkKuriboh = new JButton("Junkuriboh");
    JButton astralKuriboh = new JButton("Astrak Kuriboh");
    JButton pendKuriboh = new JButton("Kuribohble");
    JButton linkKuriboh = new JButton("Linkuriboh");
    JButton wingedKuriboh = new JButton("Winged Kuriboh");
    JButton wing6Kuriboh = new JButton("Winged Kuriboh LV6");
    JButton wing9Kuriboh = new JButton("Winged Kuriboh LV9");
    JButton wing10Kuriboh = new JButton("Winged Kuriboh LV10");
    JButton dMKuriboh = new JButton("Kuriboh");
    private JPanel descriptionPanel;
    private JLabel descriptionText;
    JLabel scoreLabel = new JLabel("Kuribohs: " + df1.format(score));
    JLabel targetClick;
    KuribohLevel current = KuribohLevel.DM;


    /**
     * 
     * @param width
     * @param height
     */
    public KuribohClickerPanel(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout(2,2));
        Border blackline = BorderFactory.createLineBorder(Color.black, 2);
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(blackline);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));


        GridBagConstraints gbc0 = new GridBagConstraints();
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setPreferredSize(new Dimension((int)(width * (2.0/3.0)), height/10));
        titlePanel.setBorder(blackline);
        JLabel title = new JLabel("Kuriboh Clicker!");
        title.setFont(new Font("Monospaced", Font.PLAIN, 30));
        gbc0.gridx = 1;
        gbc0.gridy = 0;
        titlePanel.add(title, gbc0);
        scoreLabel.setText("Kuribohs: " + df1.format(score));
        gbc0.gridx = 1;
        gbc0.gridy = 1;
        titlePanel.add(scoreLabel, gbc0);

        JLayeredPane clickPanel = new JLayeredPane();
        clickPanel.setPreferredSize(new Dimension((int)(width * (2.0/3.0)), (int) (height / (9.0/10))));
        clickPanel.setLayout(null);
        clickPanel.setBorder(blackline);
        try {
            File kuribohFile = new File("Images/DM-Kuriboh.png");
            BufferedImage dmKuriboh = ImageIO.read(kuribohFile);
            targetClick = new JLabel(new ImageIcon(dmKuriboh));
            targetClick.setSize(500, 500);
            targetClick.addMouseListener(new KuribohListener());
            clickPanel.add(targetClick, JLayeredPane.DEFAULT_LAYER);

            clickPanel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int x = (clickPanel.getWidth() - targetClick.getWidth()) / 2;
                    int y = (clickPanel.getHeight() - targetClick.getHeight()) / 2;
                    targetClick.setLocation(x, y);
                }
            });
        } catch (IOException ex) {
            System.out.println("Kuriboh-DM Didn't work");
        }
        
        contentPanel.add(titlePanel);
        contentPanel.add(clickPanel);


        JPanel shopPanel = new JPanel();
        shopPanel.setBorder(blackline);
        shopPanel.setPreferredSize(new Dimension(width/3, height));


        GridBagConstraints gbc = new GridBagConstraints();
        JPanel production = new JPanel(new GridBagLayout());
        production.setBorder(blackline);

        

        pog.setText("Pot of Greed: " + pogCost);
        try{
        BufferedImage pogImg = ImageIO.read(new File("Images/Pog.png"));
        Image scaledPog = pogImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel pogImage = new JLabel(new ImageIcon(scaledPog));
        gbc.gridx = 0;
        gbc.gridy = 0;
        pogImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Pot of Greed: Increase the amount of kuribohs generated per click. <br> Current value per click: " + pointsPerClick + "<br> Next Upgrade: " + (pointsPerClick + 1));
            }
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        production.add(pogImage, gbc);
        }catch (IOException ex){
            System.out.println("POG Didn't work");
        }
        pog.addActionListener(new PogListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        production.add(pog, gbc);


        sinSerp.setText("Sinister Serpent: " + sSCost);
        try{
        BufferedImage sSImg = ImageIO.read(new File("Images/Sinister-Serpent.png"));            
        Image scaledSS = sSImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel sSImage = new JLabel(new ImageIcon(scaledSS));
        gbc.gridx = 0;
        gbc.gridy = 1;
        sSImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Adds " + sinAddAuto + " Kuribohs every 5 seconds. <br> Next Upgrade: " + (sinAddAuto + 0.5));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        production.add(sSImage, gbc);
        }catch (IOException ex){
            System.out.println("sinister serpent Didn't work");
        }
        sinSerp.addActionListener(new SinListener());
        gbc.gridx = 1;
        gbc.gridy = 1;
        production.add(sinSerp, gbc);

        sangan.setText("Sangan: " + sanganCost);
        try{
        BufferedImage sanImg = ImageIO.read(new File("Images/Sangan.png"));
        Image scaledSan = sanImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel sanImage = new JLabel(new ImageIcon(scaledSan));
        gbc.gridx = 0;
        gbc.gridy = 2;
        sanImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Generates " + sanAddAuto + " every 5 seconds. <br> Next Upgrade: " + (sanAddAuto + 20));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        production.add(sanImage, gbc);
        }catch (IOException ex){
            System.out.println("Sangan Didn't work");
        }
        sangan.addActionListener(new SanListener());
        gbc.gridx = 1;
        gbc.gridy = 2;
        production.add(sangan, gbc);

        goldSarc.setText("Gold Sarcophagus: " + goldCost);
        try{
        BufferedImage sanImg = ImageIO.read(new File("Images/Gold-sarc.png"));
        Image scaledSan = sanImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel sanImage = new JLabel(new ImageIcon(scaledSan));
        gbc.gridx = 0;
        gbc.gridy = 3;
        sanImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Adds " + goldBoost + " 15 seconds after purchase once. <br> Next Upgrade: " + (goldBoost * 4));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        production.add(sanImage, gbc);
        }catch (IOException ex){
            System.out.println("Gold Sarc Didn't work");
        }
        goldSarc.addActionListener(new GoldListener());
        gbc.gridx = 1;
        gbc.gridy = 3;
        production.add(goldSarc, gbc);



        costDown.setText("Cost Down: " + costDownCost);
        try{
        BufferedImage sanImg = ImageIO.read(new File("Images/Cost-down.png"));
        Image scaledSan = sanImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel sanImage = new JLabel(new ImageIcon(scaledSan));
        gbc.gridx = 0;
        gbc.gridy = 4;
        sanImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Adds " + goldBoost + " 15 seconds after purchase once. <br> Next Upgrade: " + (goldBoost * 4));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        production.add(sanImage, gbc);
        }catch (IOException ex){
            System.out.println("Cost down Didn't work");
        }
        costDown.addActionListener(new CostDownListener());
        gbc.gridx = 1;
        gbc.gridy = 4;
        production.add(costDown, gbc);



        pog.setText("Pot of Greed: " + pogCost);
        try{
        BufferedImage pogImg = ImageIO.read(new File("Images/Pog.png"));
        Image scaledPog = pogImg.getScaledInstance(width/20,width/20, Image.SCALE_SMOOTH);
        JLabel pogImage = new JLabel(new ImageIcon(scaledPog));
        gbc.gridx = 0;
        gbc.gridy = 0;
        pogImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showDescription("Pot of Greed: Increase the amount of kuribohs generated per click. <br> Current value per click: " + pointsPerClick + "<br> Next Upgrade: " + (pointsPerClick + 1));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        production.add(pogImage, gbc);
        }catch (IOException ex){
            System.out.println("POG Didn't work");
        }
        pog.addActionListener(new PogListener());
        gbc.gridx = 1;
        gbc.gridy = 0;
        production.add(pog, gbc);


        descriptionPanel = new JPanel();
        descriptionPanel.setPreferredSize(new Dimension(width/3 - 10, height/10));
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBackground(new Color(255, 255, 200));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descriptionText = new JLabel("Upgrade Info Will Appear Here");
        descriptionText.setPreferredSize(descriptionPanel.getPreferredSize());
        descriptionPanel.add(descriptionText, BorderLayout.CENTER);


        
        GridBagConstraints gbc2 = new GridBagConstraints();
        JPanel cosmestics = new JPanel(new GridBagLayout());
        cosmestics.setBorder(blackline);
        try{
            BufferedImage dMImg = ImageIO.read(new File("Images/DM-Kuriboh.png"));
            Image scaledDM = dMImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel dMImage = new JLabel(new ImageIcon(scaledDM));
            gbc2.gridx = 0;
            gbc2.gridy = 0;
            cosmestics.add(dMImage, gbc2);
            }catch (IOException ex){
                System.out.println("Wing Didn't work");
            }
        dMKuriboh.addActionListener(new DMListener());
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        cosmestics.add(dMKuriboh, gbc2);


        try{
            BufferedImage sphereImg = ImageIO.read(new File("Images/sphere.png"));
            Image scaledSphere = sphereImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel sphereImage = new JLabel(new ImageIcon(scaledSphere));
            gbc2.gridx = 0;
            gbc2.gridy = 1;
            cosmestics.add(sphereImage, gbc2);
            }catch (IOException ex){
                System.out.println("Sphere no work");
            }
        sphereKuriboh = new JButton("Sphere Kuriboh: " + 50);
        sphereKuriboh.addActionListener(new SphereListener());
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        cosmestics.add(sphereKuriboh, gbc2);


        try{
            BufferedImage junkImg = ImageIO.read(new File("Images/junk.png"));
            Image scaledJunk = junkImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel junkImage = new JLabel(new ImageIcon(scaledJunk));
            gbc2.gridx = 0;
            gbc2.gridy = 2;
            cosmestics.add(junkImage, gbc2);
            }catch (IOException ex){
                System.out.println("Junk no work");
            }
        junkKuriboh = new JButton("Junkuriboh: " + 140);
        junkKuriboh.addActionListener(new JunkListener());
        gbc2.gridx = 1;
        gbc2.gridy = 2;
        cosmestics.add(junkKuriboh, gbc2);


        try{
            BufferedImage astralImg = ImageIO.read(new File("Images/Astral-Kuriboh.png"));
            Image scaledAstral = astralImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel astralImage = new JLabel(new ImageIcon(scaledAstral));
            gbc2.gridx = 0;
            gbc2.gridy = 3;
            cosmestics.add(astralImage, gbc2);
            }catch (IOException ex){
                System.out.println("Astral no work");
            }
        astralKuriboh = new JButton("Astral Kuriboh: " + 400);
        astralKuriboh.addActionListener(new AstralListener());
        gbc2.gridx = 1;
        gbc2.gridy = 3;
        cosmestics.add(astralKuriboh, gbc2);


        try{
            BufferedImage pendImg = ImageIO.read(new File("Images/pend.png"));
            Image scaledPend = pendImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel pendImage = new JLabel(new ImageIcon(scaledPend));
            gbc2.gridx = 0;
            gbc2.gridy = 4;
            cosmestics.add(pendImage, gbc2);
            }catch (IOException ex){
                System.out.println("Pend no work");
            }
        pendKuriboh = new JButton("Kuribohble: " + 1000);
        pendKuriboh.addActionListener(new PendListener());
        gbc2.gridx = 1;
        gbc2.gridy = 4;
        cosmestics.add(pendKuriboh, gbc2);


        try{
            BufferedImage linkImg = ImageIO.read(new File("Images/link.png"));
            Image scaledLink = linkImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel linkImage = new JLabel(new ImageIcon(scaledLink));
            gbc2.gridx = 0;
            gbc2.gridy = 5;
            cosmestics.add(linkImage, gbc2);
            }catch (IOException ex){
                System.out.println("Link no work");
            }
        linkKuriboh = new JButton("Linkuriboh: " + 3000);
        linkKuriboh.addActionListener(new LinkListener());
        gbc2.gridx = 1;
        gbc2.gridy = 5;
        cosmestics.add(linkKuriboh, gbc2);



        try{
            BufferedImage wingImg = ImageIO.read(new File("Images/Wing-Kuriboh.png"));
            Image scaledWing = wingImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel wingImage = new JLabel(new ImageIcon(scaledWing));
            gbc2.gridx = 0;
            gbc2.gridy = 7;
            cosmestics.add(wingImage, gbc2);
            }catch (IOException ex){
                System.out.println("Wing Didn't work");
            }
        wingedKuriboh = new JButton("Winged Kuriboh: " + 8500);
        wingedKuriboh.addActionListener(new WingListener());
        gbc2.gridx = 1;
        gbc2.gridy = 7;
        cosmestics.add(wingedKuriboh, gbc2);


        try{
            BufferedImage wingImg = ImageIO.read(new File("Images/Lv6.png"));
            Image scaledWing = wingImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel wingImage = new JLabel(new ImageIcon(scaledWing));
            gbc2.gridx = 0;
            gbc2.gridy = 8;
            cosmestics.add(wingImage, gbc2);
            }catch (IOException ex){
                System.out.println("Wing Didn't work");
            }
        wing6Kuriboh = new JButton("Winged Kuriboh LV 6: " + 24000);
        wing6Kuriboh.addActionListener(new Wing6Listener());
        gbc2.gridx = 1;
        gbc2.gridy = 8;
        cosmestics.add(wing6Kuriboh, gbc2);


        try{
            BufferedImage wingImg = ImageIO.read(new File("Images/Lv9.png"));
            Image scaledWing = wingImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel wingImage = new JLabel(new ImageIcon(scaledWing));
            gbc2.gridx = 0;
            gbc2.gridy = 9;
            cosmestics.add(wingImage, gbc2);
            }catch (IOException ex){
                System.out.println("Wing Didn't work");
            }
        wing9Kuriboh = new JButton("Winged Kuriboh LV 9: " + 170000);
        wing9Kuriboh.addActionListener(new Wing9Listener());
        gbc2.gridx = 1;
        gbc2.gridy = 9;
        cosmestics.add(wing9Kuriboh, gbc2);


        try{
            BufferedImage wingImg = ImageIO.read(new File("Images/Lv10.png"));
            Image scaledWing = wingImg.getScaledInstance(width/18,width/18, Image.SCALE_SMOOTH);
            JLabel wingImage = new JLabel(new ImageIcon(scaledWing));
            gbc2.gridx = 0;
            gbc2.gridy = 10;
            cosmestics.add(wingImage, gbc2);
            }catch (IOException ex){
                System.out.println("Wing Didn't work");
            }
        wing10Kuriboh = new JButton("Winged Kuriboh LV 10: " + 1300000);
        wing10Kuriboh.addActionListener(new Wing10Listener());
        gbc2.gridx = 1;
        gbc2.gridy = 10;
        cosmestics.add(wing10Kuriboh, gbc2);


        shopPanel.add(production);
        shopPanel.add(cosmestics);
        shopPanel.add(descriptionPanel);

        this.add(contentPanel, BorderLayout.CENTER);
        this.add(shopPanel, BorderLayout.EAST);
    }

    private class PogListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(score >= pogCost){
                score -= pogCost;
                pogCost += Math.max(pogCost/2, 75);
                pointsPerClick += 1;
                scoreLabel.setText("Kuribohs: " + df1.format(score));
            }
            pog.setText("Pot of Greed: " + pogCost);
        }
    }


    private class SinListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(score >= sSCost){
                sinAddAuto += 0.5;
                score -= sSCost;
                sSCost += Math.max(sSCost/2.5, 35);
                if(autoRunning == false){
                    startAutoClicking();
                    autoRunning = true;
                }
                scoreLabel.setText("Kuribohs: " + df1.format(score));
            }
            sinSerp.setText("Sinister Serpent: " + sSCost);
        }
    }

    private class SanListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(score >= sanganCost){
                sanAddAuto += 20;
                score -= sanganCost;
                sanganCost += Math.max(sanganCost/1.7, 255);
                if(autoRunning == false){
                    startAutoClicking();
                    autoRunning = true;
                }
                scoreLabel.setText("Kuribohs: " + df1.format(score));
            }
            sangan.setText("Sangan: " + sanganCost);
        }
    }

    private class GoldListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(score >= goldCost){
                score -= goldCost;
                scoreLabel.setText("Kuribohs: " + df1.format(score));
                goldCost += Math.max((goldCost/3.5), 300);
                goldSarc.setText("Gold Sarcophagus: " + goldCost);
                ActionListener timerListener = new TimerGold();
                Timer timer = new Timer(DELAY * 3, timerListener);
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    private class CostDownListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(score >= costDownCost){
                score -= costDownCost;
                scoreLabel.setText("Kuribohs: " + df1.format(score));
                costDownCost += Math.max((costDownCost/3), 500);
                costDown.setText("Cost Down: " + costDownCost);
                pogCost -= Math.min(pogCost/2, 75);
                sSCost -= Math.min(sSCost/2.5, 35);
                sanganCost -= Math.min(sanganCost/1.7, 255);
                goldCost -= Math.min((goldCost/1.5), 750);
                pog.setText("Pot of Greed: " + pogCost);
                sinSerp.setText("Sinister Serpent: " + sSCost);
                sangan.setText("Sangan: " + sanganCost);
                goldSarc.setText("Gold Sarcophagus: " + goldCost);
            }
        }
    }



    private class KuribohListener implements MouseListener{

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if(e.getButton() == MouseEvent.BUTTON1){
                addPoints(pointsPerClick);
                int x = (int) (Math.random() * (targetClick.getX() + 500)); // Ensure within bounds
                int y = (int) (Math.random() * (targetClick.getY() + 500));
                spawnKuribohEffect((JLayeredPane)targetClick.getParent(), x, y);
            }else if(e.getButton() == MouseEvent.BUTTON3){
                score += 1000000;
                scoreLabel.setText("Kuribohs: " + df1.format(score));
            }
        }
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {}
        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {}
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {}
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {}
    }


    private class DMListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(current != KuribohLevel.DM){
                try {
                    BufferedImage dMImg = ImageIO.read(new File("Images/DM-Kuriboh.png"));
                    Image scaledDM = dMImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledDM);
                    current = KuribohLevel.DM;
                }catch (IOException ex) {
                    System.out.println("DM-Kuriboh Didn't work");
                }
            }
        }
    }


    private class SphereListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(sphereUnlocked == false && score >= 50){
                score -= 50;
                sphereKuriboh.setText("Sphere Kuriboh");
                sphereUnlocked = true;
            }
            if(current != KuribohLevel.SPHERE && sphereUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/sphere.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.SPHERE;
                }catch (IOException ex) {
                    System.out.println("Sphere Didn't work");
                }
            }
        }
    }


    private class JunkListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(junkUnlocked == false && score >= 140 && sphereUnlocked == true){
                score -= 140;
                junkKuriboh.setText("Junkuriboh");
                junkUnlocked = true;
            }
            if(current != KuribohLevel.JUNK && junkUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/junk.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.JUNK;
                }catch (IOException ex) {
                    System.out.println("Junk Didn't work");
                }
            }
        }
    }


    private class AstralListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(astralUnlocked == false && score >= 400 && junkUnlocked){
                score -= 400;
                astralKuriboh.setText("Astral Kuriboh");
                astralUnlocked = true;
            }
            if(current != KuribohLevel.ASTRAL && astralUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/Astral-Kuriboh.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.ASTRAL;
                }catch (IOException ex) {
                    System.out.println("Astral Didn't work");
                }
            }
        }
    }


    private class PendListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(pendUnlocked == false && score >= 1000 && astralUnlocked){
                score -= 1000;
                pendKuriboh.setText("Kuribohble");
                pendUnlocked = true;
            }
            if(current != KuribohLevel.PEND && pendUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/pend.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.PEND;
                }catch (IOException ex) {
                    System.out.println("Pend Didn't work");
                }
            }
        }
    }


    private class LinkListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(linkUnlocked == false && score >= 3000 && pendUnlocked){
                score -= 3000;
                linkKuriboh.setText("Linkuriboh");
                linkUnlocked = true;
            }
            if(current != KuribohLevel.LINK && linkUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/link.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.LINK;
                }catch (IOException ex) {
                    System.out.println("Link Didn't work");
                }
            }
        }
    }

    

    private class WingListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(wingUnlocked == false && score >= 8500 && linkUnlocked){
                score -= 8500;
                wingedKuriboh.setText("Winged Kuriboh");
                wingUnlocked = true;
            }
            if(current != KuribohLevel.WING && wingUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/Wing-Kuriboh.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.WING;
                }catch (IOException ex) {
                    System.out.println("Wing-Kuriboh Didn't work");
                }
            }
        }
    }


    private class Wing6Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(wing6Unlocked == false && score >= 24000 && wingUnlocked){
                score -= 24000;
                wing6Kuriboh.setText("Winged Kuriboh LV 6");
                wing6Unlocked = true;
                
            }
            if(current != KuribohLevel.WING6 && wingUnlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/Lv6.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.WING6;
                }catch (IOException ex) {
                    System.out.println("Wing-Kuriboh 6 Didn't work");
                }
            }
        }
    }


/** */
    private class Wing9Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(wing9Unlocked == false && score >= 170000 && wing6Unlocked){
                score -= 170000;
                wing9Kuriboh.setText("Winged Kuriboh LV 9");
                wing9Unlocked = true;
            }
            if(current != KuribohLevel.WING9 && wing9Unlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/Lv9.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.WING9;
                }catch (IOException ex) {
                    System.out.println("Wing-Kuriboh 9 Didn't work");
                }
            }
        }
    }

/** */
    private class Wing10Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(wingUnlocked == false && score >= 1300000 && wing9Unlocked){
                score -= 1300000;
                wingedKuriboh.setText("Winged Kuriboh LV 10");
                wing10Unlocked = true;
            }
            if(current != KuribohLevel.WING10 && wing9Unlocked){
                try {
                    BufferedImage wingKuribohImg = ImageIO.read(new File("Images/Lv10.png"));
                    Image scaledWingKuriboh = wingKuribohImg.getScaledInstance(targetClick.getWidth(), targetClick.getHeight(), Image.SCALE_SMOOTH);
                    ChangeKuriboh(scaledWingKuriboh);
                    current = KuribohLevel.WING10;
                }catch (IOException ex) {
                    System.out.println("Wing-Kuriboh 10 Didn't work");
                }
            }
        }
    }

    
    /**
     * 
     * @param kuriboh
     */
    private void ChangeKuriboh(Image kuriboh){
        targetClick.setIcon(new ImageIcon(kuriboh));
    }

    /**
     * 
     * @param text
     */
    private void showDescription(String text) {
        descriptionText.setText("<html>" + text + "</html>");
    }

    /**
     * 
     * @param addPoint
     */
    private void addPoints(int addPoint){
        score += addPoint;
        scoreLabel.setText("Kuribohs: " + df1.format(score));
    }

    /** */
    private void SanganAutoClicker(){
        double copy = score;
        score += sanAddAuto;
        if(copy != score){
            int x = (int) (Math.random() * (targetClick.getX() + 500)); // Ensure within bounds
            int y = (int) (Math.random() * (targetClick.getY() + 500));
            spawnSanganEffect((JLayeredPane)targetClick.getParent(), x, y);
        }
        scoreLabel.setText("Kuribohs: " + df1.format(score));
    }

    /** */
    private void SerpentAutoClicker(){
        double copy = score;
        score += sinAddAuto;
        if(copy != score){
            int x = (int) (Math.random() * (targetClick.getX() + 500)); // Ensure within bounds
            int y = (int) (Math.random() * (targetClick.getY() + 500));
            spawnSerpentEffect((JLayeredPane)targetClick.getParent(), x, y);
        }
        scoreLabel.setText("Kuribohs: " + df1.format(score));
    }

    /** */
    private void startAutoClicking(){
		ActionListener timerListener = new TimerListener();
		Timer timer = new Timer(DELAY, timerListener);
		timer.start();
	}

    /** */
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			SanganAutoClicker();
            SerpentAutoClicker();
		}
	}


    private class TimerGold implements ActionListener{
		public void actionPerformed(ActionEvent e) {
            score += (goldBoost);
            goldBoost = goldCost * 4;
            int x = (int) (Math.random() * (targetClick.getX() + 500)); // Ensure within bounds
            int y = (int) (Math.random() * (targetClick.getY() + 500));
            spawnGoldEffect((JLayeredPane)targetClick.getParent(), x, y);
            scoreLabel.setText("Kuribohs: " + df1.format(score));
		}
	}

    /**
     * 
     * @param image
     * @param opacity
     * @return
     */
    private Image createFadedImage(Image image, float opacity) {
        BufferedImage fadedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fadedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return fadedImage;
    }

    /**
     * 
     * @param panel
     * @param x
     * @param y
     */
    private void spawnKuribohEffect(JLayeredPane panel, int x, int y) {
        // Create a JLabel for the small Kuriboh
        JLabel kuribohLabel = new JLabel();
        switch(current){
            case DM:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/DM-Kuriboh.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case WING:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/Wing-Kuriboh.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case JUNK:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/junk.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case LINK:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/link.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case PEND:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/pend.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case SPHERE:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/sphere.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case WING10:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/Lv10.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case WING6:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/Lv6.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case WING9:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/Lv9.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            case ASTRAL:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/Astral-Kuriboh.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
                break;
            default:
                try {
                    BufferedImage dmKuriboh = ImageIO.read(new File("Images/DM-Kuriboh.png"));
                    Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
                } catch (IOException ex) {
                    System.out.println("Kuriboh image not found!");
                }
            
        }
        
    
        // Set the initial position for the small Kuriboh
        kuribohLabel.setBounds(x, y, 50, 50);
        panel.add(kuribohLabel, JLayeredPane.PALETTE_LAYER); // Add on a higher layer
        panel.repaint();
    
        // Animate the fade effect
        Timer fadeTimer = new Timer(20, null); // 50ms per frame
        final int steps = 20; // Total frames for fading
        final float[] opacity = {1.0f}; // Starting opacity
    
        fadeTimer.addActionListener(new ActionListener() {
            int currentStep = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reduce opacity
                opacity[0] = 1.0f - ((float) currentStep / steps);
                kuribohLabel.setIcon(new ImageIcon(createFadedImage(((ImageIcon) kuribohLabel.getIcon()).getImage(), opacity[0])));
                currentStep++;
    
                // Stop and clean up when done
                if (currentStep > steps) {
                    fadeTimer.stop();
                    panel.remove(kuribohLabel);
                    panel.repaint();
                }
            }
        });
        fadeTimer.start();
    }

    /**
     * 
     * @param panel
     * @param x
     * @param y
     */
    private void spawnSerpentEffect(JLayeredPane panel, int x, int y) {
        // Create a JLabel for the small Kuriboh
        JLabel kuribohLabel = new JLabel();
        try {
            BufferedImage dmKuriboh = ImageIO.read(new File("Images/Sinister-Serpent.png"));
            Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
        } catch (IOException ex) {
            System.out.println("Kuriboh image not found!");
            return;
        }
    
        // Set the initial position for the small Kuriboh
        kuribohLabel.setBounds(x, y, 50, 50);
        panel.add(kuribohLabel, JLayeredPane.PALETTE_LAYER); // Add on a higher layer
        panel.repaint();
    
        // Animate the fade effect
        Timer fadeTimer = new Timer(50, null); // 50ms per frame
        final int steps = 20; // Total frames for fading
        final float[] opacity = {1.0f}; // Starting opacity
    
        fadeTimer.addActionListener(new ActionListener() {
            int currentStep = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reduce opacity
                opacity[0] = 1.0f - ((float) currentStep / steps);
                kuribohLabel.setIcon(new ImageIcon(createFadedImage(((ImageIcon) kuribohLabel.getIcon()).getImage(), opacity[0])));
                currentStep++;
    
                // Stop and clean up when done
                if (currentStep > steps) {
                    fadeTimer.stop();
                    panel.remove(kuribohLabel);
                    panel.repaint();
                }
            }
        });
        fadeTimer.start();
    }


    /**
     * 
     * @param panel
     * @param x
     * @param y
     */
    private void spawnSanganEffect(JLayeredPane panel, int x, int y) {
        // Create a JLabel for the small Kuriboh
        JLabel kuribohLabel = new JLabel();
        try {
            BufferedImage dmKuriboh = ImageIO.read(new File("Images/Sangan.png"));
            Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
        } catch (IOException ex) {
            System.out.println("Kuriboh image not found!");
            return;
        }
    
        // Set the initial position for the small Kuriboh
        kuribohLabel.setBounds(x, y, 50, 50);
        panel.add(kuribohLabel, JLayeredPane.PALETTE_LAYER); // Add on a higher layer
        panel.repaint();
    
        // Animate the fade effect
        Timer fadeTimer = new Timer(80, null); // 50ms per frame
        final int steps = 20; // Total frames for fading
        final float[] opacity = {1.0f}; // Starting opacity
    
        fadeTimer.addActionListener(new ActionListener() {
            int currentStep = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reduce opacity
                opacity[0] = 1.0f - ((float) currentStep / steps);
                kuribohLabel.setIcon(new ImageIcon(createFadedImage(((ImageIcon) kuribohLabel.getIcon()).getImage(), opacity[0])));
                currentStep++;
    
                // Stop and clean up when done
                if (currentStep > steps) {
                    fadeTimer.stop();
                    panel.remove(kuribohLabel);
                    panel.repaint();
                }
            }
        });
        fadeTimer.start();
    }

    private void spawnGoldEffect(JLayeredPane panel, int x, int y) {
        // Create a JLabel for the small Kuriboh
        JLabel kuribohLabel = new JLabel();
        try {
            BufferedImage dmKuriboh = ImageIO.read(new File("Images/Gold-sarc.png"));
            Image scaledKuriboh = dmKuriboh.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            kuribohLabel.setIcon(new ImageIcon(scaledKuriboh));
        } catch (IOException ex) {
            System.out.println("Gold Spawn image not found!");
            return;
        }
    
        // Set the initial position for the small Kuriboh
        kuribohLabel.setBounds(x, y, 50, 50);
        panel.add(kuribohLabel, JLayeredPane.PALETTE_LAYER); // Add on a higher layer
        panel.repaint();
    
        // Animate the fade effect
        Timer fadeTimer = new Timer(80, null); // 50ms per frame
        final int steps = 20; // Total frames for fading
        final float[] opacity = {1.0f}; // Starting opacity
    
        fadeTimer.addActionListener(new ActionListener() {
            int currentStep = 0;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reduce opacity
                opacity[0] = 1.0f - ((float) currentStep / steps);
                kuribohLabel.setIcon(new ImageIcon(createFadedImage(((ImageIcon) kuribohLabel.getIcon()).getImage(), opacity[0])));
                currentStep++;
    
                // Stop and clean up when done
                if (currentStep > steps) {
                    fadeTimer.stop();
                    panel.remove(kuribohLabel);
                    panel.repaint();
                }
            }
        });
        fadeTimer.start();
    }



}
