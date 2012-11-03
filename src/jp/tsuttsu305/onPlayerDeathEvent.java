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

public class onPlayerDeathEvent implements Listener {


	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		// プレイヤーとプレイヤーが最後に受けたダメージイベントを取得
		Player deader = event.getEntity();
		EntityDamageEvent cause = event.getEntity().getLastDamageCause();

		// 死亡メッセージ
		String deathMessage = event.getDeathMessage();

		String name = deader.getName();
		String msg = "";

		// ダメージイベントを受けずに死んだ 死因不明
		if (cause == null){
			deathMessage = name + " は死にました"; // Unknown
		}
		// ダメージイベントあり 原因によってメッセージ変更
		else{
			// ダメージイベントがEntityDamageByEntityEvent(エンティティが原因のダメージイベント)かどうかチェック
			if (cause instanceof EntityDamageByEntityEvent) {
				Entity killer = ((EntityDamageByEntityEvent) cause).getDamager(); // EntityDamageByEventのgetDamagerメソッドから原因となったエンティティを取得

				// エンティティの型チェック 特殊な表示の仕方が必要
				if (killer instanceof Player){
					// TODO: この辺に倒したプレイヤー名取得
					deathMessage = name + " はプレイヤーに倒されました";
				}
				// 飼われている狼
				else if (killer instanceof Wolf && ((Wolf) killer).isTamed()){
					// TODO: 飼い主取得
					deathMessage = name + " は飼われている狼に食べられました";
				}
				// プレイヤーが投げた弓や雪玉など
				else if (killer instanceof Projectile && ((Projectile) killer).getShooter() instanceof Player) {
					// TODO: 投げたプレイヤー取得
					deathMessage = name + " はプレイヤーが投げたアイテムで倒されました";
				}
				// そのほかのMOBは直接設定ファイルから取得
				else{
					// TODO: Mainクラスの getMessage メソッドを呼ぶ
					// deathMessage = ～.getMessage(entity.getType().getName());
					deathMessage = "";
				}
			}
			// エンティティ以外に倒されたメッセージは別に設定
			else{
				switch (cause.getCause()){
					case CONTACT:
						deathMessage = name + " はサボテンに触って死にました";
						break;

					case DROWNING:
						deathMessage = name + " は溺れて死にました";
						break;

					case FALL:
						deathMessage = name + " は落下死しました";
						break;

					case FIRE:
					case FIRE_TICK:
						deathMessage = name + " は火の中で死にました";
						break;

					case LAVA:
						deathMessage = name + " は溶岩に触って死にました";
						break;

					case LIGHTNING:
						deathMessage = name + " は雷に打たれて死にました";
						break;

					case POISON:
						deathMessage = name + " は毒で死にました";
						break;

					case STARVATION:
						deathMessage = name + " は餓死しました";
						break;

					case SUFFOCATION:
						deathMessage = name + " は窒息死しました";
						break;

					case SUICIDE:
						deathMessage = name + " は自殺しました";
						break;

					case VOID:
						deathMessage = name + " は奈落に落ちました";
						break;

					// それ以外は不明
					default:
						deathMessage = name + " は死にました"; // Unknown
						break;
				}
			}
		}

		// 設定ファイルから読み込むなら最後に一括変換したほうがスマートかも
		deathMessage = deathMessage.replace("%p", name);

		event.setDeathMessage(msg);
		return;
	}

public String re(String s , String player){


	return s = s.replaceAll("(\u0025[p])", player);

}


}
