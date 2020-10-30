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

// CITATION: modelled after JsonSerializationDemo's JsonWriterTest test class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/commit/d79763d7ed5bb61196c51570598336948efe1202
public class JsonWriterTest {
    private JsonWriter writer;
    private JsonReader reader;
    private Workspace workspace;

    @BeforeEach
    public void setup() {
        workspace = new Workspace();
    }

    @Test
    public void testWriterIllegalFilename() {
        writer = new JsonWriter("./data/??>illegal>:filename.json");

        try {
            writer.open();
            fail("IOException was expected");
        } catch (IOException ioe) {
            // do nothing
        }
    }

    @Test
    public void testWriterEmptyWorkspace() {
        String filename = "./data/JsonWriter_EmptyTest.json";
        writer = new JsonWriter(filename);
        reader = new JsonReader(filename);

        helperTestWriter(0);
    }

    @Test
    public void testWriterGeneralWorkspace() {
        String filename = "./data/JsonWriter_GeneralTest.json";
        writer = new JsonWriter(filename);
        reader = new JsonReader(filename);

        Function[] funcs = helperInitFunctions();
        workspace.addFunction(funcs[0], "my exp func");
        workspace.addFunction(funcs[1], "my poly func");

        helperTestWriter(2);
    }

    // ~~~~~~~~~~~~~~HELPERS~~~~~~~~~~~~~~~~

    private void helperTestWriter(int expectedWorkspaceSize) {
        try {
            //write workspace to file
            writer.open();
            writer.write(workspace);

            //read workspace back from file
            Workspace workspaceFromFile = reader.read();

            //check
            assertEquals(expectedWorkspaceSize, workspaceFromFile.getFunctionListLength());
            assertEquals(expectedWorkspaceSize, workspace.getFunctionListLength());
            assertEquals(workspaceFromFile, workspace);
        } catch (IOException ioe) {
            fail("IOException was not expected");
        } finally {
            writer.close();
        }
    }

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
