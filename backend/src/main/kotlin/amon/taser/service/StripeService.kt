package amon.taser.service

import amon.taser.model.User

interface StripeService {
    fun payOutToUser(userId: String, amount: Int)
    fun createConnectAccountForUser(user: User): String
    fun createConnectedLinkForUser(userId: String, returnUrl: String): String
}