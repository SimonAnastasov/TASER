package amon.taser.config

object JwtAuthConstants {
    const val SECRET = "s3cr3tt0k3n"
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
}
