package view;

import chessComponent.SquareComponent;
import controller.ClickController;
import controller.GameController;
import jdk.jshell.Snippet;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static chessComponent.SquareComponent.squareColor;
import static java.awt.SystemColor.text;
import static view.ChessGameFrame.gameController;
import static view.Chessboard.BlackScore;
import static view.Chessboard.RedScore;
public class InitialGameInterface extends JFrame{
    public static final int WIDTH=720;
    public static final int HEIGHT=720;
    public InitialGameInterface() throws IOException {
        setIconImage(ImageIO.read(new File ("D://桌面/java/DarkChess/src/Pic/图标.png")));
        setSize(WIDTH, HEIGHT);
        setTitle ("暗棋: 争霸天下");
        setLocationRelativeTo(null); // 屏幕的中间
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);//设置为null即为清空布局管理器，之后添加组件，常常是设置组件左上角坐标相对于容器左上角（0，0）的x,y值来确定组件的位置，即使更改容器大小也不会改变位置
        ImageIcon imageIcon=new ImageIcon ("D://桌面/java/DarkChess/src/Pic/背景3.png");
        JLabel logo_label=new JLabel (imageIcon);
        logo_label.setBounds (-280,-16,imageIcon.getIconWidth (),imageIcon.getIconHeight ());
        getLayeredPane ().add (logo_label,Integer.valueOf (Integer.MIN_VALUE));
        JPanel jp=(JPanel) this.getContentPane ();
        jp.setOpaque (false);
        addSurrenderButton ();
        addRuleButton ();
        addLoginButton ();
        addRegisterButton ();
        setVisible (true);
    }
    private void addSurrenderButton(){
        JButton jButton = new JButton ("快速开始");
        jButton.setLocation (WIDTH /2-90, HEIGHT / 5+130);
        jButton.setSize (180,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,26));
        jButton.setBackground (squareColor);
        jButton.addActionListener (e ->{
            ChessGameFrame mainFrame = null;
            try {
                mainFrame = new ChessGameFrame (720, 720);
                setDefaultCloseOperation (EXIT_ON_CLOSE);
            } catch (IOException ex) {
                throw new RuntimeException (ex);
            }
            mainFrame.setVisible(true);
        });
        add(jButton);
    }


    private void addLoginButton(){
        JButton jButton = new JButton ("登  陆");
        jButton.setLocation (WIDTH /2-90, HEIGHT / 5+220);
        jButton.setSize (180,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,26));
        jButton.setBackground (squareColor);
        jButton.addActionListener (e ->{
            String users = JOptionPane.showInputDialog(this, "Input username:");
            String path = "D://桌面/java/DarkChess/src/user.txt";
            try {
                File file=new File (path);
                FileReader reader=new FileReader (file);
                BufferedReader bufferedReader=new BufferedReader (reader);
                StringBuilder stringBuilder=new StringBuilder ();
                String s="";
                boolean is=false;
                while((s=bufferedReader.readLine ())!=null){
                    if(s.equals (users)){
                        is=true;
                        JOptionPane.showConfirmDialog (this,"登陆成功");
                        ChessGameFrame mainFrame = null;
                        try {
                            mainFrame = new ChessGameFrame (720, 720);
                            setDefaultCloseOperation (EXIT_ON_CLOSE);
                        } catch (IOException ex) {
                            throw new RuntimeException (ex);
                        }
                        mainFrame.setVisible(true);
                    }
                }
                if(! is){
                    JOptionPane.showConfirmDialog (this,"请您先注册个账号吧");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        add(jButton);
    }

    private void addRegisterButton(){
        JButton jButton = new JButton ("注  册");
        jButton.setLocation (WIDTH /2-90, HEIGHT / 5+310);
        jButton.setSize (180,60);
        jButton.setFont (new Font ("隶书",Font.BOLD,26));
        jButton.setBackground (squareColor);
        jButton.addActionListener (e ->{
            FileOutputStream o=null;
            String users = JOptionPane.showInputDialog(this, "Input username:");
            File file=new File ("D://桌面/java/DarkChess/src/user.txt");
            FileReader reader= null;
            try {
                reader = new FileReader (file);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException (ex);
            }
            BufferedReader bufferedReader=new BufferedReader (reader);
            StringBuilder stringBuilder=new StringBuilder ();
            String s="";
            boolean is=false;
            while(true){
                try {
                    if ((s = bufferedReader.readLine ()) == null) break;
                } catch (IOException ex) {
                    throw new RuntimeException (ex);
                }
                if(s.equals (users)){
                    is=true;
                    JOptionPane.showConfirmDialog (this,"您已经注册,直接给您登陆啦");
                    ChessGameFrame mainFrame = null;
                    try {
                        mainFrame = new ChessGameFrame (720, 720);
                        setDefaultCloseOperation (EXIT_ON_CLOSE);
                    } catch (IOException ex) {
                        throw new RuntimeException (ex);
                    }
                    mainFrame.setVisible(true);
                }
            }
            if(! is){
                if(users.length ()<3){
                    JOptionPane.showMessageDialog(this, "name应在三个字符及以上");
                }else{
                    String path = "D://桌面/java/DarkChess/src/user.txt";
                    byte[] a=new byte[]{};
                    try {
                        File file1=new File (path);
                        a=(users+"\n").getBytes ();
                        o=new FileOutputStream (file1,true);
                        o.write (a);
                        o.flush ();
                        o.close ();
                        JOptionPane.showConfirmDialog (this,"注册成功");
                        ChessGameFrame mainFrame = null;
                        try {
                            mainFrame = new ChessGameFrame (720, 720);
                            setDefaultCloseOperation (EXIT_ON_CLOSE);
                        } catch (IOException ex) {
                            throw new RuntimeException (ex);
                        }
                        mainFrame.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });
        add(jButton);
    }
    private void addRuleButton(){
        JButton jButton = new JButton ("规  则");
        jButton.setLocation (WIDTH /2-90, HEIGHT / 5+400);
        jButton.setSize (180,60);
        jButton.setBackground (squareColor);

        jButton.setFont (new Font ("隶书",Font.BOLD,26));
        jButton.addActionListener (e ->{
            JFrame jFrame=new JFrame ();
            jFrame.setSize (720,720);
            jFrame.setTitle ("暗棋规则");
            jFrame.setFont (Font.getFont ("楷体"));
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.setLocationRelativeTo(null); // 屏幕的中间
            jFrame.setVisible(true);
            JTextArea textArea=new JTextArea ();
            textArea.setLineWrap (true);
            JScrollPane scrollPane=new JScrollPane (textArea);
            jFrame.add(scrollPane);
            textArea.setFont (new Font ("楷体",Font.BOLD,20));
            textArea.setText ("-------------------------------规则------------------------------- "+"  \n" +
                    " 每方共32个，分为红黑双组，各16个，双方轮流执行：\n" +
                    "\n" +
                    " 红棋子：兵5个、炮2个、车2个、马2个、相2个、仕2个、帅1个共16个棋子。\n" +
                    " 黑棋子：卒5个、炮2个、车2个、马2个、象2个、士2个、将1个共16个棋子。\n" +
                    "\n"+
                    " 兵（卒）：兵（卒）是等级最低的棋子，只能吃对方的将（帅）。\n" +
                    " 炮：可以吃任何棋子，但中间必须隔一个棋子。\n" +
                    " 车：可以吃除（相）、士（仕）、将（帅）以外的棋子。\n" +
                    " 马：可以吃兵（卒）、炮。\n" +
                    " 象（相）：可以吃除士（仕）、将（帅）以外的棋子。\n" +
                    " 士（仕）：可以吃除将（帅）以外的棋子。\n" +
                    " 将（帅）：将（帅）是等级最高的棋子，可以吃任何棋子，但不可吃兵（卒）。\n" +
                    "\n"+
                    " 每步翻一个子或移动自己的子，每步移动一格。\n" +
                    " 特殊：炮不可移动，但可隔子打未翻开的子或对方翻开的子。\n" +
                    "\n" +
                    " 分数先达60一方获胜。\n" +
                    " 吃掉对方每个子的分数：将/帅=30，士=10，象=车=马=炮=5，兵=1。\n"+
                    "\n" +
                    " 若一方认输, 则对方获胜\n"+
                    "\n"+
                    " 您可点击作弊模式,之后您将会有很短暂的时间查看未翻的棋子\n"+
                    " 您可点击保存来保存当前棋局\n"+
                    " 您可点击读取来读取您已经保存的棋局\n");
            textArea.setBackground (Color.WHITE);
            textArea.setEditable (false);

        });
        add(jButton);
    }

}
