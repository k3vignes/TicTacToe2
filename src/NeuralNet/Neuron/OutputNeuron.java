package NeuralNet.Neuron;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user 1 on 3/16/2017.
 */
public class OutputNeuron extends Neuron {

    public OutputNeuron(String name){
        this.prevNodes = new HashMap();
        this.neuronInput = 0.0;
        this.neuronOutput = 0.0;
        this.name = name;
    }
}
