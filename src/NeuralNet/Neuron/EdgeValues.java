package NeuralNet.Neuron;

/**
 * Created by user 1 on 3/19/2017.
 */
public class EdgeValues {
    public double weight;
    public double gradient;
    public double weightDelta;

    public EdgeValues(){
        this.weight = 0.0;
        this.gradient = 0.0;
        this.weightDelta = 0.0;
    }

    public EdgeValues(double w){
        this.weight = w;
        this.gradient = 0.0;
        this.weightDelta = 0.0;
    }
}
