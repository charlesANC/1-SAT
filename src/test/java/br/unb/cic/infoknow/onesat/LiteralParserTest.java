package br.unb.cic.infoknow.onesat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LiteralParserTest {

    @Test 
    void testSimpleFormula() {
        String formula = "A ^ B";

        HashSet<Literal> literals = LiteralParser.parse(formula, "^", "~");

        assertEquals(2L, literals.size());

        List<Literal> literalList =  Arrays.asList(literals.toArray(new Literal[0]));

        literalList.sort((l1, l2) -> l1.getAtom().compareTo(l2.getAtom()));

        assertEquals("A", literalList.get(0).getAtom());
        assertEquals("B", literalList.get(1).getAtom());
        assertEquals(false, literalList.get(0).isNegated());
        assertEquals(false, literalList.get(1).isNegated());   
    }

    @Test 
    void testSimpleFormula2() {
        String formula = "A ^ ~C ^B";

        HashSet<Literal> literals = LiteralParser.parse(formula, "^", "~");

        assertEquals(3L, literals.size());

        List<Literal> literalList =  Arrays.asList(literals.toArray(new Literal[0]));

        literalList.sort((l1, l2) -> l1.getAtom().compareTo(l2.getAtom()));

        assertEquals("A", literalList.get(0).getAtom());
        assertEquals("B", literalList.get(1).getAtom());
        assertEquals("C", literalList.get(2).getAtom());        
        assertEquals(false, literalList.get(0).isNegated());
        assertEquals(false, literalList.get(1).isNegated());   
        assertEquals(true, literalList.get(2).isNegated());           
    }    

}
