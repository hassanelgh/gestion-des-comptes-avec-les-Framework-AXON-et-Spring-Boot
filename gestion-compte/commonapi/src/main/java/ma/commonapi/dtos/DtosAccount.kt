package ma.commonapi.dtos

data class CreateAccountRequestDTO(
        var currency:String="",
        var initBalance:Double= 0.0
)
