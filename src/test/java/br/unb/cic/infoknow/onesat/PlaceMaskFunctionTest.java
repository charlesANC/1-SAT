package br.unb.cic.infoknow.onesat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class PlaceMaskFunctionTest {

    @Test 
    public void testApply() {
        HashSet<String> alphabet = new HashSet<>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        
        Ranking ranking = new Ranking(alphabet, "^", "~");
        PlaceMaskFunction placeMaskFunction = new PlaceMaskFunction(ranking);
        
        assertEquals(1, placeMaskFunction.apply("A"));
        assertEquals(2, placeMaskFunction.apply("B"));
        assertEquals(4, placeMaskFunction.apply("C"));
    }

}
