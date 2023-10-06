package view;

import controller.ClickController;
import controller.GameController;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    public static GameController gameController;
    public static JLabel statusLabel1;
    private static JLabel statusLabelMode;
    private static JLabel statusLabelBlack;
    private static JLabel statusLabelRed;
    private static JLabel RedGeneralPicture,RedAdvisorPicture,RedMinisterPicture,RedChariotPicture,
            RedHorsePicture,RedCannonPicture,RedSoldierPicture;
    private static JLabel BlackGeneralPicture,BlackAdvisorPicture,BlackMinisterPicture,BlackChariotPicture,
            BlackHorsePicture,BlackCannonPicture,BlackSoldierPicture;
    private static JLabel statusLabelRedGeneral,statusLabelRedAdvisor,statusLabelRedMinister,statusLabelRedChariot,
            statusLabelRedHorse,statusLabelRedCannon,statusLabelRedSoldier;
    private static JLabel statusLabelBlackGeneral,statusLabelBlackAdvisor,statusLabelBlackMinister,statusLabelBlackChariot,
            statusLabelBlackHorse,statusLabelBlackCannon,statusLabelBlackSoldier;
    public static JLabel getStatusLabel1() {
        return statusLabel1;
    }

    public static JLabel getStatusLabelMode() {return statusLabelMode;}
    public static JLabel getStatusLabelBlack ( ) {
        return statusLabelBlack;
    }
    public static JLabel getStatusLabelRed ( ) {
        return statusLabelRed;
    }

    public static JLabel getStatusLabelRedGeneral() {return statusLabelRedGeneral;}

    public static JLabel getStatusLabelRedAdvisor() {return statusLabelRedAdvisor;}

    public static JLabel getStatusLabelRedMinister() {return statusLabelRedMinister;}

    public static JLabel getStatusLabelRedChariot() {return statusLabelRedChariot;}

    public static JLabel getStatusLabelRedHorse() {return statusLabelRedHorse;}

    public static JLabel getStatusLabelRedCannon() {return statusLabelRedCannon;}

    public static JLabel getStatusLabelRedSoldier() {return statusLabelRedSoldier;}

    public static JLabel getStatusLabelBlackGeneral() {return statusLabelBlackGeneral;}

    public static JLabel getStatusLabelBlackAdvisor() {return statusLabelBlackAdvisor;}

    public static JLabel getStatusLabelBlackMinister() {return statusLabelBlackMinister;}

    public static JLabel getStatusLabelBlackChariot() {return statusLabelBlackChariot;}

    public static JLabel getStatusLabelBlackHorse() {return statusLabelBlackHorse;}

    public static JLabel getStatusLabelBlackCannon() {return statusLabelBlackCannon;}

    public static JLabel getStatusLabelBlackSoldier() {return statusLabelBlackSoldier;}
    public int i=1;

    private JLabel logo_label;

    public ChessGameFrame(int width, int height) throws IOException {
        setIconImage(ImageIO.read(new File ("D://桌面/java/DarkChess/src/Pic/1.png")));
        setTitle("暗棋: 争霸天下"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // 屏幕的中间
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);//设置为null即为清空布局管理器，之后添加组件，常常是设置组件左上角坐标相对于容器左上角（0，0）的x,y值来确定组件的位置，即使更改容器大小也不会改变位置

        addChessboard();
        addLabel();
        addUndoButton();
        addLoadButton();
        addRestartButton();
        addBlackScore ();
        addRedScore ();
        addSaveButton ();
        addCheatButton();
        addMode();
        addDrawButton();
        addSurrenderButton();

        addRedGeneralPicture();
        addRedAdvisorPicture();
        addRedMinisterPicture();
        addRedChariotPicture();
        addRedCannonPicture();
        addRedHorsePicture();
        addRedSoldierPicture();
        addBlackGeneralPicture();
        addBlackAdvisorPicture();
        addBlackMinisterPicture();
        addBlackChariotPicture();
        addBlackCannonPicture();
        addBlackHorsePicture();
        addBlackSoldierPicture();

        addRedGeneralAmount();
        addRedAdvisorAmount();
        addRedMinisterAmount();
        addRedChariotAmount();
        addRedCannonAmount();
        addRedHorseAmount();
        addRedSoldierAmount();
        addBlackGeneralAmount();
        addBlackAdvisorAmount();
        addBlackMinisterAmount();
        addBlackChariotAmount();
        addBlackCannonAmount();
        addBlackHorseAmount();
        addBlackSoldierAmount();
    }


    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10-20, HEIGHT / 10);//设置棋盘在窗体中的位置
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel1 = new JLabel("Black's TURN");
        statusLabel1.setLocation(WIDTH/5-20, HEIGHT / 50);
        statusLabel1.setSize(200, 60);
        statusLabel1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel1);
    }
    public void addRedGeneralPicture(){
        ImageIcon General=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedGeneral.png");
        RedGeneralPicture=new JLabel();
        RedGeneralPicture.setIcon(General);
        RedGeneralPicture.setSize(38,38);
        RedGeneralPicture.setLocation(HEIGHT/10+285,HEIGHT/10);
        add(RedGeneralPicture);
    }
    public void addRedAdvisorPicture(){
        ImageIcon Advisor=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedAdvisor.png");
        RedAdvisorPicture=new JLabel();
        RedAdvisorPicture.setIcon(Advisor);
        RedAdvisorPicture.setSize(38,38);
        RedAdvisorPicture.setLocation(HEIGHT/10+285,HEIGHT/10+40);
        add(RedAdvisorPicture);
    }
    public void addRedMinisterPicture(){
        ImageIcon Minister=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedMinister.png");
        RedMinisterPicture=new JLabel();
        RedMinisterPicture.setIcon(Minister);
        RedMinisterPicture.setSize(38,38);
        RedMinisterPicture.setLocation(HEIGHT/10+285,HEIGHT/10+80);
        add(RedMinisterPicture);
    }
    public void addRedChariotPicture(){
        ImageIcon Chariot=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedChariot.png");
        RedChariotPicture=new JLabel();
        RedChariotPicture.setIcon(Chariot);
        RedChariotPicture.setSize(38,38);
        RedChariotPicture.setLocation(HEIGHT/10+285,HEIGHT/10+120);
        add(RedChariotPicture);
    }
    public void addRedCannonPicture(){
        ImageIcon Cannon=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedCannon.png");
        RedCannonPicture=new JLabel();
        RedCannonPicture.setIcon(Cannon);
        RedCannonPicture.setSize(38,38);
        RedCannonPicture.setLocation(HEIGHT/10+285,HEIGHT/10+160);
        add(RedCannonPicture);
    }
    public void addRedHorsePicture(){
        ImageIcon Horse=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedHorse.png");
        RedHorsePicture=new JLabel();
        RedHorsePicture.setIcon(Horse);
        RedHorsePicture.setSize(38,38);
        RedHorsePicture.setLocation(HEIGHT/10+285,HEIGHT/10+200);
        add(RedHorsePicture);
    }
    public void addRedSoldierPicture(){
        ImageIcon Soldier=new ImageIcon("D://桌面/java/DarkChess/src/Pic/RedSoldier.png");
        RedSoldierPicture=new JLabel();
        RedSoldierPicture.setIcon(Soldier);
        RedSoldierPicture.setSize(38,38);
        RedSoldierPicture.setLocation(HEIGHT/10+285,HEIGHT/10+240);
        add(RedSoldierPicture);
    }

    public void addBlackGeneralPicture(){
        ImageIcon General=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackGeneral.png");
        BlackGeneralPicture=new JLabel();
        BlackGeneralPicture.setIcon(General);
        BlackGeneralPicture.setSize(38,38);
        BlackGeneralPicture.setLocation(HEIGHT/10+285,HEIGHT/10+298);
        add(BlackGeneralPicture);
    }
    public void addBlackAdvisorPicture(){
        ImageIcon Advisor=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackAdvisor.png");
        BlackAdvisorPicture=new JLabel();
        BlackAdvisorPicture.setIcon(Advisor);
        BlackAdvisorPicture.setSize(38,38);
        BlackAdvisorPicture.setLocation(HEIGHT/10+285,HEIGHT/10+338);
        add(BlackAdvisorPicture);
    }
    public void addBlackMinisterPicture(){
        ImageIcon Minister=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackMinister.png");
        BlackMinisterPicture=new JLabel();
        BlackMinisterPicture.setIcon(Minister);
        BlackMinisterPicture.setSize(38,38);
        BlackMinisterPicture.setLocation(HEIGHT/10+285,HEIGHT/10+378);
        add(BlackMinisterPicture);
    }
    public void addBlackChariotPicture(){
        ImageIcon Chariot=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackChariot.png");
        BlackChariotPicture=new JLabel();
        BlackChariotPicture.setIcon(Chariot);
        BlackChariotPicture.setSize(38,38);
        BlackChariotPicture.setLocation(HEIGHT/10+285,HEIGHT/10+418);
        add(BlackChariotPicture);
    }
    public void addBlackCannonPicture(){
        ImageIcon Cannon=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackCannon.png");
        BlackCannonPicture=new JLabel();
        BlackCannonPicture.setIcon(Cannon);
        BlackCannonPicture.setSize(38,38);
        BlackCannonPicture.setLocation(HEIGHT/10+285,HEIGHT/10+458);
        add(BlackCannonPicture);
    }
    public void addBlackHorsePicture(){
        ImageIcon Horse=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackHorse.png");
        BlackHorsePicture=new JLabel();
        BlackHorsePicture.setIcon(Horse);
        BlackHorsePicture.setSize(38,38);
        BlackHorsePicture.setLocation(HEIGHT/10+285,HEIGHT/10+498);
        add(BlackHorsePicture);
    }
    public void addBlackSoldierPicture(){
        ImageIcon Soldier=new ImageIcon("D://桌面/java/DarkChess/src/Pic/BlackSoldier.png");
        BlackSoldierPicture=new JLabel();
        BlackSoldierPicture.setIcon(Soldier);
        BlackSoldierPicture.setSize(38,38);
        BlackSoldierPicture.setLocation(HEIGHT/10+285,HEIGHT/10+538);
        add(BlackSoldierPicture);
    }

    private void addRedGeneralAmount() {
        statusLabelRedGeneral = new JLabel(String.valueOf(ClickController.redGeneralAmount));
        statusLabelRedGeneral.setLocation(HEIGHT/10+330, HEIGHT / 10);
        statusLabelRedGeneral.setSize(38, 38);
        statusLabelRedGeneral.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedGeneral);
    }
    private void addRedAdvisorAmount() {
        statusLabelRedAdvisor = new JLabel(String.valueOf(ClickController.redAdvisorAmount));
        statusLabelRedAdvisor.setLocation(HEIGHT/10+330, HEIGHT / 10+40);
        statusLabelRedAdvisor.setSize(38, 38);
        statusLabelRedAdvisor.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedAdvisor);
    }
    private void addRedMinisterAmount() {
        statusLabelRedMinister = new JLabel(String.valueOf(ClickController.redMinisterAmount));
        statusLabelRedMinister.setLocation(HEIGHT/10+330, HEIGHT / 10+80);
        statusLabelRedMinister.setSize(38, 38);
        statusLabelRedMinister.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedMinister);
    }
    private void addRedChariotAmount() {
        statusLabelRedChariot = new JLabel(String.valueOf(ClickController.redChariotAmount));
        statusLabelRedChariot.setLocation(HEIGHT/10+330, HEIGHT / 10+120);
        statusLabelRedChariot.setSize(38, 38);
        statusLabelRedChariot.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedChariot);
    }
    private void addRedCannonAmount() {
        statusLabelRedCannon = new JLabel(String.valueOf(ClickController.redCannonAmount));
        statusLabelRedCannon.setLocation(HEIGHT/10+330, HEIGHT / 10+160);
        statusLabelRedCannon.setSize(38, 38);
        statusLabelRedCannon.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedCannon);
    }
    private void addRedHorseAmount() {
        statusLabelRedHorse = new JLabel(String.valueOf(ClickController.redHorseAmount));
        statusLabelRedHorse.setLocation(HEIGHT/10+330, HEIGHT / 10+200);
        statusLabelRedHorse.setSize(38, 38);
        statusLabelRedHorse.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedHorse);
    }
    private void addRedSoldierAmount() {
        statusLabelRedSoldier = new JLabel(String.valueOf(ClickController.redSoldierAmount));
        statusLabelRedSoldier.setLocation(HEIGHT/10+330, HEIGHT / 10+240);
        statusLabelRedSoldier.setSize(38, 38);
        statusLabelRedSoldier.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelRedSoldier);
    }

    private void addBlackGeneralAmount() {
        statusLabelBlackGeneral = new JLabel(String.valueOf(ClickController.blackGeneralAmount));
        statusLabelBlackGeneral.setLocation(HEIGHT/10+330, HEIGHT / 10+298);
        statusLabelBlackGeneral.setSize(38, 38);
        statusLabelBlackGeneral.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackGeneral);
    }
    private void addBlackAdvisorAmount() {
        statusLabelBlackAdvisor = new JLabel(String.valueOf(ClickController.blackAdvisorAmount));
        statusLabelBlackAdvisor.setLocation(HEIGHT/10+330, HEIGHT / 10+338);
        statusLabelBlackAdvisor.setSize(38, 38);
        statusLabelBlackAdvisor.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackAdvisor);
    }
    private void addBlackMinisterAmount() {
        statusLabelBlackMinister = new JLabel(String.valueOf(ClickController.blackMinisterAmount));
        statusLabelBlackMinister.setLocation(HEIGHT/10+330, HEIGHT / 10+378);
        statusLabelBlackMinister.setSize(38, 38);
        statusLabelBlackMinister.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackMinister);
    }
    private void addBlackChariotAmount() {
        statusLabelBlackChariot = new JLabel(String.valueOf(ClickController.blackChariotAmount));
        statusLabelBlackChariot.setLocation(HEIGHT/10+330, HEIGHT / 10+418);
        statusLabelBlackChariot.setSize(38, 38);
        statusLabelBlackChariot.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackChariot);
    }
    private void addBlackCannonAmount() {
        statusLabelBlackCannon = new JLabel(String.valueOf(ClickController.blackCannonAmount));
        statusLabelBlackCannon.setLocation(HEIGHT/10+330, HEIGHT / 10+458);
        statusLabelBlackCannon.setSize(38, 38);
        statusLabelBlackCannon.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackCannon);
    }
    private void addBlackHorseAmount() {
        statusLabelBlackHorse = new JLabel(String.valueOf(ClickController.blackHorseAmount));
        statusLabelBlackHorse.setLocation(HEIGHT/10+330, HEIGHT / 10+498);
        statusLabelBlackHorse.setSize(38, 38);
        statusLabelBlackHorse.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackHorse);
    }
    private void addBlackSoldierAmount() {
        statusLabelBlackSoldier = new JLabel(String.valueOf(ClickController.blackSoldierAmount));
        statusLabelBlackSoldier.setLocation(HEIGHT/10+330, HEIGHT / 10+538);
        statusLabelBlackSoldier.setSize(38, 38);
        statusLabelBlackSoldier.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabelBlackSoldier);
    }


    public void addMode(){
        statusLabelMode = new JLabel ("模式：正常");
        statusLabelMode.setLocation (WIDTH*3/5+50, HEIGHT / 50+10);
        statusLabelMode.setSize (150,40);
        statusLabelMode.setFont (new Font("隶书", Font.BOLD, 26));
        add(statusLabelMode);
    }
    public void addBlackScore(){
        statusLabelBlack = new JLabel ("Black: 0");
        statusLabelBlack.setLocation (WIDTH*3/5+50, HEIGHT / 50+60);
        statusLabelBlack.setSize (150,40);
        statusLabelBlack.setFont (new Font("Rockwell", Font.BOLD, 18));
        add(statusLabelBlack);

    }

    public void addRedScore(){
        statusLabelRed = new JLabel ("Red: 0");
        statusLabelRed.setLocation (WIDTH*3/5+50, HEIGHT / 50+100);
        statusLabelRed.setSize (150,40);
        statusLabelRed.setFont (new Font("Rockwell", Font.BOLD, 18));
        add(statusLabelRed);
    }

    private void addUndoButton() {
        JButton jButton = new JButton ("悔 棋");
        jButton.setLocation (WIDTH * 3 / 5+50, HEIGHT / 10 +100);
        jButton.setSize (150,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,26));
        jButton.addActionListener((e) -> {
            System.out.println("Click regret");
            int result = JOptionPane.showConfirmDialog(this, "是否要悔棋");
            if(result == JOptionPane.YES_OPTION){
                gameController.JudgeUndo();
            }

        });
        add(jButton);
    }
    private void addCheatButton(){
        JButton button = new JButton("作弊模式");//名称
        button.setLocation(WIDTH * 3 / 5 + 50, HEIGHT / 10 + 180);//位置
        button.setSize(150, 60);//大小
        button.setFont(new Font("隶书", Font.BOLD, 26));//设置字体
        add(button);

        button.addActionListener(e -> {
            if(button.getText().equals("作弊模式")&&getStatusLabelMode().getText().equals("模式：正常")){
                System.out.println("Click cheat");
                button.setText("正常模式");
                getStatusLabelMode().setText ("模式：作弊");
                ClickController.mode=1;
            }else {
                System.out.println("Click normal");
                button.setText("作弊模式");
                getStatusLabelMode().setText ("模式：正常");
                ClickController.mode=0;
            }
        });
    }

    public void addSaveButton() {
        JButton button = new JButton("保 存");//名称
        button.setLocation(WIDTH * 3 / 5 + 50, HEIGHT / 10 + 260);//位置
        button.setSize(150, 60);//大小
        button.setFont(new Font("隶书", Font.BOLD, 26));//设置字体
        add(button);

        button.addActionListener(e -> {//鼠标监听事件
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            try {
                gameController.saveGameToFile(path);//调用gameController中的saveGameToFile方法
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("读 取");//名称
        button.setLocation(WIDTH * 3 / 5+50, HEIGHT / 10 + 340);//位置
        button.setSize(150, 60);//大小
        button.setFont(new Font("隶书", Font.BOLD, 26));//设置字体
        //button.setBackground(Color.LIGHT_GRAY);//按钮的颜色
        add(button);

        button.addActionListener(e -> {//鼠标监听事件
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path.endsWith(".txt"))
                gameController.loadGameFromFile(path);//调用gameController中的loadGameFromFile方法
            else JOptionPane.showMessageDialog(this, "错误编码:101");

        });


    }
    private void addRestartButton(){
        JButton jButton = new JButton ("重新开始");
        jButton.setLocation (WIDTH * 3 / 5+50, HEIGHT / 10 + 420);
        jButton.setSize (150,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,26));
       // jButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "是否要重新开始?"));
        jButton.addActionListener (e ->{
//            SwingUtilities.invokeLater(() -> {
//                ChessGameFrame mainFrame = new ChessGameFrame(720, 720);
//                mainFrame.setVisible(true);
//                RedScore=0;
//                BlackScore=0;
//            });

            gameController.Restart();
            System.out.println("click restart");

        });
        add(jButton);
    }
    //和棋
    private void addDrawButton(){
        JButton jButton = new JButton ("主题");
        jButton.setLocation (WIDTH * 3 / 5+30, HEIGHT / 10 + 500);
        jButton.setSize (85,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,24));
        // jButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "是否要重新开始?"));

        logo_label = new JLabel();
        logo_label.setBounds(0,0,this.getWidth(),this.getHeight());
        getLayeredPane().add(logo_label,Integer.valueOf(Integer.MIN_VALUE));
        JPanel jp=(JPanel) this.getContentPane ();
        jp.setOpaque (false);
        jButton.addActionListener (e ->{
            if (i %3==0){
                ImageIcon imageIcon=new ImageIcon ("D://桌面/java/DarkChess/src/Pic/古风.jpg");
                logo_label.setIcon(imageIcon);
                i++;
            } else if (i%3==1) {
                ImageIcon imageIcon=new ImageIcon ("D://桌面/java/DarkChess/src/Pic/花.png");
                logo_label.setIcon(imageIcon);
                i++;
            }else if (i%3==2) {
                ImageIcon imageIcon=new ImageIcon ("D://桌面/java/DarkChess/src/Pic/花.jpg");
                logo_label.setIcon(imageIcon);
                i++;
            }

        });

        add(jButton);
    }

    private void addSurrenderButton(){
        JButton jButton = new JButton ("认输");
        jButton.setLocation (WIDTH * 3 / 5+135, HEIGHT / 10 + 500);
        jButton.setSize (85,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,24));
        // jButton.addActionListener((e) -> JOptionPane.showMessageDialog(this, "是否要重新开始?"));
        jButton.addActionListener (e ->{
            int result=JOptionPane.showConfirmDialog (this,"是否要认输?");
            if(result==0){
                if(statusLabel1.getText ().substring (0,3).equals ("Red")){
                    int re=JOptionPane.showConfirmDialog (this,"Black Wins!");
                    if(re==0){
                        gameController.Restart ();
                    }
                }else{
                    int re=JOptionPane.showConfirmDialog (this,"Red Wins!");
                    if(re==0){
                        gameController.Restart ();
                    }
                }
            }
        });
        add(jButton);
    }
}
