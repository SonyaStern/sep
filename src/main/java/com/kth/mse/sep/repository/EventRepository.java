package com.kth.mse.sep.repository;

import com.kth.mse.sep.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
