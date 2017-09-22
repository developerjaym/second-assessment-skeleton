package com.cooksys.second.mapper;

import org.mapstruct.Mapper;

import com.cooksys.second.dto.ContextDto;
import com.cooksys.second.entity.Context;

@Mapper(componentModel="spring")
public interface ContextMapper {

	public ContextDto toDto(Context context);

}
