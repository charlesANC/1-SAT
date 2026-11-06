package br.unb.cic.infoknow.logicngbenchmark;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.solvers.MiniSat;

public class MiniSatBenchmark {

    public static void main(String[] args) throws ParserException {
        if (args.length < 2) {
            System.out.println("Usage: java MiniSatBenchmark <input_file> <satisfiable>");
            System.out.println("Example: java MiniSatBenchmark formulas.txt true");
            return;
        }

        Long start = System.currentTimeMillis();

        FormulaFactory factory = new FormulaFactory();
        PropositionalParser parser = new PropositionalParser(factory);        

        List<Formula> formulas = readFormulasFromFile(args[0], parser);
        Tristate satisfiable = args[1].equals("true") ? Tristate.TRUE : Tristate.FALSE;

        Long time = System.currentTimeMillis();

        System.out.println("Set-up time: " + (time - start) + " ms");

        for(int j = 0; j < 10000; j++) {
            for (int i = 0; i < formulas.size(); i++) {
                MiniSat solver = MiniSat.miniSat(factory);
                solver.add(formulas.get(i));
                if (!solver.sat().equals(satisfiable)) {
                    System.out.println("Test failed for formula: " + i);
                    return;
                }
            }        
        }


        time = System.currentTimeMillis() - time;
        start = System.currentTimeMillis() - start;

        System.out.println("All tests passed!");
        System.out.println("Total time taken: " + start + " ms");
        System.out.println("Time taken: " + time + " ms");
    }

    private static List<Formula> readFormulasFromFile(String fileName, PropositionalParser parser) throws ParserException {
        List<String> lines = readFileInList(fileName);
        List<Formula> formulas = new ArrayList<>();
        for (String line : lines) {
            formulas.add(parser.parse(line));
        }
        return formulas;
    }

    private  static List<String> readFileInList(String fileName) {
        List<String> lines = new ArrayList<>();
        
      	try {
            lines = Files.readAllLines(
                Paths.get(fileName),
                StandardCharsets.UTF_8);
        } catch(IOException e) {
            e.printStackTrace();
        }
      
        return lines;
    }       
}
