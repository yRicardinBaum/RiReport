package rireport.ridev.com.br.Libs;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {
   private static Logger logger;

  static {

    try {
        Class.forName("net.md_5.bungee.api.ProxyServer");
    } catch (ClassNotFoundException e) {
        logger = org.bukkit.Bukkit.getLogger();
    }
  }
  private final String prefix;

   public ModuleLogger(String name) {
/* 24 */     this(logger, " [" + name + "] ");
/*    */   }
 public ModuleLogger(Logger parent, String name) {
    super(name, null);
     setParent(parent);
     setLevel(Level.ALL);
    this.prefix = name;
  }

  public void log(LogRecord record) {
   record.setMessage(this.prefix + record.getMessage());
     super.log(record);
   }

   public ModuleLogger getModule(String module) {
    return new ModuleLogger(getParent(), this.prefix + " [" + module + "] ");
  }
}