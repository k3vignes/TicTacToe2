package NeuralNet.Neuron;

import NeuralNet.NeuralNet;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by user 1 on 3/16/2017.
 */
public abstract class Neuron {
    public Map nextNodes;  // <Neuron, <weight, gradient>>
    public Map prevNodes; //  <Neuron, <weight, gradient>>

    public double neuronInput;
    public double neuronOutput;
    public double nodeDelta;
    public String name;
}
