package jp.tsuttsu305;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main plugin;
	Logger logger = Logger.getLogger("Minecraft");
	
	public String[] con = new String[30];

	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info(pdfFile.getName() + "version" + pdfFile.getVersion() + " is Enabled");
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();

	}

	public void onDisable(){
		PluginDescriptionFile pdfFile = getDescription();
		this.logger.info(pdfFile.getName() + "version" + pdfFile.getVersion() + " is Disabled");

	}

	public void ConfigLoad() {
		/*
		 * 0 drowned
		 * 1 zombie
		 * 2 spider
		 * 3 pigzombie
		 * 4 silverfish
		 * 5 slime
		 * 6 magmacube
		 * 7 enderman
		 * 8 enderdragon
		 * 9 cavespider
		 * 10 irongolem
		 * 11 wolf
		 * 12 giant
		 * 13 pvp
		 * 14 skeleton
		 * 15 shot
		 * 16 killed
		 * 17 ground
		 * 18 fell
		 * 19 lava
		 * 20 flames
		 * 21 burn
		 * 22 blew
		 * 23 fireball
		 * 24 magic
		 * 25 wall
		 * 26 prick
		 * 27 starved
		 * 28 arrow
		 * 29 kill
		 */
		
		
		
		con[0] = getMessage("drowned");
		con[1] = getMessage("zombie");
		con[2] = getMessage("spider");
		con[3] = getMessage("pigzombie");
		con[4] = getMessage("silverfish");
		con[5] = getMessage("slime");
		con[6] = getMessage("magmacube");
		con[7] = getMessage("enderman");
		con[8] = getMessage("enderdragon");
		con[9] = getMessage("cavespider");
		con[10] = getMessage("irongolem");
		con[11] = getMessage("wolf");
		con[12] = getMessage("giant");
		con[13] = getMessage("pvp");
		con[14] = getMessage("skeleton");
		con[15] = getMessage("shot");
		con[16] = getMessage("killed");
		con[17] = getMessage("ground");
		con[18] = getMessage("fell");
		con[19] = getMessage("lava");
		con[20] = getMessage("flames");
		con[21] = getMessage("burn");
		con[22] = getMessage("blew");
		con[23] = getMessage("fireball");
		con[24] = getMessage("magic");
		con[25] = getMessage("wall");
		con[26] = getMessage("prick");
		con[27] = getMessage("starved");
		con[28] = getMessage("arrow");
		con[29] = getMessage("kill");
		
		
		


		return;
	}
	
	public String getMessage(String cause){
	    return this.getConfig().getString(cause);
	}


}
