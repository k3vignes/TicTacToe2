package Controller;

import View.Board;

import java.util.Scanner;

/**
 * Created by user 1 on 3/16/2017.
 */
public class Controller {
    public Board b;
    char conceptBoard [][];
    public int playerMove;

    public Controller(){
        b = new Board();
        conceptBoard = new char[3][3];
        playerMove = 1;
    }

    public void makeMove(){
        promptMakeMove();
        Scanner sc = new Scanner(System.in);
        promptEnterCol();
        int col = sc.nextInt();
        promptEnterRow();
        int row = sc.nextInt();

        // if move was invalid, will loop until valid one is made
        while (!isValidMove(col, row)){
            promptWrongMove();
            promptMakeMove();
            promptEnterCol();
            col = sc.nextInt();
            promptEnterRow();
            row = sc.nextInt();
        }

        if (playerMove % 2 == 1){
            conceptBoard[row][col] = 'X';
        }
        else {
            conceptBoard[row][col] = 'O';
        }
        mapMoveToVisualBoard(row, col);
        b.printBoard();
    }

    public boolean isGameOver(){
        boolean boardFull = isBoardFull();
        boolean xWon = isXWinner();
        boolean oWon = isOWinner();
        if (boardFull || xWon  || oWon){
            if(boardFull){
                System.out.println("Game over tie game");
            }
            else if (xWon){
                System.out.println("Player X is the Winner!!!");
            }
            else if (oWon){
                System.out.println("Player O is the Winner!!!");
            }
            return true;

        }
        return false;
    }

    private void promptMakeMove(){
        System.out.println("Make a move... ");
    }

    private void promptWrongMove(){
        System.out.println("Invalid move. Make another move...");
    }

    private void promptEnterRow() {
        System.out.println("Enter row number...");
    }

    private void promptEnterCol() {
        System.out.println("Enter column number...");
    }

    private boolean isValidMove(int col, int row){
        if (conceptBoard[row][col] == 0){
            return true;
        }
        else{
            return false;
        }
    }

    private void mapMoveToVisualBoard(int row, int col){
        if (playerMove % 2 == 1){
            b.board[row * 2 + 1][col * 4 + 1] = 'X';
        }
        else{
            b.board[row * 2 + 1 ][col * 4 + 1] = 'O';
        }

    }

    private boolean isBoardFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (conceptBoard[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

     private boolean isXWinner(){
        if (conceptBoard[0][0] == conceptBoard[0][1] &&
                conceptBoard[0][1] == conceptBoard[0][2] &&
                conceptBoard[0][2] == 'X'){
            return true;
        }
        else if (conceptBoard[1][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[1][2] &&
                conceptBoard[1][2] == 'X'){
            return true;
        }
        else if (conceptBoard[2][0] == conceptBoard[2][1] &&
                conceptBoard[2][1] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'X'){
            return true;
        }
        else if (conceptBoard[0][0] == conceptBoard[1][0] &&
                conceptBoard[1][0] == conceptBoard[2][0] &&
                conceptBoard[2][0] == 'X'){
            return true;
        }
        else if (conceptBoard[0][1] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[2][1] &&
                conceptBoard[2][1] == 'X'){
            return true;
        }
        else if (conceptBoard[0][2] == conceptBoard[1][2] &&
                conceptBoard[1][2] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'X'){
            return true;
        }
        else if (conceptBoard[0][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'X'){
            return true;
        }
        else if (conceptBoard[2][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[0][2] &&
                conceptBoard[0][2] == 'X'){
            return true;
        }
        return false;

    }

    private boolean isOWinner(){
        if (conceptBoard[0][0] == conceptBoard[0][1] &&
                conceptBoard[0][1] == conceptBoard[0][2] &&
                conceptBoard[0][2] == 'O'){
            return true;
        }
        else if (conceptBoard[1][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[1][2] &&
                conceptBoard[1][2] == 'O'){
            return true;
        }
        else if (conceptBoard[2][0] == conceptBoard[2][1] &&
                conceptBoard[2][1] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'O'){
            return true;
        }
        else if (conceptBoard[0][0] == conceptBoard[1][0] &&
                conceptBoard[1][0] == conceptBoard[2][0] &&
                conceptBoard[2][0] == 'O'){
            return true;
        }
        else if (conceptBoard[0][1] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[2][1] &&
                conceptBoard[2][1] == 'O'){
            return true;
        }
        else if (conceptBoard[0][2] == conceptBoard[1][2] &&
                conceptBoard[1][2] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'O'){
            return true;
        }
        else if (conceptBoard[0][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[2][2] &&
                conceptBoard[2][2] == 'O'){
            return true;
        }
        else if (conceptBoard[2][0] == conceptBoard[1][1] &&
                conceptBoard[1][1] == conceptBoard[0][2] &&
                conceptBoard[0][2] == 'O'){
            return true;
        }
        return false;

    }

}
