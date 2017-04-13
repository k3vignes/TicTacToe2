package NeuralNetwork;

/**
 * Created by user 1 on 4/1/2017.
 */
public class Neuron {
        protected double neuronInput;
        protected double neuronOutput;
        protected double nodeDelta;

        public Neuron(Neuron n){
            this.neuronInput = n.neuronInput;
            this.neuronOutput = n.neuronOutput;
            this.nodeDelta = n.nodeDelta;
        }

        public Neuron(){ }

        public double getNeuronInput(){
            return this.neuronInput;
        }

        public double getNeuronOutput(){
            return this.neuronOutput;
        }

        public double getNodeDelta(){
            return this.nodeDelta;
        }

        public void setNeuronInput(double neuronInput){
            this.neuronInput = neuronInput;
        }

        public void setNeuronOutput(double neuronOutput){
            this.neuronOutput = neuronOutput;
        }

        public void setNodeDelta(double nodeDelta){
            this.nodeDelta =  nodeDelta;
        }

}
