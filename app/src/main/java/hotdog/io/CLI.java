package hotdog.io;

import org.apache.commons.cli.*;

public class CLI {
    public static boolean multiplePairs = false;
    public static boolean singlePair =false;
    private static String csvPath;
    private static String workPath;
    private static String[] singlePairInfo;

    public CLI (String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)){ printHelp(options); }
    }

    public String getWorkPath() { return workPath; }
    public String getCsvPath() { return csvPath; }
    public String[] getSinglePairInfo() { return singlePairInfo; }

    private static boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            workPath = cmd.getOptionValue("wp");
            if (workPath.endsWith("/")) workPath = workPath.substring(0, workPath.lastIndexOf("/"));

            if (cmd.hasOption("csv")) {
                multiplePairs = true;
                csvPath = cmd.getOptionValue("csv");
            }

            else if (cmd.hasOption("pc") && cmd.hasOption("cpc") && cmd.hasOption("proj")) {
                singlePair = true;
                singlePairInfo = new String[]
                        {cmd.getOptionValue("pc"), cmd.getOptionValue("cpc"), cmd.getOptionValue("proj")};
            }

        } catch (Exception e) {
            e.printStackTrace();
            printHelp(options);
            return true;
        }
        return false;
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
                .hasArg()
                .argName("Local path where project(s) is cloned")
                .required(true)
                .build());

        options.addOption(Option.builder("pc")
                .desc("Count the number of total changes of a project")
                .hasArg()
                .argName("Post-Commit hashcode")
                .build());

        options.addOption(Option.builder("cpc")
                .desc("")
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
        HelpFormatter formatter = new HelpFormatter();
        String header = "PyDiff";
        String footer ="\nPlease report issues at https://github.com/GuinZack/pydiff";
        formatter.printHelp("pydiff", header, options, footer, true);
    }
}
