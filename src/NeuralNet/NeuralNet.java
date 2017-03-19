package NeuralNet;

import NeuralNet.Neuron.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static java.lang.StrictMath.exp;

/**
 * Created by user 1 on 3/16/2017.
 */
public class NeuralNet {
    public ArrayList<Neuron> neuralNet;
    public ArrayList<Neuron> reversedNetwork;
    public double learningRate;
    public double momentum;

    public NeuralNet(){
        this.neuralNet = new ArrayList<Neuron>();
        this.reversedNetwork = new ArrayList<Neuron>();
        this.learningRate = 0.7;
        this.momentum = 0.3;
    }

    public void enterInput(){
        neuralNet.get(0).neuronInput = 1;
        neuralNet.get(1).neuronInput = 0;
    }

    public void createPractiseNetwork(){
        InputNeuron i1 = new InputNeuron("I1");
        InputNeuron i2 = new InputNeuron("I2");
        BiasNeuron b1 = new BiasNeuron("B1");
        BiasNeuron b2 = new BiasNeuron("B2");
        HiddenNeuron h1 = new HiddenNeuron("H1");
        HiddenNeuron h2 = new HiddenNeuron("H2");
        OutputNeuron o1 = new OutputNeuron("O1");

        EdgeValues i1h1= new EdgeValues(-0.07);
        EdgeValues i1h2= new EdgeValues(0.94);
        EdgeValues i2h1= new EdgeValues(0.22);
        EdgeValues i2h2= new EdgeValues(0.46);
        EdgeValues h1o1= new EdgeValues(-0.22);
        EdgeValues h2o1= new EdgeValues(0.58);
        EdgeValues b2o1= new EdgeValues(0.78);
        EdgeValues b1h1= new EdgeValues(-0.46);
        EdgeValues b1h2= new EdgeValues(0.1);
        //forward network
        this.neuralNet.add(i1);
        this.neuralNet.add(i2);
        this.neuralNet.add(b1);
        i1.nextNodes.put(h1, i1h1);
        i1.nextNodes.put(h2, i1h2);
        i2.nextNodes.put(h1, i2h1);
        i2.nextNodes.put(h2, i2h2);
        h1.nextNodes.put(o1, h1o1);
        h2.nextNodes.put(o1, h2o1);

        //reversed network
        this.reversedNetwork.add(o1);
        o1.prevNodes.put(h1, h1o1);
        o1.prevNodes.put(h2, h2o1);
        o1.prevNodes.put(b2, b2o1);

        h1.prevNodes.put(i1,  i1h1);
        h1.prevNodes.put(i2, i2h1);
        h1.prevNodes.put(b1, b1h1);


        h2.prevNodes.put(i1, i1h2);
        h2.prevNodes.put(i2, i2h2);
        h2.prevNodes.put(b1, b1h2);
    }

    public double calculateNetwork(){
        for (int i = 0; i < this.reversedNetwork.size(); i++){
            this.reversedNetwork.get(i).neuronOutput = calculateNetworkHelper(this.reversedNetwork.get(i));
        }
        return this.reversedNetwork.get(0).neuronOutput;
    }

    public double calculateNodeDeltas(){
        for (int i = 0; i < this.neuralNet.size(); i++){
            calculateNodeDeltasHelper(this.neuralNet.get(i));
        }
        return this.neuralNet.get(0).nodeDelta;
    }

    private double calculateNetworkHelper(Neuron n){
        if (n.getClass() == InputNeuron.class || n.getClass() == BiasNeuron.class){
            System.out.println("NEURON NAME: " + n.name + " VALUE: " + n.neuronInput);
            return n.neuronInput;
        }
        else{
            // Get of a set of the entries
            Set set = n.prevNodes.entrySet();
            // Get an iterator
            Iterator i = set.iterator();
            double sum = 0;
            while(i.hasNext()){
                Map.Entry me = (Map.Entry)i.next();
                sum = sum +  calculateNetworkHelper((Neuron)me.getKey()) * ((EdgeValues)me.getValue()).weight;
                System.out.println("NEURON NAME: " + n.name + " VALUE: " + sum);
            }
            n.neuronInput = sum;
            n.neuronOutput = sigmoidActivationFunction(n.neuronInput);
            System.out.println("NEURON NAME: " + n.name + " VALUE: " + n.neuronInput
            + " SIGMOID VALUE: " + n.neuronOutput);
            return n.neuronOutput;
        }
    }

    private double calculateNodeDeltasHelper(Neuron n){
        if (n.getClass() == OutputNeuron.class){
            n.nodeDelta = -(n.neuronOutput - 1) * sigmoidDerivative(n.neuronInput); // need to change 1 to be ideal output
            System.out.println("NEURON NAME: " + n.name + " nodeDelta: " + n.nodeDelta);
            return n.nodeDelta;
        }
        else {
            // Get of a set of the entries
            Set set = n.nextNodes.entrySet();
            // Get an iterator
            Iterator i = set.iterator();
            double sumPartOfNodeDelta = 0;
            double tempNodeDelta = 0;
            while(i.hasNext()){
                Map.Entry me = (Map.Entry)i.next();
                tempNodeDelta = calculateNodeDeltasHelper((Neuron)me.getKey());
                ((EdgeValues)me.getValue()).gradient = tempNodeDelta * n.neuronOutput;
                System.out.println("Node: " + n.name + " Gradient: " + ((EdgeValues)me.getValue()).gradient);
                sumPartOfNodeDelta = tempNodeDelta * ((EdgeValues)me.getValue()).weight;
                //System.out.println("NEURON NAME: " + n.name + " sumPartofNodeDelta: " + sumPartOfNodeDelta);
            }
            n.nodeDelta = sigmoidDerivative(n.neuronInput) * sumPartOfNodeDelta;
            System.out.println("NEURON NAME: " + n.name + " nodeDelta: " + n.nodeDelta);
            return n.neuronOutput;
        }
    }



    private double sigmoidActivationFunction(double input){
        return (1/(1+exp(-input)));
    }

    private double sigmoidDerivative(double input){
        double sigmoid = sigmoidActivationFunction(input);
        return (sigmoid * (1 - sigmoid));
    }




    public void traverseForwardNetwork(){
        for (int i = 0; i < this.neuralNet.size(); i++){
            traverseForwardNeuron(this.neuralNet.get(i));
        }
    }

    private void traverseForwardNeuron(Neuron n){
        if (n.nextNodes == null){
            System.out.println("stop traversing");
            System.out.println(n.name);
            System.out.println("^");
        }
        else{
            // Get of a set of the entries
            Set set = n.nextNodes.entrySet();
            // Get an iterator
            Iterator i = set.iterator();
            while(i.hasNext()){
                Map.Entry me = (Map.Entry)i.next();
                traverseForwardNeuron((Neuron)me.getKey());
                //System.out.print(me.getKey() + ":");
                //System.out.println(me.getValue());
                System.out.println(n.name);
                System.out.println("^");
            }
        }
    }

}