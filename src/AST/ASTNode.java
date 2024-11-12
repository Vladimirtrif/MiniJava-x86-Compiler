package AST;

import AST.Visitor.Visitor;
import Semantics.ADT;
import Semantics.BaseADT;
import Semantics.UndefinedADT;
import java_cup.runtime.ComplexSymbolFactory.Location;

abstract public class ASTNode {
  // Line number in source file.
  public final int line_number;
  public ADT type;

  // Constructor
  public ASTNode(Location pos) {
    this.line_number = pos.getLine();
    this.type = UndefinedADT.UNDEFINED;
  }
}
