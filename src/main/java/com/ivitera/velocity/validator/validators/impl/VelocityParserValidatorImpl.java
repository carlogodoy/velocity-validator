package com.ivitera.velocity.validator.validators.impl;

import com.ivitera.velocity.validator.Main;
import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import com.ivitera.velocity.validator.validators.Validator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
// import org.apache.log4j.Logger;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

public class VelocityParserValidatorImpl implements Validator {

// private static final Logger log = Logger.getLogger(Main.class);


  public void validate(File file) throws ValidationException, IOException {

    // log.info("looping file=" + file.getName());
    BufferedReader r = new BufferedReader(new FileReader(file));
      String line = null;
      String all = "";
      while ((line = r.readLine()) != null) {
        // log.info("looping=>");
        // log.info(line);
        all += new String(line) + "\n";
      }

    // log.info("all=>");
    // log.info(all);


      try(Reader br = new StringReader(all); ){
        // log.info("use string instead of file");
        RuntimeSingleton.getRuntimeServices().parse(br, "name");
     } catch (ParseException e) {
       throw new ValidationException(e);
     }

    /*
    try (BufferedReader br =
        Files.newBufferedReader(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8); ) {
      RuntimeSingleton.getRuntimeServices().parse(br, file.getAbsolutePath());
    } catch (ParseException e) {
      throw new ValidationException(e);
    }

     */
  }

  public void validateStr(String s) throws ValidationException, IOException {
    try {

      // log.info("Evaluating data=>");
      // log.info(s);

      Reader br = new StringReader(s);

      // RuntimeSingleton.getRuntimeServices().parse(br, file.getAbsolutePath());
      RuntimeSingleton.getRuntimeServices().parse(br, "name");

    } catch (ParseException e) {
      throw new ValidationException(e);
    }
  }

  public void init(File config) throws InitializationException {
    if(config==null){
      return;
    }
    try {
      RuntimeSingleton.init();
    } catch (Exception e) {
      throw new InitializationException("Failed to init VelocityParserValidator", e);
    }
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
