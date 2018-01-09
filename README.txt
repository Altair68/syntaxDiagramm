= README =

This is dynamically generated documentation for the Cinco FlowGraph example project with features
selected during project setup.


== Getting Started ==

Please note: You selected one or more features that produced Java source files. As they depend on classes
generated from the MGL, the project will report build errors (indicated by the red X marker) until you
generate the Cinco Product.

Generate your Cinco Product: right-click on /syntaxDiagramm/model/FlowGraphTool.cpd and
select 'Generate Cinco Product'

Start your generated Cinco Product: right-click on /syntaxDiagramm and
select 'Run as > Eclipse Application'.

Before you can start modeling, you need to create a project: right-click in the Project Explorer and
select 'New > New FlowGraphTool Project', give the project a name and click 'Finish'.

Now start a first FlowGraph model: right-click on your created project and select 'New > New FlowGraph'.

See below for details on the available modeling elements and effects of additional features selected
during project initialization.


== General Features ==

Basic FlowGraph models consist of three types of nodes and two types of edges:

* 'Start' nodes are shown as a green circle and can may have exactly one outgoing 'Transition'

* 'Activity' nodes have attributes 'name' and 'description' and are shown as a blue rectangle
  showing the name.  They can have multiple outgoing 'LabeledTransition' edges, and multiple incoming
  edges of arbitrary type.

* 'End' nodes are shown as a red double circle and can have multiple incoming edges of arbitrary type.


== Additional Features ==	

=== Custom Action ===

Cinco's custom actions allow one to execute arbitrary code based on the selected element. This includes
analyzing the model, transforming the model, code generation, etc. Currently, the action can be added to
the element in the MGL with two possible annotations: @contextMenuAction(...) and @doubleClickAction(...).
While the first appears in the menu for the user to choose when right-clicking on the element, the second
one is automatically executed on double-clicking the element. In the FlowGraph example, a custom action
is added to the 'Start' node. It searches for the shortest path to an 'End' node and displays the number
of required steps in a dialog window.


=== Code Generator ===

The example code generator is implemented in Xtend, as it is compatible with Java (it actually generates
.java files from .xtend files), but provides several syntactic ehancements and has built-in support for
templates. See https://eclipse.org/xtend/documentation/ for more information on Xtend.
Code generators are usually very specific to the target domain, and there is no meaningful execution semantics
for our	FlowGraph model. So, the example code generator only enumerates all nodes of the model and prints
some general information about them.



