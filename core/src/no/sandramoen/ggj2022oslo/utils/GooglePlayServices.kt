package no.sandramoen.ggj2022oslo.utils

interface GooglePlayServices {
    fun signIn()
    fun signOut()
    fun isSignedIn(): Boolean
    fun getLeaderboard()
    fun fetchHighScore()
    fun getHighScore(): Int
    fun submitScore(score: Int)
    fun incrementAchievements(level: String)
    fun showAchievements()
}
