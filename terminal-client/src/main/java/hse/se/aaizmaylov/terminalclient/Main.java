package hse.se.aaizmaylov.terminalclient;

import fr.lip6.move.pnml.framework.utils.exception.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.Reducer;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability.CoverabilityAnalyser;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting.IntoPnmlExporter;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting.PnmlColoredResult;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing.PnmlReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.*;
import hse.se.aaizmaylov.terminalclient.terminal.TerminalColors;
import hse.se.aaizmaylov.terminalclient.terminal.TerminalPrinter;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.tuple.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();

        options.addOption("a", "analyze", false, "analyzes given Petri Net");
        options.addOption("R", "reductions", true, "specify list of reductions");
        options.addOption("f", "file", true, "specify file with PNML");
        options.addOption("o", "output", true, "output file");
        options.addOption("h", "help", false, "help message");
        options.addOption("w", "without-reduction", false, "do not reduce the net");

        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot parse arguments: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (commandLine.hasOption("h")) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("reduce-tool", options);
            return;
        }

        String filepath;
        String ouputFilepath;
        boolean analyze = commandLine.hasOption("a");
        boolean reduce = !commandLine.hasOption("w");

        if (!reduce && !analyze) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot nor analyse, nor reduce");
            System.exit(-1);
            return;
        }

        Pair<List<Reduction<Place, Transition>>, List<Reduction<Transition, Place>>> reductions;

        if (!commandLine.hasOption("f")) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Needs file!");
            System.exit(1);
        }

        if (!commandLine.hasOption("o")) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Needs output file!");
            System.exit(1);
        }

        filepath = commandLine.getOptionValue("f");
        ouputFilepath = commandLine.getOptionValue("o");

        try {
            Reductions allReductions = new Reductions();

            reductions = (commandLine.hasOption("R"))
                    ? allReductions.getReductions(allReductions.parseToList(commandLine.getOptionValue("R")))
                    : allReductions.all();
        } catch (Exception ex) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot parse reductions: " + ex.getMessage());
            System.exit(1);
            return;
        }

        PnmlReader reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet;

        try {
            petriNet = reader.read(filepath);
        } catch (PetriNetReader.PetriNetReadingException e) {
            TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot read: " + e.getMessage());
            System.exit(1);
            return;
        }

        ReductionHistory history;

        if (reduce) {
            Reducer reducer = new Reducer(petriNet);
            history = reducer.reduce(reductions.getLeft(), reductions.getRight());
        }  else {
            history = new ReductionHistory(petriNet.getPlaces(), petriNet.getTransitions());
        }

        if (!analyze) {
            try {
                IntoPnmlExporter exporter = new IntoPnmlExporter();
                exporter.export(petriNet, ouputFilepath);
            } catch (ValidationFailedException | OtherException | BadFileFormatException |
                    OCLValidationFailed | UnhandledNetType | IOException |
                    VoidRepositoryException | InvalidIDException e) {

                TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot write: " + e.getMessage());
                System.exit(1);
            }
        } else {
            CoverabilityAnalyser analyser = new CoverabilityAnalyser(petriNet);

            try {
                PnmlColoredResult result = new PnmlColoredResult(filepath);

                for (Place p : analyser.getUnboundedPlaces()) {
                    for (String associatedP : history.getAssociated(p)) {
                        result.markPlaceUnbounded(associatedP);
                    }
                }

                for (Transition t : analyser.getDeadTransitions()) {
                    for (String associatedT : history.getAssociated(t)) {
                        result.markTransitionDead(associatedT);
                    }
                }

                result.saveToFile(ouputFilepath);
            } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
                TerminalPrinter.println(TerminalColors.RED_BOLD, "Cannot color vertices in file: "
                        + e.getMessage());
                System.exit(1);
            }
        }

    }
}
