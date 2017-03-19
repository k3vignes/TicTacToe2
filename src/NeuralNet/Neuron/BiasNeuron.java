package NeuralNet.Neuron;

import java.util.HashMap;

/**
 * Created by user 1 on 3/18/2017.
 */
public class BiasNeuron extends Neuron {

    public BiasNeuron(String name){
        this.neuronInput = 1.0;
        this.neuronOutput = 1.0;
        this.nextNodes = new HashMap();
        this.prevNodes = new HashMap();
        this.name = name;
    }

}
