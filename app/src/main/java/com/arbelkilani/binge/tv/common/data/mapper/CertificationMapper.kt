package com.arbelkilani.binge.tv.common.data.mapper

import com.arbelkilani.binge.tv.common.data.model.Certification
import com.arbelkilani.binge.tv.common.domain.model.CertificationEntity
import javax.inject.Inject

class CertificationMapper @Inject constructor() {

    fun map(certification: Certification) = CertificationEntity(
        name = certification.name,
        meaning = certification.meaning,
        order = certification.order
    )
}