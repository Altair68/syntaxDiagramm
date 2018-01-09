
package syntaxDiagramm.codegen

import de.jabc.cinco.meta.plugin.generator.runtime.IGenerator
import syntaxDiagramm.flowgraph.FlowGraph
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.resources.ResourcesPlugin
import de.jabc.cinco.meta.core.utils.EclipseFileUtils

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

		val transition = generateTransition();
		val zustand = generateZustand();
		val start = generateStart();
		val end = generateEnd();
		
		val transitionTargetFile = ResourcesPlugin.workspace.root.getFileForLocation(targetDir.append("Transition.java"))
		val zustandTargetFile = ResourcesPlugin.workspace.root.getFileForLocation(targetDir.append("Zustand.java"))
		val startTargetFile = ResourcesPlugin.workspace.root.getFileForLocation(targetDir.append("Start.java"))
		val endTargetFile = ResourcesPlugin.workspace.root.getFileForLocation(targetDir.append("End.java"))

		EclipseFileUtils.writeToFile(transitionTargetFile, transition)
		EclipseFileUtils.writeToFile(zustandTargetFile, zustand)
		EclipseFileUtils.writeToFile(startTargetFile, start)
		EclipseFileUtils.writeToFile(endTargetFile, end)

	}
	
	private def generateTransition()'''
		public class Transition {
			
			private Zustand Ziel;
			
			Transition(Zustand aZiel) {
				ziel = aZiel;
			}
			
			public Zustand getZiel() {
				return ziel;
			} 
		}
	'''
	
	private def generateZustand()'''
	import java.util.Vector;
	
	public class Zustand {
		private String name;
		private Vector<Transition> transitions;
		
		Zustand(String aName) {
			name = aName;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String aName) {
			name = aName;
		}
		
		public Vector<Transition> getTransitions() {
			return transitions;
		}
		
		public void setTransitions(Vector<Transition> someTransitions) {
			transitions = someTransitions;
		}
	}
	'''
	
	private def generateStart()'''
	import java.util.Vector;
	
	public class Start extends Zustand {
	
		Start() {
			this("Z0");
		}
	}
	'''
	
	private def generateEnd()'''
	import java.util.Vector;
	
	public class End extends Zustand {
	
		End() {
			this("E99999");
		}
	}
	'''

}
	
