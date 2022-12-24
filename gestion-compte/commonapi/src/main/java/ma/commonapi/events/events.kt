package ma.commonapi.events

import ma.commonapi.enums.AccountStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.Date

abstract class  BaseEvent<T>(
        @TargetAggregateIdentifier
        open val id: T
)

data class AccountActivatedEvent(
        override val id: String,
        val status: AccountStatus,
): BaseEvent<String>(id)


data class AccountCreatedEvent(
        override val id: String,
        val currency: String,
        val balance:Double,
        val status: AccountStatus,
): BaseEvent<String>(id)

data class AccountCreditedEvent(
        override val id: String,
        val currency: String,
        val amount:Double,
        val dateCreation:Date
): BaseEvent<String>(id)

data class AccountDebitedEvent(
        override val id: String,
        val currency: String,
        val amount:Double,
        val dateCreation:Date
): BaseEvent<String>(id)