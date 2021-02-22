package com.mohammadosman.unittest.domain

interface DomainMapper<DomainModel, T> {

    fun mapToDomainModel(t: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T

}