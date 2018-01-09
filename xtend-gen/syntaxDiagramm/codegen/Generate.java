package syntaxDiagramm.codegen;

import de.jabc.cinco.meta.core.utils.EclipseFileUtils;
import de.jabc.cinco.meta.plugin.generator.runtime.IGenerator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import syntaxDiagramm.flowgraph.End;
import syntaxDiagramm.flowgraph.FlowGraph;
import syntaxDiagramm.flowgraph.Start;
import syntaxDiagramm.flowgraph.Transition;

/**
 * Example class that generates code for a given FlowGraph model. As different
 *  feature examples might or might not be included (e.g. the external component
 *  library or swimlanes), this generator only does stupidly enumerate all
 *  nodes and prints some general information about them.
 */
@SuppressWarnings("all")
public class Generate implements IGenerator<FlowGraph> {
  public void generate(final FlowGraph model, final IPath targetDir, final IProgressMonitor monitor) {
    String _modelName = model.getModelName();
    boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(_modelName);
    if (_isNullOrEmpty) {
      throw new RuntimeException("Model\'s name must be set.");
    }
    final CharSequence transition = this.generateTransition();
    final CharSequence zustand = this.generateZustand();
    final CharSequence start = this.generateStart();
    final CharSequence end = this.generateEnd();
    final CharSequence main = this.generateMain(model);
    IWorkspace _workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root = _workspace.getRoot();
    IPath _append = targetDir.append("Transition.java");
    final IFile transitionTargetFile = _root.getFileForLocation(_append);
    IWorkspace _workspace_1 = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root_1 = _workspace_1.getRoot();
    IPath _append_1 = targetDir.append("Zustand.java");
    final IFile zustandTargetFile = _root_1.getFileForLocation(_append_1);
    IWorkspace _workspace_2 = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root_2 = _workspace_2.getRoot();
    IPath _append_2 = targetDir.append("Start.java");
    final IFile startTargetFile = _root_2.getFileForLocation(_append_2);
    IWorkspace _workspace_3 = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root_3 = _workspace_3.getRoot();
    IPath _append_3 = targetDir.append("End.java");
    final IFile endTargetFile = _root_3.getFileForLocation(_append_3);
    IWorkspace _workspace_4 = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root_4 = _workspace_4.getRoot();
    IPath _append_4 = targetDir.append("Main.java");
    final IFile mainTargetFile = _root_4.getFileForLocation(_append_4);
    EclipseFileUtils.writeToFile(transitionTargetFile, transition);
    EclipseFileUtils.writeToFile(zustandTargetFile, zustand);
    EclipseFileUtils.writeToFile(startTargetFile, start);
    EclipseFileUtils.writeToFile(endTargetFile, end);
    EclipseFileUtils.writeToFile(mainTargetFile, main);
  }
  
  private CharSequence generateTransition() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public class Transition {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Zustand Ziel;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Transition(Zustand aZiel) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("ziel = aZiel;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Zustand getZiel() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return ziel;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("} ");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateZustand() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Vector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class Zustand {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private String name;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Vector<Transition> transitions;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Zustand(String aName) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("name = aName;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String getName() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return name;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void setName(String aName) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("name = aName;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Vector<Transition> getTransitions() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return transitions;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void setTransitions(Vector<Transition> someTransitions) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("transitions = someTransitions;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateStart() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Vector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class Start extends Zustand {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Start() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("super(\"Z0\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateEnd() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Vector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class End extends Zustand {");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("End() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("super(\"E99999\");");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateMain(final FlowGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Vector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class Main {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Vector<Zustand> zustaende = new Vector<>();");
    _builder.newLine();
    {
      EList<Start> _starts = model.getStarts();
      for(final Start Start : _starts) {
        _builder.append("\t\t");
        _builder.append("zustaende.add(new Startzustand(\"");
        String _id = Start.getId();
        _builder.append(_id, "\t\t");
        _builder.append("\"));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      EList<End> _ends = model.getEnds();
      for(final End End : _ends) {
        _builder.append("\t\t");
        _builder.append("zustaende.add(new Endzustand(\"");
        String _id_1 = End.getId();
        _builder.append(_id_1, "\t\t");
        _builder.append("\"));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Vector<Transition> transitions = new Vector<>();");
    _builder.newLine();
    {
      EList<Transition> _transitions = model.getTransitions();
      for(final Transition Transition : _transitions) {
        _builder.append("\t\t");
        _builder.append("transitions.add(new Transition(\"");
        String _id_2 = Transition.getId();
        _builder.append(_id_2, "\t\t");
        _builder.append("\"));");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
