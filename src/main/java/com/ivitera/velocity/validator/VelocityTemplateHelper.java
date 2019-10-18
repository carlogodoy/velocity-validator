    /*
     * Copyright (C) 2018-2019 Bell Canada. All rights reserved.
     *
     * NOTICE:  All the intellectual and technical concepts contained herein are
     * proprietary to Bell Canada and are protected by trade secret or copyright law.
     * Unauthorized copying of this file, via any medium is strictly prohibited.
     */

    // package ca.bell.nso.template;
    package com.ivitera.velocity.validator;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.FileReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.io.StringWriter;
    import java.nio.charset.StandardCharsets;
    import java.util.Map;
    import java.util.Properties;
    import org.apache.velocity.Template;
    import org.apache.velocity.VelocityContext;
    import org.apache.velocity.app.Velocity;
    import org.apache.velocity.app.VelocityEngine;
    import org.apache.velocity.runtime.RuntimeConstants;
    import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

    /**
     *  Helper Class for the Apache Velocity engine.
     */
    public class VelocityTemplateHelper {
        private VelocityEngine velocityEngine;

        public VelocityTemplateHelper() {
            velocityEngine = new VelocityEngine();
            Properties properties = new Properties();
            properties.put(RuntimeConstants.INPUT_ENCODING, StandardCharsets.UTF_8.name());
            properties.put(RuntimeConstants.OUTPUT_ENCODING, StandardCharsets.UTF_8.name());
            properties.put(RuntimeConstants.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
            properties.setProperty("resource.loader", "class");
            properties.setProperty("class.resource.loader.class",
                 "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init(properties);
        }

        public VelocityContext buildContext(Map<String,Object> mergeObjs){
            VelocityContext context = new VelocityContext();
           for(Map.Entry<String,Object> e : mergeObjs.entrySet()){
               context.put(e.getKey(),e.getValue());
           }
           return context;
        }

        /**
         * Generate Payloads from  Templates
         * @param templatePath Path to the template
         * @param context Map of values
         * @return  String
         */
        public String resolveTemplateOrig(String templatePath, String templateDir, Map<String, ?> context)
            throws IOException {

            final Template template = velocityEngine.getTemplate(templatePath);

            final VelocityContext vContext = new VelocityContext(context);
            final StringWriter writer = new StringWriter();
            template.merge(vContext, writer);
            return writer.toString();


          /*
          final VelocityContext vContext = new VelocityContext(context);
          velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
          velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
          velocityEngine.setProperty("file.resource.loader.path", templateDir);
          velocityEngine.init();
          Template t = velocityEngine.getTemplate(templatePath+"//");
          VelocityContext velocityContext = new VelocityContext();
          velocityContext.put("",""); // put your template values here
          StringWriter w= new StringWriter();
          t.merge(vContext, w);
          return w.toString();
           */

            // final Template template = velocityEngine.getTemplate(templatePath);
          /*
            final VelocityContext vContext = new VelocityContext(context);


            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.setProperty("file.resource.loader.path", templateDir);
            velocityEngine.init();
            Template t = velocityEngine.getTemplate(templatePath+"//");
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("",""); // put your template values here
            StringWriter w= new StringWriter();
            t.merge(vContext, w);

           */

          /* working code

            BufferedReader br = null;

            String tmp = "";

            String fileContent = "";
            try{
                FileReader fr = new FileReader(templateDir);

                br = new BufferedReader(fr);
                String line;
                tmp = br.readLine(); // read first line of file.
                while (tmp != null) { // read a line until end of file.
                    fileContent = fileContent + tmp + "\n"; //Append the contents of the file to a string to be replaced and split out later.
                    tmp = br.readLine();
                }
                //br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }
            }
            // System.out.println("fileContent value is:" + fileContent);

            Velocity.init();
            VelocityContext vContext = new VelocityContext();
            vContext.put("name", "Velocity");
            vContext.put("project", "velocity-comp");
            StringWriter w= new StringWriter();

            // InputStream cpResource = this.getClass().getClassLoader().getResourceAsStream(templatePath);
            // File tmpFile = File.createTempFile(templatePath, "temp");
            // FileUtils.copyInputStreamToFile(cpResource, tmpFile); // FileUtils from apache-io

            // InputStream initialStream = new FileInputStream(
            // new File(templatePath));
            // OutputStream outStream = new FileOutputStream(tmpFile);
            // byte[] buffer = new byte[initialStream.available()];
            // outStream.write(buffer);

            // Velocity.mergeTemplate(tmpFile.getAbsolutePath(),StandardCharsets.UTF_8.name(),vContext,w);
            Velocity.evaluate(vContext,w,":>", fileContent);
            System.out.println("\nCompiled Content:\n\n" + w.toString() + "\n");
            return w.toString();


            end working code */


            /*
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();

            Template t = velocityEngine.getTemplate("index.vm");

            VelocityContext context = new VelocityContext();
            context.put("name", "World");

            StringWriter writer = new StringWriter();
            t.merge( context, writer );
             */
        }

        /**
         * Generate Payloads from  Templates
         * @param templatePath Path to the template
         * @param context Map of values
         * @return  String
         */
        public String resolveTemplate(String templatePath, String templateDir, String preloadPath, Map<String, ?> context)
            throws IOException {

          /*
            final Template template = velocityEngine.getTemplate(templatePath);

            final VelocityContext vContext = new VelocityContext(context);
            final StringWriter writer = new StringWriter();
            template.merge(vContext, writer);
            return writer.toString();

           */

          /*
          final VelocityContext vContext = new VelocityContext(context);
          velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
          velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
          velocityEngine.setProperty("file.resource.loader.path", templateDir);
          velocityEngine.init();
          Template t = velocityEngine.getTemplate(templatePath+"//");
          VelocityContext velocityContext = new VelocityContext();
          velocityContext.put("",""); // put your template values here
          StringWriter w= new StringWriter();
          t.merge(vContext, w);
          return w.toString();
           */

          // final Template template = velocityEngine.getTemplate(templatePath);
          /*
            final VelocityContext vContext = new VelocityContext(context);


            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.setProperty("file.resource.loader.path", templateDir);
            velocityEngine.init();
            Template t = velocityEngine.getTemplate(templatePath+"//");
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("",""); // put your template values here
            StringWriter w= new StringWriter();
            t.merge(vContext, w);

           */

            String preloadContent = getFileContent(preloadPath);
            String templateContent = getFileContent(templateDir);
            String fileContent = preloadContent + "\n" + templateContent;

            /*
            BufferedReader br = null;

            String tmp = "";

            String fileContent = "";
            try{
                FileReader fr = new FileReader(templateDir);

                br = new BufferedReader(fr);
                String line;
                tmp = br.readLine(); // read first line of file.
                while (tmp != null) { // read a line until end of file.
                    fileContent = fileContent + tmp + "\n"; //Append the contents of the file to a string to be replaced and split out later.
                    tmp = br.readLine();
                }
                //br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                }
            }
            // System.out.println("fileContent value is:" + fileContent);


             */

            Velocity.init();
            VelocityContext vContext = new VelocityContext(context);
            vContext.put("name", "Velocity");
            vContext.put("project", "velocity-comp");
            StringWriter w= new StringWriter();

            // InputStream cpResource = this.getClass().getClassLoader().getResourceAsStream(templatePath);
            // File tmpFile = File.createTempFile(templatePath, "temp");
            // FileUtils.copyInputStreamToFile(cpResource, tmpFile); // FileUtils from apache-io

            // InputStream initialStream = new FileInputStream(
                // new File(templatePath));
            // OutputStream outStream = new FileOutputStream(tmpFile);
            // byte[] buffer = new byte[initialStream.available()];
            // outStream.write(buffer);

            // Velocity.mergeTemplate(tmpFile.getAbsolutePath(),StandardCharsets.UTF_8.name(),vContext,w);
            Velocity.evaluate(vContext,w,":>", fileContent);
            System.out.println("\nEvaluated Content:\n\n" + w.toString() + "\n");
            return w.toString();



            /*
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();

            Template t = velocityEngine.getTemplate("index.vm");

            VelocityContext context = new VelocityContext();
            context.put("name", "World");

            StringWriter writer = new StringWriter();
            t.merge( context, writer );
             */
        }

        private String getFileContent(String filePath){
            if(filePath=="" || filePath==null){
                return "";
            }
            BufferedReader br = null;

            String tmp = "";

            String fileContent = "";
            try{
                FileReader fr = new FileReader(filePath);

                br = new BufferedReader(fr);
                String line;
                tmp = br.readLine(); // read first line of file.
                while (tmp != null) { // read a line until end of file.
                    fileContent = fileContent + tmp + "\n"; //Append the contents of the file to a string to be replaced and split out later.
                    tmp = br.readLine();
                }
                //br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            }
            // System.out.println("fileContent value is:" + fileContent);
            return fileContent;

        }
    }
