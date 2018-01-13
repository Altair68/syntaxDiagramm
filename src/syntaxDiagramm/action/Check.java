package syntaxDiagramm.action;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import graphmodel.Node;
import syntaxDiagramm.flowgraph.End;
import syntaxDiagramm.flowgraph.EndBranch;
import syntaxDiagramm.flowgraph.Start;
import syntaxDiagramm.flowgraph.FlowGraph;
import syntaxDiagramm.flowgraph.Variable;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.jface.dialogs.MessageDialog;

import de.jabc.cinco.meta.core.ge.style.model.customfeature.CincoCustomAction;

public class Check extends CincoCustomAction<FlowGraph> {
	public Check(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Check Syntax";
	}
	
	@Override
	public boolean canExecute(FlowGraph graph) {
		return true;
	}
	
	public void execute(FlowGraph graph) {
		EList<Start> startzustaende = graph.getStarts();
		StringBuilder message = new StringBuilder();
		if (startzustaende.size() < 1) message.append("Es muss ein Startzustand existieren.\n");
		if (startzustaende.size() > 1) message.append("Es ist nur 1 Startzustand erlaubt.\n");
		
		EList<Node> checkedItems = new BasicEList<Node>();
		for(Start startNode : startzustaende) {
			IterateSuccessors(startNode, message, checkedItems);
		}
		
		if (message.length() > 0) {
			MessageDialog.openInformation(null, "Diagrammfehler", message.toString());
		}
	}
	
	private void IterateSuccessors(Node node, StringBuilder message, EList<Node> checkedItems) {
		checkedItems.add(node);
		//message.append("Iterate: " + node.getClass().toString() + "\n");
		for (Node successor : node.getSuccessors(Node.class)) {
			//message.append("Successor: " + successor.getClass().toString() + "\n");
			// Wenn der Nachfolger ein Objekt ist, bei dem wir schon waren (ausgenommen EndBranch und End), dann handelt es sich um einen Rücksprung
			// Rücksprünge sind grundsätzlich nur für Variablen auf sich selbst zugelassen.
			if (checkedItems.contains(successor) && !(successor instanceof EndBranch || successor instanceof End) && (node != successor || !(node instanceof Variable))) {
				message.append("Rücksprung von " + node.getId() + " (" + node.getClass().toString() + ") zu " + successor.getId() + " (" + successor.getClass().toString() + ") nicht erlaubt!\n");
				break;
			}
			if (node != successor) {
				IterateSuccessors(successor, message, checkedItems);
			}
			
		}
	}

}
