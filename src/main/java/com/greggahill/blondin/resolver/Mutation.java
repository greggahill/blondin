package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.greggahill.blondin.exceptions.DateFormatException;
import com.greggahill.blondin.model.Entry;
import com.greggahill.blondin.model.Member;
import com.greggahill.blondin.model.Organization;
import com.greggahill.blondin.repository.EntryRepository;
import com.greggahill.blondin.repository.MemberRepository;
import com.greggahill.blondin.repository.OrganizationRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Mutation implements GraphQLMutationResolver {
    private MemberRepository memberRepository;
    private OrganizationRepository organizationRepository;
    private EntryRepository entryRepository;


    public Mutation(OrganizationRepository organizationRepository, MemberRepository memberRepository, EntryRepository entryRepository) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.entryRepository = entryRepository;
    }

    public Organization newOrganization(String name) {
        Organization organization = new Organization();
        organization.setName(name);

        organizationRepository.save(organization);

        return organization;
    }

    public Member newMember(String firstName, String lastName, Long organizationId) {
        Member member = new Member();
        member.setOrganization(new Organization(organizationId));
        member.setFirstName(firstName);
        member.setLastName(lastName);

        memberRepository.save(member);

        return member;
    }

    public boolean deleteMember(Long id) {
        memberRepository.delete(id);
        return true;
    }

    public Entry newEntry(String datetime, String reason, int points, Long memberId) {
        Entry entry = new Entry();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        try {
            Date formattedDate = formatter.parse(datetime);
            entry.setDatetime(formattedDate);
        } catch (ParseException pe) {
            throw new DateFormatException("Invalid date format. Expected \"dd-MMM-yyyy HH:mm:ss\" ", entry.getId());
        }
        entry.setReason(reason);
        entry.setPoints(points);
        entry.setMember(new Member(memberId));

        entryRepository.save(entry);

        return entry;
    }

    public boolean deleteEntry(Long id) {
        entryRepository.delete(id);
        return true;
    }

}
