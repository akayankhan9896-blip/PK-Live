package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Broadcaster
import com.example.model.ChatConversation
import com.example.model.EntryEffectType
import com.example.model.Gender
import com.example.model.GiftItem
import com.example.model.LiveChatMessage
import com.example.model.PartyRoom
import com.example.model.UserProfile
import com.example.model.VoiceSeat
import com.example.ui.components.AvailableGifts
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ScreenState {
    LOGIN,
    MAIN_TABS,
    LIVE_ROOM,
    SETTINGS,
    HOST_CENTER,
    WALLET,
    AI_CREATOR
}

data class PKLiveUiState(
    val isLoggedIn: Boolean = false,
    val currentScreen: ScreenState = ScreenState.LOGIN,
    val topNavTab: Int = 2, // 0: Mine, 1: Party, 2: Live
    val bottomNavTab: Int = 0, // 0: Party, 1: Discover, 2: Game, 3: Chat, 4: Profile

    // User Profile (Starts with 0 followers, 0 following, 0 friends, 0 coins, 0 diamonds)
    val userProfile: UserProfile = UserProfile(
        id = "38318266",
        username = "_PAKHTOON_",
        bio = "Live for excitement! PK Battle King 👑",
        gender = Gender.MALE,
        age = 21,
        vipLevel = 1,
        userLevel = 1,
        heartLevel = 1,
        friendsCount = 0,
        followingCount = 0,
        followersCount = 0,
        xcoins = 0,
        diamonds = 0,
        agencyName = "VIP TITANS AGENCY",
        isHost = true,
        liveHoursToday = 0f,
        monthlyRank = 0
    ),

    // Real-time Collections start completely empty
    val liveFilter: String = "Recently",
    val broadcasters: List<Broadcaster> = emptyList(),
    val partyRooms: List<PartyRoom> = emptyList(),
    val messages: List<ChatConversation> = emptyList(),

    // Active Live Room State
    val activeBroadcaster: Broadcaster? = null,
    val isPkMatchActive: Boolean = true,
    val hostPkScore: Long = 0,
    val opponentPkScore: Long = 0,
    val pkTimeLeftSeconds: Int = 300, // 5 min
    val pkWinner: String? = null,
    val isUserBroadcasting: Boolean = false,
    val isVoiceMuted: Boolean = false,
    val voiceSeats: List<VoiceSeat> = emptyList(),
    val liveChatMessages: List<LiveChatMessage> = emptyList(),
    val activeEntryEffect: EntryEffectType? = null,
    val activeGiftCelebration: Pair<GiftItem, String>? = null,
    val showRechargeDialog: Boolean = false,
    val showGiftDrawer: Boolean = false,

    // AI Creator state
    val aiPrompt: String = "",
    val isGeneratingImage: Boolean = false,
    val generatedImageStatus: String? = null
)

class PKLiveViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PKLiveUiState())
    val uiState: StateFlow<PKLiveUiState> = _uiState.asStateFlow()

    private var pkTimerJob: Job? = null

    init {
        // Voice seats initialized for empty live rooms
        val emptySeats = List(8) { index ->
            VoiceSeat(
                seatIndex = index,
                userName = null,
                isHost = index == 0,
                isMuted = false
            )
        }
        _uiState.update { it.copy(voiceSeats = emptySeats) }
    }

    fun login(profile: UserProfile) {
        _uiState.update {
            it.copy(
                isLoggedIn = true,
                userProfile = profile,
                currentScreen = ScreenState.MAIN_TABS
            )
        }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                isLoggedIn = false,
                currentScreen = ScreenState.LOGIN
            )
        }
    }

    fun setTopNavTab(index: Int) {
        _uiState.update { it.copy(topNavTab = index) }
    }

    fun setBottomNavTab(index: Int) {
        _uiState.update {
            it.copy(
                bottomNavTab = index,
                topNavTab = when (index) {
                    0 -> 1 // Party
                    4 -> 0 // Mine
                    else -> it.topNavTab
                }
            )
        }
    }

    fun setLiveFilter(filter: String) {
        _uiState.update { it.copy(liveFilter = filter) }
    }

    fun startLiveBroadcast(title: String = "My Live Stream Room 🎙️") {
        val user = _uiState.value.userProfile
        val newRoom = Broadcaster(
            id = "room_${System.currentTimeMillis()}",
            name = user.username,
            title = title,
            viewerCount = 1,
            isPkActive = true,
            pkOpponentName = "Challenger_Bot",
            gender = user.gender,
            age = user.age,
            level = user.userLevel,
            vipLevel = user.vipLevel,
            fireCount = 1,
            tag = "Live Host"
        )

        // Add to broadcasters list so room is visible
        _uiState.update { state ->
            state.copy(
                broadcasters = listOf(newRoom) + state.broadcasters.filter { it.id != newRoom.id }
            )
        }

        openLiveRoom(newRoom)
    }

    fun createPartyRoom(title: String = "VIP Voice Party Lounge 🎵") {
        val user = _uiState.value.userProfile
        val newParty = PartyRoom(
            id = "party_${System.currentTimeMillis()}",
            title = title,
            hostName = user.username,
            category = "Music",
            listenerCount = 1,
            isOfficial = false,
            badgeLabel = "HOT",
            avatars = listOf(user.username.take(2).uppercase())
        )

        _uiState.update { state ->
            state.copy(partyRooms = listOf(newParty) + state.partyRooms)
        }

        openLiveRoom(
            Broadcaster(
                id = newParty.id,
                name = newParty.hostName,
                title = newParty.title,
                viewerCount = newParty.listenerCount,
                isPkActive = false
            )
        )
    }

    fun openLiveRoom(broadcaster: Broadcaster) {
        val initialSeats = List(8) { index ->
            VoiceSeat(
                seatIndex = index,
                userName = if (index == 0) broadcaster.name else null,
                isHost = index == 0,
                isMuted = false
            )
        }

        _uiState.update {
            it.copy(
                currentScreen = ScreenState.LIVE_ROOM,
                activeBroadcaster = broadcaster,
                isPkMatchActive = broadcaster.isPkActive,
                hostPkScore = 0,
                opponentPkScore = 0,
                pkTimeLeftSeconds = 300,
                pkWinner = null,
                voiceSeats = initialSeats,
                liveChatMessages = listOf(
                    LiveChatMessage("1", "System", "Welcome to ${broadcaster.name}'s room!", isSystem = true)
                )
            )
        }
        startPkTimer()
    }

    fun closeLiveRoom() {
        pkTimerJob?.cancel()
        _uiState.update { it.copy(currentScreen = ScreenState.MAIN_TABS) }
    }

    fun navigateTo(screen: ScreenState) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    private fun startPkTimer() {
        pkTimerJob?.cancel()
        pkTimerJob = viewModelScope.launch {
            while (_uiState.value.pkTimeLeftSeconds > 0 && _uiState.value.isPkMatchActive) {
                delay(1000)
                _uiState.update { state ->
                    val newTime = state.pkTimeLeftSeconds - 1
                    if (newTime <= 0) {
                        val winner = if (state.hostPkScore >= state.opponentPkScore) {
                            state.activeBroadcaster?.name ?: "Host"
                        } else {
                            state.activeBroadcaster?.pkOpponentName ?: "Challenger"
                        }
                        state.copy(pkTimeLeftSeconds = 0, pkWinner = winner)
                    } else {
                        state.copy(pkTimeLeftSeconds = newTime)
                    }
                }
            }
        }
    }

    fun rematchPk() {
        _uiState.update {
            it.copy(
                pkTimeLeftSeconds = 300,
                pkWinner = null,
                hostPkScore = 0,
                opponentPkScore = 0,
                isPkMatchActive = true
            )
        }
        startPkTimer()
    }

    fun togglePkMatch() {
        val nextState = !_uiState.value.isPkMatchActive
        _uiState.update {
            it.copy(
                isPkMatchActive = nextState,
                pkTimeLeftSeconds = if (nextState) 300 else 0,
                pkWinner = null
            )
        }
        if (nextState) startPkTimer() else pkTimerJob?.cancel()
    }

    fun triggerEntryEffect(effectType: EntryEffectType) {
        _uiState.update { it.copy(activeEntryEffect = effectType) }
    }

    fun clearEntryEffect() {
        _uiState.update { it.copy(activeEntryEffect = null) }
    }

    fun sendGift(gift: GiftItem) {
        val currentCoins = _uiState.value.userProfile.xcoins
        if (currentCoins < gift.coins) {
            _uiState.update { it.copy(showRechargeDialog = true) }
            return
        }

        val updatedCoins = currentCoins - gift.coins
        val updatedHostScore = _uiState.value.hostPkScore + gift.coins
        val sender = _uiState.value.userProfile.username

        val newNotice = LiveChatMessage(
            id = System.currentTimeMillis().toString(),
            senderName = sender,
            message = "sent ${gift.name} ${gift.iconEmoji} (${gift.coins} Coins)!",
            isGiftNotice = true,
            userLevel = _uiState.value.userProfile.userLevel,
            vipLevel = _uiState.value.userProfile.vipLevel
        )

        _uiState.update { state ->
            state.copy(
                userProfile = state.userProfile.copy(xcoins = updatedCoins),
                hostPkScore = updatedHostScore,
                activeGiftCelebration = Pair(gift, sender),
                liveChatMessages = state.liveChatMessages + newNotice,
                showGiftDrawer = false
            )
        }
    }

    fun clearGiftCelebration() {
        _uiState.update { it.copy(activeGiftCelebration = null) }
    }

    fun addCoins(amount: Int) {
        _uiState.update { state ->
            state.copy(
                userProfile = state.userProfile.copy(
                    xcoins = state.userProfile.xcoins + amount
                )
            )
        }
    }

    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val sender = _uiState.value.userProfile
        val msg = LiveChatMessage(
            id = System.currentTimeMillis().toString(),
            senderName = sender.username,
            message = text,
            userLevel = sender.userLevel,
            vipLevel = sender.vipLevel
        )
        _uiState.update {
            it.copy(liveChatMessages = it.liveChatMessages + msg)
        }
    }

    fun toggleVoiceSeat(seatIndex: Int) {
        val userName = _uiState.value.userProfile.username
        _uiState.update { state ->
            val updatedSeats = state.voiceSeats.map { seat ->
                if (seat.seatIndex == seatIndex) {
                    if (seat.userName == null) {
                        seat.copy(userName = userName, isSpeaking = true)
                    } else if (seat.userName == userName) {
                        seat.copy(isMuted = !seat.isMuted)
                    } else {
                        seat
                    }
                } else {
                    seat
                }
            }
            state.copy(voiceSeats = updatedSeats)
        }
    }

    fun toggleUserVoiceMute() {
        _uiState.update { it.copy(isVoiceMuted = !it.isVoiceMuted) }
    }

    fun setRechargeDialog(show: Boolean) {
        _uiState.update { it.copy(showRechargeDialog = show) }
    }

    fun setGiftDrawer(show: Boolean) {
        _uiState.update { it.copy(showGiftDrawer = show) }
    }

    fun updateAiPrompt(prompt: String) {
        _uiState.update { it.copy(aiPrompt = prompt) }
    }

    fun generateAiAvatar(prompt: String) {
        _uiState.update { it.copy(isGeneratingImage = true, generatedImageStatus = "AI Studio generating banner with '$prompt'...") }
        viewModelScope.launch {
            delay(1800)
            _uiState.update {
                it.copy(
                    isGeneratingImage = false,
                    generatedImageStatus = "Custom AI banner generated successfully!"
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pkTimerJob?.cancel()
    }
}
