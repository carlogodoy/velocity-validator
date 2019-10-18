package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.InputParamsException;
import com.ivitera.velocity.validator.validators.Runner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
// import org.apache.log4j.Logger;

public class Main {

  // private static final Logger log = Logger.getLogger(Main.class);

  public static void main(String[] args) throws Exception {

    try {
      doRun(args);
    } catch (Exception e) {
      System.out.println("Failed to run Velocity validator due to an Error. " + e);
      e.printStackTrace();
    }
  }

  private static void doRun(String[] args) throws IOException, InitializationException {
    ParamsParser paramsParser = null;
    try {
      paramsParser = new ParamsParser(args).parse();
    } catch (InputParamsException e) {
      e.printStackTrace();
      System.out.println("Param parser failed:" + e.getMessage());
      System.out.println(
          "Usage: java -jar velocity-validator-1.0.jar -file=path_to_template [-eval] \n"
              + " [-outputFile=path_to_output_file] \n"
              + " [-preloadJars=colon_delimited_jars_path] \n"
              + " [-preloadVars=colon_delimited_vars_with_equal_class] \n"
              + "[-rules=path_to_config_file] [-verbose]");
      System.exit(1);
    }
    File configFile = paramsParser.getConfigFile();
    File templateBaseDirFile = paramsParser.getTemplateBaseDirFile();
    File preloadBaseDirFile = paramsParser.getPreloadTemplateBaseDirFile();
    String outputFilePath = paramsParser.getOutputFilePath();
    boolean verbose = paramsParser.isVerbose();
    String data = paramsParser.getData();
    boolean evaluate = paramsParser.isEvaluate();
    String jars = paramsParser.getJars();
    String vars = paramsParser.getVars();

    if (evaluate) {
      System.out.println(
          "Evaluating:" + templateBaseDirFile.getAbsolutePath() + "dir:" + templateBaseDirFile.getPath());
      if(preloadBaseDirFile!=null)
        System.out.println(
          "preloaded:" + preloadBaseDirFile.getAbsolutePath() + "dir:" + preloadBaseDirFile.getPath());

      ClassImporter c = new ClassImporter(verbose);
      if (jars != null && !jars.isEmpty()) {
        String[] jlist = jars.split(":");
        c.loadJars(jlist);
      }
      Map<String, Object> objs = null;
      if (vars != null && !vars.isEmpty()) {
        String[] vlist = vars.split(";");
        HashMap<String, String> m = new HashMap<>();
        for (int i = 0; i < vlist.length; i++) {
          String[] s = vlist[i].split(":");
          if (verbose) {
            System.out.println("Vars split:" + s[0] + " , " + s[1]);
          }
          m.put(s[0], s[1]);
        }
        objs = c.loadClasses(m);
      }

      if (objs == null && verbose) {
        System.out.println("Imported objects are null");
      }else if (verbose) {
        for (Map.Entry<String, Object> e : objs.entrySet()) {
          System.out.println(
              "Imported var:" + e.getKey() + " Object:" + e.getValue().getClass().getName());
        }
      }

      VelocityTemplateHelper h = new VelocityTemplateHelper();
      // if (objs != null && objs.size() > 0) {
        // VelocityContext ctx = h.buildContext(objs);
      // }
      String s = h.resolveTemplate(templateBaseDirFile.getAbsolutePath(), templateBaseDirFile.getPath(),
          preloadBaseDirFile==null?null : preloadBaseDirFile.getAbsolutePath(), objs);
      System.out.println("Evaluate complete.");
      if (outputFilePath != null) {
        System.out.println("Writing output to file.");
        try {
          Files.write(Paths.get(outputFilePath), s.getBytes());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      // log.info("Compilation complete.");
      // log.info(s);
      // log.info("\n");
    } else {

      System.out.println(
          "Validating:" + templateBaseDirFile.getAbsolutePath()); // + "dir:" + templateBaseDirFile.getPath());
      // Runner runner = new Runner(configFile, templateBaseDirFile, verbose);
      Runner runner = new Runner(configFile, templateBaseDirFile, data, verbose);
      boolean allFilesOk = runner.run();
      System.out.println("Validation complete.");
      if (!allFilesOk) {
        // return code indicates, that there is incorrect template (uses CI server for test result)
        System.exit(1);
      }
    }
  }
}
