package com.greggahill.blondin.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.greggahill.blondin.model.Entry;
import com.greggahill.blondin.model.Member;
import com.greggahill.blondin.repository.MemberRepository;


public class EntryResolver implements GraphQLResolver<Entry> {
   private MemberRepository memberRepository;

   public EntryResolver(MemberRepository memberRepository) {
       this.memberRepository = memberRepository;
   }

   public Member getMember(Entry entry) {
       return memberRepository.findOne(entry.getMember().getId());
   }

}
