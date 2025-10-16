package com.springbunny.tasks.mappers;

import com.springbunny.tasks.domain.dto.TaskListDto;
import com.springbunny.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

   TaskListDto toDto(TaskList taskList);
}
