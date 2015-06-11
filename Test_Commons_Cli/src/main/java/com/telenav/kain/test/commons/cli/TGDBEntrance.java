package com.telenav.kain.test.commons.cli;

import org.apache.commons.cli.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

/**
 * User: Yu, Xingfei
 * Date: 11/12/14
 * Time: 1:21 PM
 * Telenav.Inc
 */
public class TGDBEntrance {
    private static Logger logger = Logger.getLogger(TGDBEntrance.class);

    private static final String REGION = "region";

    private static final String CURRENT_VERSION = "currentVersion";

    private static final String PREVIOUS_VERSION = "previousVersion";

    private static final String PBF_URL = "pbfURL";

    private static final String UNIDB_SCHMEA = "unidbSchema";

    private static final String TNGEO_SCHEMA = "tngeoSchema";

    private static final String PREVIOUS_TNGEO_SCHEMA = "previousTnGeoSchema";

    private static final String PROCESSOR = "processor";

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.info("***************************************************************************");
        logger.info("*             Starting a database process                                 *");
        logger.info("***************************************************************************");

        if(ArrayUtils.isNotEmpty(args)) {
            parseArgsV2(args);
        }

        logger.info("***************************************************************************");
        logger.info("*             Ending the database process                                 *");
        logger.info("***************************************************************************");
    }

    private static void parseArgsV2(String[] args) {
        logger.info("Parsing arguments......");
        CommandLineParser parser = new BasicParser();

        Options options = new Options();
        options.addOption("b", "processPBF", false, "process PBF file, include download and UniDB loading.");
//        options.addOption("p", "preProcess", false, "preprocess unidb data.");
//        options.addOption("g", "createTnGeoDB", false, "create TnGeo DB.");
//        options.addOption("i", "incrementalProcessOnly", false, "do incremental data only");
        options.addOption("npu", "noProcessUniDB", false, "don't preprocess UniDB.");

        options.addOption("url", PBF_URL, true, "the URL of pbf.");
        options.addOption("us", UNIDB_SCHMEA, true, "the UniDB schema.");
        options.addOption("ts", TNGEO_SCHEMA, true, "the TnGeo schema");
        options.addOption("pts", PREVIOUS_TNGEO_SCHEMA, true, "the incremental data will be generated if this argument is present.");
        options.addOption("ctr", "Countries", true, "the countries.");

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            if(line.hasOption("processPBF")) {
                //process pbf.
                logger.info(line.getOptionValue("processPBF"));
            }
//
//            if(line.hasOption("preProcess")) {
//                //preprocess unidb.
//                System.out.println();
//            }
//
//            if(line.hasOption("createTnGeoDB")) {
//                //create TnGeoDB
//                System.out.println();
//            }
//
//            if(line.hasOption("incrementalProcessOnly")) {
//                if(line.hasOption(TNGEO_SCHEMA) && line.hasOption(PREVIOUS_TNGEO_SCHEMA)) {
//                    //
//                } else {
//                    logger.error("Wrong arguments, current TnGeo Schema are previous TnGeo Schema are mandatory for option 'i'.");
//                    return;
//                }
//            }

            if(line.hasOption("noProcessUniDB")) {
                Commander.getInstance().setPreProcessUniDB(false);
            }

            if(line.hasOption(PBF_URL)) {
                String pbfURl = line.getOptionValue(PBF_URL);
                Commander.getInstance().setPbfURL(pbfURl);
                Commander.getInstance().setProcessPBF(true);
            } else {
                Commander.getInstance().setProcessPBF(false);
            }

            if(line.hasOption(UNIDB_SCHMEA)) {
                String unidbSchema = line.getOptionValue(UNIDB_SCHMEA);
                Commander.getInstance().setUnidbSchema(unidbSchema);
            }

            if(line.hasOption(TNGEO_SCHEMA)) {
                String tngeoSchema = line.getOptionValue(TNGEO_SCHEMA);
                Commander.getInstance().setTngeoSchema(tngeoSchema);
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(TNGEO_SCHEMA, options);
                logger.error("Wrong arguments, TnGeo schema is mandatory.");
                return;
            }

            if(line.hasOption("Countries")) {
                String countries = line.getOptionValue("Countries");
                Commander.getInstance().setCountries(countries);
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Countries", options);
                logger.error("Wrong arguments, Countries is mandatory.");
                return;
            }

            if(line.hasOption(PREVIOUS_TNGEO_SCHEMA)) {
                String previousTnGeoSchema = line.getOptionValue(PREVIOUS_TNGEO_SCHEMA);
                Commander.getInstance().setPreviousTnGeoSchema(previousTnGeoSchema);
            }


        }catch (org.apache.commons.cli.ParseException e) {
            logger.error(e.getMessage());
        }
    }

}
