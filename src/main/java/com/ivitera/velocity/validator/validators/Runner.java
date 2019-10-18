package com.ivitera.velocity.validator.validators;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.utils.PathSearcher;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
// import org.apache.log4j.Logger;

public class Runner {

  // private static final Logger log = Logger.getLogger(Runner.class);

  private File configFile;
  private File velFile;
  private boolean verbose;
  private String data;

  public Runner(File configFile, File velFile, String data, boolean verbose) {
    this.configFile = configFile;
    this.velFile = velFile;
    this.verbose = verbose;
    this.data = data;
  }

  /*
  public RunnerSave(File configFile, File baseDir, boolean verbose) {
      this.configFile = configFile;
      this.baseDir = baseDir;
      this.verbose = verbose;
  }

   */

  // public boolean run() throws FileNotFoundException, InitializationException {
  public boolean run() throws FileNotFoundException, InitializationException {

    System.out.println("Starting run");
    // log.info("Starting run");

    List<File> files = new ArrayList<>();

    if (verbose) {
      System.out.println("verbose mode is ON");
    }

    try {
      ValidatorsService.init(configFile);
    } catch (Exception e) {
      throw new InitializationException(e);
    }

    List<? extends Validator> validators = ValidatorsService.getAllValidators();
    int errors = 0;
    for (Validator validator : validators) {
      if (validator.isEnabled()) {
        try {
          if (this.velFile != null) {
            validator.validate(this.velFile);
            if (verbose) {
              System.out.println("file OK");
            }
          } else {
            String[] spl = this.data.split("\n");
            for(int i=0;i<spl.length;i++){
              if (this.verbose) {
                System.out.println("Iterate string=>" + spl[i]);
              }
              // validator.validateStr(this.data);
            validator.validateStr(spl[i]);
            if (verbose) {
              System.out.println("Data OK");
            }
          }
        }} catch ( Exception e) {
          errors++;
          System.out.println("Error " + e.getMessage());
        }
      }
    }
    if (verbose) {
      if (errors == 0) {
        System.out.println("No errors");
      }
    }
    if (errors > 0) {
      System.out.println("Done, Found " + errors + " errors");
      return false;
    }
    return true;
  }

  public boolean run_Save() throws FileNotFoundException, InitializationException {
    List<File> files = new ArrayList<>();

    if (verbose) {
      System.out.println("verbose mode is ON");
    }

    if (velFile != null && isVelocityFile(velFile)) {
      files.add(velFile);
    } else {
      files =
          PathSearcher.getFileListing(
              velFile,
              new FileFilter() {
                public boolean accept(File file) {
                  return isVelocityFile(file);
                }
              });
    }

    try {
      ValidatorsService.init(configFile);
    } catch (Exception e) {
      throw new InitializationException(e);
    }

    List<? extends Validator> validators = ValidatorsService.getAllValidators();
    int errors = 0;
    for (Validator validator : validators) {
      if (validator.isEnabled()) {
        for (File f : files) {
          try {
            validator.validate(f);
            if (verbose) {
              System.out.println("File OK: " + getFilePrintPath(f));
            }
          } catch (Exception e) {
            errors++;
            System.out.println("Error in file " + getFilePrintPath(f));
            System.out.println(
                "    "
                    + e.getMessage()
                        .replace(velFile.getAbsolutePath(), "./")
                        .replace("\n", "\n    "));
          }
        }
      }
    }
    if (verbose) {
      System.out.println("Checked " + files.size() + " files");
      if (errors == 0) {
        System.out.println("No errors found in given path");
      }
    }
    if (errors > 0) {
      System.out.println("Done, Found " + errors + " errors");
      return false;
    }
    return true;
  }

  private String getFilePrintPath(File f) {
    String basePath = velFile.getAbsolutePath();
    if (!basePath.endsWith("/")) {
      basePath = basePath + "/";
    }
    return f.getAbsolutePath().replace(basePath, "./");
  }

  private boolean isVelocityFile(File file) {
    return file.isFile() && file.getAbsolutePath().endsWith(".vm");
  }
}
