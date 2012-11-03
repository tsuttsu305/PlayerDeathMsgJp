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
		
		String[] con = new String[30];
		
		con[0] = this.getConfig().getString("drowned");
		con[1] = this.getConfig().getString("zombie");
		con[2] = this.getConfig().getString("spider");
		con[3] = this.getConfig().getString("pigzombie");
		con[4] = this.getConfig().getString("silverfish");
		con[5] = this.getConfig().getString("slime");
		con[6] = this.getConfig().getString("magmacube");
		con[7] = this.getConfig().getString("enderman");
		con[8] = this.getConfig().getString("enderdragon");
		con[9] = this.getConfig().getString("cavespider");
		con[10] = this.getConfig().getString("irongolem");
		con[11] = this.getConfig().getString("wolf");
		con[12] = this.getConfig().getString("giant");
		con[13] = this.getConfig().getString("pvp");
		con[14] = this.getConfig().getString("skeleton");
		con[15] = this.getConfig().getString("shot");
		con[16] = this.getConfig().getString("killed");
		con[17] = this.getConfig().getString("ground");
		con[18] = this.getConfig().getString("fell");
		con[19] = this.getConfig().getString("lava");
		con[20] = this.getConfig().getString("flames");
		con[21] = this.getConfig().getString("burn");
		con[22] = this.getConfig().getString("blew");
		con[23] = this.getConfig().getString("fireball");
		con[24] = this.getConfig().getString("magic");
		con[25] = this.getConfig().getString("wall");
		con[26] = this.getConfig().getString("prick");
		con[27] = this.getConfig().getString("starved");
		con[28] = this.getConfig().getString("arrow");
		con[29] = this.getConfig().getString("kill");
		
		
		


		return;
	}


}
