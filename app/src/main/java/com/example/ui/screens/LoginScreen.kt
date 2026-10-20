package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Gender
import com.example.model.UserProfile
import com.example.ui.theme.BgDark
import com.example.ui.theme.NeonGold
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple

@Composable
fun LoginScreen(
    onLoginSuccess: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isSignUpMode by remember { mutableStateOf(false) }
    var loginMethod by remember { mutableStateOf("PHONE") } // "GOOGLE", "PHONE", "QUICK"

    // Form inputs
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    // Profile setup inputs (for Sign Up)
    var usernameInput by remember { mutableStateOf("_PAKHTOON_") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    var bioInput by remember { mutableStateOf("Live for excitement! PK Battle King 👑") }

    var isLoading by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = BgDark
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 480.dp)
                    .statusBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(16.dp))

            // App Logo with glowing aura
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .border(
                        2.dp,
                        Brush.linearGradient(listOf(NeonPurple, NeonPink, NeonGold)),
                        RoundedCornerShape(26.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pklive_logo_1792456881909),
                    contentDescription = "PK LIVE Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "PK LIVE",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                letterSpacing = 2.sp
            )

            Text(
                text = "Live Broadcast • 1v1 PK Match • Voice Party",
                color = NeonGold,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tab switch: Sign In vs Sign Up
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E1B38))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (!isSignUpMode) NeonPurple else Color.Transparent)
                        .clickable { isSignUpMode = false }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sign In",
                        color = if (!isSignUpMode) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSignUpMode) NeonPurple else Color.Transparent)
                        .clickable { isSignUpMode = true }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Create Account",
                        color = if (isSignUpMode) Color.White else Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Google Login Button (Full Width)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF221F3D))
                    .border(1.dp, Color(0xFF373264), RoundedCornerShape(16.dp))
                    .clickable {
                        isLoading = true
                        val newProfile = UserProfile(
                            id = (10000000..99999999).random().toString(),
                            username = usernameInput.ifBlank { "Google_User" },
                            bio = bioInput,
                            gender = selectedGender,
                            age = 21,
                            userLevel = 1,
                            vipLevel = 1,
                            heartLevel = 1,
                            friendsCount = 0,
                            followingCount = 0,
                            followersCount = 0,
                            xcoins = 0,
                            diamonds = 0
                        )
                        Toast.makeText(context, "Google Sign-In successful!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess(newProfile)
                    }
                    .padding(vertical = 14.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "G",
                            color = Color(0xFFEA4335),
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Continue with Google",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider "OR"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF262142))
                Text(
                    text = "  OR USE PHONE  ",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFF262142))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number Input
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number (+92 ...)", fontSize = 12.sp) },
                placeholder = { Text("03214585825", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        tint = NeonPurple,
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonPurple,
                    unfocusedBorderColor = Color(0xFF2E2B4E),
                    focusedLabelColor = NeonPurple,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password / OTP Input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password / OTP", fontSize = 12.sp) },
                placeholder = { Text("Enter password or OTP", fontSize = 12.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = NeonPurple,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle",
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonPurple,
                    unfocusedBorderColor = Color(0xFF2E2B4E),
                    focusedLabelColor = NeonPurple,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // Sign Up Profile Info Details
            AnimatedVisibility(visible = isSignUpMode) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Username
                    OutlinedTextField(
                        value = usernameInput,
                        onValueChange = { usernameInput = it },
                        label = { Text("Choose Username", fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = NeonPink, modifier = Modifier.size(20.dp))
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonPink,
                            unfocusedBorderColor = Color(0xFF2E2B4E),
                            focusedLabelColor = NeonPink,
                            unfocusedLabelColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Gender Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(Gender.MALE to "♂ Male", Gender.FEMALE to "♀ Female").forEach { (g, label) ->
                            val isSel = selectedGender == g
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSel) NeonPurple else Color(0xFF221F3D))
                                    .clickable { selectedGender = g }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Action Button (Sign In / Register)
            Button(
                onClick = {
                    val user = UserProfile(
                        id = "38318266",
                        username = usernameInput.ifBlank { "_PAKHTOON_" },
                        bio = bioInput,
                        gender = selectedGender,
                        age = 21,
                        userLevel = 1,
                        vipLevel = 1,
                        heartLevel = 1,
                        friendsCount = 0,
                        followingCount = 0,
                        followersCount = 0,
                        xcoins = 0,
                        diamonds = 0
                    )
                    Toast.makeText(context, if (isSignUpMode) "Account created!" else "Welcome back!", Toast.LENGTH_SHORT).show()
                    onLoginSuccess(user)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (isSignUpMode) "CREATE ACCOUNT & ENTER" else "LOG IN",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 15.sp,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Guest Login Option
            Text(
                text = "Explore as Guest",
                color = Color.LightGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        val guestProfile = UserProfile(
                            id = (10000000..99999999).random().toString(),
                            username = "Guest_${(100..999).random()}",
                            bio = "New explorer on PK LIVE",
                            gender = Gender.MALE,
                            age = 21,
                            userLevel = 1,
                            vipLevel = 1,
                            heartLevel = 1,
                            friendsCount = 0,
                            followingCount = 0,
                            followersCount = 0,
                            xcoins = 0,
                            diamonds = 0
                        )
                        Toast.makeText(context, "Logged in as Guest", Toast.LENGTH_SHORT).show()
                        onLoginSuccess(guestProfile)
                    }
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Policy Disclaimer
            Text(
                text = "By signing in, you agree to PK LIVE Terms of Service and Privacy Policy.",
                color = Color.Gray,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
}
