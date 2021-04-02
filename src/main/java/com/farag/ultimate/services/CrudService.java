package com.farag.ultimate.services;

import com.farag.ultimate.exceptions.AlreadyExistException;
import com.farag.ultimate.exceptions.BadRequestException;
import com.farag.ultimate.exceptions.NotFoundException;

public interface CrudService<T>{
    T insert(T object) throws AlreadyExistException, BadRequestException;
    T read(Long id) throws NotFoundException;
    Boolean delete(Long id);
    T update(T object) throws NotFoundException;
}
