import NeuralNet.NeuralNet;
import View.Board;
import Controller.Controller;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.Buffer;

public class Main {

//    public static void main(String[] args) {
//        Controller c = new Controller();
//        try{
//            c.b.loadBoard();
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        c.b.printBoard();
//        while (!c.isGameOver()){
//            c.makeMove();
//            c.playerMove++;
//        }
//    }

    public static void main(String[] args) {
        NeuralNet n = new NeuralNet();
        n.createPractiseNetwork();
        n.enterInput();
        System.out.println("calculated network: " + n.calculateNetwork());
        System.out.println("calculated node deltas: " + n.calculateNodeDeltas());

    }


}
