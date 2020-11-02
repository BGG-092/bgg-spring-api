package me.bgg.spring.demospringapi.events.repository;

import me.bgg.spring.demospringapi.events.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {
    @Query(value = "select a.id,a.basePrice from Event a")
    List<Event> findAllWithEvent();

}
