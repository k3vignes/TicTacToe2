package NeuralNetwork;

/**
 * Created by user 1 on 4/1/2017.
 */
public class Layer {
        public Neuron neurons [];
        public int numberOfNeurons;
        //public Layer nextLayer;
        public double weights[][]; // [nextLayer.numberOfNeurons][this.numberOfNeurons]
        public double weightDelta[][]; // [nextLayer.numberOfNeurons][this.numberOfNeurons]
        public double gradients[][]; // [nextLayer.numberOfNeurons][this.numberOfNeurons]

        public Layer(Layer l){
            this.numberOfNeurons = l.numberOfNeurons;
            //this.nextLayer = l.nextLayer;
            for (int i = 0; i < numberOfNeurons; i++){
                this.neurons[i] = l.neurons[i];
            }
            for (int i = 0; i < this.weights.length; i++){
                for (int j = 0; j < this.weights[i].length; j++){
                    this.weights[i][j] = l.weights[i][j];
                    this.weightDelta[i][j] = l.weightDelta[i][j];
                    this.gradients[i][j] = l.gradients[i][j];
                }
            }
        }

        public Layer(){ }

}
