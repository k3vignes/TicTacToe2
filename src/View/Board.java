package View;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Created by user 1 on 3/16/2017.
 */
public class Board {
    public char board [][];
    String path;

    public Board(){
        board = new char[6][15];
        path = "C:/Users/user 1/IdeaProjects/TicTacToe/resources/board.txt";
    }

    public void loadBoard() throws IOException{
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        int counter = 0;
        String line;
        while ((line = textReader.readLine()) != null){

            for (int i = 0; i < line.length(); i++){
                board[counter][i] = line.charAt(i);
            }
            counter++;
        }
        textReader.close();
    }

    public void printBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }


}
