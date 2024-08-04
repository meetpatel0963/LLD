package com.meetpatel.bookmyshow.provider;


import com.meetpatel.bookmyshow.model.Seat;
import com.meetpatel.bookmyshow.model.Show;

import java.util.List;

public interface SeatLockProvider {

    void lockSeats(Show show, List<Seat> seat, String user);
    void unlockSeats(Show show, List<Seat> seat, String user);
    boolean validateLock(Show show, Seat seat, String user);

    List<Seat> getLockedSeats(Show show);
}
