package syntaxDiagramm.codegen;

import de.jabc.cinco.meta.core.utils.EclipseFileUtils;
import de.jabc.cinco.meta.plugin.generator.runtime.IGenerator;
import graphmodel.Edge;
import graphmodel.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import syntaxDiagramm.flowgraph.Branch;
import syntaxDiagramm.flowgraph.End;
import syntaxDiagramm.flowgraph.FlowGraph;
import syntaxDiagramm.flowgraph.Start;
import syntaxDiagramm.flowgraph.Terminal;
import syntaxDiagramm.flowgraph.Variable;

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
    final CharSequence main = this.generateMain(model);
    IWorkspace _workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root = _workspace.getRoot();
    IPath _append = targetDir.append("Main.java");
    final IFile mainTargetFile = _root.getFileForLocation(_append);
    EclipseFileUtils.writeToFile(mainTargetFile, main);
  }
  
  private CharSequence generateMain(final FlowGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("PARSER_BEGIN(");
    String _modelName = model.getModelName();
    _builder.append(_modelName, "");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _modelName_1 = model.getModelName();
    _builder.append(_modelName_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("public static void main(String[] args) throws ParseException {");
    _builder.newLine();
    _builder.append("\t\t");
    String _modelName_2 = model.getModelName();
    _builder.append(_modelName_2, "\t\t");
    _builder.append(" parser = new ");
    String _modelName_3 = model.getModelName();
    _builder.append(_modelName_3, "\t\t");
    _builder.append("(System.in);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("System.out.println(\"Eingabe: \");");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("parser.");
    String _functionName = model.getFunctionName();
    _builder.append(_functionName, "\t\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("} catch (Exception e) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("System.err.println(e);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("PARSER_END(");
    String _modelName_4 = model.getModelName();
    _builder.append(_modelName_4, "");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("void ");
    String _functionName_1 = model.getFunctionName();
    _builder.append(_functionName_1, "");
    _builder.append("():");
    _builder.newLineIfNotEmpty();
    _builder.append("{}");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    EList<Start> _starts = model.getStarts();
    Start _get = _starts.get(0);
    CharSequence _generateNode = this.generateNode(model, _get);
    _builder.append(_generateNode, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence generateNode(final FlowGraph model, final Node aNode) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    {
      if ((aNode instanceof Start)) {
        {
          EList<Edge> _outgoing = ((Start)aNode).getOutgoing();
          for(final Edge Trans : _outgoing) {
            Node _targetElement = Trans.getTargetElement();
            Object _generateNode = this.generateNode(model, _targetElement);
            _builder.append(_generateNode, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      if ((aNode instanceof End)) {
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if ((aNode instanceof Terminal)) {
        String _name = ((Terminal)aNode).getName();
        _builder.append(_name, "");
        _builder.newLineIfNotEmpty();
        {
          EList<Edge> _outgoing_1 = ((Terminal)aNode).getOutgoing();
          for(final Edge Trans_1 : _outgoing_1) {
            Node _targetElement_1 = Trans_1.getTargetElement();
            Object _generateNode_1 = this.generateNode(model, _targetElement_1);
            _builder.append(_generateNode_1, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    {
      if ((aNode instanceof Variable)) {
        _builder.append("\t");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if ((aNode instanceof Branch)) {
        {
          EList<Edge> _outgoing_2 = ((Branch)aNode).getOutgoing();
          for(final Edge Trans_2 : _outgoing_2) {
            _builder.append("[");
            _builder.newLine();
            Node _targetElement_2 = Trans_2.getTargetElement();
            Object _generateNode_2 = this.generateNode(model, _targetElement_2);
            _builder.append(_generateNode_2, "");
            _builder.newLineIfNotEmpty();
            _builder.append("]");
            _builder.newLine();
          }
        }
      }
    }
    return _builder;
  }
}
