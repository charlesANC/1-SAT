package br.unb.cic.infoknow.onesat;

import org.junit.jupiter.api.Test;

public class FormulaParserTest {
    
    @Test 
    void testSimpleFormula() {
        String formula = "A ^ ^B";
        Formula f = FormulaParser.parse(formula, "^", "~");

        assert(f.positive() == 3L);
        assert(f.negative() == 0L);
    }

    @Test 
    void testSimpleFormula2() {
        String formula = "A ^ ~C ^B";
        Formula f = FormulaParser.parse(formula, "^", "~");

        assert(f.positive() == 3L);
        assert(f.negative() == 4L);
    }

}
