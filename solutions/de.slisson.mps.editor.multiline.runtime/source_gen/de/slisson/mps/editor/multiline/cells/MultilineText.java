package de.slisson.mps.editor.multiline.cells;

/*Generated by MPS */

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import jetbrains.mps.internal.collections.runtime.IterableUtils;
import jetbrains.mps.internal.collections.runtime.Sequence;
import jetbrains.mps.internal.collections.runtime.ISelector;

public class MultilineText {
  public static final String PROPERTY_TEXT = "text";
  public static final String PROPERTY_WORDS = "words";

  private PropertyChangeSupport myChangeSupport = new PropertyChangeSupport(this);
  private String myText;
  private String[][] myWords;

  public MultilineText(String text) {
    setText(text);
  }

  public void addListener(PropertyChangeListener l) {
    myChangeSupport.addPropertyChangeListener(l);
  }

  public void addListener(String propertyName, PropertyChangeListener l) {
    myChangeSupport.addPropertyChangeListener(propertyName, l);
  }

  public void removeListener(PropertyChangeListener l) {
    myChangeSupport.removePropertyChangeListener(l);
  }

  public void removeListener(String propertyName, PropertyChangeListener l) {
    myChangeSupport.removePropertyChangeListener(propertyName, l);
  }

  public void setText(String newText) {
    if (newText == null) {
      newText = "";
    }
    String oldText = myText;
    String[][] oldWords = myWords;
    String[][] newWords = textToWords(newText);
    this.myWords = newWords;
    this.myText = newText;
    if (neq_ua5mzx_a0g0e(newText, oldText)) {
      myChangeSupport.firePropertyChange(PROPERTY_TEXT, oldText, newText);
      myChangeSupport.firePropertyChange(PROPERTY_WORDS, oldWords, newWords);
    }
  }

  public void setTextSilently(String newText) {
    if (newText == null) {
      newText = "";
    }
    myText = newText;
    myWords = textToWords(newText);
  }

  public String getWord(int lineNum, int wordNum) {
    String[][] words = this.myWords;
    String word = null;
    if (lineNum < words.length) {
      if (wordNum < words[lineNum].length) {
        word = words[lineNum][wordNum];
      }
    }
    return word;
  }

  public void setWord(int lineNum, int wordNum, String newWord) {
    String[][] newWords = this.myWords;
    if (lineNum < newWords.length) {
      if (wordNum < newWords[lineNum].length) {
        newWords[lineNum][wordNum] = newWord;
      }
    }

    String newText = IterableUtils.join(Sequence.fromIterable(Sequence.fromArray(newWords)).select(new ISelector<String[], String>() {
      public String select(String[] line) {
        return IterableUtils.join(Sequence.fromIterable(Sequence.fromArray(line)), " ");
      }
    }), "\n");
    setText(newText);
  }

  public String[][] getWords() {
    return myWords;
  }

  public String getText() {
    return myText;
  }

  private static String[][] textToWords(String text) {
    String[][] words;
    if (text == null) {
      words = new String[0][0];
    } else {
      String[] lines = text.split("\\n", -1);
      words = new String[lines.length][];
      for (int lineNum = 0; lineNum < lines.length; ++lineNum) {
        words[lineNum] = lines[lineNum].split(" ", -1);
      }
    }
    return words;
  }

  private static boolean neq_ua5mzx_a0g0e(Object a, Object b) {
    return !((a != null ?
      a.equals(b) :
      a == b
    ));
  }
}