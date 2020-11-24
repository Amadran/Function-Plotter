# Mathematical Function Plotter

## CPSC 210 Personal Project
#### Adrian Pikor (97862742)

My project is going to be a mathematical function plotter.
It would be used by anyone that has a need to visually see
what a function looks like, as well as to calculate certain
values. It would be used by people such as:

- an undergraduate in a math or science course
- a graduate student
- a professional research scientist

This is of interest to me because I am a BCS student
whose prior degree was in physics from SFU. While I am
no longer going down that route, I still have an interest
in math and science, and I have had an interest in coding
my own version of something like Desmos or Mathematica in
terms of their plotting capabilities.

## User Stories
- As a user, I want to be able to define and add math
functions to my workspace
- As a user, I want to be able delete a function from my
workspace
- As a user, I want to be able to output the list of
functions in the workspace
- As a user, I want to be able to calculate the output of
a function in the workspace with a particular input
- As a user, I want to be able to save a workspace to a file
- As a user, I want to be able to load a workspace back up
from a file

## Phase 4: Task 2
- ##### Used the Map interface:
    - Class: Function
        - fields "constants" and "NUMBER_OF_CONSTANTS_FOR_TYPE"
        are a HashMap
        - methods:
            - Function()
            - initNumberOfConstantsForType()
            - getConstants()
            - all evalX() methods
            - toJson()
            - constantsToJson()
    - Class: Workspace
        - field "functionList" is a HashMap
        - methods:
            - Workspace()
            - addFunction()
            - removeFunction()
            - getFunction()
            - getFunctionList()
            - getFunctionListLength()
            - toJson()
    - Class: JsonReader
        - methods:
            - populateWorkspace()
            - populateConstantsAndDomain()
    - Class: FunctionPlotterGUI
        - methods:
            - addFunction()
            - loadWorkspace()
            - getFunctionNames()
    - Class: CanvasPanel
        - methods:
            - drawWorkspace()
    - Class: FunctionListPanel
        - methods:
            - addNewFuncLabel()
    - Class: WorkspaceFileHandler
        - methods:
            - loadFile()
    - Class: AddFunctionWindow
        - field "NUMBER_OF_CONSTANTS_FOR_TYPE" is a HashMap
        - methods:
            - initNumberOfConstantsForType()
            - hashMapMaxValue()
            - initializeConstantsPanel()
            - hideConstantFields()
            - getConstants()
            - actionPerformed()
            
- ##### Bi-directional Association:
    - FunctionPlotterGUI ---- ButtonPanel
        - methods:
            - FunctionPlotterGUI.initializePanels()
                - calls new ButtonPanel(this)
            - ButtonPanel.actionPerformed()
                - calls AddFunctionWindow()
                - calls AddFunctionWindow.actionPerformed()
                - calls FunctionPlotterGUI.addFunction()
            - ButtonPanel.actionPerformed()
                - calls RemoveFunctionWindow()
                - calls RemoveFunctionWindow.actionPerformed()
                - calls FunctionPlotterGUI.removeFunction()
            - ButtonPanel.actionPerformed()
                - calls FunctionPlotterGUI.saveWorkspace()
            - ButtonPanel.actionPerformed()
                - calls FunctionPlotterGUI.loadWorkspace()
    - FunctionPlotterGUI ---- WorkspaceFileHandler
        - methods:
            - FunctionPlotterGUI()
                - calls WorkspaceFileHandler(this)
            - FunctionPlotterGUI.saveWorkspace()
                - calls WorkspaceFileHandler.saveFile()
                - calls JFileChooser.showSaveDialog(this) (Java library)
            - FunctionPlotterGUI.loadWorkspace()
                - calls WorkspaceFileHandler.loadFile()
                - calls JFileChooser.showOpenDialog(this) (Java library)
    