package com.cowday.pawtography.ui.navigation

sealed interface PawtographyNav {
    val route: String
}

data object HomeScreen: PawtographyNav {
    override val route: String = PawtographyNavScreen.HOME.route
}

data object GenerateScreen: PawtographyNav {
    override val route: String = PawtographyNavScreen.GENERATE.route
}

data object CacheScreen: PawtographyNav {
    override val route: String = PawtographyNavScreen.CACHE.route
}


enum class PawtographyNavScreen(val route: String) {
    HOME("Home"),
    GENERATE("Generate"),
    CACHE("Cache")
}