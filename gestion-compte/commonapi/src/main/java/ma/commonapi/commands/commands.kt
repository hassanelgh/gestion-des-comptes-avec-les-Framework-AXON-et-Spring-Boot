package ma.commonapi.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

abstract class  BaseCommand<T>(
        @TargetAggregateIdentifier
        open val id: T
)

data class CreateAccountCommand(
        override val id: String,
        val currency: String,
        val initBalance:Double
): BaseCommand<String>(id)


data class CreditAccountCommand(
        override val id: String,
        val currency: String,
        val amount:Double
): BaseCommand<String>(id)

data class DebitAccountCommand(
        override val id: String,
        val currency: String,
        val amount:Double
): BaseCommand<String>(id)


