package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

val SuggestedPrompts = listOf(
    "Neon purple cyber lion avatar with gold crown",
    "Futuristic glowing microphone live banner with lightning",
    "VIP Dragon entry badge with holographic aura",
    "Gold and diamond championship trophy with sparks",
    "Cosmic space esports stadium with crowd"
)

@Composable
fun AiCreatorScreen(
    currentPrompt: String,
    isGenerating: Boolean,
    statusMessage: String?,
    onPromptChange: (String) -> Unit,
    onGenerate: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var promptInput by remember { mutableStateOf(currentPrompt.ifEmpty { SuggestedPrompts[0] }) }
    var selectedAspect by remember { mutableStateOf("1:1 Avatar") }
    var generationSuccess by remember { mutableStateOf(false) }

    val rotation = remember { Animatable(0f) }
    LaunchedEffect(isGenerating) {
        if (isGenerating) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 40.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "AI Visual Studio",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Introduction Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF6366F1), Color(0xFFA855F7), Color(0xFFEC4899))
                    )
                )
                .padding(18.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = NeonGold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Create Custom Assets with AI",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Generate personal live avatars, streaming room backgrounds, and custom entry effects using Gemini AI prompts.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Prompt Input Field
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Describe your image:",
                color = Color.LightGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = promptInput,
                onValueChange = {
                    promptInput = it
                    onPromptChange(it)
                },
                placeholder = { Text("e.g. Glowing neon lion with golden wings...", color = Color.Gray) },
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonPurple,
                    unfocusedBorderColor = Color(0xFF2E2B4E),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Preset Prompts Chips
            Text(
                text = "Preset Prompts:",
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 6.dp)
            ) {
                items(SuggestedPrompts) { prompt ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF201B3B))
                            .border(1.dp, Color(0xFF332B5E), RoundedCornerShape(12.dp))
                            .clickable {
                                promptInput = prompt
                                onPromptChange(prompt)
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = prompt,
                            color = Color(0xFFC4B5FD),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Aspect Ratio Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("1:1 Avatar", "16:9 Banner").forEach { opt ->
                    val isSel = opt == selectedAspect
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSel) NeonPurple else Color(0xFF201B3B))
                            .clickable { selectedAspect = opt }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = opt,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Generate Button
            Button(
                onClick = {
                    if (promptInput.isNotBlank()) {
                        generationSuccess = true
                        onGenerate(promptInput)
                        Toast.makeText(context, "Generating with Gemini AI...", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isGenerating,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                shape = RoundedCornerShape(18.dp)
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Creating with AI...", color = Color.White)
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = NeonGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Generate Artwork",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Preview Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(if (selectedAspect.contains("16:9")) 16f / 9f else 1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.radialGradient(
                            listOf(
                                Color(0xFF311042),
                                Color(0xFF1E1B4B),
                                Color(0xFF0F0F19)
                            )
                        )
                    )
                    .border(1.5.dp, Color(0xFF332B5E), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isGenerating) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = NeonGold,
                            modifier = Modifier
                                .size(48.dp)
                                .rotate(rotation.value)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = statusMessage ?: "Synthesizing pixels...",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )
                    }
                } else if (generationSuccess) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(NeonCyan, NeonPurple, NeonPink))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🎨",
                                fontSize = 42.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Artwork Generated!",
                            color = NeonGold,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = promptInput,
                            color = Color.White,
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(NeonPurple)
                                    .clickable {
                                        Toast.makeText(context, "Applied as Avatar!", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Apply as Avatar", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF2E2B4E))
                                    .clickable {
                                        Toast.makeText(context, "Saved to Backpack!", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Save Asset", color = Color.White, fontSize = 11.sp)
                            }
                        }
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ready to generate artwork",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
