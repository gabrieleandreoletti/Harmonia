package org.elis.service.definition;

import org.elis.dto.GenreDto;
import org.elis.dto.LabelDto;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;

public interface LabelService {
	void insert(LabelDto g) throws InsertFailureException, EntityAlreadyExistException;
}
