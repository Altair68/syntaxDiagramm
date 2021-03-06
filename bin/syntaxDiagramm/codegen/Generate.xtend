
package syntaxDiagramm.codegen

import de.jabc.cinco.meta.plugin.generator.runtime.IGenerator
import syntaxDiagramm.flowgraph.FlowGraph
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.resources.ResourcesPlugin
import de.jabc.cinco.meta.core.utils.EclipseFileUtils
import syntaxDiagramm.flowgraph.Transition
import graphmodel.Edge
import syntaxDiagramm.flowgraph.Variable
import graphmodel.Node
import syntaxDiagramm.flowgraph.Terminal
import syntaxDiagramm.flowgraph.Start
import syntaxDiagramm.flowgraph.End
import syntaxDiagramm.flowgraph.StartBranch
import syntaxDiagramm.flowgraph.EndBranch
import java.util.stream.Collectors
import java.util.function.Predicate
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import java.util.List

/**
 *  Example class that generates code for a given FlowGraph model. As different
 *  feature examples might or might not be included (e.g. the external component
 *  library or swimlanes), this generator only does stupidly enumerate all
 *  nodes and prints some general information about them.
 *
 */
class Generate implements IGenerator<FlowGraph> {
	
	override generate(FlowGraph model, IPath targetDir, IProgressMonitor monitor) {

		if (model.modelName.nullOrEmpty)
			throw new RuntimeException("Model's name must be set.")

		val main = generateMain(model);
		
		val mainTargetFile = ResourcesPlugin.workspace.root.getFileForLocation(targetDir.append(model.modelName + ".jj"));

		EclipseFileUtils.writeToFile(mainTargetFile, main)

	}
	
def EList<Edge> removeEndBranch(EList<Edge> cltn) {
	var EList<Edge> test = new BasicEList<Edge>();
	for (t : cltn) {
		if (!(t.targetElement instanceof EndBranch)) {
			test.add(t);
		}
	}
	return test;
}
	
	
private def generateMain(FlowGraph model)'''
	options {
		LOOKAHEAD=100;
	}
	
	PARSER_BEGIN(«model.modelName»)
	
	public class «model.modelName» {
		public static void main(String[] args) throws ParseException {
			«model.modelName» parser = new «model.modelName»(System.in);
			try {
				System.out.println("Eingabe: ");
				parser.«model.functionName»();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
	
	PARSER_END(«model.modelName»)
	
	SKIP :
{
" "
| "\t"
| "\n"
| "\r"
}
	
	void «model.functionName»():
	{}
	{
		«generateNode(model, model.starts.get(0))»
	}
'''

private def generateNode(FlowGraph model, Node aNode)'''«
	IF aNode instanceof Start»«
		FOR Trans : aNode.outgoing»«
			generateNode(model, Trans.targetElement)»«
		ENDFOR»«
	ENDIF»«
	IF aNode instanceof End»«
	ENDIF»«
	IF aNode instanceof Terminal»"«
		aNode.name»" «
		FOR Trans : aNode.outgoing»«
			generateNode(model, Trans.targetElement)»«
		ENDFOR»«
	ENDIF»«
	IF aNode instanceof Variable»«
		aNode.name»() «
		FOR Trans : aNode.outgoing»«
			if(Trans.targetElement != aNode) generateNode(model, Trans.targetElement)»«
		ENDFOR»«
	ENDIF»«
	IF aNode instanceof StartBranch»[ «
		FOR Trans : removeEndBranch(aNode.outgoing) SEPARATOR ' | '»«
			generateNode(model, Trans.targetElement)»«
		ENDFOR»]«
	ENDIF»'''
}

