package com.kain.algorithm.kom;

import java.io.*;

public class Kohonen_Topology {

    int kluster_champ;
    int dimensions_of_signal;
    int maximum_number_of_clusters;
    double max_learning_rate;
    double min_learning_rate;
    double interim_learning_rate;
    Kohonen_units[] node_in_cluster_layer;

    Kohonen_Topology() {
        interim_learning_rate = 1.0;
    }

    void establish_Kohonen_topology(int netuse) {
        String netcreate;
        int looploc = 0;

        if (netuse == 1) {
            do {
                System.out.println();
                System.out.println("Do you wish to");
                System.out.println("C.  Create your own Kohonen Map ");
                System.out.println("U.  Upload an existing Kohonen Map ");
                System.out.println("Your choice?:  ");
                netcreate = MyInput.readString();
                System.out.println();
                netcreate = netcreate.toUpperCase();
                if ((netcreate.equalsIgnoreCase("C")) || (netcreate.equalsIgnoreCase("U"))) {
                    looploc = 1;
                }
            } while (looploc <= 0);
        } else {
            netcreate = "C";
        }

        if ((netcreate.equalsIgnoreCase("U")) && (netuse == 1)) {
            upload_network();
        } else {
            if (netuse == 1) {
                System.out.println("Please enter the dimensions of the network's input signal vector: ");
                dimensions_of_signal = MyInput.readInt();
                System.out.println();
            }
            System.out.println("please enter the maximum number of clusters to be formed: ");
            maximum_number_of_clusters = MyInput.readInt();
            System.out.println();

            // establish clustering layer of Kohonen network
            node_in_cluster_layer = new Kohonen_units[maximum_number_of_clusters];
            for (int c = 0; c < maximum_number_of_clusters; c++) {
                node_in_cluster_layer[c] = new Kohonen_units();

                node_in_cluster_layer[c].number_of_inputs = dimensions_of_signal;
                node_in_cluster_layer[c].establish_input_output_arrays();
                node_in_cluster_layer[c].establish_input_weight_vector_array();
                node_in_cluster_layer[c].initialize_inputs_and_weights();
            }
        }
    }

    void upload_network() {
        String getname;
        FileReader get_ptr;
        int netid = 0;
        int nodes, dim;
        int dolock = 0;

        do {
            System.out.println();
            System.out.println("Please enter the name of the file which holds the Kohonen Map");
            getname = MyInput.readString();
            System.out.println();

            try {
                get_ptr = new FileReader(getname);
                BufferedReader br = new BufferedReader(get_ptr);
                String string;

                while ((string = br.readLine()) != null) {
                    if (string.trim().equalsIgnoreCase("")) {
                        continue;
                    }
                    netid = Integer.parseInt(string);
                    break;
                }

                if (netid == 3) {
                    dolock = 1;
                    br.close();
                    get_ptr.close();
                } else {
                    System.out.println("Error** file contents do not match Kohonen specifications");
                    System.out.println("try again");
                    br.close();
                    get_ptr.close();
                }
            } catch (IOException exc) {
                String str = exc.toString();
                System.out.println(str);
            }
        } while (dolock <= 0);

        try {
            get_ptr = new FileReader(getname);
            BufferedReader br = new BufferedReader(get_ptr);
            String string;
            int row = 0;

            while ((string = br.readLine()) != null) {
                if (string.trim().equalsIgnoreCase("")) {
                    continue;
                }
                if (!string.trim().equalsIgnoreCase("")) {
                    row++;
                }
                if (row == 2) {
                    dimensions_of_signal = Integer.parseInt(string);
                }
                if (row == 3) {
                    maximum_number_of_clusters = Integer.parseInt(string);
                    break;
                }
            }

            node_in_cluster_layer = new Kohonen_units[maximum_number_of_clusters];
            for (nodes = 0; nodes < maximum_number_of_clusters; nodes++) {
                node_in_cluster_layer[nodes] = new Kohonen_units();

                node_in_cluster_layer[nodes].number_of_inputs = dimensions_of_signal;
                node_in_cluster_layer[nodes].establish_input_output_arrays();
                node_in_cluster_layer[nodes].establish_input_weight_vector_array();
            }

            while ((string = br.readLine()) != null) {
                if (!string.trim().equalsIgnoreCase("")) {
                    row++;
                }
                int indexOfSpace = 0;
                int tempIndexOfSpace = 0;
                if (row >= 4) {
                    for (nodes = 0; nodes < maximum_number_of_clusters; nodes++) {
                        for (dim = 0; dim < dimensions_of_signal; dim++) {
                            if (dim == 0) {
                                indexOfSpace = string.indexOf(" ");
                                node_in_cluster_layer[nodes].input_weight_vector[dim] =
                                        Double.parseDouble(string.trim().substring(0, indexOfSpace).trim());
                                tempIndexOfSpace = indexOfSpace;
                            } else {
                                indexOfSpace = string.indexOf(" ", indexOfSpace + 1);
                                node_in_cluster_layer[nodes].input_weight_vector[dim] =
                                        Double.parseDouble(string.trim().substring(tempIndexOfSpace, indexOfSpace).trim());
                                tempIndexOfSpace = indexOfSpace;
                            }

                        }
                    }
                }
            }

            br.close();
            get_ptr.close();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }
    }

    void kluster_nodes_compete_for_activation() {
        double minimum_distance = 0;
        for (int m = 0; m < maximum_number_of_clusters; m++) {
            node_in_cluster_layer[m].calculate_sum_square_Euclidean_distance();
            if (m == 0) {
                kluster_champ = m;
                minimum_distance = node_in_cluster_layer[m].output_value[0];
            } else {
                if (node_in_cluster_layer[m].output_value[0] < minimum_distance) {
                    kluster_champ = m;
                    minimum_distance = node_in_cluster_layer[m].output_value[0];
                }
            }
        }
    }

    void update_the_Kohonen_network(int epoch_count, int max_epochs) {
        int maxepoch;
        if (max_epochs == 1) {
            maxepoch = 1;
        } else {
            maxepoch = max_epochs - 1;
        }
        double adjusted_learning_rate = max_learning_rate - (((max_learning_rate - min_learning_rate) / maxepoch) * epoch_count);
        interim_learning_rate = adjusted_learning_rate * interim_learning_rate;
        node_in_cluster_layer[kluster_champ].update_the_weights(interim_learning_rate);
    }

    public void savenet() {
        String savename;
        FileWriter save_ptr;
        StringBuffer s = new StringBuffer("");
        int node, dim;

        System.out.println();
        System.out.println("Please enter the name of the file which will hold the Kohonen Map");
        savename = MyInput.readString();
        System.out.println();
        try {
            save_ptr = new FileWriter(savename);
            s.append(3).append("\r\n");   // network identifier number
            s.append(dimensions_of_signal).append("\r\n");
            s.append(maximum_number_of_clusters).append("\r\n");
            for (node = 0; node < maximum_number_of_clusters; node++) {
                for (dim = 0; dim < dimensions_of_signal; dim++) {
                    s.append(node_in_cluster_layer[node].input_weight_vector[dim]).
                            append(" ");
                }
                s.append("\r\n");
            }

            save_ptr.write(s.toString());
            save_ptr.close();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }
    }
}	  