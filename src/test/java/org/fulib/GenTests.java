package org.fulib;

import org.fulib.classmodel.FileFragmentMap;
import org.junit.Test;

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

      System.out.println(fragmentMap);
   }
}
