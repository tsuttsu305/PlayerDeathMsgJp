package jp.tsuttsu305;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main plugin;
	Logger logger = Logger.getLogger("Minecraft");

	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info(pdfFile.getName() + "version" + pdfFile.getVersion() + " is Enabled");

	}

	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info(pdfFile.getName() + "version" + pdfFile.getVersion() + " is Disabled");

	}
	
	

}
