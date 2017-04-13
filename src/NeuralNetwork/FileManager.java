package NeuralNetwork;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by user 1 on 3/31/2017.
 */
public class FileManager {
    ObjectMapper mapper;
    String filePath;

    public FileManager(String filePath){
        this.mapper = new ObjectMapper();
        this.filePath = filePath;
    }

    public void saveNetwork(NeuralNetwork network){
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), network);
        }
        catch (Exception e){
            System.out.println("failed to parse and save string. Exception: " + e.getMessage());
        }
    }

    public NeuralNetwork loadNetwork(){
        NeuralNetwork temp = null;
        try {
            temp = mapper.readValue(new File(filePath), NeuralNetwork.class);
        }
        catch (Exception e){
            System.out.println("failed to parse and load string. Exception: " + e.getMessage());
        }
        return temp;
    }

    public List<double[][]> readData(){
        String line = null;
        List<double[]> inputData = new ArrayList();
        List<double[]> outputData = new ArrayList();
        List<double[][]> returnVal = new ArrayList();
        File file = new File(this.filePath);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){
                if (isValidData(line)) {
                    List<double[]> temp = readDataHelper(line);
                    inputData.add(temp.get(0));
                    outputData.add(temp.get(1));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        double[][] tempInput = new double [inputData.size()][9];
        double[][] tempOutput = new double [outputData.size()][9];
        tempInput = inputData.toArray(tempInput);
        tempOutput = outputData.toArray(tempOutput);
        returnVal.add(tempInput);
        returnVal.add(tempOutput);
        return returnVal;
    }

    private List<double[]> readDataHelper(String line){
        String[] seperated = line.split(", ");
        double[] tempInput = new double[9];
        double[] tempOutput = new double[9];
        List<double[]> returnVal = new ArrayList();
        for (int i = 0; i < seperated.length; i++){
            if (i < 9){
                tempInput[i] = (double) Integer.parseInt(seperated[i]);
            }
            else {
                tempOutput[i - 9] = (double) Integer.parseInt(seperated[i]);
            }
        }
        returnVal.add(tempInput);
        returnVal.add(tempOutput);
        return returnVal;
    }

    private boolean isValidData(String line){
        String[] seperated = line.split(", ");
        if (seperated.length == 18){
            return true;
        }
        return false;
    }
}



