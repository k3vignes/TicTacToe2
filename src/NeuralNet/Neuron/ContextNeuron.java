package NeuralNet.Neuron;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user 1 on 3/18/2017.
 */
public class ContextNeuron extends Neuron {

    public ContextNeuron(){
        this.nextNodes = new HashMap();
        this.prevNodes = new HashMap();
        this.neuronInput = 0.0;
        this.neuronOutput = 0.0;
    }
}
