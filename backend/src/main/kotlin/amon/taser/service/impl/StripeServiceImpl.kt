package amon.taser.service.impl

import amon.taser.model.User
import amon.taser.service.StripeService
import amon.taser.service.TranscriptionService
import com.stripe.Stripe
import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.model.Transfer
import com.stripe.param.AccountCreateParams
import com.stripe.param.AccountLinkCreateParams
import org.springframework.stereotype.Service

import amon.taser.model.CreatePayment;
// import amon.taser.model.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID

@Service
class StripeServiceImpl (
        val transcriptionService: TranscriptionService,
) : StripeService {
    private val apiKey = "sk_test_51NAbKcKkVIc94TENfNN0LVe2TvNv1HdieqfHwxSi22LHaP5kl69Mt0fA3bTRwBzAaMgUSV4PyMYzlntiHmrr7k7O00BetHiXtn";

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
        Stripe.apiKey = apiKey

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
        Stripe.apiKey = apiKey

        val params = AccountLinkCreateParams.builder()
            .setAccount(userId)
            .setRefreshUrl(returnUrl)
            .setReturnUrl(returnUrl)
            .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
            .build()

        return AccountLink.create(params).url
    }

    override fun getPaymentIntentClientSecret(transcriptionId: UUID): String {
        Stripe.apiKey = apiKey

        val transcription = transcriptionService.getTranscriptionResult(transcriptionId)

        val createParams = PaymentIntentCreateParams.Builder()
            .setCurrency("usd")
            // .putMetadata("featureRequest", createPayment.getFeatureRequest())
            .setAmount(transcription?.text?.length?.times(0.0001)?.toLong())
            .build()

        val intent = PaymentIntent.create(createParams)
        return intent.clientSecret
    }
}
