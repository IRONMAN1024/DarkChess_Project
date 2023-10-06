package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红炮
 */
public class CannonChessComponent extends ChessComponent {

    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "炮";
        } else {
            name = "砲";
        }
        chessComponentNum = 1;
        chessComponentScore = 5;
    }
    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        return !(destinationChess instanceof EmptySlotComponent);
    }
}