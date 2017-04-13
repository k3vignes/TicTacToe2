import NeuralNetwork.FileManager;
import NeuralNetwork.NeuralNetwork;
import View.Board;
import Controller.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.List;

import Controller.IdealOpponent;

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

//    public static void main(String[] args) {
//        int [] numNeuron = {3, 9, 3};
//        double inputData[][] = {{0,0, 1}, {0,1,1}, {1,0,1}, {1,1,1}};
//        double outputData[][] = {{0, 0, 0}, {0, 0, 0 }, {1, 0, 0}, {1, 0, 0}};
//        NeuralNetwork n = new NeuralNetwork(3, numNeuron, 0.3, 0.3);
//        String fp1 = "C:\\Users\\user 1\\IdeaProjects\\TicTacToe\\network.json";
//        //String fp2 = "C:\\Users\\user 1\\IdeaProjects\\TicTacToe\\copyNetwork.json";
//        FileManager f1 = new FileManager(fp1);
//        //FileManager f2 = new FileManager(fp2);
//        n.trainData(inputData, outputData);
//        n.printLayers();
//        f1.saveNetwork(n);
//        NeuralNetwork temp = f1.loadNetwork();
//        //f2.saveNetwork(temp);
//    }


//    public static void main(String[] args){
//        int [] numNeuron = {9, 9, 9, 9};
//        // 1 = X, -1 = O, and 0 = empty
//        // Computer is always X - moves first
//        double inputData[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {1, -1, 0, 0, 0, 0, 0, 0, 0},   //1
//                {1, 0, -1, 0, 0, 0, 0, 0, 0},
//                {1, 0, 0, -1, 0, 0, 0, 0, 0},
//                {1, 0, 0, 0, -1, 0, 0, 0, 0},
//                {1, 0, 0, 0, 0, -1, 0, 0, 0},
//                {1, 0, 0, 0, 0, 0, -1, 0, 0},
//                {1, 0, 0, 0, 0, 0, 0, -1, 0},
//                {1, 0, 0, 0, 0, 0, 0, 0, -1},
//
//                {1, -1, -1, 0, 1, 0, 0, 0, 0},   //2
//                {1, -1, 0, -1, 1, 0, 0, 0, 0},
//                {1, -1, 0, 0, 1, -1, 0, 0, 0},
//                {1, -1, 0, 0, 1, 0, -1, 0, 0},
//                {1, -1, 0, 0, 1, 0, 0, -1, 0},
//                {1, -1, 0, 0, 1, 0, 0, 0, -1},
//
//                {1, -1, -1, 0, 1, 0, 1, 0, -1},   //3
//                {1, -1, 0, -1, 1, 0, 1, 0, -1},
//                {1, -1, 0, 0, 1, -1, 1, 0, -1},
//                {1, -1, 0, 0, 1, 0, 1, -1, -1},
//
//                {1, 0, -1, -1, 0, 0, 1, 0, 0},     //4
//                {1, 0, -1, 0, 0, -1, 1, 0, 0},
//                {1, 0, -1, 0, 0, 0, 1, 0, 0},
//                {1, 0, -1, 0, 1, 0, 0, -1, 0},
//                {1, 0, -1, 0, 1, 0, 0, 0, -1},
//
//                {1, 0, -1, -1, 1, 0, 1, 0, -1},   //5
//                {1, 0, -1, 0, 1, -1, 1, 0, -1},
//                {1, 0, -1, 0, 1, 0, 1, -1, -1},
//
//
//
//                {1, 0, 0, 0, -1, -1, 0, 0, 1},    //6
//                {1, 0, 0, 0, -1, -1, 1, -1, 1}};
//        double outputData[][] = {{1, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},    //1
//                {0, 0, 0, 0, 1, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0},
//                {0, 0, 0, 0, 1, 0, 0, 0, 0},
//
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},     //2
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0},
//
//                {0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {0, 0, 1, 0, 0, 0, 0, 0, 0},      //3
//                {0, 0, 0, 1, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0},
//
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},      //4
//                {0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {0, 0, 0, 0, 0, 0, 1, 0, 0},
//
//                {0, 0, 1, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0},      //5
//                {0, 0, 0, 1, 0, 0, 0, 0, 0},
//
//                {0, 0, 0, 0, 0, 0, 1, 0, 0},
//                {0, 0, 0, 1, 0, 0, 0, 0, 0}};     //6
//        NeuralNetwork n = new NeuralNetwork(numNeuron, 0.3, 0.3);
//        String fp1 = "C:\\Users\\user 1\\IdeaProjects\\TicTacToe\\TicTacToeNetwork.json";
//        FileManager f1 = new FileManager(fp1);
//        n.trainData(inputData, outputData);
//        f1.saveNetwork(n);
//        double sampleOutput[] = n.enterInput();
//        for (int i = 0; i < n.getNumberOfOutputs(); i++){
//            System.out.println("output " + i + ": " + sampleOutput[i]);
//        }
//    }

//    public static void  main(String[] args){
//        IdealOpponent x = new IdealOpponent();
//        x.createTree();
//        x.createTrainingData();
//    }

        public static void main(String[] args){
        int [] numNeuron = {9, 4, 9};
        // 1 = X, -1 = O, and 0 = empty
        // Computer is always X - moves first
        NeuralNetwork n = new NeuralNetwork(numNeuron, 0.3, 0.05);
        String fp1 = "trainingdata.txt";
        FileManager f1 = new FileManager(fp1);
        String fp2 = "testnetwork.txt";
        FileManager f2 = new FileManager(fp2);
        System.out.println("Started Reading data...");
        long startTime = System.currentTimeMillis();
        List<double[][]> temp = f1.readData();
        double[][] inputData = temp.get(0);
        double[][] outputData = temp.get(1);
        System.out.println("Started Training...");
        n.trainData(inputData, outputData);
        f2.saveNetwork(n);
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - startTime));
        while (true) {
                double sampleOutput[] = n.enterInput();
                for (int i = 0; i < n.getNumberOfOutputs(); i++) {
                        System.out.println("output " + i + ": " + sampleOutput[i]);
                }
        }
    }



}
