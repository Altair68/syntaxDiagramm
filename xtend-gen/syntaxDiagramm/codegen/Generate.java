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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import syntaxDiagramm.flowgraph.End;
import syntaxDiagramm.flowgraph.EndBranch;
import syntaxDiagramm.flowgraph.FlowGraph;
import syntaxDiagramm.flowgraph.Start;
import syntaxDiagramm.flowgraph.StartBranch;
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
    String _modelName_1 = model.getModelName();
    String _plus = (_modelName_1 + ".jj");
    IPath _append = targetDir.append(_plus);
    final IFile mainTargetFile = _root.getFileForLocation(_append);
    EclipseFileUtils.writeToFile(mainTargetFile, main);
  }
  
  public EList<Edge> removeEndBranch(final EList<Edge> cltn) {
    EList<Edge> test = new BasicEList<Edge>();
    for (final Edge t : cltn) {
      Node _targetElement = t.getTargetElement();
      if ((!(_targetElement instanceof EndBranch))) {
        test.add(t);
      }
    }
    return test;
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
    _builder.append("SKIP :");
    _builder.newLine();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\" \"");
    _builder.newLine();
    _builder.append("| \"\\t\"");
    _builder.newLine();
    _builder.append("| \"\\n\"");
    _builder.newLine();
    _builder.append("| \"\\r\"");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
    {
      if ((aNode instanceof Start)) {
        {
          EList<Edge> _outgoing = ((Start)aNode).getOutgoing();
          for(final Edge Trans : _outgoing) {
            Node _targetElement = Trans.getTargetElement();
            Object _generateNode = this.generateNode(model, _targetElement);
            _builder.append(_generateNode, "");
          }
        }
      }
    }
    {
      if ((aNode instanceof End)) {
      }
    }
    {
      if ((aNode instanceof Terminal)) {
        _builder.append("\"");
        String _name = ((Terminal)aNode).getName();
        _builder.append(_name, "");
        _builder.append("\" ");
        {
          EList<Edge> _outgoing_1 = ((Terminal)aNode).getOutgoing();
          for(final Edge Trans_1 : _outgoing_1) {
            Node _targetElement_1 = Trans_1.getTargetElement();
            Object _generateNode_1 = this.generateNode(model, _targetElement_1);
            _builder.append(_generateNode_1, "");
          }
        }
      }
    }
    {
      if ((aNode instanceof Variable)) {
        String _name_1 = ((Variable)aNode).getName();
        _builder.append(_name_1, "");
        _builder.append("() ");
        {
          EList<Edge> _outgoing_2 = ((Variable)aNode).getOutgoing();
          for(final Edge Trans_2 : _outgoing_2) {
            Node _targetElement_2 = Trans_2.getTargetElement();
            Object _generateNode_2 = this.generateNode(model, _targetElement_2);
            _builder.append(_generateNode_2, "");
          }
        }
      }
    }
    {
      if ((aNode instanceof StartBranch)) {
        _builder.append("[ ");
        {
          EList<Edge> _outgoing_3 = ((StartBranch)aNode).getOutgoing();
          EList<Edge> _removeEndBranch = this.removeEndBranch(_outgoing_3);
          boolean _hasElements = false;
          for(final Edge Trans_3 : _removeEndBranch) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(" | ", "");
            }
            Node _targetElement_3 = Trans_3.getTargetElement();
            Object _generateNode_3 = this.generateNode(model, _targetElement_3);
            _builder.append(_generateNode_3, "");
          }
        }
        _builder.append("]");
      }
    }
    return _builder;
  }
}
