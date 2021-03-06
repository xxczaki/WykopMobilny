package io.github.feelfreelinux.wykopmobilny.models.pojo.apiv2.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
open class WykopApiResponse<out T : Any> (
        @JsonProperty("data")
        override val data : T?,

        @JsonProperty("error")
        override val error : WykopErrorResponse?
) : ApiResponse<T>