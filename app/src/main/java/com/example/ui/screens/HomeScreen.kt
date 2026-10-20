package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Broadcaster
import com.example.model.EntryEffectType
import com.example.ui.components.RechargeDialog
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import com.example.viewmodel.PKLiveViewModel
import com.example.viewmodel.ScreenState
import kotlin.random.Random

@Composable
fun MainApp(
    viewModel: PKLiveViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when {
        !uiState.isLoggedIn || uiState.currentScreen == ScreenState.LOGIN -> {
            LoginScreen(
                onLoginSuccess = { profile ->
                    viewModel.login(profile)
                },
                modifier = modifier
            )
        }
        uiState.currentScreen == ScreenState.MAIN_TABS -> {
            HomeScreen(
                viewModel = viewModel,
                modifier = modifier
            )
        }
        uiState.currentScreen == ScreenState.LIVE_ROOM -> {
            BackHandler {
                viewModel.closeLiveRoom()
            }
            uiState.activeBroadcaster?.let { broadcaster ->
                LiveRoomScreen(
                    broadcaster = broadcaster,
                    userProfile = uiState.userProfile,
                    isPkActive = uiState.isPkMatchActive,
                    hostPkScore = uiState.hostPkScore,
                    opponentPkScore = uiState.opponentPkScore,
                    pkTimeLeftSeconds = uiState.pkTimeLeftSeconds,
                    pkWinner = uiState.pkWinner,
                    isVoiceMuted = uiState.isVoiceMuted,
                    voiceSeats = uiState.voiceSeats,
                    chatMessages = uiState.liveChatMessages,
                    activeEntryEffect = uiState.activeEntryEffect,
                    activeGiftCelebration = uiState.activeGiftCelebration,
                    showRechargeDialog = uiState.showRechargeDialog,
                    showGiftDrawer = uiState.showGiftDrawer,
                    onSendMessage = { viewModel.sendChatMessage(it) },
                    onSendGift = { viewModel.sendGift(it) },
                    onTogglePk = { viewModel.togglePkMatch() },
                    onRematchPk = { viewModel.rematchPk() },
                    onToggleVoiceSeat = { viewModel.toggleVoiceSeat(it) },
                    onToggleMute = { viewModel.toggleUserVoiceMute() },
                    onTriggerEntryEffect = { viewModel.triggerEntryEffect(it) },
                    onClearEntryEffect = { viewModel.clearEntryEffect() },
                    onClearGiftCelebration = { viewModel.clearGiftCelebration() },
                    onSetRechargeDialog = { viewModel.setRechargeDialog(it) },
                    onSetGiftDrawer = { viewModel.setGiftDrawer(it) },
                    onRechargeAddCoins = { viewModel.addCoins(it) },
                    onClose = { viewModel.closeLiveRoom() }
                )
            }
        }
        uiState.currentScreen == ScreenState.SETTINGS -> {
            BackHandler {
                viewModel.navigateTo(ScreenState.MAIN_TABS)
            }
            SettingsScreen(
                onBack = { viewModel.navigateTo(ScreenState.MAIN_TABS) },
                onLogout = { viewModel.logout() }
            )
        }
        uiState.currentScreen == ScreenState.HOST_CENTER -> {
            BackHandler {
                viewModel.navigateTo(ScreenState.MAIN_TABS)
            }
            HostCenterScreen(
                userProfile = uiState.userProfile,
                onBack = { viewModel.navigateTo(ScreenState.MAIN_TABS) }
            )
        }
        uiState.currentScreen == ScreenState.AI_CREATOR -> {
            BackHandler {
                viewModel.navigateTo(ScreenState.MAIN_TABS)
            }
            AiCreatorScreen(
                currentPrompt = uiState.aiPrompt,
                isGenerating = uiState.isGeneratingImage,
                statusMessage = uiState.generatedImageStatus,
                onPromptChange = { viewModel.updateAiPrompt(it) },
                onGenerate = { viewModel.generateAiAvatar(it) },
                onBack = { viewModel.navigateTo(ScreenState.MAIN_TABS) }
            )
        }
        uiState.currentScreen == ScreenState.WALLET -> {
            BackHandler {
                viewModel.navigateTo(ScreenState.MAIN_TABS)
            }
            RechargeDialog(
                currentCoins = uiState.userProfile.xcoins,
                onRechargeSuccess = {
                    viewModel.addCoins(it)
                    viewModel.navigateTo(ScreenState.MAIN_TABS)
                },
                onDismiss = { viewModel.navigateTo(ScreenState.MAIN_TABS) }
            )
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: PKLiveViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BgDark,
        topBar = {
            TopNavigationBar(
                activeTab = uiState.topNavTab,
                onTabSelected = { viewModel.setTopNavTab(it) },
                onProfileClick = {
                    viewModel.setTopNavTab(0) // Mine
                    viewModel.setBottomNavTab(4)
                },
                onSearchClick = {
                    Toast.makeText(context, "Search Broadcasters & Party Rooms", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                activeTab = uiState.bottomNavTab,
                onTabSelected = { viewModel.setBottomNavTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
            ) {
                when {
                // If Bottom Tab is 3 (Chat/Message)
                uiState.bottomNavTab == 3 -> {
                    MessageScreen(messages = uiState.messages)
                }
                // If Bottom Tab is 2 (Game Center)
                uiState.bottomNavTab == 2 -> {
                    GameCenterScreen(
                        onAddCoins = { viewModel.addCoins(it) }
                    )
                }
                // If Bottom Tab is 1 (Discover)
                uiState.bottomNavTab == 1 -> {
                    LiveGridScreen(
                        broadcasters = uiState.broadcasters,
                        partyRooms = uiState.partyRooms,
                        selectedFilter = uiState.liveFilter,
                        onFilterSelected = { viewModel.setLiveFilter(it) },
                        onBroadcasterClick = { viewModel.openLiveRoom(it) },
                        onStartLiveClick = { viewModel.startLiveBroadcast() },
                        onMyRoomClick = {
                            viewModel.openLiveRoom(
                                Broadcaster(
                                    id = "my_room",
                                    name = "${uiState.userProfile.username}'s Room",
                                    title = "Personal VIP Lounge 👑",
                                    viewerCount = 1,
                                    isPkActive = false
                                )
                            )
                        }
                    )
                }
                // Top Tab 0: Mine Profile
                uiState.topNavTab == 0 -> {
                    MineProfileScreen(
                        userProfile = uiState.userProfile,
                        onNavigateSettings = { viewModel.navigateTo(ScreenState.SETTINGS) },
                        onNavigateHostCenter = { viewModel.navigateTo(ScreenState.HOST_CENTER) },
                        onNavigateAiStudio = { viewModel.navigateTo(ScreenState.AI_CREATOR) },
                        onRechargeClick = { viewModel.setRechargeDialog(true) },
                        onVipCenterClick = {
                            viewModel.triggerEntryEffect(EntryEffectType.LION)
                        },
                        onMyRoomClick = {
                            viewModel.openLiveRoom(
                                Broadcaster(
                                    id = "my_room",
                                    name = "${uiState.userProfile.username}'s Room",
                                    title = "Personal VIP Lounge 👑",
                                    viewerCount = 1,
                                    isPkActive = false
                                )
                            )
                        }
                    )
                }
                // Top Tab 1: Party
                uiState.topNavTab == 1 -> {
                    PartyScreen(
                        partyRooms = uiState.partyRooms,
                        onRoomClick = { room ->
                            viewModel.openLiveRoom(
                                Broadcaster(
                                    id = room.id,
                                    name = room.hostName,
                                    title = room.title,
                                    viewerCount = room.listenerCount,
                                    isPkActive = false
                                )
                            )
                        }
                    )
                }
                // Top Tab 2: Live Grid (Default)
                else -> {
                    LiveGridScreen(
                        broadcasters = uiState.broadcasters,
                        partyRooms = uiState.partyRooms,
                        selectedFilter = uiState.liveFilter,
                        onFilterSelected = { viewModel.setLiveFilter(it) },
                        onBroadcasterClick = { viewModel.openLiveRoom(it) },
                        onStartLiveClick = { viewModel.startLiveBroadcast() },
                        onMyRoomClick = {
                            viewModel.openLiveRoom(
                                Broadcaster(
                                    id = "my_room",
                                    name = "${uiState.userProfile.username}'s Room",
                                    title = "Personal VIP Lounge 👑",
                                    viewerCount = 1,
                                    isPkActive = false
                                )
                            )
                        }
                    )
                }
            }

            // Coin Recharge Dialog
            if (uiState.showRechargeDialog) {
                RechargeDialog(
                    currentCoins = uiState.userProfile.xcoins,
                    onRechargeSuccess = {
                        viewModel.addCoins(it)
                        viewModel.setRechargeDialog(false)
                    },
                    onDismiss = { viewModel.setRechargeDialog(false) }
                )
            }
        }
    }
}
}

@Composable
fun TopNavigationBar(
    activeTab: Int, // 0: Mine, 1: Party, 2: Live
    onTabSelected: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("Mine", "Party", "Live")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mine | Party | Live tabs with pink underline
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = activeTab == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onTabSelected(index) }
                ) {
                    Text(
                        text = title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                        fontSize = if (isSelected) 18.sp else 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(1.5.dp))
                                .background(NeonPink)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }
        }

        // Right side: Profile circle + Search icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, NeonPink, CircleShape)
                    .background(
                        Brush.linearGradient(listOf(NeonPurple, NeonPink))
                    )
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "P",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    activeTab: Int, // 0: Party, 1: Discover, 2: Game, 3: Chat, 4: Profile
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Pair("Party", Icons.Default.Group),
        Pair("Discover", Icons.Default.Explore),
        Pair("Game", Icons.Default.Casino),
        Pair("Chat", Icons.Default.ChatBubble),
        Pair("Profile", Icons.Default.Person)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(Color(0xFF141226))
            .border(0.5.dp, Color(0xFF262142))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, (label, icon) ->
            val isSelected = activeTab == index
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (isSelected) NeonPink else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    color = if (isSelected) NeonPink else Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun GameCenterScreen(
    onAddCoins: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var diceResult by remember { mutableIntStateOf(6) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LUDO & Lucky Dice Mini-Game",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Roll the lucky dice to win free Xcoins!",
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFDB2777)))
                )
                .border(2.dp, NeonGold, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$diceResult 🎲",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 44.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                diceResult = Random.nextInt(1, 7)
                val won = diceResult * 50
                onAddCoins(won)
                Toast.makeText(context, "Rolled $diceResult! Won $won Xcoins!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.width(220.dp)
        ) {
            Text(
                text = "ROLL DICE",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
