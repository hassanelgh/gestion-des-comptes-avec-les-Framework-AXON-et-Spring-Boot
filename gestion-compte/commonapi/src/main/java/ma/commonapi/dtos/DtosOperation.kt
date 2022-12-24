package ma.commonapi.dtos

import ma.commonapi.enums.OperationType

data class OperationAccountRequestDTO(
        var accountId:String="",
        var currency:String="",
        var amount:Double=0.0
)