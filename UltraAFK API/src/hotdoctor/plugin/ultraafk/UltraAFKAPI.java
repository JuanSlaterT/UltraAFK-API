package hotdoctor.plugin.ultraafk;

import java.io.File;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import hotdoctor.plugin.ultraafk.afkModes.AFKModes;
import hotdoctor.plugin.ultraafk.modes.Modes;


public class UltraAFKAPI {
	
	private Main plugin;
	
	public UltraAFKAPI(Main plugin) {
		this.plugin = plugin;
	}
	
	public static List<Player> afkList = new ArrayList<>();
	
	public static boolean isPlayerAFK(Player p) {
		return Main.isPlayerAFK(p);
	}
	
	public File getPlayerFile(Entity p) {
		return plugin.getFile(p);
	}
	public YamlConfiguration getConfigFile(Entity p) {
		return plugin.getData(p);
		
	}
	
	public boolean isBungeeCordServer() {
		if(plugin.afkManager.getMode().equals(Modes.AFKZONE) || plugin.afkManager.getMode().equals(Modes.LOBBY)) {
			return true;
		}else if(plugin.afkManager.getMode().equals(Modes.MULTIWORLD)){
			return false;	
		}else {
			return false;
		}
	}
	
	public boolean isMultiWorldServer() {
		if(plugin.afkManager.getMode().equals(Modes.AFKZONE) || plugin.afkManager.getMode().equals(Modes.LOBBY)) {
			return false;
		}else if(plugin.afkManager.getMode().equals(Modes.MULTIWORLD)){
			return true;	
		}else {
			return false;
		}
	}
	
	public Modes getServerMode() {
		return plugin.afkManager.getMode();
	}
	
	public AFKModes getAFKMode() {
		return plugin.afkModes.getMode();
	}
	
	@SuppressWarnings("null")
	public int getTimeOfPlayer(Player p) {
		if(Main.isPlayerAFK(p)) {
			if(plugin.elapsedTimeAFK.containsKey(p)) {
				return plugin.elapsedTimeAFK.get(p);
			}else {
				return (Integer) null;
			}
		}else {
			if(plugin.elapsedTime.containsKey(p)) {
				return plugin.elapsedTime.get(p);
			}else {
				return (Integer) null;
			}
		}
		
	}
	
	public List<Location> getAFKZoneLocations() {
		List<Location> locs = new ArrayList<>();
		if(plugin.afkzone.getAFKZone() != null) {
			locs.add(plugin.afkzone.getAFKZone());
		}else if(plugin.afkzone.getAFKZones() != null || !plugin.afkzone.getAFKZones().isEmpty()) {
			locs = plugin.afkzone.getAFKZones();
		}
		
		return locs;
	}
	
	@SuppressWarnings("null")
	public int getAFKTimeOfPlayer(Player p) {
		if(this.isMultiWorldServer() || this.getServerMode().equals(Modes.LOBBY)) {
			if(this.getAFKMode().equals(AFKModes.SINGULAR)) {
				return plugin.time;
			}else if(this.getAFKMode().equals(AFKModes.PERPERMISSIONS)) {
				if(!(p.isOp())) {
					Iterator<PermissionAttachmentInfo> perms = p.getEffectivePermissions().iterator();
					 while(perms.hasNext()) {
						String perm = perms.next().toString();
						if(plugin.permissionsList.contains(perm)) {
							int afkTime = plugin.permissions.get(perm);
							return afkTime;
						}
					 }
					 int afkTime = plugin.PerPermissionsDEFAULT;
					 return afkTime;
				}else {
					int afkTime = plugin.PerPermissionsOP;
					return afkTime;
				}
			}else {
				return (Integer) null;
			}
		}else {
			return (Integer) null;
		}
	}
	
	
	
	
	

}
