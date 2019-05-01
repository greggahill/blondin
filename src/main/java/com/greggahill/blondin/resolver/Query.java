package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.greggahill.blondin.model.Entry;
import com.greggahill.blondin.model.Member;
import com.greggahill.blondin.model.Organization;
import com.greggahill.blondin.repository.EntryRepository;
import com.greggahill.blondin.repository.MemberRepository;
import com.greggahill.blondin.repository.OrganizationRepository;

import javax.persistence.Id;
import java.util.List;

public class Query implements GraphQLQueryResolver {
    private EntryRepository entryRepository;
    private MemberRepository memberRepository;
    private OrganizationRepository organizationRepository;

    public Query(OrganizationRepository organizationRepository, MemberRepository bookRepository, EntryRepository entryRepository) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = bookRepository;
        this.entryRepository = entryRepository;
    }

    public Iterable<Entry> findAllEntries() {
        return entryRepository.findAll();
    }

    public Iterable<Entry> findAllEntriesByMemberID(Long member_id) {
        return entryRepository.findAllEntriesByMemberID(member_id);
    }

    public int getAvailablePointsByMemberID(Long member_id) {
        List<Entry> entries = entryRepository.findAllEntriesByMemberID(member_id);
        int availablePoints=0;
        for (Entry e: entries) {
            availablePoints += e.getPoints();
        }
        return availablePoints;

    }

    public Iterable<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Iterable<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    public long countEntries() {
        return entryRepository.count();
    }

    public long countMembers() {
        return memberRepository.count();
    }

    public long countOrganizations() {
        return organizationRepository.count();
    }
}
