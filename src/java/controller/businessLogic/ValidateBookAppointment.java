
package controller.businessLogic;


import java.time.LocalDate;


// Lớp xử lý logic xác thực khi đặt lịch hẹn (appointment).
public class ValidateBookAppointment {

    /**
     * Kiểm tra ngày đặt lịch phải là ngày trong tương lai.
     *
     * @param date Ngày cần kiểm tra
     * @throws IllegalArgumentException Nếu ngày không phải là ngày trong tương lai
     */
    public static void validateDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (!date.isAfter(today)) {
            throw new IllegalArgumentException("Bạn chỉ có thể đặt lịch trong tương lai.");
        }
    }
}