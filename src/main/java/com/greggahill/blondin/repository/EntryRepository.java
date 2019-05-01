package com.greggahill.blondin.repository;

import com.greggahill.blondin.model.Entry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepository  extends CrudRepository<Entry, Long> {
/*
    @Query("SELECT e from Entry e WHERE e.member.id = :member_id")
    List<Entry> findAllEntriesByMemberID(@Param("member_id") Long member_id );
 */
    @Query("SELECT e from Entry e WHERE e.member.id = :member_id")
    List<Entry> findAllEntriesByMemberID(@Param("member_id") Long member_id );
}
