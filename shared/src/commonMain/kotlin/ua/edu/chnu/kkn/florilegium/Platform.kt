package ua.edu.chnu.kkn.florilegium

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform