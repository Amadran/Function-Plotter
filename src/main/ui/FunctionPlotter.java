package ui;

import model.Function;
import model.Workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//interface for function plotting program, which contains a list of all workspaces and
//handles input for the creation, deletion, renaming, and switching of them, as well as
//handling input for the definition of functions for a selected workspace
public class FunctionPlotter {
    // number of constants that the UI should wait for when processInputDefineFunction is running,
    // since it is different for each function type
    private static final HashMap<String, Integer> NUMBER_OF_CONSTANTS_FOR_TYPE = new HashMap<>();
    private static final String ERROR_INPUT_GENERIC = "Please enter an appropriate command";
    private static final String ERROR_INPUT_DOMAIN_BOUNDARY = "Left boundary must be less than right boundary";

    private HashMap<String, Workspace> workspaceList; //entries consist of (workspaceName, workspace object) pairs
    private String activeWorkspaceName;
    private Scanner input;

    // EFFECTS: initializes Scanner object
    public FunctionPlotter() {
        initNumberOfConstantsForType();
        workspaceList = new HashMap<>();
        input = new Scanner(System.in);
        startUserInterface(); //start the user interface
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

    // MODIFIES: this
    // EFFECTS: starts the program interface
    private void startUserInterface() {
        System.out.println("~~~Mathematical Function Plotter~~~");
        System.out.println("~~~CPSC 210 Term Project by Adrian Pikor~~~\n");

        while (true) {
            if (workspaceList.size() == 0) {
                createWorkspace();
            } else {
                mainMenu();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input to create a new workspace
    private void createWorkspace() {
        System.out.println("Enter a name for the new workspace: ");
        activeWorkspaceName = input.nextLine();
        workspaceList.put(activeWorkspaceName, new Workspace());
    }

    // MODIFIES: this
    // EFFECTS: shows the main menu with numeric options
    private void mainMenu() {
        while (true) {
            System.out.println("\n1 - Add a function to '" + activeWorkspaceName + "' (current workspace)\n"
                    + "2 - Show functions\n3 - Evaluate a function at a particular value (current workspace)\n"
                    + "4 - Change workspaces\n5 - Rename a workspace\n"
                    + "6 - Delete a workspace\n7 - Quit Program");
            processMainMenuInput(input.nextLine());
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input from main menu (exits program in the case "quit" is selected)
    private void processMainMenuInput(String menuInput) {
        switch (menuInput) {
            case "1":
                createNewFunction();
                break;
            case "2":
                outputFunctions();
                break;
            case "3":
                evaluateFunctionAtX();
                break;
            case "4":
                changeActiveWorkspace();
                break;
            default:
                processMainMenuInputContinue(menuInput);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input from main menu (continuation of switch statement in first method)
    private void processMainMenuInputContinue(String menuInput) {
        switch (menuInput) {
            case "5":
                renameWorkspace();
                break;
            case "6":
                deleteWorkspace();
                break;
            case "7":
                System.exit(0);
            default:
                System.out.println(ERROR_INPUT_GENERIC);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input to create a new function and add it to the currently active workspace
    private void createNewFunction() {
        Workspace activeWorkspace = workspaceList.get(activeWorkspaceName);

        while (true) {
            String name = defineFunctionName();
            String type = defineFunctionType();
            ArrayList<Double> domain = defineFunctionDomain();
            HashMap<String, Double> constants = defineFunctionConstants(type);

            if (type != null && domain != null) {
                activeWorkspace.addFunction(new Function(type, constants, domain), name);
                break;
            }
        }
    }

    // EFFECTS: converts type integer into appropriate Function type string
    private String convertToTypeString(String type) {
        switch (type) {
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
            System.out.println("\nSelect a function type:\n1 - linear\n2 - polynomial (up to x^5)\n"
                    + "3 - exponential\n4 - trigonometric\n5 - logarithmic (natural)");
            type = convertToTypeString(input.nextLine());

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
            System.out.println("Specify the domain of the function (x range):\nLeft boundary: ");
            domain.add(0, input.nextDouble());
            input.nextLine();
            System.out.println("Right boundary: ");
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
    //          of functions of the currently active workspace
    private void outputFunctions() {
        HashMap<String, Function> activeWorkspaceFuncList = workspaceList.get(activeWorkspaceName).getFunctionList();
        ArrayList<String> funcNames = new ArrayList<>(activeWorkspaceFuncList.keySet());
        ArrayList<Function> funcArray = new ArrayList<>(activeWorkspaceFuncList.values());

        if (funcArray.size() == 0) {
            System.out.println("Workspace is empty, must create at least one function");
        } else {
            for (int i = 0; i < funcArray.size(); i++) {
                Function func = funcArray.get(i);
                System.out.println("Name: " + funcNames.get(i));
                System.out.println("Type: " + func.getFunctionType());
                System.out.println("Constants: " + func.getConstants());
                System.out.println("Domain: " + func.getDomain());
                System.out.println("x-values: " + func.getValuesX());
                System.out.println("y-values: " + func.getValuesY() + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input to select and evaluate a certain function of the currently active workspace
    private void evaluateFunctionAtX() {
        Workspace activeWorkspace = workspaceList.get(activeWorkspaceName);
        ArrayList<String> functionNames = new ArrayList<>(activeWorkspace.getFunctionList().keySet());
        ArrayList<Function> functionList = new ArrayList<>(activeWorkspace.getFunctionList().values());

        while (true) {
            if (functionList.size() == 0) {
                System.out.println("Function list is empty for this workspace, create at least one function");
                break;
            }

            int selection = evalAtXHelper(functionNames);
            if (selection == -1) {
                break;
            } else if (selection <= functionNames.size()) {
                System.out.println("Input a value for x: ");
                double x = input.nextDouble();
                input.nextLine();
                System.out.println(functionNames.get(selection - 1) + "(x) = "
                        + functionList.get(selection - 1).evalFunction(x));
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // REQUIRES: functionNames must be the list returned by activeWorkspace.getFunctionList().keySet()'s iterator
    // MODIFIES: this
    // EFFECTS: evaluateAtFunctionX helper (prints out list of options and returns selection)
    private int evalAtXHelper(ArrayList<String> functionNames) {
        System.out.println("Select a function to evaluate: ");
        System.out.println("-1 - Go back to main menu");
        return makeListSelection(functionNames);
    }

    // MODIFIES: this
    // EFFECTS: changes the currently active workspace
    private void changeActiveWorkspace() {
        //put all workspace names (keys of workspaceList HashMap) into easily index-able ArrayList
        ArrayList<String> workspaceNames = new ArrayList<>(workspaceList.keySet());

        while (true) {
            System.out.println("Select a workspace: ");
            System.out.println("-1 - Go back to main menu");
            System.out.println("0 - Create a new workspace");
            int selection = makeListSelection(workspaceNames);

            if (selection == -1) {
                break;
            } else if (selection == 0) {
                createWorkspace();
                break;
            } else if (selection <= workspaceNames.size()) {
                activeWorkspaceName = workspaceNames.get(selection - 1);
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the selected workspace
    private void deleteWorkspace() {
        //put all workspace names (keys of workspaceList HashMap) into easily index-able ArrayList
        ArrayList<String> workspaceNames = new ArrayList<>(workspaceList.keySet());

        while (true) {
            System.out.println("Select a workspace to delete: ");
            System.out.println("-1 - Go back to main menu");
            int selection = makeListSelection(workspaceNames);

            if (selection == -1) {
                break;
            } else if (selection <= workspaceNames.size()) {
                String workspaceToDelete = workspaceNames.get(selection - 1);
                workspaceList.remove(workspaceToDelete);
                workspaceNames.remove(selection - 1);

                //prompt creation of new workspace if the only one was deleted
                if (workspaceList.size() == 0) {
                    createWorkspace();
                } else {
                    activeWorkspaceName = workspaceNames.get(0); //set active to first in list
                }
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: renames the selected workspace
    private void renameWorkspace() {
        //put all workspace names (keys of workspaceList HashMap) into easily index-able ArrayList
        ArrayList<String> workspaceNames = new ArrayList<>(workspaceList.keySet());
        Workspace activeWorkspace = workspaceList.get(activeWorkspaceName);

        while (true) {
            System.out.println("Select a workspace to rename: ");
            System.out.println("-1 - Go back to main menu");
            int selection = makeListSelection(workspaceNames);

            if (selection == -1) {
                break;
            } else if (selection <= workspaceNames.size()) {
                System.out.println("Enter a new name for the workspace: ");
                String newWorkspaceName = input.nextLine();
                workspaceList.remove(activeWorkspaceName);
                activeWorkspaceName = newWorkspaceName;
                workspaceList.put(activeWorkspaceName, activeWorkspace);
                break;
            } else {
                System.out.println(ERROR_INPUT_GENERIC);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the selection of a particular object (workspace, function in active workspace, etc.)
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

//    // REQUIRES: list must contain existing workspace names
//    // MODIFIES: this
//    // EFFECTS: returns the selection of a particular workspace in workspaceList
//    private int selectWorkspace(ArrayList<String> workspaceNames) {
//        //print out all workspace names in same order as in the ArrayList, to facilitate selection
//        int i = 0;
//        for (String workspaceName : workspaceNames) {
//            System.out.println(i + 1 + " - " + workspaceName);
//            i++;
//        }
//
//        //return int selection of input
//        //***nextInt() leaves the trailing \n, which eats into the next nextLine() call,
//        //***so after every nextInt() call a nextLine() call must be placed
//        //***(https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo)
//        int selection = input.nextInt();
//        input.nextLine();
//        return selection;
//    }
//
//    // REQUIRES: list must contain existing function names in active workspace
//    // MODIFIES: this
//    // EFFECTS: returns the selection of a particular function in active workspace's functionList
//    private int selectFunction(ArrayList<String> functionNames) {
//        //print out all function names in same order as in the ArrayList, to facilitate selection
//        int i = 0;
//        for (String functionName : functionNames) {
//            System.out.println(i + 1 + " - " + functionName);
//            i++;
//        }
//
//        //return int selection of input
//        int selection = input.nextInt();
//        input.nextLine();
//        return selection;
//    }
}
