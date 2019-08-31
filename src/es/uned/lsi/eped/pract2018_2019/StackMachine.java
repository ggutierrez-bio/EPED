package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.*;

public class StackMachine {
	private Stack<Node> opStack;
	
	public StackMachine() {
		opStack = new Stack<>();
	}
	
	public Operand execute(SynTree syn) {
		BTree<Node> tree = syn.getSynTree();
		Queue<Node> input = new Queue<>();
		opStack.clear();
		postorder(tree, input);
		while (!input.isEmpty()) {
			Node n = input.getFirst();
			input.dequeue();
			switch(n.getNodeType()) {
			case OPERAND:
				opStack.push(n);
				break;
			case OPERATOR:
				operate((Operator)n);
				break;
			}
		}
		
		return (Operand) opStack.getTop();
	}
	
	private void postorder(BTreeIF<Node> t, Queue<Node> q) {
		if ( !t.isEmpty() ) {
			if ( t.getLeftChild() != null ) { postorder(t.getLeftChild(),q); }
			if ( t.getRightChild() != null ) { postorder(t.getRightChild(),q); }
			q.enqueue(t.getRoot());
		}
	}
	
	private void operate(Operator op) {
		Operand right = (Operand) opStack.getTop();
		opStack.pop();
		Operand left = (Operand) opStack.getTop();
		switch (op.getOperatorType()) {
		case ADD:
			left.add(right);
			break;
		case MULT:
			left.mult(right);
			break;
		case SUB:
			left.sub(right);
			break;
		default:
			break;
		}
	}
}
