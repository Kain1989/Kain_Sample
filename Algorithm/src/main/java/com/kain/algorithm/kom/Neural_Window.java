package com.kain.algorithm.kom;

//this class holds the different types of neural nets
public class Neural_Window {

    public String neural_network_type;
    public int neural_network_number;

    public void display_menu_for_net_selection(int NNnum) {
        neural_network_number = NNnum;
        System.out.println("****************");
        System.out.println();
        System.out.println(" Neural Network " + neural_network_number + " ");
        System.out.println();
        System.out.println();
        System.out.println("Please select one of the following network types from the Main Menu");
        int i = 0;
        do {
            System.out.println();
            i = i + 1;
        } while (i < 3);
        System.out.println("                            ");
        System.out.println(" *** / Main Menu \\ ***");
        System.out.println();
        System.out.println(" F.  Feedforward network using backpropagation ");
        System.out.println(" A.  Adaptive Resonance Theory network for binary signals ");
        System.out.println(" K.  Kohonen Self-Organizing Map ");
        System.out.println(" R.  Radial Basis Function Network ");
        System.out.println(" E.  Exit Program");
        System.out.println();
        System.out.println("Network Type (?) ");
        neural_network_type = MyInput.readString();
        neural_network_type = neural_network_type.toUpperCase();
        if (!neural_network_type.equalsIgnoreCase("E")) {
            establish_network_type();
        }
    }

    private void establish_network_type() {
        int NNN = neural_network_number;

        NeuralK KOH;

        //Kohonen Self-Organizing Map
        KOH = new NeuralK();
        Storage Kstore = new Storage();
        KOH.construct_Kohonen_network();
        KOH.network_training_testing(NNN);
        Kstore.save_neural_network(KOH.Kohonen_Design);
    }

    public static void main(String[] args) {

        int number_of_nets;
        Neural_Window User_net = new Neural_Window();
        System.out.println(" ******* Welcome to Pitt-Networks!! ******** ");
        System.out.println("Please enter the number of networks you wish to develop: ");
        number_of_nets = MyInput.readInt();

        for (int NWnet = 1; NWnet < number_of_nets + 1; NWnet++) {
            User_net.display_menu_for_net_selection(NWnet);
            if (User_net.neural_network_type.equalsIgnoreCase("E")) {
                break;
            }
        }
    }
}