package ui;

import model.Function;
import model.Workspace;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//starts program through initializing FunctionPlotter
public class Main {
    public static void main(String[] args) {
        FunctionPlotter fp = new FunctionPlotter();

//        Workspace ws = new Workspace();
//
//        String type1 = Function.TYPE_EXP;
//        HashMap<String, Double> constants1 = new HashMap<>();
//        constants1.put("a", 2.34);
//        constants1.put("b", -0.388);
//        constants1.put("c", 0.0);
//        List<Double> domain1 = new ArrayList<>();
//        domain1.add(-0.5);
//        domain1.add(1.25);
//        Function func1 = new Function(type1, constants1, domain1);
//
//
//        String type2 = Function.TYPE_POLY;
//        HashMap<String, Double> constants2 = new HashMap<>();
//        constants2.put("a", 0.521);
//        constants2.put("b", -2.3134);
//        constants2.put("c", 4.342);
//        constants2.put("d", -2.7);
//        constants2.put("e", 0.0);
//        constants2.put("f", 0.015);
//        List<Double> domain2 = new ArrayList<>();
//        domain2.add(-0.5);
//        domain2.add(0.5);
//        Function func2 = new Function(type2, constants2, domain2);
//
//        ws.addFunction(func1, "my test exponential");
//        ws.addFunction(func2, "polynomial test");
//
//        JsonWriter writer = new JsonWriter("./data/test.json");
//
//        try {
//            writer.open();
//            writer.write(ws);
//            writer.close();
//            System.out.println("Done!");
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
    }
}
