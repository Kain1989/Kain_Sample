package com.kain.algorithm.kom;

import java.io.*;

public class Kohonen_Training_Data {

    //Number of dimensions contained in signal
    int signal_dimensions;

    double[] max_output_value;
    double[] min_output_value;

    //Dimensions of test data output
    int nodes_in_output_layer;

    //Pointer to the array containing signals
    sample_data[] number_of_samples;
    //Number of signals in training set
    int sample_number;

    String filename;

    void acquire_net_info(int signal) {
        signal_dimensions = signal;
    }

    void normalize_data_in_array() {
        int i, j, imax, imin;
        int trigger;
        double min = 0, max = 0;
        max_output_value = new double[signal_dimensions];
        min_output_value = new double[signal_dimensions];

        for (j = 0; j < signal_dimensions; j++) {
            trigger = 1;
            // identify minimum and maximum values for each dimension
            for (i = 0; i < sample_number; i++) {
                if (i == 0) {
                    max = number_of_samples[i].data_in_sample[j];
                    min = number_of_samples[i].data_in_sample[j];
                } else {
                    if (number_of_samples[i].data_in_sample[j] < min) {
                        min = number_of_samples[i].data_in_sample[j];
                    }

                    if (number_of_samples[i].data_in_sample[j] > max) {
                        max = number_of_samples[i].data_in_sample[j];
                    }
                }
            }

            // normalize the values in each dimension of the signal
            max_output_value[j] = max;
            min_output_value[j] = min;

            imax = (int) (max);
            imin = (int) (min);

            if ((imax == 1) && (imin == 0) && (max <= 1.0) && (min <= 0.0)) {
                trigger = 0;
            }

            if ((imax == 1) && (imin == 1) && (max <= 1.0) && (min <= 1.0)) {
                trigger = 0;
            }

            if ((imax == 0) && (imin == 0) && (max <= 0.0) && (min <= 0.0)) {
                trigger = 0;
            }

            if (trigger != 0)   //  do not normalize binary signals
            {
                for (i = 0; i < sample_number; i++) {
                    number_of_samples[i].data_in_sample[j] = (number_of_samples[i].data_in_sample[j] - min) / (max - min);
                }
            }
        }
    }

    void specify_signal_sample_size() {
        String tchoice;
        int dolock = 1;
        do {
            System.out.println();
            System.out.println("Please select the number of samples you wish to use");
            System.out.println("	A.  All samples in the file");
            System.out.println("	S.  Specific number of samples");
            System.out.println("	Your Selection: ");
            tchoice = MyInput.readString();
            System.out.println();
            tchoice = tchoice.toUpperCase();
            if ((tchoice.equalsIgnoreCase("A")) || (tchoice.equalsIgnoreCase("S"))) {
                dolock = 0;
            }
        } while (dolock >= 1);

        System.out.println();
        if (tchoice.equalsIgnoreCase("A")) {
            determine_sample_number();
        } else {
            System.out.println();
            System.out.println("please enter the number of testing samples you wish to use: ");
            sample_number = MyInput.readInt();
            System.out.println();
        }
        load_data_into_array();
    }

    void request_Kohonen_data(int net_no) {
        System.out.println("Enter the file name containing the training data for Kohonen network no. " + net_no);
        filename = MyInput.readString();
        System.out.println();
        specify_signal_sample_size();
        normalize_data_in_array();
    }

    void determine_sample_number() {
        FileReader dfile_ptr;
        try {
            dfile_ptr = new FileReader(filename);
            BufferedReader br = new BufferedReader(dfile_ptr);
            String string;
            sample_number = 0;

            while ((string = br.readLine()) != null) {
                if (string.equalsIgnoreCase("")) {
                    continue;
                } else if (!string.equalsIgnoreCase("")) {
                    sample_number++;
                }
            }
            br.close();
            dfile_ptr.close();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }
    }

    void load_data_into_array() {
        // open the file containing the data
        FileReader file_ptr;
        int i;
        try {
            file_ptr = new FileReader(filename);
            BufferedReader br = new BufferedReader(file_ptr);
            String string;

            //create dynamic array to hold the specified number of samples
            number_of_samples = new sample_data[sample_number];

            //create a dynamic array to hold the dimensions of each signal
            for (i = 0; i < sample_number; i++) {
                number_of_samples[i] = new sample_data();

                number_of_samples[i].data_in_sample = new double[signal_dimensions + nodes_in_output_layer];
            }

            int dimensions = signal_dimensions + nodes_in_output_layer;
            int row = 0;

            while ((string = br.readLine()) != null) {
                if (string.trim().equalsIgnoreCase("")) {
                    //read in data from file and place in array
                    String[] s = string.split(",");

                    System.out.println("s.length=" + s.length);

                    if (row < sample_number) {
                        for (int j = 0; j < dimensions; j++) {
                            if (s[j] != null) {
                                number_of_samples[row].data_in_sample[j] = Double.parseDouble(s[j]);
                            }
                        }
                    }

                    row++;
                }
            }

            file_ptr.close();
            System.out.println();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }

    }

    public void delete_signal_array() {
        number_of_samples = null;
    }
}
