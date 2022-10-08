package com.onlinejava.project.bookstore.admin.adapters.database;

import com.onlinejava.project.bookstore.common.domain.entity.Member;
import com.onlinejava.project.bookstore.admin.application.ports.output.MemberRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;
import java.util.Optional;

@Bean
public class DatabaseMemberRepository extends DatabaseRepository<Member> implements MemberRepository {
    @Inject
    protected JdbcTemplate template;

    @Override
    public List<Member> findAll() {
        return template.list("select * from member", Member.class);
    }

    @Override
    public Optional<Member> findByUserName(String userName) {
        String sql = String.format("select * from member where user_name = '%s'", userName);
        return template.get(sql, Member.class);
    }

    @Override
    public List<Member> findActiveMembers() {
        return template.list("select * from member where active = true", Member.class);
    }

    @Override
    public boolean add(Member member) {
        String sql = String.format(
                "insert into member (user_name, email, address, grade, total_point, active) " +
                " values ('%s', '%s', '%s', '%s', %d, '%s') ",
                member.getUserName(),
                member.getEmail(),
                member.getAddress(),
                member.getGrade(),
                member.getTotalPoint(),
                member.isActive()
        );
        return template.insert(sql, 1);
    }

    @Override
    public void initData() {
        String memberDDL = "CREATE TABLE IF NOT EXISTS member (" +
                "user_name varchar(255), " +
                "email varchar(255), " +
                "address varchar(255), " +
                "total_point numeric, " +
                "grade varchar(255), " +
                "active boolean," +
                "primary key (user_name)" +
                ")";
        template.execute(memberDDL);
    }
}
