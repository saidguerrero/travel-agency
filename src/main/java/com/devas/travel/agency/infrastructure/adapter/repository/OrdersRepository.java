package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(Long id);

    Optional<Orders> findByReservationNumber(String reservationNumber);

    List<Orders> findAllBySalesPersonUserIdOrderByOrderIdDesc(int userId);

    Page<Orders> findBySalesPersonUserIdOrderByOrderIdDesc(int userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT :#{#pageable.pageNumber}, :#{#pageable.pageSize} ",
            countQuery = "SELECT count(*) FROM orders",
            nativeQuery = true)
    Page<Orders> findOrderById( Pageable pageable);

    @Query(value = "SELECT * FROM orders where supplier_id =:userid  ORDER BY order_id DESC LIMIT :#{#pageable.pageNumber}, :#{#pageable.pageSize} ",
            nativeQuery = true)
    Page<Orders> findOrderByIdUser(int userid, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE (reservation_number LIKE %:search% OR full_name LIKE %:search% )  AND supplier_id =:userId ORDER BY order_id DESC" +
            " LIMIT :#{#pageable.pageNumber}, :#{#pageable.pageSize} ",
            nativeQuery = true)
    Page<Orders> findByReservationAndNameByIdUser(String search, int userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE (reservation_number LIKE %:search% OR full_name LIKE %:search% )  ORDER BY order_id DESC" +
            " LIMIT :#{#pageable.pageNumber}, :#{#pageable.pageSize} ",
            nativeQuery = true)
    Page<Orders> findByReservationAndName(String search, Pageable pageable);

    List<Orders> findByReservationNumberLikeAndSalesPersonUserId(String word, int userId);

    List<Orders> findByFullNameLikeAndSalesPersonUserId(String word, int userId);

    List<Orders> findByReservationNumberLike(String word);

    List<Orders> findByFullNameLike(String word);

}
