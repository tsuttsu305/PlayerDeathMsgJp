package jp.tsuttsu305;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDeathEvent implements Listener {

	public void onPlayerDeath(PlayerDeathEvent event){

		if (event instanceof PlayerDeathEvent){
			String player = event.getEntity().getName();
			String deathMessage = event.getDeathMessage();
			String msg = "";
			
			
			if (deathMessage.contains("drowned")) {
				msg = Main.con[0].replaceAll("$player", player);
				event.setDeathMessage(msg);
				
			}else if (deathMessage.contains("was slain by Zombie")){
				msg = Main.con[1].replaceAll("$player", player);
				event.setDeathMessage(msg);
				
			}

			

		}


		return;
	}


}
