package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.greggahill.blondin.model.Member;
import com.greggahill.blondin.model.Organization;
import com.greggahill.blondin.repository.OrganizationRepository;


public class MemberResolver implements GraphQLResolver<Member> {
   private OrganizationRepository organizationRepository;

   public MemberResolver(OrganizationRepository organizationRepository) {
       this.organizationRepository = organizationRepository;
   }

   public Organization getOrganization(Member member) {
       return organizationRepository.findOne(member.getOrganization().getId());
   }
}
