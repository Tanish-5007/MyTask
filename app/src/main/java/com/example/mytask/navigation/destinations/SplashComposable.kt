package com.example.mytask.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mytask.ui.screens.splash.SplashScreen



fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
){
    composable(
        route = "splash"
    ){
        SplashScreen(
            navigateToListScreen = navigateToListScreen
        )
    }
}