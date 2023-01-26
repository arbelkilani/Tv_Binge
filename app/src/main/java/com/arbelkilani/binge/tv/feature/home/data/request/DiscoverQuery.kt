package com.arbelkilani.binge.tv.feature.home.data.request

class DiscoverQuery {
    data class Builder(
        var hashMap: HashMap<String, String> = hashMapOf()
    ) {

        fun sortBy(value: SortBy) = apply { hashMap["sort_by"] = value.value }
        fun airDateGte(value: String) = apply { hashMap["air_date.gte"] = value }
        fun airDateLte(value: String) = apply { hashMap["air_date.lte"] = value }
        fun firstAirDateGte(value: String) = apply { hashMap["first_air_date.gte"] = value }
        fun firstAirDateLte(value: String) = apply { hashMap["first_air_date.lte"] = value }
        fun firstAirDateYear(value: String) = apply { hashMap["first_air_date_year"] = value }
        fun timezone(value: String) = apply { hashMap["timezone"] = value }
        fun voteAverageGte(value: String) = apply { hashMap["vote_average.gte"] = value }
        fun voteCountGte(value: String) = apply { hashMap["vote_count.gte"] = value }
        fun genres(value: String?) =
            apply { if (!value.isNullOrEmpty()) hashMap["with_genres"] = value }

        fun networks(value: String) = apply { hashMap["with_networks"] = value }
        fun runtimeGte(value: String) = apply { hashMap["with_runtime.gte"] = value }
        fun runtimeLte(value: String) = apply { hashMap["with_runtime.lte"] = value }
        fun watchRegion(value: String) = apply { hashMap["watch_region"] = value }
        fun watchProviders(value: String?) =
            apply { if (!value.isNullOrEmpty()) hashMap["with_watch_providers"] = value }

        fun monetizationType(value: MonetizationType) =
            apply { hashMap["with_watch_monetization_types"] = value.value }

        fun status(value: Status) = apply { hashMap["with_status"] = value.value }
        fun type(value: Type) = apply { hashMap["with_type"] = value.value }

        fun build() = hashMap
    }

    enum class SortBy(val value: String) {
        VOTE_AVERAGE_DESC("vote_average.desc"), VOTE_AVERAGE_ASC("vote_average.asc"), FIRST_AIR_DATE_DESC(
            "first_air_date.desc"
        ),
        FIRST_AIR_DATE_ASC("first_air_date.asc"),
    }

    enum class Type(val value: String) {
        DOCUMENTARY("0"), NEWS("1"), MINISERIES("2"), REALITY("3"), SCRIPTED("4"), TALK_SHOW("5"), VIDEO(
            "6"
        )
    }

    enum class Status(val value: String) {
        RETURNING_SERIES("0"), PLANNED("1"), IN_PRODUCTION("2"), ENDED("3"), CANCELLED("4"), PILOT("5")
    }

    enum class MonetizationType(val value: String) {
        FLAT_RATE("flatrate"), FREE("free"), ADS("ads"), RENT("rent"), BUY("buy")
    }
}
