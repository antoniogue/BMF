package ie.ucd.clarity.bmf.GUI.common.formulas;

public interface Visitor {
	 public void visit(Group group);
	public void visit(Operator operator);

}
