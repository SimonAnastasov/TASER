package amon.taser.service

import amon.taser.model.User
import java.util.UUID

interface StripeService {
    fun payOutToUser(userId: String, amount: Int)
    fun createConnectAccountForUser(user: User): String
    fun createConnectedLinkForUser(userId: String, returnUrl: String): String
    fun getPaymentIntentClientSecret(transcriptionId: UUID): String
}