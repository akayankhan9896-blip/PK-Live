package com.example.model

enum class Gender {
    MALE, FEMALE
}

enum class EntryEffectType(val displayName: String, val coinPrice: Int) {
    SKATEBOARD("Neon Skateboard", 1000),
    BIKE("Golden Bike", 3000),
    LION("Roaring Lion", 5000),
    CAR("Cyber Supercar", 10000),
    TIGER("Fierce Tiger", 15000),
    EAGLE("Thunder Eagle", 20000),
    DRAGON("Cosmic Dragon", 30000)
}

data class GiftItem(
    val id: String,
    val name: String,
    val coins: Int,
    val iconEmoji: String,
    val description: String
)

data class UserProfile(
    val id: String = "38318266",
    val username: String = "_PAKHTOON_",
    val bio: String = "Live for excitement! PK Battle King 👑",
    val gender: Gender = Gender.MALE,
    val age: Int = 21,
    val vipLevel: Int = 1,
    val userLevel: Int = 30,
    val heartLevel: Int = 3,
    val friendsCount: Int = 18,
    val followingCount: Int = 20,
    val followersCount: Int = 19,
    val xcoins: Long = 12500,
    val diamonds: Long = 8400,
    val agencyName: String = "VIP TITANS AGENCY",
    val isHost: Boolean = true,
    val liveHoursToday: Float = 3.5f,
    val monthlyRank: Int = 4
)

data class Broadcaster(
    val id: String,
    val name: String,
    val title: String,
    val viewerCount: Int,
    val isPkActive: Boolean,
    val pkOpponentName: String? = null,
    val gender: Gender = Gender.FEMALE,
    val age: Int = 22,
    val level: Int = 25,
    val vipLevel: Int = 2,
    val fireCount: Int = 272,
    val tag: String = "Popular",
    val rtmpUrl: String = "rtmp://live.pk-live.web.app/live/"
)

data class LiveChatMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val userLevel: Int = 12,
    val vipLevel: Int = 0,
    val isGiftNotice: Boolean = false,
    val isSystem: Boolean = false
)

data class PartyRoom(
    val id: String,
    val title: String,
    val hostName: String,
    val category: String,
    val listenerCount: Int,
    val isOfficial: Boolean,
    val badgeLabel: String,
    val avatars: List<String>
)

data class ChatConversation(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timeAgo: String,
    val isOfficial: Boolean = false,
    val vipLevel: Int = 0,
    val unreadCount: Int = 0,
    val gender: Gender? = null,
    val age: Int? = null,
    val filterType: String = "All" // Friend request, Events, Notification, Visitors
)

data class VoiceSeat(
    val seatIndex: Int,
    val userName: String? = null,
    val isMuted: Boolean = false,
    val isSpeaking: Boolean = false,
    val isHost: Boolean = false
)

data class RechargePackage(
    val pkrAmount: Int,
    val coinsAmount: Int,
    val bonusCoins: Int = 0,
    val isPopular: Boolean = false
)
