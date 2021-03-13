package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.statistics.CreatorYearStatistics;
import com.hvt.booking_lux.model.statistics.ResObjectYearStatistics;
import com.hvt.booking_lux.model.statistics.ResObjectMonthlyVisitorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUnitResObjectCreator(User creator);

    @Query(value = "select extract(month from r.reservation_date) as month, count(ro.id) as num, sum(DATEDIFF(day, r.from_date, r.to_date) * r.price_per_night) as total" +
            " from Reservation as r join Unit as u on u.id = r.unit_id join RES_OBJECT as ro on ro.id = u.res_object_id where ro.creator_username=?1" +
            " and ?2 = extract(year from r.reservation_date) group by month", nativeQuery = true)
    List<CreatorYearStatistics> findAnnualPropertyReservationCount(String creator, int year);

    @Query(value = "select ro.id, ro.name as name,extract(month from r.reservation_date) as month, count(ro.id) as num, sum(DATEDIFF(day, r.from_date, r.to_date) * r.price_per_night) as total" +
            " from Reservation as r " +
            "join Unit as u on u.id = r.unit_id " +
            "join RES_OBJECT as ro on ro.id = u.res_object_id where ro.creator_username=?1" +
            " and ?2 = extract(year from r.reservation_date) and ro.id = ?3" +
            " group by ro.id, month,name", nativeQuery = true)
    List<ResObjectYearStatistics> findAnnualReservationCountForProperty(String creator, int year, long id);

    @Query(value = "select ro.id, ro.name as name,extract(month from r.from_date) as month, count(ro.id) as num, sum(DATEDIFF(day, r.from_date, r.to_date) * r.price_per_night) as total" +
            " from Reservation as r " +
            "join Unit as u on u.id = r.unit_id " +
            "join RES_OBJECT as ro on ro.id = u.res_object_id where ro.creator_username=?1" +
            " and ?2 = extract(year from r.reservation_date) and ro.id = ?3" +
            " group by ro.id, month,name", nativeQuery = true)
    List<ResObjectMonthlyVisitorCount> findMonthlyVisitors(String creator, int year, long id);

    List<Reservation> findAllByUser(User user);
}
