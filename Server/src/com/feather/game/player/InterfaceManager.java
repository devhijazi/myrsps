package com.feather.game.player;

import java.util.concurrent.ConcurrentHashMap;

public class InterfaceManager {

	public static final int FIXED_WINDOW_ID = 548;
	public static final int RESIZABLE_WINDOW_ID = 746;
	public static final int CHAT_BOX_TAB = 13;
	public static final int FIXED_SCREEN_TAB_ID = 27;
	public static final int RESIZABLE_SCREEN_TAB_ID = 28;
	public static final int FIXED_INV_TAB_ID = 166; 
	public static final int RESIZABLE_INV_TAB_ID = 108;
	private Player player;
	

	private final ConcurrentHashMap<Integer, int[]> openedinterfaces = new ConcurrentHashMap<Integer, int[]>();

	private boolean resizableScreen;
	private int windowsPane;

	public InterfaceManager(Player player) {
		this.player = player;
	}

	public void sendTab(int tabId, int interfaceId) {
		player.getPackets().sendInterface(true,
				resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID, tabId,
				interfaceId);
	}

	public void sendChatBoxInterface(int interfaceId) {
		player.getPackets().sendInterface(true, 752, CHAT_BOX_TAB, interfaceId);
	}

	public void closeChatBoxInterface() {
		player.getPackets().closeInterface(CHAT_BOX_TAB);
	}

	public void sendOverlay(int interfaceId, boolean fullScreen) {
		sendTab(resizableScreen ? fullScreen ? 1 : 11 : 0, interfaceId);
	}
	
	public void closeOverlay(boolean fullScreen) {
		player.getPackets().closeInterface(resizableScreen ? fullScreen ? 1 : 11 : 0);
	}
	
	public void sendInterface(int interfaceId) {
		player.getPackets()
				.sendInterface(
						false,
						resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID,
						resizableScreen ? RESIZABLE_SCREEN_TAB_ID
								: FIXED_SCREEN_TAB_ID, interfaceId);
	}

	public void sendInventoryInterface(int childId) {
		player.getPackets().sendInterface(false,
				resizableScreen ? RESIZABLE_WINDOW_ID : FIXED_WINDOW_ID,
				resizableScreen ? RESIZABLE_INV_TAB_ID : FIXED_INV_TAB_ID,
				childId);
	}

	public final void sendInterfaces() {
		if (player.getDisplayMode() == 2 || player.getDisplayMode() == 3) {
			resizableScreen = true;
			sendFullScreenInterfaces();
		} else {
			resizableScreen = false;
			sendFixedInterfaces();
		}
		player.getSkills().sendInterfaces();
		player.getCombatDefinitions().sendUnlockAttackStylesButtons();
		player.getMusicsManager().unlockMusicPlayer();
		player.getEmotesManager().unlockEmotesBook();
		player.getInventory().unlockInventoryOptions();
		player.getPrayer().unlockPrayerBookButtons();
		if (player.getFamiliar() != null && player.isRunning())
			player.getFamiliar().unlock();
		player.getControlerManager().sendInterfaces();
	}

	public void replaceRealChatBoxInterface(int interfaceId) {
		player.getPackets().sendInterface(true, 752, 11, interfaceId);
	}

	public void closeReplacedRealChatBoxInterface() {
		player.getPackets().closeInterface(752, 11);
	}

	public void sendWindowPane() {
		player.getPackets().sendWindowsPane(resizableScreen ? 746 : 548, 0);
	}
	
	public void sendFullScreenInterfaces() {
		player.getPackets().sendWindowsPane(746, 0);
		sendTab(21, 752);
		sendTab(22, 751);
		sendTab(15, 745);
		sendTab(25, 754);
		sendTab(195, 748); 
		sendTab(196, 749);
		sendTab(197, 750);
		//sendTab(198, 747); 
		player.getPackets().sendInterface(true, 752, 9, 137);
		sendCombatStyles();
		sendTaskSystem();
		sendSkills();
		sendTab(114, 190);
		sendInventory();
		sendEquipment();
		sendPrayerBook();
		sendMagicBook();
		sendTab(120, 550); // friend list
		sendTab(121, 1109); // 551 ignore now friendchat
		sendTab(122, 1110); // 589 old clan chat now new clan chat
		sendSettings();
		sendEmotes();
		sendTab(125, 187); // music
		sendTab(126, 34); // notes
		sendTab(129, 182); // logout*/
	}
	
	public void sendFixedInterfaces() {
		player.getPackets().sendWindowsPane(548, 0);
		sendTab(161, 752);
		sendTab(37, 751);
		sendTab(23, 745);
		sendTab(25, 754);
		sendTab(155, 747); 
		sendTab(151, 748);
		sendTab(152, 749);
		sendTab(153, 750);
		player.getPackets().sendInterface(true, 752, 9, 137);
		sendMagicBook();
		sendPrayerBook();
		sendEquipment();
		sendInventory();
		sendTab(174, 190);//quest
		sendTab(181, 1109);// 551 ignore now friendchat
		sendTab(182, 1110);// 589 old clan chat now new clan chat
		sendTab(180, 550);// friend list
		sendTab(185, 187);// music
		sendTab(186, 34); // notes
		sendTab(189, 182);
		sendSkills();
		sendEmotes();
		sendSettings();
		sendTaskSystem();
		sendCombatStyles();
	}

	public void sendXPPopup() {
		sendTab(resizableScreen ? 38 : 10, 1213); //xp 
	}
	
	public void sendXPDisplay() {
		sendXPDisplay(1215);  //xp counter
	}
	
	public void sendXPDisplay(int interfaceId) {
		sendTab(resizableScreen ? 27 : 29, interfaceId);  //xp counter
	}
	
	public void closeXPPopup() {
		player.getPackets().closeInterface(resizableScreen ? 38 : 10);
	}
	
	public void closeXPDisplay() {
		player.getPackets().closeInterface(resizableScreen ? 27 : 29);
	}
	
	public void sendEquipment() {
		sendTab(resizableScreen ? 116 : 176, 387);
	}
	
	public void closeInterface(int one, int two) {
		player.getPackets().closeInterface(resizableScreen ? two : one);
	}

	public void closeEquipment() {
		player.getPackets().closeInterface(resizableScreen ? 116 : 176);
	}

	public void sendInventory() {
		sendTab(resizableScreen ? 115 : 175, Inventory.INVENTORY_INTERFACE);
	}

	public void closeInventory() {
		player.getPackets().closeInterface(resizableScreen ? 115 : 175);
	}
	
	public void closeSkills() {
		player.getPackets().closeInterface(resizableScreen ? 113 : 206);
	}
	
	public void closeCombatStyles() {
		player.getPackets().closeInterface(resizableScreen ? 111 : 204);
	}
	
	public void closeTaskSystem() {
		player.getPackets().closeInterface(resizableScreen ? 112 : 205);
	}
	
	public void sendCombatStyles() {
		sendTab(resizableScreen ? 111 : 171, 884);
	}
	
	public void sendTaskSystem() {
		sendTab(resizableScreen ? 112 : 172, 1056);
	}

	public void sendSkills() {
		sendTab(resizableScreen ? 113 : 173, 320);
	}

	public void sendSettings() {
		sendSettings(261);
	}

	public void sendSettings(int interfaceId) {
		sendTab(resizableScreen ? 123 : 183, interfaceId);
	}

	public void sendPrayerBook() {
		sendTab(resizableScreen ? 117 : 177, 271);
	}
	
	public void closePrayerBook() {
		player.getPackets().closeInterface(resizableScreen ? 117 : 210);
	}


	public void sendMagicBook() {
		sendTab(resizableScreen ? 118 : 178, player.getCombatDefinitions()
				.getSpellBook());
	}
	
	public void sendGodwars() {
		player.getPackets().sendIComponentText(601, 8, "" + player.armadyl);
		player.getPackets().sendIComponentText(601, 9, "" + player.bandos);
		player.getInterfaceManager().sendOverlay(601, true);
	}
	
	public void closeMagicBook() {
		player.getPackets().closeInterface(resizableScreen ? 118 : 211);
	}
	
	public void sendEmotes() {
		sendTab(resizableScreen ? 124 : 184, 590);
	}
	
	public void closeEmotes() {
		player.getPackets().closeInterface(resizableScreen ? 124 : 217);
	}

	public boolean addInterface(int windowId, int tabId, int childId) {
		if (openedinterfaces.containsKey(tabId))
			player.getPackets().closeInterface(tabId);
		openedinterfaces.put(tabId, new int[] { childId, windowId });
		return openedinterfaces.get(tabId)[0] == childId;
	}

	public boolean containsInterface(int tabId, int childId) {
		if (childId == windowsPane)
			return true;
		if (!openedinterfaces.containsKey(tabId))
			return false;
		return openedinterfaces.get(tabId)[0] == childId;
	}

	public int getTabWindow(int tabId) {
		if (!openedinterfaces.containsKey(tabId))
			return FIXED_WINDOW_ID;
		return openedinterfaces.get(tabId)[1];
	}

	public boolean containsInterface(int childId) {
		if (childId == windowsPane)
			return true;
		for (int[] value : openedinterfaces.values())
			if (value[0] == childId)
				return true;
		return false;
	}

	public boolean containsTab(int tabId) {
		return openedinterfaces.containsKey(tabId);
	}

	public void removeAll() {
		openedinterfaces.clear();
	}

	public boolean containsScreenInter() {
		return containsTab(resizableScreen ? RESIZABLE_SCREEN_TAB_ID
				: FIXED_SCREEN_TAB_ID);
	}

	public void closeScreenInterface() {
		player.getPackets()
				.closeInterface(
						resizableScreen ? RESIZABLE_SCREEN_TAB_ID
								: FIXED_SCREEN_TAB_ID);
	}

	public boolean containsInventoryInter() {
		return containsTab(resizableScreen ? RESIZABLE_INV_TAB_ID
				: FIXED_INV_TAB_ID);
	}

	public void closeInventoryInterface() {
		player.getPackets().closeInterface(
				resizableScreen ? RESIZABLE_INV_TAB_ID : FIXED_INV_TAB_ID);
	}

	public boolean containsChatBoxInter() {
		return containsTab(CHAT_BOX_TAB);
	}

	public boolean removeTab(int tabId) {
		return openedinterfaces.remove(tabId) != null;
	}

	public boolean removeInterface(int tabId, int childId) {
		if (!openedinterfaces.containsKey(tabId))
			return false;
		if (openedinterfaces.get(tabId)[0] != childId)
			return false;
		return openedinterfaces.remove(tabId) != null;
	}

	public void sendFadingInterface(int backgroundInterface) {
		if (hasResizableScreen()) 
			player.getPackets().sendInterface(true, RESIZABLE_WINDOW_ID, 12,backgroundInterface);
		else
			player.getPackets().sendInterface(true, FIXED_WINDOW_ID, 11,backgroundInterface);
	}
	
	public void closeFadingInterface() {
		if (hasResizableScreen()) 
			player.getPackets().closeInterface(12);
		else
			player.getPackets().closeInterface(11);
	}
	
	public void sendScreenInterface(int backgroundInterface, int interfaceId) {
		player.getInterfaceManager().closeScreenInterface();

		if (hasResizableScreen()) {
			player.getPackets().sendInterface(false, RESIZABLE_WINDOW_ID, 40,
					backgroundInterface);
			player.getPackets().sendInterface(false, RESIZABLE_WINDOW_ID, 41,
					interfaceId);
		} else {
			player.getPackets().sendInterface(false, FIXED_WINDOW_ID, 200,
					backgroundInterface);
			player.getPackets().sendInterface(false, FIXED_WINDOW_ID, 201,
					interfaceId);
			
		}
		

		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				if (hasResizableScreen()) {
					player.getPackets().closeInterface(40);
					player.getPackets().closeInterface(41);
				} else {
					player.getPackets().closeInterface(200);
					player.getPackets().closeInterface(201);
				}
			}
		});
	}

	public void setWindowsPane(int windowsPane) {
		this.windowsPane = windowsPane;
	}

	public int getWindowsPane() {
		return windowsPane;
	}
	
	public void gazeOrbOfOculus() {
		player.getPackets().sendWindowsPane(475, 0);
		player.getPackets().sendInterface(true, 475, 57, 751);
		player.getPackets().sendInterface(true, 475, 55, 752);
		player.setCloseInterfacesEvent(new Runnable() {

			@Override
			public void run() {
				player.getPackets().sendWindowsPane(player.getInterfaceManager().hasResizableScreen() ? 746 : 548, 0);
				player.getPackets().sendResetCamera();
			}
			
		});
	}
	public boolean hasResizableScreen() {
		return resizableScreen;
	}
	/*
	 * returns lastGameTab
	 */
	public int openGameTab(int tabId) {
		player.getPackets().sendGlobalConfig(168, tabId);
		int lastTab = 4; // tabId
		// tab = tabId;
		return lastTab;
	}

}
