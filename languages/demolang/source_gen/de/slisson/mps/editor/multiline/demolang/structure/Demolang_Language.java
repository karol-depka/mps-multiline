package de.slisson.mps.editor.multiline.demolang.structure;

/*Generated by MPS */

import jetbrains.mps.project.structure.modules.ModuleReference;
import jetbrains.mps.smodel.Language;
import jetbrains.mps.smodel.MPSModuleRepository;

public class Demolang_Language {
  public static ModuleReference MODULE_REFERENCE = ModuleReference.fromString("26a9201d-e70b-4755-acd6-40baf7a63b3a(de.slisson.mps.editor.multiline.demolang)");

  public static Language get() {
    return (Language) MPSModuleRepository.getInstance().getModule(MODULE_REFERENCE);
  }
}
