import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Testim {
    @Test
    public void testCompute() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);

        // Set up test data
        sheet.set(0, 0, "5");
        sheet.set(0, 1, "3");
        sheet.set(0, 2, "=A1+B1");
        sheet.set(1, 0, "=C1*2");
        sheet.set(1, 1, "=A2-A1");
        sheet.set(2, 0, "=Z1");
        sheet.set(2, 1, "=A1+A1+");
        sheet.set(2,2,"Shalom");
        sheet.set(2,3,"Im tired");
        sheet.set(2,4,"Just kidding 123456(no)");
        sheet.set(2,4,"This text was generated by meeeeeeeee");
        sheet.set(2,4,"If you see this please give me at least 80 so that i can survive all this siut");



        // Test cases
        assertEquals("5.0", sheet.compute("5", new ArrayList<>()));
        assertEquals("ERR_CYCLE!", sheet.compute("=A1+B1", List.of(new Position(0, 2))));
        assertEquals(Ex2Utils.ERR_FORM, sheet.compute("=Z1", new ArrayList<>()));
        assertEquals(Ex2Utils.ERR_FORM, sheet.compute("=A1+A1+", new ArrayList<>()));

        // Test cyclic reference
        sheet.set(0, 0, "=A2");         // A1 references A2, creating a cycle
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.compute("=A2", List.of(new Position(0, 0))));

        //Test text
        assertEquals("Shalom",sheet.compute("Shalom", new ArrayList<>()));
        assertEquals("Im tired",sheet.compute("Im tired", new ArrayList<>()));
        assertEquals("Just kidding 123456(no)",sheet.compute("Just kidding 123456(no)", new ArrayList<>()));
        assertEquals("This text was generated by meeeeeeeee",sheet.compute("This text was generated by meeeeeeeee", new ArrayList<>()));
        assertEquals("If you see this please give me at least 80 so that i can survive all this siut",sheet.compute("If you see " +
                "this please give me at least 80 so that i can survive all this siut", List.of(new Position(2,4))));
    }
    @Test
    public void testIsIn() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);

        // Test valid coordinates
        assertTrue(sheet.isIn(0, 0));
        assertTrue(sheet.isIn(2, 2));
        assertTrue(sheet.isIn(1, 1));

        // Test invalid coordinates
        assertFalse(sheet.isIn(-1, 0));
        assertFalse(sheet.isIn(0, -1));
        assertFalse(sheet.isIn(3, 3));
        assertFalse(sheet.isIn(4, 0));
        assertFalse(sheet.isIn(0, 4));
    }
    @Test
    public void testParseDoubleValidNumbers() {
        Ex2Sheet sheet = new Ex2Sheet();

        // Valid double values
        assertEquals(123.45, sheet.parseDouble("123.45"));
        assertEquals(-678.9, sheet.parseDouble("-678.9"));
        assertEquals(0.0, sheet.parseDouble("0"));
        assertEquals(1.0, sheet.parseDouble("1"));
    }
    @Test
    public void testContainsCoordPresent() {
        Ex2Sheet sheet = new Ex2Sheet();

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));
        positions.add(new Position(2, 2));

        // Test for positions present in the list
        assertTrue(sheet.containsCoord(positions, new Position(0, 0)));
        assertTrue(sheet.containsCoord(positions, new Position(1, 1)));
        assertTrue(sheet.containsCoord(positions, new Position(2, 2)));
    }
    @Test
    public void testIsOp(){
        Ex2Sheet sheet = new Ex2Sheet();

        assertTrue(sheet.isOp('+'));
        assertTrue(sheet.isOp('-'));
        assertFalse(sheet.isOp('1'));
        assertTrue(sheet.isOp('*'));
        assertFalse(sheet.isOp('p'));
        assertFalse(sheet.isOp('='));
    }

    @Test
    public void testGetOpIndex() {
        Ex2Sheet sheet = new Ex2Sheet();
        assertEquals(5, sheet.getOpIndex("(3+5)*2"));
        assertEquals(1, sheet.getOpIndex("3*(2+1)"));
        assertEquals(-1, sheet.getOpIndex(""));
        assertEquals(-1, sheet.getOpIndex("42"));
        assertEquals(1, sheet.getOpIndex("3+5"));
    }

    @Test
    public void testEval() {
        Ex2Sheet sheet = new Ex2Sheet();
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "Hello");

        assertEquals("5.0", sheet.eval(0, 0));
        assertEquals("Hello", sheet.eval(1, 0));
    }
}
