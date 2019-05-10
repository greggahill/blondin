package com.greggahill.blondin;

import com.greggahill.blondin.exceptions.GraphQLErrorAdapter;

import com.greggahill.blondin.model.Member;
import com.greggahill.blondin.model.Organization;
import com.greggahill.blondin.repository.*;
import com.greggahill.blondin.resolver.EntryResolver;
import com.greggahill.blondin.resolver.MemberResolver;
import com.greggahill.blondin.resolver.Mutation;
import com.greggahill.blondin.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class BlondinApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlondinApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                List<GraphQLError> e = new ArrayList<>();
                e.addAll(clientErrors);
                e.addAll(serverErrors);
                return e;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }

    @Bean
    public EntryResolver entryResolver(MemberRepository memberRepository) {
        return new EntryResolver(memberRepository);
    }

    @Bean
    public MemberResolver memberResolver(OrganizationRepository organizationRepository) {
        return new MemberResolver(organizationRepository);
    }

    @Bean
    public Query query(UserRepository userRepository, LoginRepository loginRepository,
                       OrganizationRepository organizationRepository, MemberRepository memberRepository,
                       EntryRepository entryRepository) {
        return new Query(userRepository, loginRepository, organizationRepository, memberRepository, entryRepository);
    }

    @Bean
    public Mutation mutation(UserRepository userRepository, OrganizationRepository organizationRepository,
                             MemberRepository memberRepository, EntryRepository entryRepository) {
        return new Mutation(userRepository, organizationRepository, memberRepository, entryRepository);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, OrganizationRepository organizationRepository, MemberRepository memberRepository) {
        return (args) -> {
            Organization organization = new Organization("GreggCo");
            organizationRepository.save(organization);

            memberRepository.save(new Member("Gregg", "Hill", organization));
        };
    }
}
