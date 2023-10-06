package view;


import chessComponent.*;
import controller.GameController;
import controller.MusicController;
import controller.SoundController;
import model.*;
import controller.ClickController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static view.ChessGameFrame.gameController;
import static view.ChessGameFrame.statusLabel1;


/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {


    public static final int ROW_SIZE = 8;//设置棋盘的高
    public static final int COL_SIZE = 4;//设置棋盘的宽

    public static int BlackScore = 0;
    public static int RedScore = 0;

    public static SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];//建一个数组存坐标
    //todo: you can change the initial player
    public ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;//设置棋子的大小

    public static ArrayList<SquareComponent> removedChess=new ArrayList<>();
    public int con=0;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard();

    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     *
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     *
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.

        if(statusLabel1.getText ().startsWith("Red"))
            GameController.step.add(String.format("%d%d%d%d0", chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY(),
                chess2.getChessboardPoint().getX(), chess2.getChessboardPoint().getY()));
        else GameController.step.add(String.format("%d%d%d%d1", chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY(),
                chess2.getChessboardPoint().getX(), chess2.getChessboardPoint().getY()));

        boolean canCatch = false;//chess1是否可以吃chess2
        boolean canSwap = false;//chess1移动是否为1
        if (chess1.chessComponentNum != 1) {//如果chess1不为炮

            if ((Math.abs(chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY()) +
                    Math.abs(chess1.getChessboardPoint().getX() - chess2.getChessboardPoint().getX())) == 1) {
                canSwap = true;//上下左右移动只能为1
            }
        }

        if (chess1.chessComponentNum == 0 && chess2.chessComponentNum == 6 || chess1.chessComponentNum == 0 && chess2.chessComponentNum == 0) {
            canCatch = true;
        } else if (chess1.chessComponentNum > 1 && chess2.chessComponentNum <= chess1.chessComponentNum) {
            canCatch=true;
        }
        if (!(chess2 instanceof EmptySlotComponent) && canCatch && canSwap) {
            sound();
            clickController.getChessAmount(chess2);

            if(chess2.getChessColor ().equals (ChessColor.RED)){
                BlackScore+=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
            }else if(chess2.getChessColor ().equals (ChessColor.BLACK)){
                RedScore+=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
            }
            clickController.getScore ();

            remove(chess2);
            removedChess.add(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
            chess1.swapLocation(chess2);

        } else if ((chess2 instanceof EmptySlotComponent) && canSwap) {
            remove(chess2);
            removedChess.add(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
            chess1.swapLocation(chess2);

        }
        int row1, row2, col1, col2;
        row1 = chess1.getChessboardPoint().getX();
        col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        row2 = chess2.getChessboardPoint().getX();
        col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
        //chess1为炮时
        if (chess1.chessComponentNum == 1) {
            canSwap = false;
            int num = 0;
            if (chess1.getChessboardPoint().getX() == chess2.getChessboardPoint().getX()) {
                if (chess1.getChessboardPoint().getY() > chess2.getChessboardPoint().getY()) {
                    for (int i = chess2.getChessboardPoint().getY(); i < chess1.getChessboardPoint().getY(); i++) {
                        if (squareComponents[chess1.getChessboardPoint().getX()][i] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                } else if (chess1.getChessboardPoint().getY() < chess2.getChessboardPoint().getY()) {
                    for (int i = chess1.getChessboardPoint().getY(); i < chess2.getChessboardPoint().getY(); i++) {
                        if (squareComponents[chess1.getChessboardPoint().getX()][i] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess2.getChessboardPoint().getY() - chess1.getChessboardPoint().getY() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                }
            } else if (chess1.getChessboardPoint().getY() == chess2.getChessboardPoint().getY()) {
                if (chess1.getChessboardPoint().getX() > chess2.getChessboardPoint().getX()) {
                    for (int i = chess2.getChessboardPoint().getX(); i < chess1.getChessboardPoint().getX(); i++) {
                        if (squareComponents[i][chess1.getChessboardPoint().getY()] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess1.getChessboardPoint().getX() - chess2.getChessboardPoint().getX() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                } else if (chess1.getChessboardPoint().getX() < chess2.getChessboardPoint().getX()) {
                    for (int i = chess1.getChessboardPoint().getX(); i < chess2.getChessboardPoint().getX(); i++) {
                        if (squareComponents[i][chess1.getChessboardPoint().getY()] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess2.getChessboardPoint().getX() - chess1.getChessboardPoint().getX() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                }
            }
        }
        if (!(chess2 instanceof EmptySlotComponent) && canSwap) {
            sound();
            clickController.getChessAmount(chess2);

            if(chess2.getChessColor ().equals (ChessColor.RED)){
                BlackScore+=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
            }else if(chess2.getChessColor ().equals (ChessColor.BLACK)){
                RedScore+=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
            }
            clickController.getScore ();

            remove(chess2);
            removedChess.add(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
            chess1.swapLocation(chess2);

        }
        row1 = chess1.getChessboardPoint().getX();
        col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        row2 = chess2.getChessboardPoint().getX();
        col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;
        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();


    }

    public void sound(){
        String path = "D://桌面/java/DarkChess/sound.wav";
        SoundController.AudioPlay2 music = new SoundController.AudioPlay2(path);
        music.run = true;
        music.start();
    }

    //FIXME:   Initialize chessboard for testing only.
    public void initAllChessOnBoard() {
        // 0 1红士 2 3黑士 3 4红炮 5 6黑炮 7 8红车 9 10 黑车 11帅 12将 13 14红马 15 16 黑马 17 18红象 19 20黑象 21 22 23 24 25 26 兵 27 28 29 30 31卒
        int[] a = new int[32];
        Random random = new Random();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                SquareComponent squareComponent = null;
                int num = 0;
                do {
                    num = random.nextInt(32);
                } while (a[num] != 0);
                if (num == 0 || num == 1) {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 2 || num == 3) {
                    squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 4 || num == 5) {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 6 || num == 7) {
                    squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 8 || num == 9) {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 10 || num == 11) {
                    squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 12) {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 13) {
                    squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 14 || num == 15) {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 16 || num == 17) {
                    squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 18 || num == 19) {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 20 || num == 21) {
                    squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else if (num == 22 || num == 23 || num == 24 || num == 25 || num == 26) {
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                } else {
                    squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    a[num]++;
                    GameController.chess[i][j] = num;
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }
    }


    /**
     * 绘制棋盘格子
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     *
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    /**
     * 通过GameController调用该方法
     *
     * @param chessData
     */
    public void loadGame(List<String> chessData) {
        String[][] loadBoard = new String[8][4];
        SquareComponent[][] loadComponent = new SquareComponent[ROW_SIZE][COL_SIZE];
        con=0;
        //判断棋盘是否正确
        try {
            for (int i = 0; i < loadBoard.length; i++) {
                for (int j = 0; j < loadBoard[i].length; j++) {
                    loadBoard[i][j] = chessData.get(i).substring(3 * j, 3 * j + 2);
                    if (Integer.parseInt(loadBoard[i][j].substring(0, 2))<0||Integer.parseInt(loadBoard[i][j].substring(0, 2))>31){
                        JOptionPane.showMessageDialog(this, "错误编码:103");
                        con=1;
                    }
                }
            }
        }catch (IndexOutOfBoundsException a){
            JOptionPane.showMessageDialog(this, "错误编码:102");
            con=1;
        }

        if (con==0){
            for (int i = 9; i < chessData.size(); i++) {
                if (chessData.get(i).length()==4||chessData.get(i).length()==2){
                    JOptionPane.showMessageDialog(this, "错误编码:104");
                    con=1;
                }
            }
        }


        if (con==0){
            for (int i = 10; i < chessData.size(); i++) {
                if (chessData.get(i-1).endsWith("1")&&chessData.get(i).endsWith("1")||
                        chessData.get(i-1).endsWith("0")&&chessData.get(i).endsWith("0")){
                    JOptionPane.showMessageDialog(this, "错误编码:105");
                    con=1;
                }
            }
        }

//        if (con == 0) {
//            for (int i = 0; i < loadBoard.length; i++) {
//                for (int j = 0; j < loadBoard[i].length; j++) {
//                    //获取棋盘棋子
//                    loadBoard[i][j] = chessData.get(i).substring(3 * j, 3 * j + 2);
//                    SquareComponent squareComponent = null;
//                    if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 0 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 1) {
//                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 2 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 3) {
//                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 4 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 5) {
//                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 6 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 7) {
//                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 8 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 9) {
//                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 10 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 11) {
//                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 12) {
//                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 13) {
//                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 14 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 15) {
//                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 16 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 17) {
//                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 18 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 19) {
//                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 20 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 21) {
//                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 22 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 23
//                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 24 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 25
//                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 26) {
//                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 27 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 28
//                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 29 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 30
//                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 31){
//                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
//                        loadComponent[i][j] = squareComponent;
//                    }
//                }
//            }
//            for (int i = 9; i < chessData.size(); i++) {
//                if (chessData.get(i).length()==5){
//                    int x1, y1, x2, y2;
//                    x1 = Integer.parseInt(chessData.get(i).substring(0, 1));
//                    y1 = Integer.parseInt(chessData.get(i).substring(1, 2));
//                    x2 = Integer.parseInt(chessData.get(i).substring(2, 3));
//                    y2 = Integer.parseInt(chessData.get(i).substring(3, 4));
//                    SquareComponent c1 = squareComponents[x1][y1];
//                    SquareComponent c2 = squareComponents[x2][y2];
//                    judge(c1, c2);
//                }
//            }
//        }


        //执行行棋步骤
        if (con==0){
            gameController.Restart();

            for (int i = 0; i < loadBoard.length; i++) {
                for (int j = 0; j < loadBoard[i].length; j++) {
                    //获取棋盘棋子
                    loadBoard[i][j] = chessData.get(i).substring(3 * j, 3 * j + 2);
                    SquareComponent squareComponent = null;
                    if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 0 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 1) {
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 2 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 3) {
                        squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 4 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 5) {
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 6 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 7) {
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 8 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 9) {
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 10 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 11) {
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 12) {
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 13) {
                        squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 14 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 15) {
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 16 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 17) {
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 18 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 19) {
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 20 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 21) {
                        squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 22 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 23
                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 24 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 25
                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 26) {
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.RED, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    } else if (Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 27 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 28
                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 29 || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 30
                            || Integer.parseInt(loadBoard[i][j].substring(0, 2)) == 31){
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                        loadComponent[i][j] = squareComponent;
                    }

                    squareComponent.setVisible(true);
                    putChessOnBoard(squareComponent);
                    repaint();


                }
            }
            //执行行棋步骤
            for (int i = 9; i < chessData.size(); i++) {
                if (chessData.get(i).length()==5){
                    int x1, y1, x2, y2;
                    x1 = Integer.parseInt(chessData.get(i).substring(0, 1));
                    y1 = Integer.parseInt(chessData.get(i).substring(1, 2));
                    x2 = Integer.parseInt(chessData.get(i).substring(2, 3));
                    y2 = Integer.parseInt(chessData.get(i).substring(3, 4));
                    SquareComponent c1 = squareComponents[x1][y1];
                    SquareComponent c2 = squareComponents[x2][y2];
                    swapChessComponents(c1, c2);
                    clickController.swapPlayer();
                    clickController.getScore();
                }
                else if (chessData.get(i).length()==3){
                    int x,y;
                    x = Integer.parseInt(chessData.get(i).substring(0, 1));
                    y = Integer.parseInt(chessData.get(i).substring(1, 2));
                    SquareComponent c=squareComponents[x][y];
                    if (clickController.handleFirst(c)) {
                        c.setReversal(true);
                        c.repaint();
                        clickController.swapPlayer();
                    }
                }

            }
        }


    }

    public void judge(SquareComponent chess1, SquareComponent chess2){
        boolean canCatch = false;//chess1是否可以吃chess2
        boolean canSwap = false;//chess1移动是否为1
        if (chess1.chessComponentNum != 1) {//如果chess1不为炮

            if ((Math.abs(chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY()) +
                    Math.abs(chess1.getChessboardPoint().getX() - chess2.getChessboardPoint().getX())) == 1) {
                canSwap = true;//上下左右移动只能为1
            }
        }

        if (chess1.chessComponentNum == 0 && chess2.chessComponentNum == 6 || chess1.chessComponentNum == 0 && chess2.chessComponentNum == 0) {
            canCatch = true;
        } else if (chess1.chessComponentNum > 1 && chess2.chessComponentNum <= chess1.chessComponentNum) {
            canCatch=true;
        }


        //chess1为炮时
        if (chess1.chessComponentNum == 1) {
            canSwap = false;
            int num = 0;
            if (chess1.getChessboardPoint().getX() == chess2.getChessboardPoint().getX()) {
                if (chess1.getChessboardPoint().getY() > chess2.getChessboardPoint().getY()) {
                    for (int i = chess2.getChessboardPoint().getY(); i < chess1.getChessboardPoint().getY(); i++) {
                        if (squareComponents[chess1.getChessboardPoint().getX()][i] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess1.getChessboardPoint().getY() - chess2.getChessboardPoint().getY() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                } else if (chess1.getChessboardPoint().getY() < chess2.getChessboardPoint().getY()) {
                    for (int i = chess1.getChessboardPoint().getY(); i < chess2.getChessboardPoint().getY(); i++) {
                        if (squareComponents[chess1.getChessboardPoint().getX()][i] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess2.getChessboardPoint().getY() - chess1.getChessboardPoint().getY() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                }
            } else if (chess1.getChessboardPoint().getY() == chess2.getChessboardPoint().getY()) {
                if (chess1.getChessboardPoint().getX() > chess2.getChessboardPoint().getX()) {
                    for (int i = chess2.getChessboardPoint().getX(); i < chess1.getChessboardPoint().getX(); i++) {
                        if (squareComponents[i][chess1.getChessboardPoint().getY()] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess1.getChessboardPoint().getX() - chess2.getChessboardPoint().getX() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                } else if (chess1.getChessboardPoint().getX() < chess2.getChessboardPoint().getX()) {
                    for (int i = chess1.getChessboardPoint().getX(); i < chess2.getChessboardPoint().getX(); i++) {
                        if (squareComponents[i][chess1.getChessboardPoint().getY()] instanceof EmptySlotComponent) {
                            num++;
                        }
                    }
                    if (chess2.getChessboardPoint().getX() - chess1.getChessboardPoint().getX() - num - 1 == 1) {
                        canSwap = true;
                        num = 0;
                    }
                }
            }
        }

        if (!canCatch||!canSwap){
            JOptionPane.showMessageDialog(this, "error4(行棋步骤错误)");
            con=1;
        }
    }

    //执行悔棋
    public void Undo(){
        String s=GameController.step.get(GameController.step.size()-1);
        //如果上一步是翻子
        if (s.length()==3){
            int x=Integer.parseInt(s.substring(0,1));
            int y=Integer.parseInt(s.substring(1,2));
            SquareComponent chess=squareComponents[x][y];
            chess.setReversal(false);
            chess.repaint();
            clickController.swapPlayer();
        }
        //如果上一步是行棋
        else if (s.length()==5) {
            int x1=Integer.parseInt(s.substring(0,1));
            int y1=Integer.parseInt(s.substring(1,2));
            int x2=Integer.parseInt(s.substring(2,3));
            int y2=Integer.parseInt(s.substring(3,4));
            SquareComponent chess1=squareComponents[x2][y2];
            SquareComponent chess2=squareComponents[x1][y1];
            chess1.swapLocation(chess2);
            remove(chess2);
            add(chess2=removedChess.get(removedChess.size()-1));

            if(chess2.getChessColor ().equals (ChessColor.RED)){
                BlackScore-=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
            }else if(chess2.getChessColor ().equals (ChessColor.BLACK)){
                RedScore-=chess2.chessComponentScore;
                ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
            }
            clickController.getScore ();
            int row1 = chess1.getChessboardPoint().getX();
            int col1 = chess1.getChessboardPoint().getY();
            squareComponents[row1][col1] = chess1;
            int row2 = chess2.getChessboardPoint().getX();
            int col2 = chess2.getChessboardPoint().getY();
            squareComponents[row2][col2] = chess2;
            //只重新绘制chess1 chess2，其他不变
            chess1.repaint();
            chess2.repaint();
            clickController.swapPlayer();
            clickController.undoChessAmount(chess2);
            removedChess.remove(removedChess.size()-1);
        }
        GameController.step.remove(GameController.step.size()-1);
    }
}