package controller;


import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


import static chessComponent.SquareComponent.spacingLength;
import static view.ChessGameFrame.gameController;
import static view.ChessGameFrame.statusLabel1;
import static view.Chessboard.*;

public class ClickController {
    private final Chessboard chessboard;
    private SquareComponent first;
    public static int mode=0;
    public static int redGeneralAmount=0,redAdvisorAmount=0,redMinisterAmount=0,redChariotAmount=0,redCannonAmount=0,
            redHorseAmount=0,redSoldierAmount=0;
    public static int blackGeneralAmount=0,blackAdvisorAmount=0,blackMinisterAmount=0,blackChariotAmount=0,blackCannonAmount=0,
            blackHorseAmount=0,blackSoldierAmount=0;



    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (mode==0){
            if (first == null|| first instanceof EmptySlotComponent) {
                if (handleFirst(squareComponent)) {
                    squareComponent.setSelected(true);
                    first = squareComponent;
                    first.repaint();
//                    for(int i=0;i<8;i++){
//                        for(int j=0;j<4;j++){
//                            if(squareComponent.chessComponentNum!=1){
//                                boolean canCatch = false;
//                                if(squareComponent.chessComponentNum == 0&&squareComponents[i][j].chessComponentNum==6 ||squareComponent.chessComponentNum ==0&&squareComponents[i][j].chessComponentNum==0){
//                                    canCatch = true;
//                                }else if(squareComponent.chessComponentNum>1&&squareComponents[i][j].chessComponentNum<=squareComponent.chessComponentNum){
//                                    canCatch=true;
//                                }
//                                boolean canSwap = false;
//                                if(Math.abs (squareComponent.getChessboardPoint ().getY ()-squareComponents[i][j].getChessboardPoint ().getY ())+Math.abs (squareComponent.getChessboardPoint ().getX ()-squareComponents[i][j].getChessboardPoint ().getX ())==1){
//                                    canSwap = true;
//                                }
//                                boolean canColor = false;
//                                if(squareComponent.getChessColor ()!=squareComponents[i][j].getChessColor ()){
//                                    canColor=true;
//                                }
//                                if(canSwap&&canCatch&&canColor){
//                                    squareComponents[i][j].setCanRun (true);
//                                    squareComponents[i][j].repaint ();
//                                }
//                            }else{
//                                boolean canColor=false;
//                                if(canCannonSwap (squareComponents[i][j])&&!(squareComponents[i][j] instanceof EmptySlotComponent)){
//                                    if(squareComponents[i][j].isReversal ()&&squareComponent.getChessColor ()!=squareComponents[i][j].getChessColor ()){
//                                        canColor=true;
//                                    }else if(!squareComponents[i][j].isReversal ()){
//                                        canColor=true;
//                                    }
//                                   if(canColor){
//                                       squareComponents[i][j].setCanRun (true);
//                                       squareComponents[i][j].repaint ();
//                                   }
//                                }
//                            }
//                        }
//                    }
                }
            } else {
                if (first == squareComponent) { // 再次点击取消选取
                    squareComponent.setSelected(false);
                    SquareComponent recordFirst = first;
                    first = null;
                    recordFirst.repaint();
//                    for(int i=0;i<8;i++){
//                        for(int j=0;j<4;j++){
//                            if(squareComponents[i][j].isCanRun ()){
//                                squareComponents[i][j].setCanRun (false);
//                                SquareComponent squareComponent1=squareComponents[i][j];
//                                squareComponents[i][j]=null;
//                                squareComponent1.repaint ();
//                            }
//                        }
//                    }
                } else if (handleSecond(squareComponent)) {
                    //repaint in swap chess method.
                    if(first.chessComponentNum!=1){
                        boolean canCatch = false;
                        if(first.chessComponentNum == 0&&squareComponent.chessComponentNum==6 ||first.chessComponentNum ==0&&squareComponent.chessComponentNum==0){
                            canCatch = true;
                        }else if(first.chessComponentNum>1&&squareComponent.chessComponentNum<=first.chessComponentNum){
                            canCatch=true;
                        }
                        boolean canSwap = false;
                        if(Math.abs (first.getChessboardPoint ().getY ()-squareComponent.getChessboardPoint ().getY ())+Math.abs (first.getChessboardPoint ().getX ()-squareComponent.getChessboardPoint ().getX ())==1){
                            canSwap = true;
                        }
                        if(canSwap&&canCatch){
//                        if(squareComponent.getChessColor ().equals (ChessColor.RED)){
//                            BlackScore+=squareComponent.chessComponentScore;
//                            ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
//                        }else if(squareComponent.getChessColor ().equals (ChessColor.BLACK)){
//                            RedScore+=squareComponent.chessComponentScore;
//                            ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
//                        }
                            chessboard.swapChessComponents(first, squareComponent);
                            chessboard.clickController.swapPlayer();

//                        chessboard.clickController.getScore ();
                            first.setSelected(false);
                            first = null;
                        }
                    }else{
                        if(canCannonSwap (squareComponent)&&!(squareComponent instanceof EmptySlotComponent)){
//                        if(squareComponent.getChessColor ().equals (ChessColor.RED)){
//                            BlackScore+=squareComponent.chessComponentScore;
//                            ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
//                        }else if(squareComponent.getChessColor ().equals (ChessColor.BLACK)){
//                            RedScore+=squareComponent.chessComponentScore;
//                            ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
//                        }
                            chessboard.swapChessComponents(first, squareComponent);
                            chessboard.clickController.swapPlayer();
//                        chessboard.clickController.getScore ();
                            first.setSelected(false);
                            first = null;
                        }
                    }
                }
            }
        } else if (mode==1) {
            if (!squareComponent.isReversal()){
                squareComponent.setReversal(true);
                squareComponent.repaint();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        squareComponent.setReversal(false);
                        squareComponent.repaint();
                    }
                },1500);
            }
        }

    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    //private->public
    public boolean handleFirst(SquareComponent squareComponent) {
        if (!squareComponent.isReversal()&&(!(squareComponent instanceof EmptySlotComponent))) {
            squareComponent.setReversal(true);
            System.out.printf("onClick to reverse a chess [%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            if(statusLabel1.getText ().startsWith ("Red")){
                GameController.step.add(String.format("%d%d0",squareComponent.getChessboardPoint().getX(),squareComponent.getChessboardPoint().getY()));
            }else GameController.step.add(String.format("%d%d1",squareComponent.getChessboardPoint().getX(),squareComponent.getChessboardPoint().getY()));

//            GameController.chess[squareComponent.getChessboardPoint().getX()][squareComponent.getChessboardPoint().getY()]+=100;
//            GameController.GameFile.add("onClick to reverse a chess ["+squareComponent.getChessboardPoint().getX()+","+squareComponent.getChessboardPoint().getY()+"]\n");
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param squareComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(SquareComponent squareComponent) {

        //没翻开或空棋子，进入if
        if (!squareComponent.isReversal()&&first.chessComponentNum!=1) {
            //没翻开且非空棋子不能走
            if (!(squareComponent instanceof EmptySlotComponent)) {
                return false;
            }
        }
        return squareComponent.getChessColor() != chessboard.getCurrentColor() && first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint())
                ||first.chessComponentNum==1&&!squareComponent.isReversal ()&&first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
    }

    public void swapPlayer() {
        if(RedScore<60&&BlackScore<60){
            chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
            ChessGameFrame.getStatusLabel1().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
        }else if(RedScore>=60&&BlackScore<60){
            ChessGameFrame.getStatusLabel1().setText(String.format("Red Wins!"));
            int result=JOptionPane.showConfirmDialog(null,"Red Wins!");
            if(result==0){
                gameController.Restart ();
                ChessGameFrame.getStatusLabel1 ().setText ("Black's TURN");
            }
        }else if(RedScore<60){
            ChessGameFrame.getStatusLabel1().setText(String.format("Black Wins!"));
            int result=JOptionPane.showConfirmDialog(null,"Black Wins!");
            if(result==0){
                gameController.Restart ();
                ChessGameFrame.getStatusLabel1 ().setText ("Black's TURN");
            }
        }
    }

    public boolean canCannonSwap(SquareComponent squareComponent){
            boolean canSwap=false;
            int num = 0;
            if(first.getChessboardPoint ().getX ()==squareComponent.getChessboardPoint ().getX ()){
                if(first.getChessboardPoint ().getY ()>squareComponent.getChessboardPoint ().getY ()){
                    for(int i = squareComponent.getChessboardPoint ().getY ();i<first.getChessboardPoint ().getY ();i++){
                        if(chessboard.squareComponents[first.getChessboardPoint ().getX ()][i] instanceof EmptySlotComponent){
                            num++;
                        }
                    }
                    if(first.getChessboardPoint ().getY ()-squareComponent.getChessboardPoint ().getY ()-num-1==1){
                        canSwap = true;
                    }
                }else if(first.getChessboardPoint ().getY ()<squareComponent.getChessboardPoint ().getY ()){
                    for(int i = first.getChessboardPoint ().getY ();i<squareComponent.getChessboardPoint ().getY ();i++){
                        if(chessboard.squareComponents[first.getChessboardPoint ().getX ()][i] instanceof EmptySlotComponent){
                            num++;
                        }
                    }
                    if(squareComponent.getChessboardPoint ().getY ()-first.getChessboardPoint ().getY ()-num-1==1){
                        canSwap = true;
                    }
                }
            }else if(first.getChessboardPoint ().getY ()==squareComponent.getChessboardPoint ().getY ()){
                if(first.getChessboardPoint ().getX ()>squareComponent.getChessboardPoint ().getX ()){
                    for(int i = squareComponent.getChessboardPoint ().getX ();i<first.getChessboardPoint ().getX ();i++){
                        if(chessboard.squareComponents[i][first.getChessboardPoint ().getY ()] instanceof EmptySlotComponent){
                            num++;
                        }
                    }
                    if(first.getChessboardPoint ().getX ()-squareComponent.getChessboardPoint ().getX()-num-1==1){
                        canSwap = true;
                    }
                }else if(first.getChessboardPoint ().getX ()<squareComponent.getChessboardPoint ().getX ()){
                    for(int i = first.getChessboardPoint ().getX ();i<squareComponent.getChessboardPoint ().getX ();i++){
                        if(chessboard.squareComponents[i][first.getChessboardPoint ().getY ()] instanceof EmptySlotComponent){
                            num++;
                        }
                    }
                    if(squareComponent.getChessboardPoint ().getX ()-first.getChessboardPoint ().getX ()-num-1==1){
                        canSwap = true;
                    }
                }
            }
            return canSwap;
    }
    public void getScore(){
        if(chessboard.getCurrentColor ().equals (ChessColor.RED)){
            ChessGameFrame.getStatusLabelRed().setText(String.format("Red: %d", RedScore));
        }else if(chessboard.getCurrentColor ().equals (ChessColor.BLACK)){
            ChessGameFrame.getStatusLabelBlack ().setText (String.format ("Black: %d",BlackScore));
        }
    }
    //被吃棋子数量
    public void getChessAmount(SquareComponent chess){
        if (chess.chessComponentNum==0){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redSoldierAmount+=1;
                ChessGameFrame.getStatusLabelRedSoldier().setText(String.valueOf(redSoldierAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackSoldierAmount+=1;
                ChessGameFrame.getStatusLabelBlackSoldier().setText(String.valueOf(blackSoldierAmount));
            }
        }else if (chess.chessComponentNum==1){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redCannonAmount+=1;
                ChessGameFrame.getStatusLabelRedCannon().setText(String.valueOf(redCannonAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackCannonAmount+=1;
                ChessGameFrame.getStatusLabelBlackCannon().setText(String.valueOf(blackCannonAmount));
            }
        }else if (chess.chessComponentNum==2){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redHorseAmount+=1;
                ChessGameFrame.getStatusLabelRedHorse().setText(String.valueOf(redHorseAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackHorseAmount+=1;
                ChessGameFrame.getStatusLabelBlackHorse().setText(String.valueOf(blackHorseAmount));
            }
        }else if (chess.chessComponentNum==3){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redChariotAmount+=1;
                ChessGameFrame.getStatusLabelRedChariot().setText(String.valueOf(redChariotAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackChariotAmount+=1;
                ChessGameFrame.getStatusLabelBlackChariot().setText(String.valueOf(blackChariotAmount));
            }
        }else if (chess.chessComponentNum==4){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redMinisterAmount+=1;
                ChessGameFrame.getStatusLabelRedMinister().setText(String.valueOf(redMinisterAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackMinisterAmount+=1;
                ChessGameFrame.getStatusLabelBlackMinister().setText(String.valueOf(blackMinisterAmount));
            }
        }else if (chess.chessComponentNum==5){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redAdvisorAmount+=1;
                ChessGameFrame.getStatusLabelRedAdvisor().setText(String.valueOf(redAdvisorAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackAdvisorAmount+=1;
                ChessGameFrame.getStatusLabelBlackAdvisor().setText(String.valueOf(blackAdvisorAmount));
            }
        }else if (chess.chessComponentNum==6){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redGeneralAmount+=1;
                ChessGameFrame.getStatusLabelRedGeneral().setText(String.valueOf(redGeneralAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackGeneralAmount+=1;
                ChessGameFrame.getStatusLabelBlackGeneral().setText(String.valueOf(blackGeneralAmount));
            }
        }
    }
//悔棋时被吃棋子数量减少
    public void undoChessAmount(SquareComponent chess){
        if (chess.chessComponentNum==0){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redSoldierAmount-=1;
                ChessGameFrame.getStatusLabelRedSoldier().setText(String.valueOf(redSoldierAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackSoldierAmount-=1;
                ChessGameFrame.getStatusLabelBlackSoldier().setText(String.valueOf(blackSoldierAmount));
            }
        }else if (chess.chessComponentNum==1){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redCannonAmount-=1;
                ChessGameFrame.getStatusLabelRedCannon().setText(String.valueOf(redCannonAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackCannonAmount-=1;
                ChessGameFrame.getStatusLabelBlackCannon().setText(String.valueOf(blackCannonAmount));
            }
        }else if (chess.chessComponentNum==2){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redHorseAmount-=1;
                ChessGameFrame.getStatusLabelRedHorse().setText(String.valueOf(redHorseAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackHorseAmount-=1;
                ChessGameFrame.getStatusLabelBlackHorse().setText(String.valueOf(blackHorseAmount));
            }
        }else if (chess.chessComponentNum==3){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redChariotAmount-=1;
                ChessGameFrame.getStatusLabelRedChariot().setText(String.valueOf(redChariotAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackChariotAmount-=1;
                ChessGameFrame.getStatusLabelBlackChariot().setText(String.valueOf(blackChariotAmount));
            }
        }else if (chess.chessComponentNum==4){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redMinisterAmount-=1;
                ChessGameFrame.getStatusLabelRedMinister().setText(String.valueOf(redMinisterAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackMinisterAmount-=1;
                ChessGameFrame.getStatusLabelBlackMinister().setText(String.valueOf(blackMinisterAmount));
            }
        }else if (chess.chessComponentNum==5){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redAdvisorAmount-=1;
                ChessGameFrame.getStatusLabelRedAdvisor().setText(String.valueOf(redAdvisorAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackAdvisorAmount-=1;
                ChessGameFrame.getStatusLabelBlackAdvisor().setText(String.valueOf(blackAdvisorAmount));
            }
        }else if (chess.chessComponentNum==6){
            if (chess.getChessColor().equals (ChessColor.RED)){
                redGeneralAmount-=1;
                ChessGameFrame.getStatusLabelRedGeneral().setText(String.valueOf(redGeneralAmount));
            }else if (chess.getChessColor().equals (ChessColor.BLACK)){
                blackGeneralAmount-=1;
                ChessGameFrame.getStatusLabelBlackGeneral().setText(String.valueOf(blackGeneralAmount));
            }
        }
    }

}
