package br.unb.cic.infoknow.onesat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OneSATTestUsingSearch {

    private final static String andOperator = "^";
    private final static String negativeOperator = "~";    

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java OneSATTestUsingSearch <input_file> <satisfiable>");
            System.out.println("Example: java OneSATTestUsingSearch formulas.txt true");
            return;
        }

        Long start = System.currentTimeMillis();

        List<HashSet<Literal>> formulas = readLiteralsFromFile(args[0]);
        boolean satisfiable = args[1].equals("true");

        Long time = System.currentTimeMillis();

        for(int j = 0; j < 10000; j++) {
            for (int i = 0; i < formulas.size(); i++) {
                HashSet<Literal> f = formulas.get(i);
                if (Literal.isSatisfiable(f) != satisfiable) {
                    System.out.println("Test failed for formula: " + i);
                    return ;
                }
            }
        }


        time = System.currentTimeMillis() - time;
        start = System.currentTimeMillis() - start;

        System.out.println("All tests passed!");
        System.out.println("Total time taken: " + start + " ms");
        System.out.println("Time taken: " + time + " ms");
    }

    private static List<HashSet<Literal>> readLiteralsFromFile(String fileName) {
        List<String> lines = readFileInList(fileName);
        List<HashSet<Literal>> formulas = new ArrayList<>();
        for (String line : lines) {
            formulas.add(LiteralParser.parse(line, andOperator, negativeOperator));
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
