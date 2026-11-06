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

public class GlucoseBenchmarkCashedSolver {

    public static void main(String[] args) throws ParserException {
        if (args.length < 2) {
            System.out.println("Usage: java GlucoseBenchmarkCashedSolver <input_file> <satisfiable>");
            System.out.println("Example: java GlucoseBenchmarkCashedSolver formulas.txt true");
            return;
        }

        List<String> lines = readFileInList(args[0]);
        Tristate satisfiable = args[1].equals("true") ? Tristate.TRUE : Tristate.FALSE;        

        FormulaFactory factory = new FormulaFactory();
        PropositionalParser parser = new PropositionalParser(factory);                

        Long start = System.currentTimeMillis();

        for(int i = 0; i < 10; i++) {
            List<Formula> formulas = linesToFormulas(lines, parser);
            List<MiniSat> solvers = formulas.stream().map(f -> {
                MiniSat solver = MiniSat.glucose(factory);
                solver.add(f);
                return solver;  
            }).toList();

            for(int j = 0; j < 1000; j++) {
                for (int k = 0; k < solvers.size(); k++) {
                    if (!solvers.get(k).sat().equals(satisfiable)) {
                        System.out.println("Test failed for formula: " + k);
                        return;
                    }
                }        
            }
        }

        start = System.currentTimeMillis() - start;

        System.out.println("All tests passed!");
        System.out.println("Total time taken: " + start + " ms");
    }

    private static List<Formula> linesToFormulas(List<String> lines, PropositionalParser parser) throws ParserException {
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
