package de.slisson.mps.editor.multiline.demolang.editor;

/*Generated by MPS */

import jetbrains.mps.nodeEditor.EditorCellKeyMap;
import jetbrains.mps.nodeEditor.EditorCellKeyMapAction;
import java.awt.event.KeyEvent;
import jetbrains.mps.nodeEditor.EditorContext;
import jetbrains.mps.nodeEditor.cells.EditorCell;
import jetbrains.mps.smodel.SNode;
import java.util.List;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SPropertyOperations;

public class km extends EditorCellKeyMap {
  public km() {
    this.setApplicableToEveryModel(false);
    EditorCellKeyMapAction action;
    action = new km.km_Action0();
    this.putAction("any", "VK_ENTER", action);
  }

  public static class km_Action0 extends EditorCellKeyMapAction {
    public km_Action0() {
      this.setShownInPopupMenu(false);
    }

    public boolean isMenuAlwaysShown() {
      return false;
    }

    public boolean canExecute(final KeyEvent keyEvent, final EditorContext editorContext) {
      EditorCell contextCell = editorContext.getContextCell();
      if ((contextCell == null)) {
        return false;
      }
      SNode contextNode = contextCell.getSNode();
      if (contextNode == null) {
        return false;
      }
      if (contextNode.isInstanceOfConcept("de.slisson.mps.editor.multiline.demolang.structure.ConceptA")) {
        return true;
      }
      return false;
    }

    public void execute(final KeyEvent keyEvent, final EditorContext editorContext) {
      EditorCell contextCell = editorContext.getContextCell();
      this.execute_internal(keyEvent, editorContext, contextCell.getSNode(), this.getSelectedNodes(editorContext));
    }

    private void execute_internal(final KeyEvent keyEvent, final EditorContext editorContext, final SNode node, final List<SNode> selectedNodes) {
      SPropertyOperations.set(node, "text", SPropertyOperations.getString(node, "text"));
    }

    public String getKeyStroke() {
      return " ENTER";
    }
  }
}