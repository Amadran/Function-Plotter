package ui;

import model.Function;
import model.Workspace;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//User interface for function plotting program, which uses the methods available to Workspace and Function
//to output relevant information
public class FunctionPlotterConsoleApp {
    // NUMBER_OF_CONSTANTS_FOR_TYPE: number of constants that the UI should wait for when defineFunctionConstants
    // is running, since it is different for each function type
    private static final HashMap<String, Integer> NUMBER_OF_CONSTANTS_FOR_TYPE = new HashMap<>();
    private static final String FUNC_EXPRESSION_LINEAR = "a*x + b";
    private static final String FUNC_EXPRESSION_POLY = "a*x^5 + b*x^4 + c*x^3 + d*x^2 + e*x + f";
    private static final String FUNC_EXPRESSION_EXP = "a*e^(b*x) + c";
    private static final String FUNC_EXPRESSION_TRIG = "a*sin(b*x) + c*cos(d*x) + e*tan(f*x) + g";
    private static final String FUNC_EXPRESSION_LOG = "a*ln(b*x) + c";
    private static final String ERROR_INPUT_GENERIC = "Please enter an appropriate command";
    private static final String ERROR_INPUT_DOMAIN_BOUNDARY = "Left boundary must be less than right boundary";
    private static final String ERROR_INPUT_EMPTY_WORKSPACE = "Workspace is empty, must create at least one function";

    private Workspace workspace;
    private Scanner input;

    // MODIFIES: this, Function
    // EFFECTS: initializes workspace (Workspace object), input (Scanner object), and
    //          NUMBER_OF_CONSTANTS_FOR_TYPE, as well as starting the user interface
    public FunctionPlotterConsoleApp() {
        initNumberOfConstantsForType();
        workspace = new Workspace();
        input = new Scanner(System.in);
        startUserInterface();
    }

    // MODIFIES: this
    // EFFECTS: initializes HashMap containing mappings between function type and their
    //          respective number of constants in the function definition
    private void initNumberOfConstantsForType() {
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_LINEAR, 2);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_POLY, 6);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_EXP, 3);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_TRIG, 7);
        NUMBER_OF_CONSTANTS_FOR_TYPE.put(Function.TYPE_LOG, 3);
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: starts the program interface
    private void startUserInterface() {
        System.out.println("~~~Mathematical Function Plotter~~~");
        System.out.println("~~~CPSC 210 Term Project by Adrian Pikor~~~\n");
        mainMenu();
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: shows the main menu with numeric options
    private void mainMenu() {
        while (true) {
            System.out.println("\n1 - Add a function to the workspace\n2 - Show function descriptions and outputs\n"
                    + "3 - Evaluate a function at a particular value\n4 - Delete a function from the workspace\n"
                    + "5 - Save Workspace to file\n6 - Load Workspace from file\n7 - Quit Program");
            processMainMenuInput(input.nextLine());
        }
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: processes input from main menu
    private void processMainMenuInput(String menuInput) {
        switch (menuInput) {
            case "1":
                createNewFunction();
                break;
            case "2":
                outputFunctions();
                break;
            case "3":
                evaluateFunction();
                break;
            case "4":
                deleteFunction();
                break;
            default:
                processMainMenuInputSwitchContinuation(menuInput);
        }
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: processes input from main menu
    private void processMainMenuInputSwitchContinuation(String menuInput) {
        switch (menuInput) {
            case "5":
                saveWorkspace();
                break;
            case "6":
                loadWorkspace();
                break;
            case "7":
                System.exit(0); //quit condition
                break;
            default:
                System.out.println(ERROR_INPUT_GENERIC);
        }
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: processes input to create a new function and adds it to the workspace
    private void createNewFunction() {
        while (true) {
            String name = defineFunctionName();
            String type = defineFunctionType();
            ArrayList<Double> domain = defineFunctionDomain();
            HashMap<String, Double> constants = defineFunctionConstants(type);

            if (type != null && domain != null) {
                workspace.addFunction(new Function(type, constants, domain), name);
                break;
            }
        }
    }

    // EFFECTS: converts selection into appropriate Function type string
    //          (returns null if invalid selection; used in defineFunctionType())
    private String convertSelectionToType(String selection) {
        switch (selection) {
            case "1":
                return Function.TYPE_LINEAR;
            case "2":
                return Function.TYPE_POLY;
            case "3":
                return Function.TYPE_EXP;
            case "4":
                return Function.TYPE_TRIG;
            case "5":
                return Function.TYPE_LOG;
            default:
                return null;
        }
    }

    // EFFECTS: converts Function type string into a human-readable expression of the math function
    //          (returns null if invalid type; used in outputFunctions())
    private String convertTypeToFunctionExpression(String type) {
        switch (type) {
            case Function.TYPE_LINEAR:
                return FUNC_EXPRESSION_LINEAR;
            case Function.TYPE_POLY:
                return FUNC_EXPRESSION_POLY;
            case Function.TYPE_EXP:
                return FUNC_EXPRESSION_EXP;
            case Function.TYPE_TRIG:
                return FUNC_EXPRESSION_TRIG;
            case Function.TYPE_LOG:
                return FUNC_EXPRESSION_LOG;
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input for a new function's name
    private String defineFunctionName() {
        System.out.println("\n\nEnter a name for your function: ");
        return input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: processes input to define the type of a new function
    private String defineFunctionType() {
        String type = null;

        while (true) {
            System.out.println("\nSelect a function type:\n1 - linear (" + FUNC_EXPRESSION_LINEAR + ")\n"
                    + "2 - polynomial (" + FUNC_EXPRESSION_POLY + ")\n"
                    + "3 - exponential (" + FUNC_EXPRESSION_EXP + ")\n"
                    + "4 - trigonometric (" + FUNC_EXPRESSION_TRIG + ")\n"
                    + "5 - logarithmic (" + FUNC_EXPRESSION_LOG + ")");
            type = convertSelectionToType(input.nextLine());

            if (type == null) {
                System.out.println(ERROR_INPUT_GENERIC);
            } else {
                return type;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input to define a new function's domain
    private ArrayList<Double> defineFunctionDomain() {
        ArrayList<Double> domain = new ArrayList<>();

        while (true) {
            System.out.println("Specify the domain of the function\n"
                    + "(the boundaries should be a multiple of Function.DELTA = " + Function.DELTA
                    + ", otherwise the right boundary will be excluded)\nLeft boundary: ");
            domain.add(0, input.nextDouble());
            input.nextLine();
            System.out.println("\nRight boundary: ");
            domain.add(1, input.nextDouble());
            input.nextLine();

            if (domain.get(0) >= domain.get(1)) {
                System.out.println(ERROR_INPUT_DOMAIN_BOUNDARY);
            } else {
                return domain;
            }
        }
    }

    // REQUIRES: type must be one of the valid Function types (see Function)
    // MODIFIES: this
    // EFFECTS: processes input to define a new function's constants
    private HashMap<String, Double> defineFunctionConstants(String type) {
        System.out.println("Specify the constants for the function: ");

        HashMap<String, Double> constants = new HashMap<>();
        for (int i = 0; i < NUMBER_OF_CONSTANTS_FOR_TYPE.get(type); i++) {
            System.out.println(Function.CONSTANT_NAMES[i] + ": ");
            constants.put(Function.CONSTANT_NAMES[i], input.nextDouble());
            input.nextLine();
        }

        return constants;
    }


    // EFFECTS: outputs information related to each Function object in the list
    //          of functions in the workspace
    private void outputFunctions() {
        HashMap<String, Function> functionList = workspace.getFunctionList();
        ArrayList<String> funcNames = new ArrayList<>(functionList.keySet());
        ArrayList<Function> funcArray = new ArrayList<>(functionList.values());

        if (funcArray.size() == 0) {
            System.out.println(ERROR_INPUT_EMPTY_WORKSPACE);
        } else {
            for (int i = 0; i < funcArray.size(); i++) {
                Function func = funcArray.get(i);
                String type = func.getFunctionType();

                System.out.println("Name: " + funcNames.get(i));
                System.out.println("Type: " + type + " (f(x) = " + convertTypeToFunctionExpression(type) + ")");
                System.out.println("Constants: " + func.getConstants());
                System.out.println("Domain: " + func.getDomain());
                System.out.println("x-values: " + func.getValuesX());
                System.out.println("y-values: " + func.getValuesY() + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input to select and evaluate a certain function of the workspace
    private void evaluateFunction() {
        ArrayList<String> funcNames = new ArrayList<>(workspace.getFunctionList().keySet());
        ArrayList<Function> funcArray = new ArrayList<>(workspace.getFunctionList().values());

        while (true) {
            if (funcArray.size() == 0) {
                System.out.println(ERROR_INPUT_EMPTY_WORKSPACE);
                break;
            }

            System.out.println("Select a function to evaluate: ");
            System.out.println("-1 - Go back to main menu");
            int selection = makeListSelection(funcNames);

            if (selection == -1) {
                break;
            } else if (selection <= funcNames.size()) {
                System.out.println("Input a value for x: ");
                double x = input.nextDouble();
                input.nextLine();
                System.out.println(funcNames.get(selection - 1) + "(x) = "
                        + funcArray.get(selection - 1).evalFunction(x));
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // MODIFIES: this, Function, Workspace
    // EFFECTS: processes input to select and remove function (after confirmation; see confirmDeleteFunction)
    private void deleteFunction() {
        ArrayList<String> funcNames = new ArrayList<>(workspace.getFunctionList().keySet());
        ArrayList<Function> funcArray = new ArrayList<>(workspace.getFunctionList().values());

        while (true) {
            if (funcArray.size() == 0) {
                System.out.println(ERROR_INPUT_EMPTY_WORKSPACE);
                break;
            }

            System.out.println("Select a function to delete: ");
            System.out.println("-1 - Go back to main menu");
            int selection = makeListSelection(funcNames);

            if (selection == -1) {
                break;
            } else if (selection >= 1 && selection <= funcNames.size()) {
                confirmDeleteFunction(funcNames.get(selection - 1));
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // REQUIRES: Function of name funcName exists in workspace
    // MODIFIES: this, Workspace, Function
    // EFFECTS: confirms removal of selected function in workspace, removes function if yes is selected,
    //          otherwise does nothing
    private void confirmDeleteFunction(String funcName) {
        while (true) {
            System.out.println("Are you sure you want to delete this function from the workspace?");
            System.out.println("1 - Yes\n2 - No");
            String confirmation = input.nextLine();

            if (confirmation.equals("1")) {
                workspace.removeFunction(funcName);
                break;
            } else if (confirmation.equals("2")) {
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the selection of a particular object (e.g. a Function object inside a workspace)
    private int makeListSelection(ArrayList<String> namesList) {
        //print out all names (e.g. workspaces, functions in active workspace)
        int i = 0;
        for (String name : namesList) {
            System.out.println(i + 1 + " - " + name);
            i++;
        }

        //return int selection of input
        //***nextInt() leaves the trailing \n, which eats into the next nextLine() call,
        //***so after every nextInt() call a nextLine() call must be placed
        //***(https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo)
        int selection = input.nextInt();
        input.nextLine();
        return selection;
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of the workspace to a .json file
    private void saveWorkspace() {
        String filename = null;
        JsonWriter writer;

        while (true) {
            System.out.println("Enter a name for the file:");
            filename = input.nextLine();
            writer = new JsonWriter("./data/" + filename + ".json");

            try {
                writer.open();
                writer.write(workspace);
                writer.close();
                System.out.println("File saved to './data/" + filename + ".json'");
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File could not be opened for writing\n");
            }
        }
    }

    // MODIFIES: this, Workspace, Function (?)
    // EFFECTS: loads a workspace from a .json file into this
    private void loadWorkspace() {
        String filename = null;
        JsonReader reader;

        while (true) {
            System.out.println("Enter the name for the file to load:");
            filename = input.nextLine();
            reader = new JsonReader("./data/" + filename + ".json");

            try {
                workspace = reader.read();
                System.out.println("File './data/" + filename + ".json' loaded");
                break;
            } catch (IOException e) {
                System.out.println("File could not be opened for reading\nMake sure the file exists");
            }
        }
    }
}
