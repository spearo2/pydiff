package hotdog.io;

import org.apache.commons.cli.*;

public class CLI {
    private static boolean multiplePairs = false;
    private static boolean singlePair =false;
    private static String csvPath;
    private static String workPath;
    private static String pc;
    private static String cpc;
    private static String proj;

    public CLI (String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)){
            printHelp(options);
        }
    }

    private static boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("csv") && cmd.hasOption("wp")) {
                multiplePairs = true;
                workPath = cmd.getOptionValue("wp");
                csvPath = cmd.getOptionValue("csv");
            }

            else if (cmd.hasOption("pc") && cmd.hasOption("cpc") && cmd.hasOption("proj")) {
                singlePair = true;
                pc = cmd.getOptionValue("pc");
                cpc = cmd.getOptionValue("cpc");
                proj = cmd.getOptionValue("proj");
            }

            else
                throw new Exception("incorrect CLI input");

        } catch (Exception e) {
            e.printStackTrace();
            printHelp(options);
            return false;
        }
        return true;
    }

    private static Options createOptions() {

        Options options = new Options();

        options.addOption(Option.builder("csv").longOpt("csvPath")
                .desc("Set a path with a csv file")
                .hasArg()
                .argName("Local path to the input csv file")
                .build());

        options.addOption(Option.builder("wp").longOpt("workPath")
                .desc("Set a path to a cloned project(s)")
                .argName("Local path where project(s) is cloned")
                .build());

        options.addOption(Option.builder("pc")
                .desc("Count the number of total changes of a project")
                .argName("Post-Commit hashcode")
                .build());

        options.addOption(Option.builder("cpc")
                .desc("Set a path to .chg, default: /data/CGYW/chg. This option provides a index.csv, Statistics.txt, and .chg binary file")
                .hasArg()
                .argName("Change-Prone-Commit hashcode")
                .build());

        options.addOption(Option.builder("proj").longOpt("projectName")
                .desc("Name of the pc/cpc commit project")
                .hasArg()
                .argName("https://github.com/.../[projectName]")
                .build());

        options.addOption(Option.builder("h").longOpt("help")
                .desc("Help")
                .build());

        return options;
    }

    private static void printHelp(Options options) {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        String header = "AST Change Analyzer";
        String footer ="\nPlease report issues at https://github.com/GuinZack/pydiff";
        formatter.printHelp("pydiff", header, options, footer, true);
    }
}
