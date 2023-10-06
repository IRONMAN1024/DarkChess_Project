import controller.MusicController;
import view.ChessGameFrame;
import view.InitialGameInterface;

import javax.swing.*;

import java.io.IOException;

import static controller.MusicController.music;

public class Main {

    public static void main(String[] args) throws IOException {
        InitialGameInterface initialGameInterface=new InitialGameInterface ();
        while(music==0){
            music=1;
            String path = "D://桌面/java/DarkChess/music.wav";
            MusicController.AudioPlay2 music = new MusicController.AudioPlay2(path);
            music.run = true;
            music.start();
        }

    }
}
