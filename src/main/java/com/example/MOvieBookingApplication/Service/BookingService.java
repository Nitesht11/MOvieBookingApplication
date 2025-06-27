package com.example.MOvieBookingApplication.Service;

import com.example.MOvieBookingApplication.DTO.BookingDTO;
import com.example.MOvieBookingApplication.Entity.Booking;
import com.example.MOvieBookingApplication.Entity.BookingStatus;
import com.example.MOvieBookingApplication.Entity.Show;
import com.example.MOvieBookingApplication.Entity.User;
import com.example.MOvieBookingApplication.Respository.BookingRepository;
import com.example.MOvieBookingApplication.Respository.ShowRepository;
import com.example.MOvieBookingApplication.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;
    public Booking  createbooking (BookingDTO bookingDTO){

        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(()->new RuntimeException("show not found"));
        if(! isSeatsAvailable(show.getId(),bookingDTO.getNumberOfSeats())){
            throw  new RuntimeException("Not enough Seats Available");
        }
         if (bookingDTO.getSeatNumbers().size()!= bookingDTO.getNumberOfSeats()){
             throw new RuntimeException(("seat Numbers and numbers must be the same"));
         }

         validateDuplicateSeats(show.getId(),bookingDTO.getSeatNumbers() );

         User user= userRepository.findById(bookingDTO.getUserId())
                 .orElseThrow(()->new RuntimeException("user not found"));

         Booking booking=  new Booking();
         booking.setUser(user);
         booking.setShow(show);
         booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
         booking.setSeatNumbers(bookingDTO.getSeatNumbers());
         booking.setPrice(calculateTotalAmount(show.getPrice(),bookingDTO.getNumberOfSeats()));
         booking.setBookingTime(LocalDateTime.now());
         booking.setBookingStatus(BookingStatus.PENDING);

         return  bookingRepository.save(booking);

    }

    public List<Booking> getUserBookings(Long userId){
         return bookingRepository.findByUser_Id(userId);
    }

    public List<Booking> getShowBookings(Long showId){
        return bookingRepository.findByShow_Id(showId);
    }


    public Booking confirmBooking ( Long  bookingId){
          Booking booking = bookingRepository.findById(bookingId)             // method to get booking//
                  .orElseThrow(()->new RuntimeException("Booking not Found"));


          if (booking.getBookingStatus() != BookingStatus.PENDING){
               throw new RuntimeException("booking is not in pending Status");
          }

          booking.setBookingStatus(BookingStatus.CONFIRMED);

          return  bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId){

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new RuntimeException("Booking not Found"));

        validateCancellation(booking);

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return  bookingRepository.save(booking);
    }

        public void validateCancellation (Booking booking){

          LocalDateTime showTime=  booking.getShow().getShowTime();
          LocalDateTime deadLineTime= showTime.minusHours(2);

          if(LocalDateTime.now().isAfter(deadLineTime)){
              throw new RuntimeException("cannot  cancel the booking");
        }
            if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
                throw  new RuntimeException((" booking already cancelled"));
            }
    }

     public boolean isSeatsAvailable (long showid, Integer numberOfSeats){

        Show show = showRepository.findById(showid).orElseThrow(()-> new RuntimeException("Show not Found"));

        int bookedSeats = show.getBookings().stream()
                .filter(booking->booking.getBookingStatus()!= BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();
        return (show.getTheater().getTheaterCapacity()-bookedSeats)>= numberOfSeats;

     }

     public void validateDuplicateSeats(long showId, List<String> seatNumbers){
           Show show =showRepository.findById(showId)
                   .orElseThrow(()->new RuntimeException("show not Found"));

         Set<String> occupiedSeats = show.getBookings().stream()
                 .filter(b->b.getBookingStatus()!= BookingStatus.CANCELLED)
                 .flatMap(b->b.getSeatNumbers().stream())
                 .collect(Collectors.toSet());

         List<String> duplicateSeats =seatNumbers.stream()
                 .filter(occupiedSeats::contains)
                 .collect(Collectors.toList());

         if(!duplicateSeats.isEmpty()){
             throw new RuntimeException("Seats are Already booked");
         }
     }
     public Double calculateTotalAmount(Double price, Integer numberOfSeats){
        return price * numberOfSeats;

     }

     public List<Booking>  getBookingByStatus(BookingStatus bookingStatus){
        return  bookingRepository.findByBookingStatus(bookingStatus);
     }
}


