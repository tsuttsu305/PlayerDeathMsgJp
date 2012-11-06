package jp.tsuttsu305;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class onPlayerDeathEvent extends Main implements Listener {


	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		// プレイヤーとプレイヤーが最後に受けたダメージイベントを取得
		Player deader = event.getEntity();
		EntityDamageEvent cause = event.getEntity().getLastDamageCause();

		// 死亡メッセージ
		String deathMessage = event.getDeathMessage();

		String name = deader.getName();



		// ダメージイベントを受けずに死んだ 死因不明
		if (cause == null){
			deathMessage = getMessage("unknown"); // Unknown
		}
		// ダメージイベントあり 原因によってメッセージ変更
		else{
			// ダメージイベントがEntityDamageByEntityEvent(エンティティが原因のダメージイベント)かどうかチェック
			if (cause instanceof EntityDamageByEntityEvent) {
				Entity killer = ((EntityDamageByEntityEvent) cause).getDamager(); // EntityDamageByEventのgetDamagerメソッドから原因となったエンティティを取得

				// エンティティの型チェック 特殊な表示の仕方が必要
				if (killer instanceof Player){
					// この辺に倒したプレイヤー名取得
					//Player killerP = deader.getKiller();
					//killerが持ってたアイテム
					//ItemStack hand = killerP.getItemInHand();
					//deathMessage = getMessage("pvp");
					deathMessage = Main.con[14];
					//deathMessage = deathMessage.replace("%k", killerP.getName());
					//deathMessage = deathMessage.replace("%i", hand.getType().toString());
				}
				// 飼われている狼
				else if (killer instanceof Wolf && ((Wolf) killer).isTamed()){
					//  飼い主取得
					String tamer = ((Wolf)killer).getOwner().getName();

					deathMessage = getMessage("tamewolf");
					deathMessage = deathMessage.replace("%o", tamer);
				}
				// プレイヤーが投げた弓や雪玉など
				else if (killer instanceof Projectile && ((Projectile) killer).getShooter() instanceof Player) {
					// 投げたプレイヤー取得
					Player sh = ((Player)killer).getPlayer();
					ItemStack pass = ((Projectile)killer).getShooter().getKiller().getItemInHand();

					deathMessage = getMessage("throw");
					deathMessage = deathMessage.replace("%i", pass.getType().toString());
					deathMessage = deathMessage.replace("%k", sh.getName());


				}
				// そのほかのMOBは直接設定ファイルから取得
				else{
					//Mainクラスの Main.plugin.getMessage メソッドを呼ぶ
					deathMessage = getMessage(killer.getType().getName());
				}
			}
			// エンティティ以外に倒されたメッセージは別に設定
			else{
				switch (cause.getCause()){
					case CONTACT:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case DROWNING:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case FALL:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case FIRE:
					case FIRE_TICK:
						deathMessage = getMessage("fire");
						break;

					case LAVA:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case LIGHTNING:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case POISON:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case STARVATION:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case SUFFOCATION:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case SUICIDE:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					case VOID:
						deathMessage = getMessage(cause.getCause().toString());
						break;

					// それ以外は不明
					default:
						deathMessage = getMessage("unknown"); // Unknown
						break;
				}
			}
		}

		// 設定ファイルから読み込むなら最後に一括変換したほうがスマートかも
		deathMessage = deathMessage.replace("%p", name);

		event.setDeathMessage(deathMessage);
		return;
	}




}
