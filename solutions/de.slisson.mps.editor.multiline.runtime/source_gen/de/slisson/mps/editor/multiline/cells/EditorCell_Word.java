package de.slisson.mps.editor.multiline.cells;

/*Generated by MPS */

import jetbrains.mps.nodeEditor.cells.EditorCell_Property;
import jetbrains.mps.nodeEditor.style.Padding;
import jetbrains.mps.nodeEditor.style.Measure;
import jetbrains.mps.logging.Logger;
import jetbrains.mps.nodeEditor.EditorContext;
import jetbrains.mps.nodeEditor.cells.ModelAccessor;
import jetbrains.mps.smodel.SNode;
import jetbrains.mps.nodeEditor.style.Style;
import jetbrains.mps.nodeEditor.style.StyleAttributes;
import jetbrains.mps.nodeEditor.CellActionType;
import jetbrains.mps.nodeEditor.EditorCellKeyMap;
import jetbrains.mps.smodel.NodeReadAccessInEditorListener;
import jetbrains.mps.util.Pair;
import jetbrains.mps.smodel.NodeReadAccessCasterInEditor;
import jetbrains.mps.nodeEditor.cells.PropertyAccessor;

public class EditorCell_Word extends EditorCell_Property {
  private static final Padding HALF_SPACE_PADDING = new Padding(0.5, Measure.SPACES);
  private static final Padding FULL_SPACE_PADDING = new Padding(1.0, Measure.SPACES);
  private static final Logger LOG = Logger.getLogger(EditorCell_Word.class);

  public EditorCell_Word(EditorContext context, ModelAccessor modelAccessor, SNode node) {
    super(context, modelAccessor, node);
    setEditable(true);
    setDefaultText("");
    Style style = this.getStyle();
    style.set(StyleAttributes.PADDING_RIGHT, FULL_SPACE_PADDING);
    setAction(CellActionType.INSERT, new NewLineAction(this));
    setAction(CellActionType.INSERT_BEFORE, new NewLineAction(this));

    EditorCellKeyMap km = new EditorCellKeyMap();
    km.putAction("any", "VK_TAB", new TabAction(this));
    addKeyMap(km);
  }

  @Override
  public boolean canPasteText() {
    return isEditable();
  }

  @Override
  public void setText(String string) {
    super.setText(string);
  }

  @Override
  public boolean executeTextAction(CellActionType type, boolean allowErrors) {
    if (type == CellActionType.BACKSPACE || type == CellActionType.DELETE) {
      if (getCaretPosition() == 0) {
        executePrecedingCharacterDelete();
        return true;
      }
    }
    return super.executeTextAction(type, allowErrors);
  }

  public void executePrecedingCharacterDelete() {
    getEditorContext().executeCommand(new Runnable() {
      public void run() {
        deletePrecedingCharacter();
      }
    });
  }

  private void deletePrecedingCharacter() {
    EditorCell_Multiline mlCell = this.getParentMultiline();
    if (mlCell != null) {
      int caretPos = mlCell.getCaretPosition();
      if (caretPos > 0) {
        String text = mlCell.getTextBeforeCaret();
        text = text.substring(0, text.length() - 1);
        text += mlCell.getTextAfterCaret();
        mlCell.setText(text);
        mlCell.setCaretPosition(caretPos - 1);
      }
    }
  }

  public void insertText(String text) {
    EditorCell_Multiline mlCell = getParentMultiline();
    if (mlCell != null) {
      int newCaretPos = mlCell.getCaretPosition() + text.length();
      super.insertText(text);
      mlCell.setCaretPosition(newCaretPos);
    }
  }

  public EditorCell_Multiline getParentMultiline() {
    return check_xru0dp_a0a6(getParent(), this);
  }

  @Override
  public EditorCell_Line getParent() {
    return (EditorCell_Line) super.getParent();
  }

  public void removeLeftPadding() {
    getStyle().set(StyleAttributes.PADDING_LEFT, (Padding) null);
  }

  public void removeRightPadding() {
    getStyle().set(StyleAttributes.PADDING_RIGHT, (Padding) null);
  }

  public void setRightPadding() {
    getStyle().set(StyleAttributes.PADDING_RIGHT, FULL_SPACE_PADDING);
  }

  @Override
  public void setCaretPositionIfPossible(int i) {
    if (i == getText().length() + 1) {
      int pos = getParentMultiline().getTextBefore(this, getText().length()).length() + 1;
      getParentMultiline().setCaretPosition(pos);
    } else {
      super.setCaretPositionIfPossible(i);
    }
  }

  public String getTextBefore(int pos) {
    String result = getText();
    result = result.substring(0, Math.min(pos, result.length()));
    return result;
  }

  @Override
  public void synchronizeViewWithModel() {
    check_xru0dp_a0a31(getParentMultiline(), this);
  }

  private static void addPropertyDependenciesToEditor(NodeReadAccessInEditorListener listener, EditorCell_Word result) {
    for (Pair pair : listener.popCleanlyReadAccessedProperties()) {
      result.getEditor().addCellDependentOnNodeProperty(result, pair);
    }
  }

  public static EditorCell_Word create(EditorContext editorContext, ModelAccessor modelAccessor, SNode node) {
    NodeReadAccessInEditorListener listener = NodeReadAccessCasterInEditor.getReadAccessListener();
    if (modelAccessor instanceof PropertyAccessor) {
      if (listener != null) {
        listener.clearCleanlyReadAccessProperties();
      }
    }
    EditorCell_Word result = new EditorCell_Word(editorContext, modelAccessor, node);
    if (listener != null) {
      EditorCell_Word.addPropertyDependenciesToEditor(listener, result);
    }
    return result;
  }

  private static EditorCell_Multiline check_xru0dp_a0a6(EditorCell_Line checkedDotOperand, EditorCell_Word checkedDotThisExpression) {
    if (null != checkedDotOperand) {
      return checkedDotOperand.getParent();
    }
    return null;
  }

  private static void check_xru0dp_a0a31(EditorCell_Multiline checkedDotOperand, EditorCell_Word checkedDotThisExpression) {
    if (null != checkedDotOperand) {
      checkedDotOperand.synchronizeViewWithModel();
    }

  }
}
