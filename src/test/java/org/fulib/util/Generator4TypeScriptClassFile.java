package org.fulib.util;

import org.fulib.Parser;
import org.fulib.TypeScriptParser;
import org.fulib.classmodel.Clazz;
import org.fulib.classmodel.FileFragmentMap;

public class Generator4TypeScriptClassFile
{
   private String customTemplatesFile;

   public Generator4TypeScriptClassFile setCustomTemplatesFile(String customTemplatesFile)
   {
      this.customTemplatesFile = customTemplatesFile;
      return this;
   }

   public void generate(Clazz clazz)
   {
      String classFileName = clazz.getModel().getPackageSrcFolder() + clazz.getName() + ".ts";
      FileFragmentMap fragmentMap = TypeScriptParser.parse(classFileName);


   }
}
