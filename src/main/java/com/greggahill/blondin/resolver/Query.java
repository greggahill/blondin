package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.greggahill.blondin.exceptions.AccountLockedException;
import com.greggahill.blondin.exceptions.UserNotFoundException;
import com.greggahill.blondin.model.*;
import com.greggahill.blondin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Query implements GraphQLQueryResolver {
    private EntryRepository entryRepository;
    private MemberRepository memberRepository;
    private OrganizationRepository organizationRepository;
    private UserRepository userRepository;
    private LoginRepository loginRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Query(UserRepository userRepository, LoginRepository loginRepository, OrganizationRepository organizationRepository,
                 MemberRepository bookRepository, EntryRepository entryRepository) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = bookRepository;
        this.entryRepository = entryRepository;
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
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

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
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

    public long countUsers() {
        return userRepository.count();
    }

    public User loginUser(String userName, String password) {
        Login login = loginRepository.findUserByUserName(userName);

        if ((login != null) && login.isLocked()) {
            throw new AccountLockedException("The login is locked", userName);
        }

        if (login == null) {
            login = new Login(userName, bCryptPasswordEncoder.encode(password), 0,
                    System.currentTimeMillis(), false);
        }

        User user = userRepository.findUserByUserName((userName));

        //  user has never been added but we still want to capture the login attempt
        if (user == null) {
            if (!bCryptPasswordEncoder.matches(password, login.getPassword())) { login.fail(); }  // may have fat fingered password
            loginRepository.save(login);
            throw new UserNotFoundException("The username or password are incorrect", userName);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (!bCryptPasswordEncoder.matches(password, login.getPassword())) { login.fail(); }  // may have fat fingered password
            loginRepository.save(login);
            throw new UserNotFoundException("The username or password are incorrect", userName);
        }
        return user;
    }

    public long countOrganizations() {
        return organizationRepository.count();
    }
}
