package com.kain.algorithm.kom;

import java.io.*;

/**
 * class containing the Kohonen neural net structure
 * along with training and testing data
 *
 * @author xjtuhuth 07/10/2007
 */
public class NeuralK {

    private Kohonen_Training_Data Kohonen_Train = new Kohonen_Training_Data();
    private Kohonen_Test_Data[] Kohonen_Test; // number of tests is variable
    private int number_of_Kohonen_tests;

    public Kohonen_Topology Kohonen_Design = new Kohonen_Topology();

    public void construct_Kohonen_network() {
        System.out.println("**** Kohonen Self-Organizing Map ****");
        Kohonen_Design.establish_Kohonen_topology(1);
    }

    private void initialize_Kohonen_training_storage_array(int KN) {
        int KT = KN;
        Kohonen_Train.acquire_net_info(Kohonen_Design.dimensions_of_signal);
        Kohonen_Train.request_Kohonen_data(KT);
    }

    private void establish_Kohonen_test_battery_size() {
        System.out.println("Please enter the number of tests you wish to run on the Kohonen Neural Network: ");
        number_of_Kohonen_tests = MyInput.readInt();
        System.out.println();
        if (number_of_Kohonen_tests > 0) {
            // create testing array
            Kohonen_Test = new Kohonen_Test_Data[number_of_Kohonen_tests];
            for (int t = 0; t < number_of_Kohonen_tests; t++) {
                Kohonen_Test[t] = new Kohonen_Test_Data();
                Kohonen_Test[t].acquire_net_info(Kohonen_Design.dimensions_of_signal);
            }
        }
    }

    private void train_Kohonen_network(int KOHN) {
        int dim, ep, k_epochs, pattern, knodes, dolock;
        System.out.println();
        System.out.println("For Neural Network #" + KOHN);
        System.out.println("please enter the maximum learning rate parameter (0-1): ");
        Kohonen_Design.max_learning_rate = MyInput.readDouble();
        System.out.println();
        System.out.println("please enter the minimum learning rate parameter (0-1): ");
        Kohonen_Design.min_learning_rate = MyInput.readDouble();
        System.out.println();
        System.out.println("please enter the number of epochs used to train the Kohonen Map: ");
        k_epochs = MyInput.readInt();
        System.out.println();
        ep = 0;
        dolock = 0;
        do {
            for (pattern = 0; pattern < Kohonen_Train.sample_number; pattern++) {
                for (knodes = 0; knodes < Kohonen_Design.maximum_number_of_clusters; knodes++) {
                    for (dim = 0; dim < Kohonen_Design.dimensions_of_signal; dim++) {
                        Kohonen_Design.node_in_cluster_layer[knodes].input_value[dim] = Kohonen_Train.number_of_samples[pattern].data_in_sample[dim];
                    }
                }
                Kohonen_Design.kluster_nodes_compete_for_activation();
                Kohonen_Design.update_the_Kohonen_network(ep, k_epochs);
            }
            System.out.println("Epoch " + (ep + 1) + " is completed");
            if ((ep == k_epochs - 1) || (Kohonen_Design.interim_learning_rate == 0.0)) {
                dolock = 1;
            }
            ep = ep + 1;
        } while (dolock <= 0);

        Kohonen_Train.delete_signal_array();
    }

    private void test_Kohonen_network(int KNET) {
        int tnet, dim, pattern, knodes;
        double realvalue;
        tnet = KNET;
        for (int ktest = 0; ktest < number_of_Kohonen_tests; ktest++) {
            Kohonen_Test[ktest].request_Kohonen_data(tnet);
            System.out.println("For Kohonen neural network #" + KNET + " and test #" + (ktest + 1) + ":");
            System.out.println("please enter the name of the file to hold the test");
            Kohonen_Test[ktest].resultsname = MyInput.readString();
            System.out.println();
            FileWriter Kohonen_savefile_ptr;
            StringBuffer s = new StringBuffer("");
            try {
                Kohonen_savefile_ptr = new FileWriter(Kohonen_Test[ktest].resultsname);

                for (pattern = 0; pattern < Kohonen_Test[ktest].sample_number; pattern++) {
                    for (knodes = 0; knodes < Kohonen_Design.maximum_number_of_clusters; knodes++) {
                        for (dim = 0; dim < Kohonen_Design.dimensions_of_signal; dim++) {
                            Kohonen_Design.node_in_cluster_layer[knodes].input_value[dim] = Kohonen_Test[ktest].number_of_samples[pattern].data_in_sample[dim];
                        }
                    }
                    Kohonen_Design.kluster_nodes_compete_for_activation();

                    s.append(pattern + 1).append(" ");
                    for (dim = 0; dim < Kohonen_Design.dimensions_of_signal; dim++) {
                        realvalue = (Kohonen_Test[ktest].number_of_samples[pattern].data_in_sample[dim] * (Kohonen_Test[ktest].max_output_value[dim] - Kohonen_Test[ktest].min_output_value[dim])) + Kohonen_Test[ktest].min_output_value[dim];
                        s.append(realvalue).append(" ");
                    }
                    s.append(" ").append(Kohonen_Design.kluster_champ + 1).append("\r\n");
                }
                Kohonen_savefile_ptr.write(s.toString());

                Kohonen_savefile_ptr.close();
            } catch (IOException exc) {
                System.out.println(exc.toString());
            }

            Kohonen_Test[ktest].delete_signal_array();
        }  // end test loop
    }

    public void network_training_testing(int TT) {
        int tt = TT;
        int menu_choice;

        System.out.println();
        System.out.println("**************** Operations Menu ****************");
        System.out.println("  Please select one of the following options:");
        System.out.println("      1. Train Kohonen network only ");
        System.out.println("      2. Test Kohonen network only ");
        System.out.println("      3. Train and Test Kohonen network");
        System.out.println("*************************************************");
        System.out.println("         Your choice?: ");
        menu_choice = MyInput.readInt();
        System.out.println();
        switch (menu_choice) {
            case 1:
                initialize_Kohonen_training_storage_array(tt);
                train_Kohonen_network(tt);
                break;

            case 2:
                establish_Kohonen_test_battery_size();
                if (number_of_Kohonen_tests > 0) {
                    test_Kohonen_network(tt);
                }
                break;

            case 3:
                initialize_Kohonen_training_storage_array(tt);
                train_Kohonen_network(tt);
                establish_Kohonen_test_battery_size();
                if (number_of_Kohonen_tests > 0) {
                    test_Kohonen_network(tt);
                }
                break;

            default:
                network_training_testing(tt);
        }
    }
}
