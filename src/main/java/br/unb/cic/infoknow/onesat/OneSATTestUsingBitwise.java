package br.unb.cic.infoknow.onesat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OneSATTestUsingBitwise {

    private final static String alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
    private final static String andOperator = "&";
    private final static String negativeOperator = "~";    

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java OneSATTestUsingBitwise <input_file> <satisfiable>");
            System.out.println("Example: java OneSATTestUsingBitwise formulas.txt true");
            return;
        }

        List<String> lines = readFileInList(args[0]);
        boolean satisfiable = args[1].equals("true");        

        HashSet<String> alphabetSet = AlphabetExtractor.createOrdering(alphabet, ",");
        Ranking ranking = new Ranking(alphabetSet, andOperator, negativeOperator);                

        Long start = System.currentTimeMillis();

        for(int i = 0; i < 10; i++) {        
            List<HashSet<Literal>> listOfLiterals = readLiteralsFromFile(lines);
            List<Formula> formulas = stringsToFormulas(listOfLiterals, ranking);


            for(int j = 0; j < 1000; j++) {        
                for (int k = 0; k < formulas.size(); k++) {
                    Formula f = formulas.get(k);
                    if (f.isSatisfiable() != satisfiable) {
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

    private static List<Formula> stringsToFormulas(List<HashSet<Literal>> formulas, Ranking ranking) {
        List<Formula> formulasList = new ArrayList<>();
        for (HashSet<Literal> f : formulas) {
            formulasList.add(FormulaParser.parse(f, ranking));
        }
        return formulasList;
    }

    private static List<HashSet<Literal>> readLiteralsFromFile(List<String> lines) {
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
