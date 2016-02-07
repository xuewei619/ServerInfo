package com.serverinfo.plugins;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.serverinfo.dao.UserDAO;
import com.serverinfo.listener.ActionListener;
import com.serverinfo.listener.DropListner;
import com.serverinfo.listener.LoginListener;
import com.serverinfo.model.User;
import com.serverinfo.util.Utils;

public class LoginPlugin extends JavaPlugin {
	private UserDAO userDao = new UserDAO();
	
	public static Map<String,Object> loginPlayer = new HashMap<String,Object>();
	public static Map<String,Object> arrowPlayer = new HashMap<String, Object>();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new LoginListener(), this);
		getServer().getPluginManager().registerEvents(new ActionListener(), this);
		getServer().getPluginManager().registerEvents(new DropListner(), this);
		getLogger().info("LoginPlugin is started");
	}

	@Override
	public void onDisable() {
		getLogger().info("LoginPlugin is stop");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command != null && ("login".equals(command.getName()) || "l".equals(command.getName()))) {

			if (sender instanceof Player) {
				Player player = (Player) sender;				
				String username = player.getName();
				String password = args[0];
				String address = player.getAddress().getAddress().getHostAddress();
				User user = userDao.selectUser(username, password);
				if (user != null && user.getId() != null) {
					if (user.getLastlogin_time() != null) {
						sender.sendMessage(
								ChatColor.YELLOW + "��¼�ɹ�  �ϴε�¼ʱ��Ϊ " + Utils.date2str(user.getLastlogin_time()));
					}
					userDao.updateLastTime(user.getId(), new Date(),address);					
					loginPlayer.put(player.getName(),true);
					EntityTargetLivingEntityEvent.getHandlerList().unregister(this);
					return true;

				} else {
					sender.sendMessage(ChatColor.RED + "�û����������");
					return true;
				}
			}

		}

		if (command != null && ("reg".equals(command.getName()) || "r".equals(command.getName()))) {
			if (sender instanceof Player) {
				sender = (Player) sender;
				String username = sender.getName();
				String password = args[0];
				User user = userDao.selectUser(username);
				if (user != null && user.getId() == null) {
					User nuser = new User();
					nuser.setId(Utils.getUuid());
					nuser.setUsername(username);
					nuser.setPassword(password);
					nuser.setReg_time(new Date());
					userDao.insertUser(nuser);
					sender.sendMessage(ChatColor.YELLOW + "ע��ɹ� ��¼������/login ����");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "���û����Ѵ���");
					return true;
				}
			}
		}

//		if ("tp".equals(command.getName())) {
//			if (sender instanceof Player) {
//				Player player = (Player) sender;
//				Player target = sender.getServer().getPlayer(args[0]);
//				if (target != null) {
//					if (target.isOnline()) {
//						sender.sendMessage(ChatColor.YELLOW + "������ " + target.getName());
//						player.teleport(target);
//					} else {
//						sender.sendMessage(ChatColor.RED + "����Ҳ�����");
//						return true;
//					}
//				} else {
//					sender.sendMessage(ChatColor.RED + "����Ҳ�����");
//					return true;
//				}
//			}
//		}

		if ("arrow".equals(command.getName())) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				sender.sendMessage(ChatColor.YELLOW + "�ѿ������ģʽ");
				arrowPlayer.put(player.getName(),true);
				return true;
			}
		}

		if ("noarrow".equals(command.getName())) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.YELLOW + "�ѹر����ģʽ");
				Player player = (Player)sender;
				if(!arrowPlayer.isEmpty()){
					arrowPlayer.remove(player.getName());	
				}							
				return true;
			}
		}

		

		
		return false;
	}

	

}
