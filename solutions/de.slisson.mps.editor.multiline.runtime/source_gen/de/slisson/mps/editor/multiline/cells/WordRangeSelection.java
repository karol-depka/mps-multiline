package de.slisson.mps.editor.multiline.cells;

/*Generated by MPS */

import jetbrains.mps.nodeEditor.selection.AbstractMultipleSelection;
import jetbrains.mps.nodeEditor.EditorComponent;
import java.util.Map;
import jetbrains.mps.nodeEditor.cells.CellInfo;
import jetbrains.mps.nodeEditor.selection.SelectionStoreException;
import jetbrains.mps.nodeEditor.selection.SelectionRestoreException;
import jetbrains.mps.nodeEditor.cells.EditorCell;
import jetbrains.mps.nodeEditor.selection.SelectionInfo;
import jetbrains.mps.internal.collections.runtime.Sequence;
import jetbrains.mps.project.structure.modules.ModuleReference;
import jetbrains.mps.nodeEditor.CellActionType;
import jetbrains.mps.nodeEditor.selection.Selection;
import java.util.List;
import jetbrains.mps.internal.collections.runtime.ListSequence;
import java.util.ArrayList;
import jetbrains.mps.nodeEditor.selection.SelectionManager;
import jetbrains.mps.nodeEditor.selection.SingularSelection;

public class WordRangeSelection extends AbstractMultipleSelection {
  private static final String START_WORD_NUMBER_PROPERTY = "startWordNumber";
  private static final String END_WORD_NUMBER_PROPERTY = "endWordNumber";
  private static final String LEFT_DIRECTION_PROPERTY = "leftDirection";

  private EditorCell_Multiline myMultilineCell;
  private int myStartWordNumber;
  private int myEndWordNumber;
  private boolean myLeftDirection;

  public WordRangeSelection(EditorComponent editorComponent, Map<String, String> properties, CellInfo cellInfo) throws SelectionStoreException, SelectionRestoreException {
    super(editorComponent);
    if (cellInfo == null) {
      throw new SelectionStoreException("Requred CellInfo parameter is null");
    }
    EditorCell editorCell = cellInfo.findCell(editorComponent);
    if (editorCell instanceof EditorCell_Multiline) {
      myMultilineCell = (EditorCell_Multiline) editorCell;
    } else {
      throw new SelectionRestoreException();
    }
    myLeftDirection = SelectionInfo.Util.getBooleanProperty(properties, WordRangeSelection.LEFT_DIRECTION_PROPERTY);
    myStartWordNumber = SelectionInfo.Util.getIntProperty(properties, WordRangeSelection.START_WORD_NUMBER_PROPERTY);
    if (myStartWordNumber < 0) {
      throw new SelectionStoreException("Only positive column numbers are supported: " + myStartWordNumber);
    }
    myEndWordNumber = SelectionInfo.Util.getIntProperty(properties, END_WORD_NUMBER_PROPERTY);
    if (myEndWordNumber < 0) {
      throw new SelectionStoreException("Only positive column numbers are supported: " + myEndWordNumber);
    }
    if (myStartWordNumber > myEndWordNumber) {
      throw new SelectionRestoreException();
    }
    int lineSize = Sequence.fromIterable(myMultilineCell.getWordCells()).count();
    if (myEndWordNumber >= lineSize) {
      throw new SelectionRestoreException();
    }
    initSelectedCells();
  }

  public WordRangeSelection(EditorComponent editorComponent, EditorCell_Multiline multilineCell, int startWordNumber, int endWordNumber, boolean leftDirection) {
    super(editorComponent);
    myMultilineCell = multilineCell;
    myLeftDirection = leftDirection;
    myStartWordNumber = startWordNumber;
    myEndWordNumber = endWordNumber;
    int lineSize = Sequence.fromIterable(myMultilineCell.getWordCells()).count();
    assert myStartWordNumber >= 0;
    assert myStartWordNumber <= myEndWordNumber;
    assert myEndWordNumber < lineSize;
    initSelectedCells();
  }

  public SelectionInfo getSelectionInfo() throws SelectionStoreException {
    SelectionInfo selectionInto = new SelectionInfo(this.getClass().getName(), ModuleReference.fromString("31c91def-a131-41a1-9018-102874f49a12(de.slisson.mps.editor.multiline)").getModuleFqName());
    selectionInto.setCellInfo(myMultilineCell.getCellInfo());
    selectionInto.getPropertiesMap().put(WordRangeSelection.LEFT_DIRECTION_PROPERTY, Boolean.toString(myLeftDirection));
    selectionInto.getPropertiesMap().put(WordRangeSelection.START_WORD_NUMBER_PROPERTY, Integer.toString(myStartWordNumber));
    selectionInto.getPropertiesMap().put(END_WORD_NUMBER_PROPERTY, Integer.toString(myEndWordNumber));
    return selectionInto;
  }

  @Override
  public void executeAction(CellActionType type) {
    if (CellActionType.SELECT_LEFT == type || CellActionType.SELECT_RIGHT == type) {
      if ((myLeftDirection ?
        CellActionType.SELECT_LEFT == type :
        CellActionType.SELECT_RIGHT == type
      )) {
        enlargeSelection();
      } else {
        reduceSelection();
      }
      return;
    }
    super.executeAction(type);
  }

  public boolean isSame(Selection selection) {
    if (this == selection) {
      return true;
    }
    if (selection == null || getClass() != selection.getClass()) {
      return false;
    }
    WordRangeSelection that = (WordRangeSelection) selection;
    if (!(myMultilineCell.equals(that.myMultilineCell))) {
      return false;
    }
    return myStartWordNumber == that.myStartWordNumber && myEndWordNumber == that.myEndWordNumber;
  }

  private void initSelectedCells() {
    List<EditorCell> selection = ListSequence.fromList(new ArrayList<EditorCell>());
    int index = -1;
    int startCellNumber = Math.min(myStartWordNumber, myEndWordNumber);
    int endCellNumber = Math.max(myStartWordNumber, myEndWordNumber);
    for (EditorCell_Word wordCell : Sequence.fromIterable(myMultilineCell.getWordCells())) {
      index++;
      if (index < startCellNumber) {
        continue;
      }
      if (index > endCellNumber) {
        break;
      }
      ListSequence.fromList(selection).addElement(wordCell);
    }
    setSelectedCells(selection);
  }

  private void enlargeSelection() {
    int newStartWordNumber = myStartWordNumber;
    int newEndWordNumber = myEndWordNumber;
    if (myLeftDirection) {
      if (newStartWordNumber > 0) {
        newStartWordNumber--;
      }
    } else {
      int wordCellsNum = Sequence.fromIterable(myMultilineCell.getWordCells()).count();
      if (newEndWordNumber < wordCellsNum - 1) {
        newEndWordNumber++;
      }
    }

    SelectionManager selectionManager = getEditorComponent().getSelectionManager();
    Selection newSelection;
    if (newStartWordNumber != myStartWordNumber || newEndWordNumber != myEndWordNumber) {
      newSelection = new WordRangeSelection(getEditorComponent(), myMultilineCell, newStartWordNumber, newEndWordNumber, myLeftDirection);
    } else {
      newSelection = selectionManager.createSelection(myMultilineCell.getParent());
      if (newSelection instanceof SingularSelection) {
        ((SingularSelection) newSelection).setSideSelectDirection((myLeftDirection ?
          SingularSelection.SideSelectDirection.LEFT :
          SingularSelection.SideSelectDirection.RIGHT
        ));
      }
    }
    selectionManager.pushSelection(newSelection);
  }

  private void reduceSelection() {
    SelectionManager selectionManager = getEditorComponent().getSelectionManager();
    selectionManager.popSelection();
  }
}
