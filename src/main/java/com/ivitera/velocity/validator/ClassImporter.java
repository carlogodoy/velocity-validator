package com.ivitera.velocity.validator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import org.xeustechnologies.jcl.*;

public class ClassImporter {

  JarClassLoader jcl;
  JclObjectFactory jclObjFactory;
  Boolean verbose = false;

  public ClassImporter(Boolean verbose){
    this.verbose = verbose;
   this.jcl = new JarClassLoader();
   this.jclObjFactory = JclObjectFactory.getInstance();
 }

 public void loadJars(String[] jars){
   for(int i=0; i<jars.length; i++){
     if(verbose)
       System.out.println("Params parser, loadJars:" + jars[i] );
     jcl.add(jars[i]);
   }
 }

 public Map<String,Object> loadClasses(Map<String,String> m){

   HashMap<String,Object> objects = new HashMap<>();
   for(Map.Entry<String,String> e : m.entrySet()){
      if (verbose) {
        System.out.println("loading class:" + e.getKey() + " " + e.getValue());
      }
      Object o = jclObjFactory.create(jcl,e.getValue());
      objects.put(e.getKey(),o);
  }
   return objects;
 }

  public void testClassLoader2(){
   JarClassLoader jcl = new JarClassLoader();
    jcl.add("/workspace/dev/velocity/slf4j-api-1.7.25.jar");
    // jcl.add("/workspace/dev/velocity/slf4j-api-1.7.12.jar");

    // jcl.add("/workspace/dev/velocity/slf4j-simple-1.7.25.jar");
    // jcl.add("/workspace/dev/velocity/slf4j-log4j12-1.7.12.jar");
    jcl.add("/workspace/dev/velocity/slf4j-nop-1.7.25.jar");

    jcl.add("/workspace/dev/velocity/sli-common-0.3.3.jar");
    JclObjectFactory factory = JclObjectFactory.getInstance();
    // Object o = factory.create(jcl,"org.slf4j.impl.StaticLoggerBinder");
    Object p = factory.create(jcl,"org.onap.ccsdk.sli.core.sli.SvcLogicContext");

  }

  public void testClassloader()
      throws ClassNotFoundException, NoSuchMethodException, MalformedURLException {

    URLClassLoader childLog = new URLClassLoader (new URL[] {new URL("file:///workspace/dev/velocity/slf4j-api-1.7.21.jar.jar")}, Main.class.getClassLoader());
    // Class classToLoadLog = Class.forName("org.slf4j.LoggerFactory", true, childLog);

    URLClassLoader child = new URLClassLoader (new URL[] {new URL("file:///workspace/dev/velocity/sli-common-0.3.3.jar")}, Main.class.getClassLoader());
    Class classToLoad = Class.forName("org.onap.ccsdk.sli.core.sli.SvcLogicContext", true, child);

    // File file = new File("/workspace/dev/velocity/sli-common-0.3.3.jar");
    // URL url = file.toURI().toURL();

    // URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
    // Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);




    Method method = classToLoad.getDeclaredMethod("getAttribute");
    // Object instance = classToLoad.newInstance();
    // Object result = method.invoke(instance);
    System.out.println("Found method:"+method.getName());
  }

}
