package com.capstone.client.order.domain.repository.custom.impl;

import com.capstone.client.order.domain.model.entity.Coupon;
import com.capstone.client.order.domain.repository.custom.CouponRepoCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CouponRepoImpl implements CouponRepoCustom {
    @PersistenceContext
    EntityManager em;

    public List<Coupon> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq = cb.createQuery(Coupon.class);
        Root<Coupon> from = cq.from(Coupon.class);
        return em.createQuery(cq).getResultList();
    }

}
