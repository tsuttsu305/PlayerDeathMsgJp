package jp.tsuttsu305;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDeathEvent implements Listener {


@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){

		if (event instanceof PlayerDeathEvent){
			String player = event.getEntity().getName();
			String deathMessage = event.getDeathMessage();
			String msg = "";

			//1
			if (deathMessage.contains("drowned")) {
				msg = Main.con[0].replaceAll("(\u0025[p])", player);


			}else if (deathMessage.contains("was slain by Zombie")){//2
				msg = Main.con[1].replaceAll("(\u0025[p])", player);


			}else if (deathMessage.contains("was slain by Spider")){
				msg = Main.con[2].replaceAll("(\u0025[p])", player);

			}else if (deathMessage.contains("was slain by PigZombie"))
			{
				msg = re(Main.con[3], player);
			}else if (deathMessage.contains("was slain by Silverfish"))
			{
				msg = re(Main.con[4], player);

			}else if (deathMessage.contains("was slain by Slime"))
			{
				msg = re(Main.con[5], player);

			}else if (deathMessage.contains("was slain by MagmaCube"))
			{
				msg = re(Main.con[6], player);

			}else if (deathMessage.contains("was slain by Enderman"))
			{
				msg = re(Main.con[7], player);

			}else if (deathMessage.contains("was slain by Enderdragon"))
			{
				msg = re(Main.con[8], player);

			}else if (deathMessage.contains("was slain by Cave Spider"))
			{
				msg = re(Main.con[9], player);

			}else if (deathMessage.contains("was slain by Irongolem"))
			{
				msg = re(Main.con[10],player);

			}else if (deathMessage.contains("was slain by Wolf"))
			{
				msg = re(Main.con[11],player);

			}else if (deathMessage.contains("was slain by Giant"))
			{
				msg = re(Main.con[12], player);
			}else if (deathMessage.contains("was slain by Wither"))
			{
				msg = re(Main.con[13], player);
			}else if (deathMessage.contains("was slain by"))
			{
				msg = re(Main.con[14], player);
				msg = msg.replaceAll("(\u0025[k])", event.getEntity().getKiller().getName());
				msg = msg.replaceAll("(\u0025[i])", event.getEntity().getKiller().getItemInHand().getType().toString());


			}else if (deathMessage.contains("was shot by Skeleton"))
			{
				msg = re(Main.con[15], player);
			}else if (deathMessage.contains("was shot by"))
			{
				msg = re(Main.con[16], player);
				msg = msg.replaceAll("(\u0025[k])", event.getEntity().getKiller().getName());

			}else if (deathMessage.contains("was killed by"))
			{
				msg = re(Main.con[17], player);
				msg = msg.replaceAll("(\u0025[k])", event.getEntity().getKiller().getName());

			}else if (deathMessage.contains("hit the ground too hard"))
			{
				msg = re(Main.con[18], player);
			}else if (deathMessage.contains("fell out of the world"))
			{
				msg = re(Main.con[19], player);
			}else if (deathMessage.contains("tried to swim in lava"))
			{
				msg = re(Main.con[20], player);
			}else if (deathMessage.contains("went up in flames"))
			{
				msg = re(Main.con[21], player);

			}else if (deathMessage.contains("burned to death"))
			{
				msg = re(Main.con[22], player);
			}else if (deathMessage.contains("blew up"))
			{
				msg = re(Main.con[23], player);
			}else if (deathMessage.contains("was fireballed by"))
			{
				msg = re(Main.con[24], player);
				msg = msg.replaceAll("(\u0025[k])", event.getEntity().getKiller().getName());

			}else if (deathMessage.contains("was killed by magic"))
			{
				msg = re(Main.con[25], player);
			}else if (deathMessage.contains("suffocated in a wall"))
			{
				msg = re(Main.con[26], player);
			}else if (deathMessage.contains("was pricked to death"))
			{
				msg = re(Main.con[27], player);
			}else if (deathMessage.contains("starved to death"))
			{
				msg = re(Main.con[28], player);
			}else if (deathMessage.contains("was shot by arrow"))
			{
				msg = re(Main.con[29], player);
			}else if (deathMessage.contains("died"))
			{
				msg = re(Main.con[30], player);
			}






			event.setDeathMessage(msg);



		}


		return;
	}

public String re(String s , String player){


	return s = s.replaceAll("(\u0025[p])", player);

}


}
