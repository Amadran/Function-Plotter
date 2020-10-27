package persistence;

import model.Function;
import model.Workspace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: modelled after JsonSerializationDemo's JsonReaderTest test class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d79763d7ed5bb61196c51570598336948efe1202
public class JsonReaderTest {
    private JsonReader reader;
    private Workspace workspace;

    @BeforeEach
    public void setup() {
        workspace = new Workspace();
    }

    @Test
    public void testJsonReaderNonExistentFile() {
        reader = new JsonReader("./data/JsonReader_NonExistentFile.json");

        try {
            workspace = reader.read();
            fail("IOException was expected");
        } catch (IOException ioe) {
            // do nothing
        }
    }

    @Test
    public void testJsonReaderFileOfEmptyWorkspace() {
        reader = new JsonReader("./data/JsonReader_EmptyWorkspace.json");

        try {
            workspace = reader.read();
        } catch (IOException ioe) {
            fail("IOException was not expected");
        }

        assertEquals(0, workspace.getFunctionListLength());
    }

    @Test
    public void testJsonReaderGeneralWorkspace() {
        reader = new JsonReader("./data/JsonReader_GeneralWorkspace.json");

        try {
            workspace = reader.read();
        } catch (IOException ioe) {
            fail("IOException was not expected");
        }

        Function[] expectedFuncs = helperInitFunctions();
        String expectedFunc1Name = "my test exponential";
        String expectedFunc2Name = "polynomial test";

        assertEquals(2, workspace.getFunctionListLength());
        assertEquals(expectedFuncs[0], workspace.getFunction(expectedFunc1Name));
        assertEquals(expectedFuncs[1], workspace.getFunction(expectedFunc2Name));
    }

    // ~~~~~~~~~~~~~~HELPERS~~~~~~~~~~~~~~~~

    private Function[] helperInitFunctions() {
        String type1 = Function.TYPE_EXP;
        HashMap<String, Double> constants1 = new HashMap<>();
        constants1.put("a", 2.34);
        constants1.put("b", -0.388);
        constants1.put("c", 0.0);
        List<Double> domain1 = new ArrayList<>();
        domain1.add(-0.5);
        domain1.add(1.25);
        Function func1 = new Function(type1, constants1, domain1);

        String type2 = Function.TYPE_POLY;
        HashMap<String, Double> constants2 = new HashMap<>();
        constants2.put("a", 0.521);
        constants2.put("b", -2.3134);
        constants2.put("c", 4.342);
        constants2.put("d", -2.7);
        constants2.put("e", 0.0);
        constants2.put("f", 0.015);
        List<Double> domain2 = new ArrayList<>();
        domain2.add(-0.5);
        domain2.add(0.5);
        Function func2 = new Function(type2, constants2, domain2);

        return new Function[] {func1, func2};
    }
}
