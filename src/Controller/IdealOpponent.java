package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user 1 on 4/8/2017.
 */
public class IdealOpponent {
    BoardState moves;

    public IdealOpponent(){
        this.moves = new BoardState(1);
    }

    public void createTree(){
        this.moves.createTree(this.moves);
        this.moves.rankMoves(this.moves);
    }

    public void createTrainingData(){
        String trainingData = this.moves.createTrainingData(this.moves);
        System.out.println("LENGTH: " + trainingData.length());
        try{
            FileWriter fw = new FileWriter("trainingdata.txt");
            BufferedWriter  bw = new BufferedWriter(fw);
            bw.write(trainingData);
            bw.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void printTree() {
        printTreeHelper(this.moves);
    }

    private void printTreeHelper(BoardState b){
        if (b.isGameOver()){
            printNode(b);
        }
        else{
            for (int i = 0; i < b.nextMoves.size(); i++){
                printTreeHelper(b.nextMoves.get(i));
            }
        }
    }

    private void printNode(BoardState b){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(b.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("move: " + b.move);
        System.out.println("rank: " + b.rank);
        System.out.println("isWinner: " + b.isWinner);
        System.out.println("isLoser: " + b.isLoser);
        System.out.println("isDraw " + b.isDraw);
    }

    private class BoardState{
        private final int BOARD_LENGTH;
        private final int BOARD_HEIGHT;

        List<BoardState> nextMoves;
        int [][] board;
        int move;
        int rank;
        boolean isWinner;
        boolean isLoser;
        boolean isDraw;

        public BoardState(int move){
            this.BOARD_LENGTH = 3;
            this.BOARD_HEIGHT = 3;
            this.nextMoves = new ArrayList<BoardState>();
            this.board = new int[3][3];
            this.move = move;
            this.rank = 0;
            this.isWinner = false;
            this.isLoser = false;
            this.isDraw = false;
        }

        public void createTree(BoardState b){
            if (!b.isGameOver()) {
                for (int i = 0; i < this.BOARD_HEIGHT; i++) {
                    for (int j = 0; j < this.BOARD_LENGTH; j++) {
                        if (b.board[i][j] == 0) {
                            BoardState temp = new BoardState(b.move + 1);
                            temp.board = copyBoard(b.board);
                            if (b.move % 2 == 1) {
                                temp.board[i][j] = 1;
                            } else {
                                temp.board[i][j] = -1;
                            }
                            b.nextMoves.add(temp);
                            createTree(temp);
                        }
                    }
                }
            }
            else{
                b.isWinner = b.isXWinner();
                b.isLoser = b.isOWinner();
                if (!b.isWinner && !b.isLoser) {
                    b.isDraw = true;
                }
            }
        }

        public int rankMoves(BoardState b){
            if (b.isGameOver()){
                if (b.isWinner){
                    b.rank = 1;
                }
                else if (b.isLoser){
                    b.rank = -1;
                }
                else{
                    b.rank = 0;
                }
                return b.rank;
            }
            else{
                for (int i = 0; i < b.nextMoves.size(); i++){
                    b.rank += rankMoves(b.nextMoves.get(i));
                }
                return b.rank;
            }
        }

        public String createTrainingData(BoardState b){
            String training = "";
            if (!b.isGameOver()){
                if (b.move % 2 == 1) {
                    training = writeInput(b.board);
                    training += ", " + writeInput(bestMove(b));
                }
                for (int i = 0; i < b.nextMoves.size(); i++){
                    String temp = createTrainingData(b.nextMoves.get(i));
                    if (!temp.equals("")) {
                        training += "\n" + temp;
                    }
                }
            }
            return training;
        }

        //    public int bestMove(){
//        int maxRank = 0;
//        for (int i = 0; i < this.moves.nextMoves.size(); i++){
//            if (maxRank < this.moves.nextMoves.get(i).rank){
//                maxRank = this.moves.nextMoves.get(i).rank;
//            }
//        }
//
//    }

        private int[][] bestMove(BoardState b){
            int maxRank = b.nextMoves.get(0).rank;
            int maxRankIndex = 0;
            int moveIndexRow = 0;
            int moveIndexCol = 0;
            int [][] move = new int[this.BOARD_LENGTH][this.BOARD_HEIGHT];
            for (int i = 0; i < b.nextMoves.size(); i++){
                if (maxRank < b.nextMoves.get(i).rank){
                    maxRank = b.nextMoves.get(i).rank;
                    maxRankIndex = i;
                }
            }
            for (int i = 0; i < this.BOARD_LENGTH; i++){
                for (int j = 0; j < this.BOARD_HEIGHT; j++){
                    if (b.board[i][j] != b.nextMoves.get(maxRankIndex).board[i][j]){
                        moveIndexRow = i;
                        moveIndexCol = j;
                    }
                }
            }
            for (int i = 0; i < this.BOARD_LENGTH; i++){
                for (int j = 0; j < this.BOARD_HEIGHT; j++){
                    if (i == moveIndexRow && j == moveIndexCol){
                        move[i][j] = 1;
                    }
                    else{
                        move[i][j] = 0;
                    }
                }
            }
            return move;
        }

        private String writeInput(int[][] board){
            String temp = "";
            for (int i = 0; i < this.BOARD_LENGTH; i++){
                for (int j = 0; j < this.BOARD_HEIGHT; j++){
                    if (temp.equals("")){
                        temp = board[i][j] + "";
                    }
                    else{
                        temp += ", " + board[i][j];
                    }
                }
            }
            return temp;
        }


        public boolean isGameOver(){
            boolean boardFull = isBoardFull();
            boolean xWon = isXWinner();
            boolean oWon = isOWinner();
            if (boardFull || xWon  || oWon){
                return true;
            }
            return false;
        }

        private int[][] copyBoard(int[][] board){
            int temp[][] = new int[this.BOARD_LENGTH][this.BOARD_HEIGHT];
            for (int i = 0; i < this.BOARD_LENGTH; i++){
                for (int j = 0; j < this.BOARD_HEIGHT; j++){
                    temp[i][j] = board[i][j];
                }
            }
            return temp;
        }

        private boolean isBoardFull(){
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    if (this.board[i][j] == 0){
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isXWinner(){
            if (this.board[0][0] == this.board[0][1] &&
                    this.board[0][1] == this.board[0][2] &&
                    this.board[0][2] == 1){
                return true;
            }
            else if (this.board[1][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[1][2] &&
                    this.board[1][2] == 1){
                return true;
            }
            else if (this.board[2][0] == this.board[2][1] &&
                    this.board[2][1] == this.board[2][2] &&
                    this.board[2][2] == 1){
                return true;
            }
            else if (this.board[0][0] == this.board[1][0] &&
                    this.board[1][0] == this.board[2][0] &&
                    this.board[2][0] == 1){
                return true;
            }
            else if (this.board[0][1] == this.board[1][1] &&
                    this.board[1][1] == this.board[2][1] &&
                    this.board[2][1] == 1){
                return true;
            }
            else if (this.board[0][2] == this.board[1][2] &&
                    this.board[1][2] == this.board[2][2] &&
                    this.board[2][2] == 1){
                return true;
            }
            else if (this.board[0][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[2][2] &&
                    this.board[2][2] == 1){
                return true;
            }
            else if (this.board[2][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[0][2] &&
                    this.board[0][2] == 1){
                return true;
            }
            return false;

        }

        private boolean isOWinner() {
            if (this.board[0][0] == this.board[0][1] &&
                    this.board[0][1] == this.board[0][2] &&
                    this.board[0][2] == -1) {
                return true;
            } else if (this.board[1][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[1][2] &&
                    this.board[1][2] == -1) {
                return true;
            } else if (this.board[2][0] == this.board[2][1] &&
                    this.board[2][1] == this.board[2][2] &&
                    this.board[2][2] == -1) {
                return true;
            } else if (this.board[0][0] == this.board[1][0] &&
                    this.board[1][0] == this.board[2][0] &&
                    this.board[2][0] == -1) {
                return true;
            } else if (this.board[0][1] == this.board[1][1] &&
                    this.board[1][1] == this.board[2][1] &&
                    this.board[2][1] == -1) {
                return true;
            } else if (this.board[0][2] == this.board[1][2] &&
                    this.board[1][2] == this.board[2][2] &&
                    this.board[2][2] == -1) {
                return true;
            } else if (this.board[0][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[2][2] &&
                    this.board[2][2] == -1) {
                return true;
            } else if (this.board[2][0] == this.board[1][1] &&
                    this.board[1][1] == this.board[0][2] &&
                    this.board[0][2] == -1) {
                return true;
            }
            return false;
        }




    }
}
