package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.response.Certification
import com.arbelkilani.binge.tv.common.domain.entity.CertificationEntity
import javax.inject.Inject

class CertificationMapper @Inject constructor() {

    fun map(certification: Certification) = CertificationEntity(
        name = certification.name,
        meaning = certification.meaning,
        order = certification.order
    )
}