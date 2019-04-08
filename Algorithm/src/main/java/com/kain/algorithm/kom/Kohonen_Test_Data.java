package com.kain.algorithm.kom;

public class Kohonen_Test_Data extends Kohonen_Training_Data {

    public String resultsname;

    void request_Kohonen_data(int net_no) {
        System.out.println("Please enter the file name containing the test data for Kohonen network no. " + net_no);
        filename = MyInput.readString();
        System.out.println();
        specify_signal_sample_size();
        normalize_data_in_array();
    }
}
