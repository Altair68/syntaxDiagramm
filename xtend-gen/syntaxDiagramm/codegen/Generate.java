package syntaxDiagramm.codegen;

import de.jabc.cinco.meta.core.utils.EclipseFileUtils;
import de.jabc.cinco.meta.plugin.generator.runtime.IGenerator;
import graphmodel.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import syntaxDiagramm.flowgraph.FlowGraph;

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
    final CharSequence code = this.generateCode(model);
    IWorkspace _workspace = ResourcesPlugin.getWorkspace();
    IWorkspaceRoot _root = _workspace.getRoot();
    String _modelName_1 = model.getModelName();
    String _plus = (_modelName_1 + ".txt");
    IPath _append = targetDir.append(_plus);
    final IFile targetFile = _root.getFileForLocation(_append);
    EclipseFileUtils.writeToFile(targetFile, code);
  }
  
  private CharSequence generateCode(final FlowGraph model) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("=== ");
    String _modelName = model.getModelName();
    _builder.append(_modelName, "");
    _builder.append(" ===");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("The model contains ");
    EList<Node> _allNodes = model.getAllNodes();
    int _size = _allNodes.size();
    _builder.append(_size, "");
    _builder.append(" nodes. Here\'s some general information about them:");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Node> _allNodes_1 = model.getAllNodes();
      for(final Node node : _allNodes_1) {
        _builder.append("* node ");
        String _id = node.getId();
        _builder.append(_id, "");
        _builder.append(" of type \'");
        EClass _eClass = node.eClass();
        String _name = _eClass.getName();
        _builder.append(_name, "");
        _builder.append("\' with ");
        EList<Node> _successors = node.<Node>getSuccessors();
        int _size_1 = _successors.size();
        _builder.append(_size_1, "");
        _builder.append(" successors and ");
        EList<Node> _predecessors = node.<Node>getPredecessors();
        int _size_2 = _predecessors.size();
        _builder.append(_size_2, "");
        _builder.append(" predecessors");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
