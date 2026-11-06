package br.unb.cic.infoknow.onesat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OneSATTestUsingSearch {

    private final static String andOperator = "&";
    private final static String negativeOperator = "~";    

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java OneSATTestUsingSearch <input_file> <satisfiable>");
            System.out.println("Example: java OneSATTestUsingSearch formulas.txt true");
            return;
        }

        List<String> lines = readFileInList(args[0]);        
        boolean satisfiable = args[1].equals("true");        

        Long start = System.currentTimeMillis();

        for(int i = 0; i < 10; i++) {        

            List<HashSet<Literal>> formulas = stringToLIstOfLiterals(lines);

            
            for (int j = 0; j < 1000; j++ ) {
                for (int k = 0; k < formulas.size(); k++) {
                    HashSet<Literal> f = formulas.get(k);
                    if (Literal.isSatisfiable(f) != satisfiable) {
                        System.out.println("Test failed for formula: " + k);
                        return ;
                    }
                }
            }
        }

        start = System.currentTimeMillis() - start;

        System.out.println("All tests passed!");
        System.out.println("Total time taken: " + start + " ms");
    }

    private static List<HashSet<Literal>> stringToLIstOfLiterals(List<String> lines) {
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
