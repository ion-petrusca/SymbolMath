package org.symbolmath.transformer;

import org.symbolmath.ast.ASTElement;
import org.symbolmath.ast.expression.MathExpression;
import org.symbolmath.ast.operation.ASTOperation;
import org.symbolmath.ast.operation.AddOperation;
import org.symbolmath.ast.operation.MultiplyOperation;
import org.symbolmath.parser.Parser;

public class Transformer {
  private ASTElement element;

  public Transformer(ASTElement element) {
    this.element = element;
  }

  public ASTElement transform() {
    ASTElement element2 = transform(element, MultiplyOperation.class);
    ASTElement element3 = transform(element, AddOperation.class);
    return element3;
  }

  private ASTElement transform(ASTElement element, Class<? extends ASTOperation> operationClass) {
    if (element.getClass().equals(operationClass)) {
      MathExpression expression = ((ASTOperation) element).createExpression();
      ASTElement parent = element.getParent();
      if (parent != null) {
        parent.replaceChild(element, expression);
      } else {
        element = expression;
      }
    }

    for (ASTElement child : element.getChildren()) {
      transform(child, operationClass);
    }

    return element;
  }
}
