package com.ngapak.dev.javaelectronics.features.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ngapak.dev.javaelectronics.core.Injection
import com.ngapak.dev.javaelectronics.features.auth.presentation.AuthViewModel
import com.ngapak.dev.javaelectronics.features.auth.presentation.AuthViewModelFactory

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            Icons.Rounded.Person,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Fajar Sidik Prasetio",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.Edit, contentDescription = "Edit Full Name")
            }
        }
        HorizontalDivider()
        CustomListTile(icon = Icons.Rounded.Mail, iconDescription = "Edit Email", title = "Email")
        HorizontalDivider()
        CustomListTile(
            icon = Icons.Rounded.Phone,
            iconDescription = "Edit Phone Number",
            title = "Phone"
        )
        HorizontalDivider()

        Button(onClick = {
            authViewModel.signOut()
            navigateToLogin()
        }, modifier = modifier.fillMaxWidth()) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun CustomListTile(
    icon: ImageVector,
    title: String,
    iconDescription: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = iconDescription)
        Text(
            text = title,
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        IconButton(onClick = {}) {
            Icon(Icons.Rounded.Edit, contentDescription = "Edit $title")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ListTilePreview() {
    ProfileScreen(
        viewModel(factory = AuthViewModelFactory(Injection.provideAuthUseCase())),
        navigateToLogin = {})
}