package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.service.StripeService
import com.stripe.Stripe
import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.model.Transfer
import com.stripe.param.AccountCreateParams
import com.stripe.param.AccountLinkCreateParams
import org.springframework.stereotype.Service

@Service
class StripeServiceImpl: StripeService {
    private val apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

    override fun payOutToUser(userId: String, amount: Int) {
        Stripe.apiKey = apiKey

        val params = mapOf<String, Any>(
            "amount" to amount,
            "currency" to "usd",
            "destination" to userId
        )
        Transfer.create(params)
    }

    override fun createConnectAccountForUser(user: User): String {
        val params = AccountCreateParams.builder()
            .setType(AccountCreateParams.Type.EXPRESS)
            .setCountry("US")
            .setEmail(user.email)
            .setDefaultCurrency("usd")
            .build()

        val account: Account = Account.create(params)
        return account.id

    }

    override fun createConnectedLinkForUser(userId: String, returnUrl: String): String {
        val params = AccountLinkCreateParams.builder()
            .setAccount(userId)
            .setRefreshUrl(returnUrl)
            .setReturnUrl(returnUrl)
            .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
            .build()

        return AccountLink.create(params).url
    }
}