package com.onlinejava.project.bookstore.admin.adapters.out.database;

import com.onlinejava.project.bookstore.common.domain.entity.Purchase;
import com.onlinejava.project.bookstore.admin.application.ports.output.PurchaseRepository;
import com.onlinejava.project.bookstore.core.factory.Bean;
import com.onlinejava.project.bookstore.core.factory.Inject;

import java.util.List;

@Bean
public class DatabasePurchaseRepository extends DatabaseRepository<Purchase> implements PurchaseRepository {
    @Inject
    protected JdbcTemplate template;

    @Override
    public List<Purchase> findAll() {
        return template.list("select * from purchase", Purchase.class);
    }

    @Override
    public List<Purchase> findByCustomer(String customerName) {
        String sql = String.format("select * from purchase where customer = '%s'", customerName);
        return template.list(sql, Purchase.class);
    }

    @Override
    public boolean add(Purchase purchase) {
        String sql = String.format(
                "insert into purchase (title, customer, number_of_purchase, total_price, point) " +
                "values ('%s', '%s', %d, %d, %d)",
                purchase.getTitle(),
                purchase.getCustomer(),
                purchase.getNumberOfPurchase(),
                purchase.getTotalPrice(),
                purchase.getPoint()
        );
        return template.insert(sql, 1);
    }

    @Override
    public void initData() {
        String purchaseDDL = "CREATE TABLE IF NOT EXISTS purchase (" +
                "title varchar(255), " +
                "customer varchar(255), " +
                "number_of_purchase numeric, " +
                "total_price numeric, " +
                "point numeric, " +
                "primary key (title, customer)" +
                ")";
        template.execute(purchaseDDL);
    }
}
