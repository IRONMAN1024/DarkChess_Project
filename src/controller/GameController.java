package controller;

import chessComponent.ChessComponent;
import chessComponent.SquareComponent;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static view.ChessGameFrame.statusLabel1;
import static view.Chessboard.BlackScore;
import static view.Chessboard.RedScore;

/**
 * 这个类主要完成由窗体上组件触发的动作。
 * 例如点击button等
 * ChessGameFrame中组件调用本类的对象，在本类中的方法里完成逻辑运算，将运算的结果传递至chessboard中绘制
 */
public class GameController {
    private Chessboard chessboard;
    public static ArrayList<String> GameFile=new ArrayList<>();
    public static int[][] chess=new int[8][4];
    public static ArrayList<String> step=new ArrayList<>();
//    public static File f=new File ("src/GameFile.txt");
//    public static BufferedWriter writer;
//
//    static {
//        try {
//            writer = new BufferedWriter(new FileWriter("src/GameFile.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));//读取路径对应的txt文档
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "错误编码:100");
        }
        return null;
    }

    public List<String> saveGameToFile(String path) throws IOException {
        String filePath= String.valueOf(Path.of(path));//读取路径对应的txt文档
        File f=new File(filePath);
//        BufferedWriter writer=new BufferedWriter(new FileWriter(filePath));

//        List<String> chessData = Files.readAllLines(Path.of(path));//读取路径对应的txt文档
//        File f=new File (chessData.toString());
//        BufferedWriter writer=new BufferedWriter(new FileWriter(chessData.toString()));

//        for(int i = 0; i< GameController.chess.length; i++){
//            for(int j=0;j<GameController.chess[i].length;j++){
//                writer.write(GameController.chess[i][j]);//注意不能写成(grades[i,j])
//            }
//        }
//        writer.write(String.valueOf(GameController.GameFile));
//        writer.close();

//        File f=new File ("src/Test.txt");
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        PrintStream printStream = new PrintStream (fileOutputStream);
        System.setOut(printStream);
        for(int i = 0; i< GameController.chess.length; i++){
            for(int j=0;j<GameController.chess[i].length;j++){
                System.out.print(String.format("%02d ",GameController.chess[i][j]));
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < GameController.step.size(); i++) {
            System.out.println(GameController.step.get(i));
        }
        return null;
    }

    //判断悔棋时是否为第一步
    public void JudgeUndo(){
        if (step.size()==0){
            JOptionPane.showMessageDialog(null, "这已经是第一步了哦！");
        }else chessboard.Undo();
    }

    public void Restart(){
        step.clear();
        chessboard.initAllChessOnBoard();
        for (int i = 0; i < chessboard.squareComponents.length; i++) {
            for (int j = 0; j < chessboard.squareComponents[i].length; j++) {
                SquareComponent chess = chessboard.squareComponents[i][j];
                chess.repaint();
            }
        }

        Chessboard.RedScore=0;
        ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
        Chessboard.BlackScore=0;
        ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));


        ClickController.redSoldierAmount=0;
        ChessGameFrame.getStatusLabelRedSoldier().setText(String.valueOf(ClickController.redSoldierAmount));
        ClickController.blackSoldierAmount=0;
        ChessGameFrame.getStatusLabelBlackSoldier().setText(String.valueOf(ClickController.blackSoldierAmount));
        ClickController.redCannonAmount=0;
        ChessGameFrame.getStatusLabelRedCannon().setText(String.valueOf(ClickController.redCannonAmount));
        ClickController.blackCannonAmount=0;
        ChessGameFrame.getStatusLabelBlackCannon().setText(String.valueOf(ClickController.blackCannonAmount));
        ClickController.redHorseAmount=0;
        ChessGameFrame.getStatusLabelRedHorse().setText(String.valueOf(ClickController.redHorseAmount));
        ClickController.blackHorseAmount=0;
        ChessGameFrame.getStatusLabelBlackHorse().setText(String.valueOf(ClickController.blackHorseAmount));
        ClickController.redChariotAmount=0;
        ChessGameFrame.getStatusLabelRedChariot().setText(String.valueOf(ClickController.redChariotAmount));
        ClickController.blackChariotAmount=0;
        ChessGameFrame.getStatusLabelBlackChariot().setText(String.valueOf(ClickController.blackChariotAmount));
        ClickController.redMinisterAmount=0;
        ChessGameFrame.getStatusLabelRedMinister().setText(String.valueOf(ClickController.redMinisterAmount));
        ClickController.blackMinisterAmount=0;
        ChessGameFrame.getStatusLabelBlackMinister().setText(String.valueOf(ClickController.blackMinisterAmount));
        ClickController.redAdvisorAmount=0;
        ChessGameFrame.getStatusLabelRedAdvisor().setText(String.valueOf(ClickController.redAdvisorAmount));
        ClickController.blackAdvisorAmount=0;
        ChessGameFrame.getStatusLabelBlackAdvisor().setText(String.valueOf(ClickController.blackAdvisorAmount));
        ClickController.redGeneralAmount=0;
        ChessGameFrame.getStatusLabelRedGeneral().setText(String.valueOf(ClickController.redGeneralAmount));
        ClickController.blackGeneralAmount=0;
        ChessGameFrame.getStatusLabelBlackGeneral().setText(String.valueOf(ClickController.blackGeneralAmount));
    }
    public void drawGame(){
        if(step.size ()==0){
            JOptionPane.showMessageDialog(null, "游戏还没开始不能和棋");
        }else{
            statusLabel1.setText ("Chess Draw!");
            Restart ();
            statusLabel1.setText ("Black's TURN");
        }
    }
}
