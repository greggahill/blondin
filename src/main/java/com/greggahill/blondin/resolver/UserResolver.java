package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.greggahill.blondin.model.Organization;
import com.greggahill.blondin.model.User;
import com.greggahill.blondin.repository.OrganizationRepository;


public class UserResolver implements GraphQLResolver<User> {
   private OrganizationRepository organizationRepository;

   public UserResolver(OrganizationRepository organizationRepository) {
       this.organizationRepository = organizationRepository;
   }

   public Organization getOrganization(User user) {
       return organizationRepository.findOne(user.getOrganization().getId());
   }
}
