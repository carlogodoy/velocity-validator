package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.exceptions.InputParamsException;

import java.io.File;


class ParamsParser {

    private String[] args;
    private boolean verbose;
    private File configFile;
    private File templateBaseDirFile =null;
  private File preloadTemplateBaseDirFile =null;
  private String data;
    private boolean evaluate;
    private String outputFilePath;
    private String jars;
    private String vars;

    public ParamsParser(String... args) {
        this.args = args;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getTemplateBaseDirFile() { return templateBaseDirFile; }

  public File getPreloadTemplateBaseDirFile() { return preloadTemplateBaseDirFile; }

  public String getData() { return data; }

    public boolean isEvaluate(){ return evaluate; }

    public String getOutputFilePath() { return outputFilePath; }

    public String getJars(){ return jars; }

    public String getVars(){ return vars; }

  public ParamsParser parse() throws InputParamsException {
        // if (args.length > 3) {
            // throw new InputParamsException();
        // }

        String templatePath = null;
    String preloadTemplatePath = null;
    verbose = false;
        String regexRulesPath = null;

        for (String arg : args) {
          try{
            if ("-verbose".equals(arg)) {
                verbose = true;
                continue;
            }

            if (arg.startsWith("-rules=")) {
                regexRulesPath = arg.replace("-rules=", "");
                continue;
            }

            if (arg.startsWith("-data=")) {
                data = arg.replace("-data=", "");
                continue;
            }

            if (arg.startsWith("-file=")) {
                templatePath = arg.replace("-file=", "");
                continue;
            }

            if (arg.startsWith("-outputFile=")) {
                outputFilePath = arg.replace("-outputFile=", "");
                continue;
            }

            if (arg.startsWith("-preloadJars=")) {
                jars = arg.replace("-preloadJars=", "");
                continue;
            }

            if (arg.startsWith("-preloadVars=")) {
                vars = arg.replace("-preloadVars=", "");
                continue;
            }

            if (arg.startsWith("-preloadTemplate=")) {
              preloadTemplatePath = arg.replace("-preloadTemplate=", "");
              continue;
            }

            if ("-eval".equals(arg)) {
                evaluate = true;
                continue;
            }
            }catch(Exception e){
              e.printStackTrace();
              throw new InputParamsException(e.getMessage());
          }

            if (!arg.startsWith("-")) {
                //templatesPath = arg;
                //continue;
            //} else {
                throw new InputParamsException("Unknown param: " + arg);
            }
        }

        //if (templatesPath == null) {
            //templatesPath = System.getProperty("user.dir");
        //}

        //if (!templatesPath.endsWith("/")) {
            //templatesPath = templatesPath + "/";
        //}

        configFile = null;
        if (regexRulesPath != null) {
            configFile = new File(regexRulesPath);
        }

    if(null!=templatePath){
      templateBaseDirFile = new File(templatePath);
    }
    if(null!=preloadTemplatePath){
          preloadTemplateBaseDirFile = new File(preloadTemplatePath);
        }
        return this;
    }

}
