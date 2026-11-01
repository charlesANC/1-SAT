package br.unb.cic.infoknow.onesat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class IntegrationTest {


    String alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
    String andOperator = "^";
    String negativeOperator = "~";

    String formula1 = "a ^ b ^ c ^ d ^ e ^ f ^ ~g ^ ~h ^ ~i ^ ~j ^ k ^ l ^ m ^ n ^ o ^ p ^ q ^ r ^ s ^ t ^ u ^ v ^ w ^ x ^ y ^ z";  
    String formula2 = "a ^ ~b ^ c";


    @Test 
    void testIntegration() {
        HashSet<String> alphabetSet = AlphabetExtractor.createOrdering(alphabet, ",");
        Ranking ranking = new Ranking(alphabetSet, andOperator, negativeOperator);
        HashSet<Literal> literals1 = LiteralParser.parse(formula1, andOperator, negativeOperator);
        HashSet<Literal> literals2 = LiteralParser.parse(formula2, andOperator, negativeOperator);

        assert(Literal.isSatisfiable(literals1));
        assert(Literal.isSatisfiable(literals2));

        Formula f1 = FormulaParser.parse(literals1, ranking);
        Formula f2 = FormulaParser.parse(literals2, ranking);

        System.out.println("Positive: " + Integer.toBinaryString(f1.positive()));
        System.out.println("Negative: " + Integer.toBinaryString(f1.negative()));

        System.out.println("Positive: " + Integer.toBinaryString(f2.positive()));
        System.out.println("Negative: " + Integer.toBinaryString(f2.negative()));

        Formula f3 = f1.conjunction(f2);

        System.out.println("Positive: " + Integer.toBinaryString(f3.positive()));
        System.out.println("Negative: " + Integer.toBinaryString(f3.negative()));

        assert(f1.isSatisfiable());
        assert(f2.isSatisfiable());
        assert(!f3.isSatisfiable());

        String formula3 = formula1 + " ^ " + formula2;
        HashSet<Literal> literals3 = LiteralParser.parse(formula3, andOperator, negativeOperator);
        Formula f4 = FormulaParser.parse(literals3, ranking);        

        assert(!Literal.isSatisfiable(literals3));        

        assertEquals(f4, f3);
        assert(!f4.isSatisfiable());        

    }

    @Test 
    void testImplication() {
        HashSet<String> alphabetSet = AlphabetExtractor.createOrdering(alphabet, ",");
        Ranking ranking = new Ranking(alphabetSet, andOperator, negativeOperator);

        HashSet<Literal> f1 = LiteralParser.parse("A ^ ~B ^ C", "^", "~");        
        HashSet<Literal> f2 = LiteralParser.parse("A ^ ~B", "^", "~");
        HashSet<Literal> f3 = LiteralParser.parse("A ^ C", "^", "~");        
        HashSet<Literal> f4 = LiteralParser.parse("A ^ B", "^", "~");                
        HashSet<Literal> f5 = LiteralParser.parse("D", "^", "~");                        


        Formula antecedent = FormulaParser.parse(f1, ranking);
        Formula consequent1 = FormulaParser.parse(f2, ranking); 
        Formula consequent2 = FormulaParser.parse(f3, ranking);
        Formula consequent3 = FormulaParser.parse(f4, ranking);
        Formula consequent4 = FormulaParser.parse(f5, ranking);

        assert(antecedent.implies(consequent1));
        assert(antecedent.implies(consequent2));
        assert(!antecedent.implies(consequent3));
        assert(!antecedent.implies(consequent4));
    }         

}
