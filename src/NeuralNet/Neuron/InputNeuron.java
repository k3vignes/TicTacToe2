package NeuralNet.Neuron;

import NeuralNet.NeuralNet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user 1 on 3/16/2017.
 */
public class InputNeuron extends Neuron {

    public InputNeuron(String name){
        this.nextNodes = new HashMap();
        this.neuronInput = 0.0;
        this.neuronOutput = 0.0;
        this.name = name;
    }


}
