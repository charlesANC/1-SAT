package br.unb.cic.infoknow.onesat;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class LiteralTest {

    @Test 
    void testLiteral() {
        Literal litA = new Literal(" ~ A ^", "^", "~");
        assert(litA.isNegated());
        assert(!litA.isEmpty());
        assert(litA.getAtom().equals("A"));

        Literal litB = new Literal(" B ^~", "^", "~");
        assert(!litB.isNegated());
        assert(!litB.isEmpty());
        assert(litB.getAtom().equals("B"));
    }

    @Test 
    void testImplication() {
        HashSet<Literal> antecedent = LiteralParser.parse("A ^ ~B ^ C", "^", "~");        
        HashSet<Literal> consequent1 = LiteralParser.parse("A ^ ~B", "^", "~");
        HashSet<Literal> consequent2 = LiteralParser.parse("A ^ C", "^", "~");        
        HashSet<Literal> consequent3 = LiteralParser.parse("A ^ B", "^", "~");                
        HashSet<Literal> consequent4 = LiteralParser.parse("D", "^", "~");                        

        assert(Literal.implies(antecedent, consequent1));
        assert(Literal.implies(antecedent, consequent2));
        assert(!Literal.implies(antecedent, consequent3));
        assert(!Literal.implies(antecedent, consequent4));
    }

}
