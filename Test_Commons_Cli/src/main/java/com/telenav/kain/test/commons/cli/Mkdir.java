package com.telenav.kain.test.commons.cli;

import org.apache.commons.cli.*;

/**
 * Created by zfshi on 5/28/2015.
 */
public class Mkdir {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Options opt = new Options();
        opt.addOption("p", false, "no error if existing, " +
                "make parent directories as needed.");
        opt.addOption("v", "verbose", false, "explain what is being done.");
        opt.addOption(OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("search for buildfile towards the root of the filesystem and use it")
                .create("O"));
        opt.addOption(OptionBuilder.withLongOpt("block-size")
                .withDescription("use SIZE-byte blocks")
                .withValueSeparator('=')
                .hasArg()
                .create());
        opt.addOption("h", "help", false, "print help for the command.");

        String formatstr = "gmkdir [-p][-v/--verbose][--block-size][-h/--help] DirectoryName";

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();
        CommandLine cl = null;
        try {
            // ??Options???
            cl = parser.parse(opt, args);
        } catch (ParseException e) {
            formatter.printHelp(formatstr, opt); // ???????????????
        }
        // ?????-h?--help?????????
        if (cl.hasOption("h")) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp(formatstr, "", opt, "");
            return;
        }
        // ?????-p??
        if (cl.hasOption("p")) {
            System.out.println("has p");
        }
        // ?????-v?--verbose??
        if (cl.hasOption("v")) {
            System.out.println("has v");
        }
        // ???????????DirectoryName
        String[] str = cl.getArgs();
        int length = str.length;
        System.out.println("length=" + length);
        System.out.println("Str[0]=" + str[0]);
        //??????block-size??
        if (cl.hasOption("block-size")) {
            // print the value of block-size
            System.out.println("block-size=" + cl.getOptionValue("block-size"));
        }
    }

}
