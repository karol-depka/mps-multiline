package de.slisson.mps.editor.multiline.cells;

/*Generated by MPS */

import jetbrains.mps.nodeEditor.cells.EditorCell_Collection;
import jetbrains.mps.nodeEditor.EditorContext;
import jetbrains.mps.smodel.SNode;
import jetbrains.mps.nodeEditor.cellLayout.CellLayout_Indent;
import jetbrains.mps.nodeEditor.style.StyleAttributes;
import jetbrains.mps.nodeEditor.cells.EditorCell;
import java.util.List;
import jetbrains.mps.internal.collections.runtime.ListSequence;
import java.util.ArrayList;
import jetbrains.mps.internal.collections.runtime.Sequence;
import jetbrains.mps.internal.collections.runtime.ISelector;
import jetbrains.mps.internal.collections.runtime.IterableUtils;
import java.util.Iterator;
import jetbrains.mps.internal.collections.runtime.IVisitor;

public class EditorCell_Line extends EditorCell_Collection {
  private int myLineNum;

  public EditorCell_Line(EditorContext editorContext, SNode node, int lineNum) {
    super(editorContext, node, new CellLayout_Indent(), null);
    myLineNum = lineNum;
    getStyle().set(StyleAttributes.INDENT_LAYOUT_INDENT_ANCHOR, false);
    getStyle().set(StyleAttributes.INDENT_LAYOUT_WRAP_ANCHOR, true);

  }

  public void setIndentLayoutNewLine(boolean value) {
    getStyle().set(StyleAttributes.INDENT_LAYOUT_NEW_LINE, value);
  }

  @Override
  public void addCellAt(int i, EditorCell cell, boolean b) {
    if (!(cell instanceof EditorCell_Word)) {
      throw new IllegalArgumentException("Cells of type EditorCell_Word allowed only. Was of type: " + check_xrqfoi_a0a0a0a0b(check_xrqfoi_a0a0a0a0a1(cell)));

    }
    super.addCellAt(i, cell, b);
  }

  public Iterable<EditorCell_Word> getCellsBefore(EditorCell_Word cell) {
    List<EditorCell_Word> result = ListSequence.fromList(new ArrayList<EditorCell_Word>());
    int cellNum = getCellNumber(cell);
    if (cellNum >= 0) {
      ListSequence.fromList(result).addSequence(Sequence.fromIterable(Sequence.fromArray(getCells())).take(cellNum).select(new ISelector<EditorCell, EditorCell_Word>() {
        public EditorCell_Word select(EditorCell it) {
          return (EditorCell_Word) it;
        }
      }));
    }
    return result;
  }

  public void setCaretPosition(EditorContext context, int pos) {
    int remainingPos = pos;
    for (EditorCell_Word cell : Sequence.fromIterable(getWordCells())) {
      if (remainingPos <= cell.getText().length()) {
        context.getNodeEditorComponent().getSelectionManager().setSelection(cell, remainingPos);
        break;
      }
      remainingPos -= cell.getText().length() + 1;
    }
  }

  public String getText() {
    return IterableUtils.join(Sequence.fromIterable(getWordCells()).select(new ISelector<EditorCell_Word, String>() {
      public String select(EditorCell_Word it) {
        return it.getText();
      }
    }), " ");
  }

  public Iterable<EditorCell_Word> getWordCells() {
    return Sequence.fromIterable(Sequence.fromArray(super.getCells())).select(new ISelector<EditorCell, EditorCell_Word>() {
      public EditorCell_Word select(EditorCell it) {
        return (EditorCell_Word) it;
      }
    });
  }

  public void setWords(Iterable<String> words) {
    setNumberOfWordCells(Sequence.fromIterable(words).count());
    {
      Iterator<EditorCell_Word> cell_it = Sequence.fromIterable(getWordCells()).iterator();
      Iterator<String> word_it = Sequence.fromIterable(words).iterator();
      EditorCell_Word cell_var;
      String word_var;
      while (cell_it.hasNext() && word_it.hasNext()) {
        cell_var = cell_it.next();
        word_var = word_it.next();
        if (neq_xrqfoi_a0c0e0b0g(cell_var.getText(), word_var)) {
          cell_var.setText(word_var);
        }
      }
    }
  }

  public void setNumberOfWordCells(int count) {
    while (getCellsCount() > count) {
      removeCell(Sequence.fromIterable(Sequence.fromArray(getCells())).last());
    }
    while (getCellsCount() < count) {
      addEditorCell(getParent().newWordCell(myLineNum, getCellsCount()));
    }
    Sequence.fromIterable(getWordCells()).visitAll(new IVisitor<EditorCell_Word>() {
      public void visit(EditorCell_Word it) {
        it.setRightPadding();
      }
    });
    check_xrqfoi_a3a7(Sequence.fromIterable(getWordCells()).last(), this);
  }

  @Override
  public void synchronizeViewWithModel() {
    check_xrqfoi_a0a8(getParent(), this);
  }

  @Override
  public EditorCell_Multiline getParent() {
    return (EditorCell_Multiline) super.getParent();
  }

  private static String check_xrqfoi_a0a0a0a0b(Class<?> checkedDotOperand) {
    if (null != checkedDotOperand) {
      return checkedDotOperand.getName();
    }
    return null;
  }

  private static Class<?> check_xrqfoi_a0a0a0a0a1(EditorCell checkedDotOperand) {
    if (null != checkedDotOperand) {
      return checkedDotOperand.getClass();
    }
    return null;
  }

  private static void check_xrqfoi_a3a7(EditorCell_Word checkedDotOperand, EditorCell_Line checkedDotThisExpression) {
    if (null != checkedDotOperand) {
      checkedDotOperand.removeRightPadding();
    }

  }

  private static void check_xrqfoi_a0a8(EditorCell_Multiline checkedDotOperand, EditorCell_Line checkedDotThisExpression) {
    if (null != checkedDotOperand) {
      checkedDotOperand.synchronizeViewWithModel();
    }

  }

  private static boolean neq_xrqfoi_a0c0e0b0g(Object a, Object b) {
    return !((a != null ?
      a.equals(b) :
      a == b
    ));
  }
}
