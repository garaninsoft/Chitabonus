package com.garaninsoft.chitabonus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.garaninsoft.chitabonus.ui.screens.addchild.AddChildScreen
import com.garaninsoft.chitabonus.ui.screens.childlist.ChildListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.ChildList.route
    ) {
        composable(Screens.ChildList.route) {
            ChildListScreen(
                onAddChild = {
                    navController.navigate(Screens.AddChild.route)
                },
                onChildClick = { childId ->
                    // TODO: Переход на профиль ребёнка
                }
            )
        }

        composable(Screens.AddChild.route) {
            AddChildScreen(navController = navController)
        }

        // TODO: Добавить остальные экраны
    }
}

sealed class Screens(val route: String) {
    object ChildList : Screens("child_list")
    object AddChild : Screens("add_child")
    object ChildProfile : Screens("child_profile/{childId}") {
        fun createRoute(childId: Long) = "child_profile/$childId"
    }

    object BookList : Screens("book_list")
    object Examination : Screens("examination/{childBookId}") {
        fun createRoute(childBookId: Long) = "examination/$childBookId"
    }
}