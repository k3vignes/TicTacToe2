package NeuralNetwork;

/**
 * Created by user 1 on 4/6/2017.
 */
public class BiasNeuron extends Neuron {

    public BiasNeuron(){
        this.neuronInput = 1;
        this.neuronOutput = 1;
    }

    @Override
    public void setNeuronInput(double neuronInput){
        // throw not implemented so user has to check if it's a bias neuron
    }

    @Override
    public void setNeuronOutput(double neuronOutput){
        // throw not implemented so user has to check if it's a bias neuron
    }

}
