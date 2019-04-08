package com.kain.algorithm.kom;

public class Storage {

    public void save_neural_network(Kohonen_Topology Kohonen_Design) {
        String schoice;
        int dolock = 0;

        do {
            System.out.println();
            System.out.println("Do you wish to save this neural network? (Y/N): ");
            schoice = MyInput.readString();
            schoice = schoice.toUpperCase();
            if ((schoice.equalsIgnoreCase("Y")) || (schoice.equalsIgnoreCase("N"))) {
                dolock = 1;
            }
        } while (dolock <= 0);
        if (schoice.equalsIgnoreCase("Y")) {
            Kohonen_Design.savenet();
        }
    }
}
