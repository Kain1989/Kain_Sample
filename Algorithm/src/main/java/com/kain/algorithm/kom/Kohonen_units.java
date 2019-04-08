package com.kain.algorithm.kom;

import java.util.Random;

public class Kohonen_units {


    int number_of_inputs;

    int number_of_outputs;

    double[] input_weight_vector;
    double[] input_value;
    double[] output_value;

    double transfer_function_width;                 // RBFN
    double Gaussian_transfer_output;                // RBFN

    Kohonen_units() {
        number_of_outputs = 1;
    }

    void establish_input_output_arrays() {
        input_value = new double[number_of_inputs];
        output_value = new double[number_of_outputs];
    }

    void establish_input_weight_vector_array() {
        input_weight_vector = new double[number_of_inputs];
    }

    void initialize_inputs_and_weights() {
        Random random = new Random();
        for (int k = 0; k < number_of_inputs; k++) {
            input_weight_vector[k] = random.nextDouble();

            System.out.println("input_weight_vetor[" + k + "]=" + input_weight_vector[k]);
        }
    }

    void calculate_sum_square_Euclidean_distance() {
        double sumsquare;
        double ss1;
        int ci;
        output_value[0] = 0.0;
        for (int k = 0; k < number_of_inputs; k++) {
            ci = k;

            if (input_value[ci] == 0.0) {
                sumsquare = Math.pow(input_weight_vector[ci], 2.0);
            } else {
                sumsquare = Math.pow(Math.abs(input_weight_vector[ci] - input_value[ci]), 2.0);
            }
            output_value[0] += sumsquare;
        }
        ss1 = output_value[0];
        output_value[0] = Math.sqrt(Math.abs(ss1));
    }

    void update_the_weights(double learning_rate) {
        for (int k = 0; k < number_of_inputs; k++) {
            input_weight_vector[k] = input_weight_vector[k] + (learning_rate * (input_value[k] - input_weight_vector[k]));
        }
    }

    //RBFN //
    void execute_Gaussian_transfer_function() {
        double transfer_ratio = (-1.0) * Math.pow((output_value[0] / transfer_function_width), 2.0);
        Gaussian_transfer_output = Math.exp(transfer_ratio);
    }
}
