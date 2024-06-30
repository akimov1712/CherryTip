package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.LoginDto
import ru.topbun.cherry_tip.data.source.network.dto.SignUpDto
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity

fun LoginEntity.toDto() = LoginDto(
    email = email,
    password = password
)

fun LoginDto.toEntity() = LoginEntity(
    email = email,
    password = password
)

fun SignUpEntity.toDto() = SignUpDto(
    username = username,
    email = email,
    password = password
)

fun SignUpDto.toEntity() = SignUpEntity(
    username = username,
    email = email,
    password = password
)