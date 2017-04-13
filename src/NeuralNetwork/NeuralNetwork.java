package NeuralNetwork;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Scanner;

import static java.lang.StrictMath.exp;

import static java.lang.StrictMath.random;


/**
 * Created by user 1 on 3/20/2017.
 */

// figure out later what needs to public and what doesnt
public class NeuralNetwork {
    public Layer layers [];
    int numberOfInputs;
    int numberOfOutputs;
    int numberOfLayers;
    double learningRate;
    double momentum;


    // need to check numLayers == numNeuronsPerLayer.length & numLayers >= 2
    // first layer is automatically input and last is automatically output
    public NeuralNetwork(int [] numNeuronsPerLayer){
        this.numberOfLayers = numNeuronsPerLayer.length;
        this.numberOfInputs = numNeuronsPerLayer[0];
        this.numberOfOutputs = numNeuronsPerLayer[numNeuronsPerLayer.length - 1];
        this.layers = new Layer[this.numberOfLayers];
        for (int i = 0; i < this.numberOfLayers; i++){
            this.layers[i] = new Layer();
            this.layers[i].numberOfNeurons = numNeuronsPerLayer[i] + 1; // +1 cause bias neuron
            this.layers[i].neurons = new Neuron[this.layers[i].numberOfNeurons];
            for (int j = 0; j < this.layers[i].numberOfNeurons; j++){
                if (j == this.layers[i].numberOfNeurons - 1){
                    this.layers[i].neurons[j] = new BiasNeuron();
                } // add else if to check if this is last layer and if it is then don't add a bias neuron
                else {
                    this.layers[i].neurons[j] = new Neuron();
                }
            }
        }
        for (int j = 0; j < this.numberOfLayers - 1; j++ ){
            //layers[j].nextLayer = layers[j+1];
            layers[j].weights = new double[layers[j+1].numberOfNeurons][layers[j].numberOfNeurons];
            layers[j].weights = fillWithRandomValues(layers[j].weights);
            layers[j].weightDelta = new double[layers[j+1].numberOfNeurons][layers[j].numberOfNeurons];
            layers[j].weightDelta = fillWithZeros(layers[j].weightDelta);
            layers[j].gradients = new double[layers[j+1].numberOfNeurons][layers[j].numberOfNeurons];
            layers[j].gradients = fillWithZeros(layers[j].gradients);
        }
    }

    public NeuralNetwork(int [] numNeuronsPerLayer, double lr){
        this(numNeuronsPerLayer);
        this.learningRate = lr;
    }

    public NeuralNetwork(int [] numNeuronsPerLayer, double lr, double m){
        this(numNeuronsPerLayer, lr);
        this.momentum = m;
    }

    public NeuralNetwork(NeuralNetwork n){
        this.learningRate = n.learningRate;
        this.momentum = n.momentum;
        this.numberOfLayers = n.numberOfLayers;
        this.layers = new Layer[n.numberOfLayers];
        for (int i = 0; i < n.numberOfLayers; i++){
            this.layers[i] = n.layers[i];
        }
    }

    public NeuralNetwork(){}

    public void tempSetLRandM(double l, double m){
        this.learningRate = l;
        this.momentum = m;
    }

    public int getNumberOfInputs(){
        return this.numberOfInputs;
    }

    public int getNumberOfOutputs(){
        return this.numberOfOutputs;
    }

    public int getNumberOfLayers(){
        return this.numberOfLayers;
    }

    public void trainData(double [][] input, double[][] idealOutput){
        //try Layer lastLayer = this.layers[this.layers.length -1];
        for (int epoch = 0; epoch < 1000; epoch++){
            System.out.println("EPOCH: " + epoch);
            for (int i = 0; i < input.length; i++){
                //System.out.println("input: " + input[i][0] + " " + input[i][1] + " " + input[i][2] + " IdealOutput: " + idealOutput[i][0]);
                double actualOutput[] = calculateOutputOfSample(input[i]);
                double error[] = calculateError(actualOutput, idealOutput[i]);
//                System.out.println("input length: " + input.length + " input[i] lenght: " + input[i].length);
//                System.out.println();
//                for(int k = 0; k < idealOutput[i].length; k++){
//                    System.out.print(" output: " + idealOutput[i][k]);
//                }
//                System.out.println();
                calculateNodeDelta(error, idealOutput[i]);
                calculateGradients();
                //printLayers();  ///-- need to test starting at this point, and comment rest of function (below) out
                calculateWeightUpdate();
                //printLayers();
                //System.out.println("input: " + input[i][0] + " " + input[i][1] + " " + input[i][2] + " IdealOutput: " + idealOutput[i][0] + " Actual output: " + this.layers[1].neurons[0].neuronOutput);
            }
        }
    }

    public double[] enterInput(){
        double input[] = new double[this.layers[0].numberOfNeurons];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numberOfInputs; i++){
            System.out.println("Enter value: " + (i + 1) + " of " + this.numberOfInputs);
            input[i] = sc.nextDouble();
        }
        return calculateOutputOfSample(input);
    }

    private void calculateWeightUpdate(){
        for(int i = 0; i < this.layers.length - 1; i++){
            for(int j = 0; j < this.layers[i].weights.length; j++){
                for (int k = 0; k < this.layers[i].weights[j].length; k++){
                    this.layers[i].weightDelta[j][k] = this.learningRate * this.layers[i].gradients[j][k]
                            + this.momentum * this.layers[i].weightDelta[j][k];
                    this.layers[i].weights[j][k] += this.layers[i].weightDelta[j][k];
                }
            }
        }
    }

    private void calculateGradients(){
        for(int i = 0; i < this.layers.length - 1; i++){
            for (int j = 0; j < this.layers[i].weights.length; j++){
                for (int k = 0; k < this.layers[i].weights[j].length; k++){
                    this.layers[i].gradients[j][k] = this.layers[i + 1].neurons[j].getNodeDelta() * this.layers[i].neurons[k].getNeuronOutput();
                }
            }
        }
    }

    public void printLayers(){
        for (int i = 0; i < this.layers.length - 1; i++){
            for ( int j = 0; j < this.layers[i].weights.length; j++){
                for (int k = 0; k < this.layers[i].weights[j].length; k++){
                    System.out.println("Layer: " + i);
                    System.out.println("j: " + j);
                    System.out.println("k: " + k);
                    System.out.println("weights[j][k]: " + this.layers[i].weights[j][k]);
                    System.out.println("weightDelta: " + this.layers[i].weightDelta[j][k]);
                    System.out.println("gradients: " + this.layers[i].gradients[j][k]);
                }
            }
        }

    }

    public void printNeurons(){
        for(int i = 0; i < this.layers.length; i++){
            for (int j = 0; j < this.layers[i].neurons.length; j++){
                Neuron n = this.layers[i].neurons[j];
                System.out.println("layer: " + i);
                System.out.println("neuron: " + j);
                System.out.println("neuronInput: " + n.getNeuronInput());
                System.out.println("neuronOutput: " + n.getNeuronOutput());
                System.out.println("nodeDelta: " + n.getNodeDelta());
            }
        }
    }

    private double[] calculateError(double[] actualOutput, double[] idealOutput){
        double error[] = new double[idealOutput.length];
        for (int j = 0; j < error.length; j++){
            error[j] = idealOutput[j] - actualOutput[j];
        }
        return error;
    }

    private void calculateNodeDelta(double [] errors, double[] idealOutput){
        // calculates the output neurons node delta
        for (int j = 0; j < errors.length; j++){
            double tempNodeDelta = errors[j] * hypTanDerivative(this.layers[this.layers.length - 1].neurons[j].getNeuronInput());
            this.layers[this.layers.length - 1].neurons[j].setNodeDelta(tempNodeDelta);
        }
        // work backwards to propgate the node delta back
        for (int i = this.layers.length - 2; i >= 0; i--){
            double sumDeltaTimesWeight [] = new double[this.layers[i].neurons.length];
            for (int j = 0; j < this.layers[i].weights.length; j++){
                for (int k = 0; k < this.layers[i].weights[j].length; k++){
                    sumDeltaTimesWeight[k] += this.layers[i].weights[j][k] * this.layers[i+1].neurons[j].getNodeDelta();
                }
            }
            // after getting the Summation(weight * node delta) we need to multiply by derivative
            for(int x = 0; x < sumDeltaTimesWeight.length; x++){
                double tempDelta = sumDeltaTimesWeight[x] * hypTanDerivative(this.layers[i].neurons[x].getNeuronInput());
                this.layers[i].neurons[x].setNodeDelta(tempDelta);
            }
        }
    }

    private void calculateGradients(double[] errors){
        int indexSecondLastLayer = this.layers.length - 2;
        for (int i = 0; i < errors.length; i++){
            for (int j = 0; j < this.layers[indexSecondLastLayer].numberOfNeurons; j++) {
                this.layers[indexSecondLastLayer].gradients[i][j] = this.layers[indexSecondLastLayer + 1].neurons[i].getNodeDelta() * this.layers[indexSecondLastLayer].neurons[j].getNeuronOutput();
            }
        }
        for(int i = indexSecondLastLayer - 1; i ==  0 ; i--){

        }
    }

    // need to check inputSample.length == this.layers[0].neurons.length
    private double[] calculateOutputOfSample(double inputSample[]){
        int numOutputNeuron = this.layers[this.layers.length - 1].neurons.length;
        double [] output = new double[numOutputNeuron];
        for (int index = 0; index < inputSample.length; index++){
            this.layers[0].neurons[index].setNeuronInput(inputSample[index]);
            this.layers[0].neurons[index].setNeuronOutput(inputSample[index]);
        }
        for (int i = 0; i < this.layers.length - 1; i++){         // start at 1 because we already inserted into orginal function- 2 cause skip no weights past the last layer
            for (int j = 0; j < this.layers[i].weights.length; j++){
                double weightedOutput = 0;
                for (int k = 0; k < this.layers[i].weights[j].length; k++){
                    weightedOutput += this.layers[i].neurons[k].getNeuronOutput() * this.layers[i].weights[j][k];
                }
                this.layers[i + 1].neurons[j].setNeuronInput(weightedOutput);
                this.layers[i + 1].neurons[j].setNeuronOutput(hypTanActivationFunction(weightedOutput));
            }
        }
        for (int i = 0; i < numOutputNeuron; i++){
            output[i] = this.layers[this.layers.length - 1].neurons[i].getNeuronOutput();
        }
        return output;
    }

    // check value.length == weights.length;
    private double dotProduct(double [] values, double [] weights){
        double sum = 0;
        for (int i = 0; i < values.length; i++){
            sum += values[i] * weights[i];
        }
        return sum;
    }

    private double hypTanActivationFunction(double input){
        return (exp(input) - exp(-input))/(exp(input) + exp(-input));
    }

    private double hypTanDerivative(double input){
        double hypTan = hypTanActivationFunction(input);
        return 1 - (hypTan * hypTan);
    }

    private double sigmoidActivationFunction(double input){
        return (1/(1+(exp(-input))));
    }

    private double sigmoidDerivative(double input){
        double sigmoidValue = sigmoidActivationFunction(input);
        return (sigmoidValue*(1-sigmoidValue));
    }

    private double[][] fillWithRandomValues(double[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                //matrix[i][j] = random();
                matrix[i][j] = 0.5;
            }
        }
        return matrix;
    }

    private double[][] fillWithZeros(double[][] matrix){
        for (int i= 0; i < matrix.length; i++){
            for (int j=0; j < matrix[i].length; j++){
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

}
