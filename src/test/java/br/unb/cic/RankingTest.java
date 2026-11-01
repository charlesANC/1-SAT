package br.unb.cic;

import br.unb.cic.infoknow.onesat.Ranking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingTest {

    @Test
    public void testParseClause() {
        String input = "A ^ ~B ^ C";
        String andOperator = "^";
        String negativeOperator = "~";

        Ranking r = new Ranking(input, andOperator, negativeOperator);

        assertEquals(0, r.getOrder("A"));
        assertEquals(1, r.getOrder("B"));
        assertEquals(2, r.getOrder("C"));
    }

    @Test 
    public void newClause() {
        String input = "A ^ ~B ^ C";
        String andOperator = "^";
        String negativeOperator = "~";

        Ranking r = new Ranking(input, andOperator, negativeOperator);
        
        assertEquals(3, r.getOrder("D"));
        assertEquals(4, r.getOrder("E"));        
    }
}
