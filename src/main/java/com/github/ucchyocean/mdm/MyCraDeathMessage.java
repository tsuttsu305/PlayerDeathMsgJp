/*
 * @author     ucchy
 * @license    GPLv3
 * @copyright  Copyright ucchy 2013
 * このソースコードは、tsuttsu305氏のリポジトリからフォークさせていただきました。感謝。
 */
package com.github.ucchyocean.mdm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author ucchy
 * My-Craサーバー用、デスメッセージカスタマイズプラグイン
 */
public class MyCraDeathMessage extends JavaPlugin implements Listener {

    private boolean loggingDeathMessage;
    private boolean suppressDeathMessage;
    private boolean prefixWorld;
    private YamlConfiguration defaultMessages;

    /**
     * プラグイン有効時に呼び出されるメソッド
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable(){
        loadFiles();
        getServer().getPluginManager().registerEvents(this, this);
    }

    /**
     * config.ymlが存在するかどうかチェックし、
     * 存在しないならデフォルトのconfig.ymlをコピーするメソッド
     */
    private void loadFiles() {

        // フォルダやファイルがない場合は、作成したりする
        File dir = new File(getDataFolder().getAbsolutePath());
        if ( !dir.exists() ) {
            dir.mkdirs();
        }

        File file = new File(getDataFolder(), "config.yml");
        if ( !file.exists() ) {
            Utility.copyFileFromJar(getFile(), file, "config.yml", false);
        }

        file = new File(getDataFolder(), "messages.yml");
        if ( !file.exists() ) {
            Utility.copyFileFromJar(getFile(), file, "messages.yml", false);
        }

        // 再読み込み処理
        reloadConfig();

        // 設定の取得
        FileConfiguration config = getConfig();

        loggingDeathMessage = config.getBoolean("loggingDeathMessage", false);
        suppressDeathMessage = config.getBoolean("suppressDeathMessage", false);
        prefixWorld = config.getBoolean("prefixWorld", true);

        // メッセージのデフォルトを、Jarの中から読み込む
        defaultMessages = loadDefaultMessages();
    }

    /**
     * デスメッセージを取得するメソッド
     * @param cause プレイヤー死亡理由
     * @return 理由に応じたメッセージ。
     */
    public String getMessage(String cause) {

        String defaultMessage = defaultMessages.getString(
                cause, "&e" + cause + "(%p_%k_%i_%o)");

        File file = new File(getDataFolder(), "messages.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString(cause, defaultMessage);
    }

    /**
     * プレイヤーが死亡したときに呼び出されるメソッド
     * @param event プレイヤー死亡イベント
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){

        // プレイヤーとプレイヤーが最後に受けたダメージイベントを取得
        Player deader = event.getEntity();
        final EntityDamageEvent cause = event.getEntity().getLastDamageCause();

        // 死亡メッセージ
        String deathMessage = event.getDeathMessage();

        // ダメージイベントを受けずに死んだ 死因不明
        if (cause == null) {
            deathMessage = getMessage("unknown"); // Unknown
        }
        // ダメージイベントあり 原因によってメッセージ変更
        else {
            // ダメージイベントがEntityDamageByEntityEvent(エンティティが原因のダメージイベント)かどうかチェック
            if (cause instanceof EntityDamageByEntityEvent) {
                // EntityDamageByEventのgetDamagerメソッドから原因となったエンティティを取得
                Entity killer = ((EntityDamageByEntityEvent) cause).getDamager();

                // エンティティの型チェック 特殊な表示の仕方が必要
                if (killer instanceof Player){
                    // 倒したプレイヤー名取得
                    Player killerP = (Player)killer;
                    //killerが持ってたアイテム
                    ItemStack hand = killerP.getItemInHand();
                    String handItemName = hand.getType().toString();
                    if ( hand.getType().equals(Material.AIR) ) {
                        handItemName = "素手";
                    }
                    deathMessage = getMessage("pvp");

                    deathMessage = deathMessage.replace("%k", killerP.getName());
                    deathMessage = deathMessage.replace("%i", handItemName);
                }
                // 飼われている狼
                else if (killer instanceof Wolf && ((Wolf) killer).isTamed()){
                    //  飼い主取得
                    String tamer = ((Wolf)killer).getOwner().getName();

                    deathMessage = getMessage("tamewolf");
                    deathMessage = deathMessage.replace("%o", tamer);
                }
                // 打たれた矢
                else if (killer instanceof Arrow) {
                    Arrow arrow = (Arrow)killer;
                    LivingEntity shooter = arrow.getShooter();
                    String killerName;
                    if ( shooter instanceof Player ) {
                        killerName = ((Player)shooter).getName();
                    } else if ( shooter instanceof Skeleton ) {
                        killerName = "スケルトン";
                    } else {
                        killerName = "ディスペンサー";
                    }

                    deathMessage = getMessage("arrow");
                    deathMessage = deathMessage.replace("%k", killerName);
                }
                // プレイヤーが投げた雪玉など
                else if (killer instanceof Projectile && ((Projectile) killer).getShooter() instanceof Player) {
                    // 投げたプレイヤー取得
                    Player sh = (Player) ((Projectile)killer).getShooter();

                    if ( killer instanceof ThrownPotion ) {
                        deathMessage = getMessage("potion");
                    } else {
                        deathMessage = getMessage("throw");
                    }
                    deathMessage = deathMessage.replace("%k", sh.getName());
                }
                // そのほかのMOBは直接設定ファイルから取得
                else {
                    // 直接 getMessage メソッドを呼ぶ
                    deathMessage = getMessage(killer.getType().getName().toLowerCase());
                }
            }
            // エンティティ以外に倒されたメッセージは別に設定
            else {
                if (cause.getCause() == DamageCause.FIRE_TICK) {
                    deathMessage = getMessage("fire");
                } else {
                    deathMessage = getMessage(cause.getCause().toString().toLowerCase());
                }
            }
        }

        // %p を、死亡した人の名前で置き換えする
        deathMessage = deathMessage.replace("%p", deader.getName());

        // カラーコードを置き換える
        deathMessage = Utility.replaceColorCode(deathMessage);

        if ( prefixWorld ) {
            // ワールド名を頭につける
            World world = deader.getWorld();
            deathMessage = "[" + world.getName() + "] " + deathMessage;
        }

        if ( loggingDeathMessage ) {
            // ロギング
            getLogger().info(ChatColor.stripColor(deathMessage));
        }

        if ( suppressDeathMessage ) {
            // メッセージを消去して、OPにだけ送信する
            event.setDeathMessage("");
            Player[] players = getServer().getOnlinePlayers();
            for ( Player player : players ) {
                if ( player.isOp() ) {
                    player.sendMessage(deathMessage);
                }
            }
        } else {
            // メッセージを再設定する
            event.setDeathMessage(deathMessage);
        }
    }

    /**
     * コマンドが実行されたときに呼び出されるメソッド
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        // NOTE: AdminCmd など、killコマンドの実行時に、ダメージ原因（DamageCause）を設定しない
        //       お行儀の悪い子が多いので、プレイヤーコマンドに介在して、
        //       DamageCause を設定する必要がある。
        // たぶん、Essentials の killコマンドも、DamageCauseを設定していない。公式は設定する。

        // killコマンドではないなら、用は無いので、終了する。
        if ( !(event.getMessage().equalsIgnoreCase("/kill")) ) {
            return;
        }

        // killコマンドを実行された場合は、DamageCause.SUICIDEを設定する
        Player player = event.getPlayer();
        player.setLastDamageCause(new EntityDamageEvent(player, DamageCause.SUICIDE, 100));
    }

    private YamlConfiguration loadDefaultMessages() {

        YamlConfiguration messages = new YamlConfiguration();
        try {
            JarFile jarFile = new JarFile(getFile());
            ZipEntry zipEntry = jarFile.getEntry("messages.yml");
            InputStream inputStream = jarFile.getInputStream(zipEntry);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ( (line = reader.readLine()) != null ) {
                if ( line.contains(":") && !line.startsWith("#") ) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String value = line.substring(line.indexOf(":") + 1).trim();
                    if ( value.startsWith("'") && value.endsWith("'") )
                        value = value.substring(1, value.length()-1);
                    messages.set(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }
}