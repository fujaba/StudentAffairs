package org.fulib;

import org.fulib.builder.ClassBuilder;
import org.fulib.builder.ClassModelBuilder;
import org.fulib.classmodel.FileFragmentMap;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GenTests
{
   @Test
   public void testParser()
   {
      FileFragmentMap fragmentMap = new TypeScriptParser()
            .doParse("src/segroupweb/src/model/seGroup.ts");

      assertThat(fragmentMap, not(equalTo(null)));

      // System.out.println(fragmentMap);
   }

   @Test
   public void testCodeGen() throws IOException, InterruptedException
   {
      ClassModelBuilder mb = Fulib.classModelBuilder("tGroupModel", "src/tGroupWeb/src");

      ClassBuilder tGroup = mb.buildClass("TGroup")
            .buildAttribute("name", mb.STRING);

      ClassBuilder tStudent = mb.buildClass("TStudent")
            .buildAttribute("studentId", mb.STRING)
            .buildAttribute("name", mb.STRING);


      tGroup.buildAssociation(tStudent, "students", mb.MANY, "tGroup", mb.ONE);

      // clear target directory
      Tools.removeDirAndFiles("src/tGroupWeb/src/tGroupModel");

      TypeScriptGenerator generator = new TypeScriptGenerator();
      generator.generate(mb.getClassModel());

      assertThat(Files.exists(Paths.get("src/tGroupWeb/src/tGroupModel/TGroup.ts")), is(true));


      // do it again
      TypeScriptGenerator generator2 = new TypeScriptGenerator();
      generator2.generate(mb.getClassModel());

      assertThat(Files.exists(Paths.get("src/tGroupWeb/src/tGroupModel/TGroup.ts")), is(true));

      Process exec = Runtime.getRuntime().exec("ls -l");

      ProcessBuilder builder = new ProcessBuilder(
            "bash",  "-c", "cd ./src/tGroupWeb && yarn run test"); // We handle variables
      builder.inheritIO();
      Process start = builder.start();
      start.waitFor();
      int i = start.exitValue();

      assertThat(i, equalTo(0));
   }
}
